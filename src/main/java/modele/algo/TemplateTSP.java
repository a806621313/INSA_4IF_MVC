package modele.algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * La classe du template du TSP
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public abstract class TemplateTSP implements TSP {

	private Integer[] meilleureSolution;
	private int coutMeilleureSolution = 0;
	private Boolean tempsLimiteAtteint;

	public Boolean getTempsLimiteAtteint() {
		return tempsLimiteAtteint;
	}

	/**
	 * Méthode qui calcule une répartition des points de livraison sur les livreurs
	 * @param nbLivreur         : nombre de livreur
	 * @param nbPointLivraisons : nombre total des points de livraisons
	 * @return le clustering des nombres de points de livraison pour les livreurs
	 */
	public int[] clusteringNbPointLivraisonParLivreurNaive(int nbLivreur, int nbPointLivraisons) {
		int[] resultat = new int[nbLivreur];
		int nbDeBase = nbPointLivraisons / nbLivreur;

		if (nbPointLivraisons % nbDeBase == 0 && nbPointLivraisons / nbDeBase == nbLivreur && nbDeBase != 1) {
			for (int i = 0; i < nbLivreur; i++) {
				resultat[i] = nbDeBase;
			}
		} else {
			int nbBasePlusOne = nbDeBase + 1;
			int nbPartieMaxBasePlusOne = nbPointLivraisons / nbBasePlusOne;
			int nbActuelBasePlusOne = nbPartieMaxBasePlusOne;
			int somme = nbActuelBasePlusOne * (nbBasePlusOne) + (nbLivreur - nbActuelBasePlusOne) * nbDeBase;
			while (somme != nbPointLivraisons) {
				nbActuelBasePlusOne--;
				somme = nbActuelBasePlusOne * nbBasePlusOne + (nbLivreur - nbActuelBasePlusOne) * nbDeBase;
			}
			for (int i = 0; i < nbActuelBasePlusOne; i++) {
				resultat[i] = nbBasePlusOne;
			}
			for (int i = nbActuelBasePlusOne; i < nbLivreur; i++) {
				resultat[i] = nbDeBase;
			}
		}

		return resultat;
	}

	/**
	 * Méthode qui calcule un clustering des points de livraisons
	 * 
	 * @param nbSommets    : nombre de sommets
	 * @param nbParLivreur : liste des nombres de points de livraison par livreur
	 * @param cout         : les coûts entre les différents points de livraison
	 * @return liste des groupes des points de livraison
	 */
	public ArrayList<int[]> clusteringPointLivraisonNaive(int nbSommets, int[][] cout, int[] nbParLivreur) {
		ArrayList<int[]> retour = new ArrayList<int[]>();
		ArrayList<Integer> lesIntersections = new ArrayList<Integer>();
		for (int i = 1; i < nbSommets; i++) {
			lesIntersections.add(i);
		}
		for (int i = 0; i < nbParLivreur.length; i++) {
			int nbTotal = nbParLivreur[i];
			int[] tempGroupe = new int[nbTotal];
			Integer interCourant = lesIntersections.get(0);
			lesIntersections.remove(interCourant);
			PriorityQueue<Paire> queue = new PriorityQueue<Paire>(1, new Comparator<Paire>() {
				public int compare(Paire p1, Paire p2) {
					if (p1.getCout() < p2.getCout()) {
						return -1;
					}
					if (p1.getCout() > p2.getCout()) {
						return 1;
					}
					return 0;
				}
			});
			int length = lesIntersections.size();
			for (int j = 0; j < length; j++) {
				Paire temp = new Paire(cout[interCourant][lesIntersections.get(j)], lesIntersections.get(j));
				queue.add(temp);
			}
			tempGroupe[0] = interCourant;
			for (int j = 1; j < nbTotal; j++) {
				Paire paireCourant = queue.poll();
				tempGroupe[j] = paireCourant.getNumeroLivraison();
				lesIntersections.remove((Integer) paireCourant.getNumeroLivraison());
			}
			retour.add(tempGroupe);
		}
		return retour;
	}

	/**
	 * Méthode pour trouver une solution
	 * 
	 * @param tpsLimite: le temps limite pour trouver une solution saisi par l'utilisateur
	 * @param nbSommets: le nombre des sommets
	 * @param cout: le coût entre différents points de livraison
	 * @param duree: duree[i] = durée pour visiter le sommet i
	 * @param nbLivreur: le nombre de livreurs
	 */
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree, int nbLivreur) {
		tempsLimiteAtteint = false;
		coutMeilleureSolution = Integer.MAX_VALUE;
		meilleureSolution = new Integer[nbSommets + nbLivreur - 1];
		ArrayList<Integer> nonVus = new ArrayList<Integer>();
		for (int i = 1; i < nbSommets; i++)
			nonVus.add(i);
		ArrayList<Integer> vus = new ArrayList<Integer>(nbSommets);
		vus.add(0); // le premier sommet visite est 0
		int[] nbPointLivraisonParLivreur = clusteringNbPointLivraisonParLivreurNaive(nbLivreur, nbSommets - 1);
		branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tpsLimite,
				nbPointLivraisonParLivreur, 0, nbLivreur - 1, 0);
	}

	public Integer getMeilleureSolution(int i) {
		if ((meilleureSolution == null) || (i < 0) || (i >= meilleureSolution.length))
			return null;
		return meilleureSolution[i];
	}

	public int getCoutMeilleureSolution() {
		return coutMeilleureSolution;
	}

	/**
	 * Méthode d'élaguage devant être redéfinie par les sous-classes du TemplateTSP
	 * 
	 * @param                            sommetCourant
	 * @param nonVus                     : tableau des sommets restant à visiter
	 * @param cout                       : cout[i][j] = durée pour aller de i a j
	 * @param duree                      : duree[i] = durée pour visiter le sommet
	 *                                   i.
	 * @param nbTourneeAvantDest         : nombre de tournées devant être
	 *                                   faites avant la derniere tournée
	 * @param tourneeFaite               : nombre de tournées déjà parcourues
	 * @param nbPointLivraisonParLivreur :tableau de nombre de point de livraison
	 *                                   par livreur
	 * @oaram compteurNbLivraisonsActuels : nombre de livraisons déjà faite par le
	 *        livreur actuel
	 * @return une borne inferieure du coût des permutations commençant par
	 *         sommetCourant. Contenant chaque sommet de nonVus exactement une fois
	 *         et terminant par le sommet 0
	 */
	protected abstract int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree,
			int nbTourneeAvantDest, int tourneeFaite, int[] nbPointLivraisonParLivreur,
			int compteurNbLivraisonsActuels);

	/**
	 * Méthode  définissant un itérateur devant être redéfini par les sous-classes de TemplateTSP
	 * 
	 * @param sommetCrt
	 * @param nonVus    : tableau des sommets restant à visiter
	 * @param cout      : cout[i][j] = durée pour aller de i a j, avec 0 <= i <
	 *                  nbSommets et 0 <= j < nbSommets
	 * @param duree     : duree[i] = durée pour visiter le sommet i, avec 0 <= i <
	 *                  nbSommets
	 * @return un itérateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout,
			int[] duree);

	/**
	 * Méthode définissant le patron (template) d'une résolution par separation et
	 * évaluation (branch and bound) du TSP
	 * 
	 * @param sommetCrt                  le dernier sommet visité
	 * @param nonVus                     la liste des sommets qui n'ont pas encore
	 *                                   été visités
	 * @param vus                        la liste des sommets visités (y compris
	 *                                   sommetCrt)
	 * @param coutVus                    la somme des coûts des arcs du chemin
	 *                                   passant par tous les sommets de vus + la
	 *                                   somme des durée des sommets de vus
	 * @param cout                       : cout[i][j] = durée pour aller de i a j,
	 *                                   avec 0 <= i < nbSommets et 0 <= j <
	 *                                   nbSommets
	 * @param duree                      : duree[i] = durée pour visiter le sommet
	 *                                   i, avec 0 <= i < nbSommets
	 * @param tpsDebut                   : moment où la resolution a commencé
	 * @param tpsLimite                  : limite de temps pour la resolution
	 * @param nbTourneeAvantDest         : nombre de tournées qui doivent être
	 *                                   faites avant la derniere tournée
	 * @param tourneeFaite               : nombre de tournées deja parcourues
	 * @param nbPointLivraisonParLivreur :tableau de nombre de point de livraison
	 *                                   par livreur
	 * @oaram compteurNbLivraisonsActuels : nombre de livraison déjà faite par le
	 *        livreur actuel
	 */
	void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout,
			int[] duree, long tpsDebut, int tpsLimite, int[] nbPointLivraisonParLivreur,
			int compteurNbLivraisonsActuels, int nbTourneeAvantDest, int tourneeFaite) {
		if (System.currentTimeMillis() - tpsDebut > tpsLimite) {
			tempsLimiteAtteint = true;
			return;
		}
		if (nonVus.size() == 0) { // tous les sommets ont ete visites
			coutVus += cout[sommetCrt][0];
			if (coutVus < coutMeilleureSolution) { // on a trouve une solution meilleure que meilleureSolution
				vus.toArray(meilleureSolution);
				coutMeilleureSolution = coutVus;
			}
		} else if (coutVus + bound(sommetCrt, nonVus, cout, duree, nbTourneeAvantDest, tourneeFaite,
				nbPointLivraisonParLivreur, compteurNbLivraisonsActuels) < coutMeilleureSolution) {
			if (tourneeFaite < nbTourneeAvantDest) {
				if (compteurNbLivraisonsActuels < nbPointLivraisonParLivreur[tourneeFaite]) {
					Iterator<Integer> it = iterator(sommetCrt, nonVus, cout, duree);
					while (it.hasNext()) {
						Integer prochainSommet = it.next();
						vus.add(prochainSommet);
						nonVus.remove(prochainSommet);
						branchAndBound(prochainSommet, nonVus, vus,
								coutVus + cout[sommetCrt][prochainSommet] + duree[prochainSommet], cout, duree,
								tpsDebut, tpsLimite, nbPointLivraisonParLivreur, compteurNbLivraisonsActuels + 1,
								nbTourneeAvantDest, tourneeFaite);
						vus.remove(prochainSommet);
						nonVus.add(prochainSommet);
					}
				} else {
					Integer entrepot = 0;
					vus.add(entrepot);
					branchAndBound(0, nonVus, vus, coutVus + cout[sommetCrt][0] + duree[0], cout, duree, tpsDebut,
							tpsLimite, nbPointLivraisonParLivreur, 0, nbTourneeAvantDest, tourneeFaite + 1);
					vus.remove(vus.size() - 1);
				}
			} else {
				Iterator<Integer> it = iterator(sommetCrt, nonVus, cout, duree);
				while (it.hasNext()) {
					Integer prochainSommet = it.next();
					vus.add(prochainSommet);
					nonVus.remove(prochainSommet);
					branchAndBound(prochainSommet, nonVus, vus,
							coutVus + cout[sommetCrt][prochainSommet] + duree[prochainSommet], cout, duree, tpsDebut,
							tpsLimite, nbPointLivraisonParLivreur, compteurNbLivraisonsActuels + 1, nbTourneeAvantDest,
							tourneeFaite);
					vus.remove(prochainSommet);
					nonVus.add(prochainSommet);
				}
			}
		}
	}
}