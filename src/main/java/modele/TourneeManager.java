package modele;

import java.util.ArrayList;
import java.util.Observable;

import controleur.Controleur;
import modele.algo.AEtoile;
import modele.algo.OutilTSP;
import modele.algo.TSPSimple;
import modele.metier.Chemin;
import modele.metier.DemandeLivraison;
import modele.metier.Entrepot;
import modele.metier.Intersection;
import modele.metier.IntersectionNormal;
//import modele.metier.IntersectionNormal;
import modele.metier.Plan;
import modele.metier.PointLivraison;
import modele.metier.Tournee;
import modele.metier.Troncon;

/**
 * La classe du TourneeManager
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class TourneeManager extends Observable {

	private ArrayList<Tournee> listeTournees;
	private int TIME_LIMITE;
	private int tourneeChangedIndex;
	private int tourneeSupprimerIndex;
	private int tourneeAjouterIndex;

	/**
	 * Constructeur de la classe TourneeManager
	 */
	public TourneeManager() {
		listeTournees = new ArrayList<Tournee>();
		TIME_LIMITE = 10000;
		tourneeChangedIndex = 0;
		tourneeSupprimerIndex = 0;
		tourneeAjouterIndex = 0;
	}

	/**
	 * Méthode pour effacer toutes les tournées
	 */
	public void clear() {
		this.listeTournees.clear();
	}
	
	/**
	 * Méthode pour fixer la limite du temps de calcul
	 * @param unTemps : une limite du temps
	 */
	public void setTimeLimite(int unTemps) {
		TIME_LIMITE = unTemps;
	}

	/**
	 * Méthode pour obtenir la limite de temps
	 * @return la limite du temps actuel du TourneeManager
	 */
	public int getTimeLimite() {
		return TIME_LIMITE;
	}
	
	/**
	 * Méthode pour notifier les observateurs à rafraîchir leur contenu (sur la tournée et la demande de livraison)
	 */
	public void notifyVue() {
		setChanged();
		notifyObservers("TourneesEtDemandeLivraison");
	}

	/**
	 * Méthode pour retourner l'index de la tournée changée
	 * @return l'index de la tournée changée
	 */
	public int getTourneeChangedIndex() {
		return tourneeChangedIndex;
	}

	/**
	 * Méthode pour retourner l'index de la tournée où nous avons ajouté un point de
	 * livraison après le déplacement
	 * @return l'index de la tournée ajoutée
	 */
	public int getTourneeAjouterIndex() {
		return tourneeAjouterIndex;
	}

	/**
	 * Méthode pour retourner l'index de la tournée ou nous avons supprimé un point
	 * de livraison après le déplacement
	 * @return l'index de la tournée supprimée
	 */
	public int getTourneeSupprimerIndex() {
		return tourneeSupprimerIndex;
	}

	/**
	 * Méthode pour calculer les tournées selon le mode choisi par l'utilisateur
	 * 
	 * @param demande : la demande de livraison
	 * @param unPlan :  le plan de la ville
	 * @param nbLivreur : Nombre de livreurs
	 * @param mode : mode choisi par l'utilisateur (1 sans clustering, 2 pour clustering)
	 * @throws Exception : l'exception lorsqu'on calcule les tournées selon mode choisi
	 */
	public void calculerLesTourneesSelonMode(DemandeLivraison demande, Plan unPlan, int nbLivreur, int mode)
			throws Exception {
		if (mode == 1) {
			calculerLesTournees(demande, unPlan, nbLivreur);
		} else {
			calculerLesTourneesClustering(demande, unPlan, nbLivreur);
		}
	}

	/**
	 * Méthode pour calculer les tournées selon le nombre de livreur (version sans clustering)
	 * 
	 * @param demande : la demande de livraison
	 * @param unPlan :  le plan de la ville
	 * @param nbLivreur : nombre de livreurs
	 * @throws Exception : l'exception lorsqu'on calcule les tournées
	 */
	public void calculerLesTournees(DemandeLivraison demande, Plan unPlan, int nbLivreur) throws Exception {
		clear();
		ArrayList<Intersection> intersectionsDemande = OutilTSP.getAllIntersectionDemande(demande);
		// Initialisation des paramètres importants
		int length = intersectionsDemande.size();
		int[][] cout = new int[length][length];
		Chemin[][] pccs = new Chemin[length][length];
		int[] duree = new int[length];
		OutilTSP.initialisationTabCoutEtChemin(unPlan, intersectionsDemande, cout, pccs, length);
		OutilTSP.intialisationTabDuree(intersectionsDemande, duree, length);
		TSPSimple tsp = new TSPSimple();
		Integer[] meilleureSolution = new Integer[length + nbLivreur];
		tsp.chercheSolution(TIME_LIMITE, length, cout, duree, nbLivreur);
		if (tsp.getTempsLimiteAtteint()) {
			// Prendre la meilleureSolution calculee
			for (int i = 0; i < length + nbLivreur - 1; i++) {
				meilleureSolution[i] = tsp.getMeilleureSolution(i);
			}
			meilleureSolution[length + nbLivreur - 1] = 0;

			ArrayList<Integer> positionEntrepots = trouverPositionsEntrepot(meilleureSolution);
			int nbPositionEntrepots = positionEntrepots.size() - 1;
			for (int i = 0; i < nbPositionEntrepots; i++) {
				int positionStart = positionEntrepots.get(i);
				int positionEnd = positionEntrepots.get(i + 1);
				ArrayList<Chemin> listeSolution = new ArrayList<Chemin>();
				for (int j = positionStart; j < positionEnd; j++) {
					listeSolution.add(pccs[meilleureSolution[j]][meilleureSolution[j + 1]]);
				}
				Tournee solution = new Tournee(listeSolution);
				this.listeTournees.add(solution);
			}
			setChanged();
			notifyObservers("Alert Temps");
		} else {
			// Prendre la meilleureSolution calculee
			for (int i = 0; i < length + nbLivreur - 1; i++) {
				meilleureSolution[i] = tsp.getMeilleureSolution(i);
			}
			meilleureSolution[length + nbLivreur - 1] = 0;

			ArrayList<Integer> positionEntrepots = trouverPositionsEntrepot(meilleureSolution);
			int nbPositionEntrepots = positionEntrepots.size() - 1;
			for (int i = 0; i < nbPositionEntrepots; i++) {
				int positionStart = positionEntrepots.get(i);
				int positionEnd = positionEntrepots.get(i + 1);
				ArrayList<Chemin> listeSolution = new ArrayList<Chemin>();
				for (int j = positionStart; j < positionEnd; j++) {
					listeSolution.add(pccs[meilleureSolution[j]][meilleureSolution[j + 1]]);
				}
				Tournee solution = new Tournee(listeSolution);
				this.listeTournees.add(solution);
			}
			setChanged();
			notifyObservers("Tournees");
		}

	}

	/**
	 * Méthode pour calculer les tournées selon le nombre de livreur (version clustering)
	 * 
	 * @param nbLivreur : Nombre de livreurs
	 * @param demande : la demande de livraison
	 * @param unPlan : le plan de la ville
	 * @throws Exception : l'exception lors on calcule les tournées en clustering
	 */
	public void calculerLesTourneesClustering(DemandeLivraison demande, Plan unPlan, int nbLivreur) throws Exception {
		clear();
		ArrayList<Intersection> intersectionsDemande = OutilTSP.getAllIntersectionDemande(demande);
		// Initialisation des parametres importants
		int length = intersectionsDemande.size();
		int[][] cout = new int[length][length];
		Chemin[][] pccs = new Chemin[length][length];
		int[] duree = new int[length];
		OutilTSP.initialisationTabCoutEtChemin(unPlan, intersectionsDemande, cout, pccs, length);
		OutilTSP.intialisationTabDuree(intersectionsDemande, duree, length);
		TSPSimple tsp = new TSPSimple();
		int[] nbPointLivraisonParLivreur = tsp.clusteringNbPointLivraisonParLivreurNaive(nbLivreur, length - 1);
		ArrayList<int[]> groupes = tsp.clusteringPointLivraisonNaive(length, cout, nbPointLivraisonParLivreur);
		int nbGroupes = groupes.size();
		boolean alert = false;
		for (int i = 0; i < nbGroupes; i++) {
			ArrayList<Intersection> temp = new ArrayList<Intersection>();
			temp.add(intersectionsDemande.get(0));
			int[] tempGroupes = groupes.get(i);
			for (int j = 0; j < tempGroupes.length; j++) {
				temp.add(intersectionsDemande.get(tempGroupes[j]));
			}
			int tempLength = temp.size();
			int[][] tempCout = new int[tempLength][tempLength];
			Chemin[][] tempPccs = new Chemin[tempLength][tempLength];
			int[] tempDuree = new int[tempLength];
			OutilTSP.initialisationTabCoutEtChemin(unPlan, temp, tempCout, tempPccs, tempLength);
			OutilTSP.intialisationTabDuree(temp, tempDuree, tempLength);
			TSPSimple tempTsp = new TSPSimple();
			Integer[] meilleureSolution = new Integer[tempLength + 1];
			tempTsp.chercheSolution(TIME_LIMITE, tempLength, tempCout, tempDuree, 1);
			if (tempTsp.getTempsLimiteAtteint()) {
				alert = true;
				for (int p = 0; p < tempLength; p++) {
					meilleureSolution[p] = tempTsp.getMeilleureSolution(p);
				}
				meilleureSolution[tempLength] = 0;

				ArrayList<Integer> positionEntrepots = trouverPositionsEntrepot(meilleureSolution);
				int nbPositionEntrepots = positionEntrepots.size() - 1;
				for (int p = 0; p < nbPositionEntrepots; p++) {
					int positionStart = positionEntrepots.get(p);
					int positionEnd = positionEntrepots.get(p + 1);
					ArrayList<Chemin> listeSolution = new ArrayList<Chemin>();
					for (int j = positionStart; j < positionEnd; j++) {
						listeSolution.add(tempPccs[meilleureSolution[j]][meilleureSolution[j + 1]]);
					}
					Tournee solution = new Tournee(listeSolution);
					this.listeTournees.add(solution);
				}
			} else {
				for (int p = 0; p < tempLength; p++) {
					meilleureSolution[p] = tempTsp.getMeilleureSolution(p);
				}
				meilleureSolution[tempLength] = 0;

				ArrayList<Integer> positionEntrepots = trouverPositionsEntrepot(meilleureSolution);
				int nbPositionEntrepots = positionEntrepots.size() - 1;
				for (int p = 0; p < nbPositionEntrepots; p++) {
					int positionStart = positionEntrepots.get(p);
					int positionEnd = positionEntrepots.get(p + 1);
					ArrayList<Chemin> listeSolution = new ArrayList<Chemin>();
					for (int j = positionStart; j < positionEnd; j++) {
						listeSolution.add(tempPccs[meilleureSolution[j]][meilleureSolution[j + 1]]);
					}
					Tournee solution = new Tournee(listeSolution);
					this.listeTournees.add(solution);
				}
			}

		}
		if (alert) {
			setChanged();
			notifyObservers("Alert Temps");
		} else {
			setChanged();
			notifyObservers("Tournees");
		}

	}
	
	/**
	 * Méthode pour trouver les positions des entrepôts dans la meilleure solution trouvée
	 * (méthode utilisée pour une solution trouvée avec l'algo sans clustering)
	 * (existence des entrepôts fictifs)
	 * @param : meilleureSolution meilleureSolution trouvée
	 * @return positions d'entrepôt dans la meilleure solution
	 */
	private ArrayList<Integer> trouverPositionsEntrepot(Integer[] meilleureSolution) throws Exception {
		ArrayList<Integer> retour = new ArrayList<Integer>();
		for (Integer i = 0; i < meilleureSolution.length; i++) {
			if (meilleureSolution[i] == 0) {
				retour.add(i);
			}
		}
		return retour;
	}

	/**
	 * Méthode pour avoir la liste des tournées stockées dans le tourneeManager
	 * @return la liste des tournées calculées
	 */
	public ArrayList<Tournee> getListeTournees() {
		return listeTournees;
	}

	/**
	 * Méthode pour mettre une liste des tournées dans le tourneeManager.  
	 * @param listeTournees : une liste des tournes.
	 */
	public void setListeTournees(ArrayList<Tournee> listeTournees) {
		this.listeTournees = listeTournees;
	}

	/**
	 * Méthode pour ajouter un point de livraison dans une tournee spécifiée qui notifie les vues a rafraîchir
	 * 
	 * @param idDepart : id du point de livraison après lequel nous voulons ajouter le nouveau point de livraison
	 * @param idNouvelle : id du nouveau point de livraison
	 * @param duree : la durée du point ajouté
	 * @throws Exception : l'exception lorsqu'on ajoute un point de livraison
	 */
	public void ajouterPointLivraison(long idDepart, long idNouvelle, int duree) throws Exception {
		int find = 0;
		int index = 0;
		int posChemin = 0;
		if (idDepart == Controleur.getInstance().getMaDemande().getIdEntrepot()) {
			find = 1;
			index = 1;
			posChemin = -1;
		} else {
			for (Tournee t : listeTournees) {
				if (find == 0) {
					posChemin = 0;
					ArrayList<Chemin> tempChemin = t.getListeChemins();
					for (Chemin c : tempChemin) {
						Intersection dest = c.getIntersectionDest();
						if (dest.equals(idDepart)) {
							find = 1;
							break;
						}
						posChemin++;
					}
				} else {
					break;
				}
				index++;
			}
		}
		if (find == 1) {
			index--;
			posChemin++;
			Intersection depart = listeTournees.get(index).getListeChemins().get(posChemin).getIntersectionDepart();
			Intersection oldDest = listeTournees.get(index).getListeChemins().get(posChemin).getIntersectionDest();
			IntersectionNormal tempNouvellePoint = Controleur.getInstance().getMonPlan()
					.getIntersectionNormal(idNouvelle);
			Controleur.getInstance().getMaDemande().ajouterPointLivraisonMetier(idNouvelle,
					tempNouvellePoint.getLatitude(), tempNouvellePoint.getLongitude(), duree);
			Intersection newDest = Controleur.getInstance().getMaDemande().getPointLivraisonParId(idNouvelle);
			ArrayList<Intersection> interdepartNewDest = AEtoile.getInstance().algoAEtoile(depart, newDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Intersection> inetrnewDestOldDest = AEtoile.getInstance().algoAEtoile(newDest, oldDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Troncon> tronDepartNewDest = AEtoile.getInstance().traductionTrajet(interdepartNewDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Troncon> tronNewDestOldDest = AEtoile.getInstance().traductionTrajet(inetrnewDestOldDest,
					Controleur.getInstance().getMonPlan());
			Chemin departNewDest = new Chemin(interdepartNewDest, tronDepartNewDest);
			Chemin newDestOldDest = new Chemin(inetrnewDestOldDest, tronNewDestOldDest);
			int newDestDuree = (int) (departNewDest.getCout() / 15000 * 60 * 60);
			int oldDestDuree = (int) (newDestOldDest.getCout() / 15000 * 60 * 60);
			departNewDest.setDuree(newDestDuree);
			newDestOldDest.setDuree(oldDestDuree);
			listeTournees.get(index).getListeChemins().remove(posChemin);
			listeTournees.get(index).getListeChemins().add(posChemin, departNewDest);
			listeTournees.get(index).getListeChemins().add(posChemin + 1, newDestOldDest);
			tourneeChangedIndex = index;
			setChanged();
			notifyObservers("UniqueTournee");
		} else {
			System.out.println("Point Livraison Introuvable");
			Exception e = new Exception();
			throw e;
		}
	}

	/**
	 * Méthode pour ajouter un point de livraison dans une tournée spécifiée qui ne modifie pas l'affichage
	 * 
	 * @param idDepart : id du point de livraison après lequel nous voulons ajouter le nouveau point de livraison
	 * @param idNouvelle : id du nouveau point de livraison
	 * @param duree : la durée du point ajouté
	 * @throws Exception :  l'exception lorsqu'on ajoute un point de livraison
	 */
	public void ajouterPointLivraisonMetier(long idDepart, long idNouvelle, int duree) throws Exception {
		int find = 0;
		int index = 0;
		int posChemin = 0;
		if (idDepart == Controleur.getInstance().getMaDemande().getIdEntrepot()) {
			find = 1;
			index = 1;
			posChemin = -1;
		} else {
			for (Tournee t : listeTournees) {
				if (find == 0) {
					posChemin = 0;
					ArrayList<Chemin> tempChemin = t.getListeChemins();
					for (Chemin c : tempChemin) {
						Intersection dest = c.getIntersectionDest();
						if (dest.equals(idDepart)) {
							find = 1;
							break;
						}
						posChemin++;
					}
				} else {
					break;
				}
				index++;
			}
		}
		if (find == 1) {
			index--;
			posChemin++;
			Intersection depart = listeTournees.get(index).getListeChemins().get(posChemin).getIntersectionDepart();
			Intersection oldDest = listeTournees.get(index).getListeChemins().get(posChemin).getIntersectionDest();
			IntersectionNormal tempNouvellePoint = Controleur.getInstance().getMonPlan()
					.getIntersectionNormal(idNouvelle);
			Controleur.getInstance().getMaDemande().ajouterPointLivraisonMetier(idNouvelle,
					tempNouvellePoint.getLatitude(), tempNouvellePoint.getLongitude(), duree);
			Intersection newDest = Controleur.getInstance().getMaDemande().getPointLivraisonParId(idNouvelle);
			ArrayList<Intersection> interdepartNewDest = AEtoile.getInstance().algoAEtoile(depart, newDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Intersection> inetrnewDestOldDest = AEtoile.getInstance().algoAEtoile(newDest, oldDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Troncon> tronDepartNewDest = AEtoile.getInstance().traductionTrajet(interdepartNewDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Troncon> tronNewDestOldDest = AEtoile.getInstance().traductionTrajet(inetrnewDestOldDest,
					Controleur.getInstance().getMonPlan());
			Chemin departNewDest = new Chemin(interdepartNewDest, tronDepartNewDest);
			Chemin newDestOldDest = new Chemin(inetrnewDestOldDest, tronNewDestOldDest);
			int newDestDuree = (int) (departNewDest.getCout() / 15000 * 60 * 60);
			int oldDestDuree = (int) (newDestOldDest.getCout() / 15000 * 60 * 60);
			departNewDest.setDuree(newDestDuree);
			newDestOldDest.setDuree(oldDestDuree);
			listeTournees.get(index).getListeChemins().remove(posChemin);
			listeTournees.get(index).getListeChemins().add(posChemin, departNewDest);
			listeTournees.get(index).getListeChemins().add(posChemin + 1, newDestOldDest);
		} else {
			System.out.println("Point Livraison Introuvable");
			Exception e = new Exception();
			throw e;
		}
	}

	/**
	 * Méthode pour supprimer un point de livraison qui notifie les vues a rafraîchir
	 * @param id : id du point de livraison à supprimer
	 * @return true si la tournée du point de livraison a été supprimée 
	 * (cas d'une tournée qui a juste un point de livraison), false sinon
	 * @throws Exception si erreur durant la suppression
	 */
	public boolean supprimerPointLivraison(long id) throws Exception {
		int find = 0;
		int index = 0;
		int posChemin = 0;
		boolean supprime = false;
		for (Tournee t : listeTournees) {
			if (find == 0) {
				posChemin = 0;
				ArrayList<Chemin> tempChemin = t.getListeChemins();
				for (Chemin c : tempChemin) {
					Intersection dest = c.getIntersectionDest();
					if (dest.equals(id)) {
						find = 1;
						break;
					}
					posChemin++;
				}
			} else {
				break;
			}
			index++;
		}
		if (find == 1) {
			index--;
			int posOneEnleve = posChemin;
			int posTwoEnleve = posChemin + 1;
			Intersection newDepart = listeTournees.get(index).getListeChemins().get(posOneEnleve)
					.getIntersectionDepart();
			Intersection newDest = listeTournees.get(index).getListeChemins().get(posTwoEnleve).getIntersectionDest();
			ArrayList<Intersection> internewDepartNewDest = AEtoile.getInstance().algoAEtoile(newDepart, newDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Troncon> tronNewDestNewDest = AEtoile.getInstance().traductionTrajet(internewDepartNewDest,
					Controleur.getInstance().getMonPlan());
			Chemin newDepartNewDest = new Chemin(internewDepartNewDest, tronNewDestNewDest);
			int newDuree = (int) (newDepartNewDest.getCout() / 15000 * 60 * 60);
			newDepartNewDest.setDuree(newDuree);
			Controleur.getInstance().getMaDemande().supprimerPointLivraisonMetier(id);
			if (newDepart instanceof Entrepot && newDest instanceof Entrepot) {
				listeTournees.remove(index);
				tourneeChangedIndex = index;
				supprime = true;
				setChanged();
				notifyObservers("SupprimerTournee");
			} else {
				listeTournees.get(index).getListeChemins().remove(posTwoEnleve);
				listeTournees.get(index).getListeChemins().remove(posOneEnleve);
				listeTournees.get(index).getListeChemins().add(posChemin, newDepartNewDest);
				tourneeChangedIndex = index;
				setChanged();
				notifyObservers("UniqueTournee");
			}
			return supprime;
		} else {
			System.out.println("Point Livraison Introuvable");
			Exception e = new Exception();
			throw e;
		}
	}

	/**
	 *  Méthode pour supprimer un point de livraison qui ne notifie pas les vues à rafraîchir
	 * @param id: id du point de livraison à supprimer
	 * @return true si la tournée du point de livraison se situe a été supprimée
	 * (cas d'une tournee qui a juste un point de livraison), false sinon.
	 * @throws Exception si erreur durant la suppression.
	 */
	public boolean supprimerPointLivraisonMetier(long id) throws Exception {
		int find = 0;
		int index = 0;
		int posChemin = 0;
		boolean supprime = false;
		for (Tournee t : listeTournees) {
			if (find == 0) {
				posChemin = 0;
				ArrayList<Chemin> tempChemin = t.getListeChemins();
				for (Chemin c : tempChemin) {
					Intersection dest = c.getIntersectionDest();
					if (dest.equals(id)) {
						find = 1;
						break;
					}
					posChemin++;
				}
			} else {
				break;
			}
			index++;
		}
		if (find == 1) {
			index--;
			int posOneEnleve = posChemin;
			int posTwoEnleve = posChemin + 1;
			Intersection newDepart = listeTournees.get(index).getListeChemins().get(posOneEnleve)
					.getIntersectionDepart();
			Intersection newDest = listeTournees.get(index).getListeChemins().get(posTwoEnleve).getIntersectionDest();
			ArrayList<Intersection> internewDepartNewDest = AEtoile.getInstance().algoAEtoile(newDepart, newDest,
					Controleur.getInstance().getMonPlan());
			ArrayList<Troncon> tronNewDestNewDest = AEtoile.getInstance().traductionTrajet(internewDepartNewDest,
					Controleur.getInstance().getMonPlan());
			Chemin newDepartNewDest = new Chemin(internewDepartNewDest, tronNewDestNewDest);
			int newDuree = (int) (newDepartNewDest.getCout() / 15000 * 60 * 60);
			newDepartNewDest.setDuree(newDuree);
			Controleur.getInstance().getMaDemande().supprimerPointLivraisonMetier(id);
			if (newDepart instanceof Entrepot && newDest instanceof Entrepot) {
				supprime = true;
				listeTournees.remove(index);
			} else {
				listeTournees.get(index).getListeChemins().remove(posTwoEnleve);
				listeTournees.get(index).getListeChemins().remove(posOneEnleve);
				listeTournees.get(index).getListeChemins().add(posChemin, newDepartNewDest);
			}
			return supprime;
		} else {
			System.out.println("Point Livraison Introuvable");
			Exception e = new Exception();
			throw e;
		}
	}
	
	/**
	 * Méthode pour déplacer un point de livraison après le point de livraison ayant l'id idApresDeplacer 
	 * et puis supprimer ce point
	 * @param idADeplacer : id du point de livraison à déplacer
	 * @param idApresDeplacer : id du point de livraison après lequel on met le point de livraison à déplacer
	 * @throws Exception si le déplacement se passe au sein d'une meme tournée
	 */
	public void deplacerPointLivraison(long idADeplacer, long idApresDeplacer) throws Exception {
		// Trouver oe se situe le point e deplacer dans la liste des tournees
		int findADplacer = 0;
		int indexADeplacer = 0;
		int posCheminADeplacer = 0;
		for (Tournee t : listeTournees) {
			if (findADplacer == 0) {
				posCheminADeplacer = 0;
				ArrayList<Chemin> tempChemin = t.getListeChemins();
				for (Chemin c : tempChemin) {
					Intersection dest = c.getIntersectionDest();

					if (dest.equals(idADeplacer)) {
						findADplacer = 1;
						break;
					}
					posCheminADeplacer++;
				}
			} else {
				break;
			}
			indexADeplacer++;
		}
		// Trouver oe se situe le point apres lequel nous voulons mettre le point trouve
		// au-dessus dans la liste des tournees
		int findApresDplacer = 0;
		int indexApresDeplacer = 0;
		int posCheminApresDeplacer = 0;
		for (Tournee t : listeTournees) {
			if (findApresDplacer == 0) {
				posCheminApresDeplacer = 0;
				ArrayList<Chemin> tempChemin = t.getListeChemins();
				for (Chemin c : tempChemin) {
					Intersection dest = c.getIntersectionDest();

					if (dest.equals(idApresDeplacer)) {
						findApresDplacer = 1;
						break;
					}
					posCheminApresDeplacer++;
				}
			} else {
				break;
			}
			indexApresDeplacer++;
		}

		if (indexADeplacer == indexApresDeplacer) {
//			Deplacement irrealisable
			Exception e = new Exception();
			throw e;
		} else {
			// Effectuer le deplacement
			if (findADplacer == 1 && findApresDplacer == 1) {
				// Ajouter le point de livraison e deplacer dans la nouvelle tournee
				indexApresDeplacer--;
				posCheminApresDeplacer++;
				Intersection depart = listeTournees.get(indexApresDeplacer).getListeChemins()
						.get(posCheminApresDeplacer).getIntersectionDepart();
				Intersection oldDest = listeTournees.get(indexApresDeplacer).getListeChemins()
						.get(posCheminApresDeplacer).getIntersectionDest();
				Intersection nouveauDest = Controleur.getInstance().getMaDemande().getLivraisons().get(idADeplacer);
				ArrayList<Intersection> interdepartNouveauDest = AEtoile.getInstance().algoAEtoile(depart, nouveauDest,
						Controleur.getInstance().getMonPlan());
				ArrayList<Intersection> inetrnouveauDestOldDest = AEtoile.getInstance().algoAEtoile(nouveauDest,
						oldDest, Controleur.getInstance().getMonPlan());
				ArrayList<Troncon> tronDepartNouveauDest = AEtoile.getInstance()
						.traductionTrajet(interdepartNouveauDest, Controleur.getInstance().getMonPlan());
				ArrayList<Troncon> tronNouveauDestOldDest = AEtoile.getInstance()
						.traductionTrajet(inetrnouveauDestOldDest, Controleur.getInstance().getMonPlan());
				Chemin departNouveauDest = new Chemin(interdepartNouveauDest, tronDepartNouveauDest);
				Chemin nouveauDestOldDest = new Chemin(inetrnouveauDestOldDest, tronNouveauDestOldDest);
				int nouveauDestDuree = (int) (departNouveauDest.getCout() / 15000 * 60 * 60);
				int oldDestDuree = (int) (nouveauDestOldDest.getCout() / 15000 * 60 * 60);
				departNouveauDest.setDuree(nouveauDestDuree);
				nouveauDestOldDest.setDuree(oldDestDuree);
				listeTournees.get(indexApresDeplacer).getListeChemins().remove(posCheminApresDeplacer);
				listeTournees.get(indexApresDeplacer).getListeChemins().add(posCheminApresDeplacer, departNouveauDest);
				listeTournees.get(indexApresDeplacer).getListeChemins().add(posCheminApresDeplacer + 1,
						nouveauDestOldDest);
				tourneeAjouterIndex = indexApresDeplacer;

				// Supprimer le point de livraison e deplacer
				boolean supprimerTourneeADeplacer = false;
				indexADeplacer--;
				int posOneEnleve = posCheminADeplacer;
				int posTwoEnleve = posCheminADeplacer + 1;
				Intersection newDepart = listeTournees.get(indexADeplacer).getListeChemins().get(posOneEnleve)
						.getIntersectionDepart();
				Intersection newDest = listeTournees.get(indexADeplacer).getListeChemins().get(posTwoEnleve)
						.getIntersectionDest();
				ArrayList<Intersection> internewDepartNewDest = AEtoile.getInstance().algoAEtoile(newDepart, newDest,
						Controleur.getInstance().getMonPlan());
				ArrayList<Troncon> tronNewDestNewDest = AEtoile.getInstance().traductionTrajet(internewDepartNewDest,
						Controleur.getInstance().getMonPlan());
				Chemin newDepartNewDest = new Chemin(internewDepartNewDest, tronNewDestNewDest);
				int newDuree = (int) (newDepartNewDest.getCout() / 15000 * 60 * 60);
				newDepartNewDest.setDuree(newDuree);
				if (newDepart instanceof Entrepot && newDest instanceof Entrepot) {
					supprimerTourneeADeplacer = true;
					listeTournees.remove(indexADeplacer);
					tourneeSupprimerIndex = indexADeplacer;
				} else {
					listeTournees.get(indexADeplacer).getListeChemins().remove(posTwoEnleve);
					listeTournees.get(indexADeplacer).getListeChemins().remove(posOneEnleve);
					listeTournees.get(indexADeplacer).getListeChemins().add(posCheminADeplacer, newDepartNewDest);
					tourneeSupprimerIndex = indexADeplacer;
				}

				if (supprimerTourneeADeplacer) {
					setChanged();
					notifyObservers("DeplacementSupprimerTournee");
				} else {
					setChanged();
					notifyObservers("DeplacementSansSupprimerTournee");
				}
			} else {
				System.out.println("Point Livraison Introuvable");
				Exception e = new Exception();
				throw e;
			}
		}
	}

	/**
	 * Créer une tournée qui a juste une livraison
	 * @param tempP : le point qu'on ajoute dans le plan
	 * @param prePoint : l'entrepôt de la demande de livraison
	 * @throws Exception si erreur durant l'ajout
	 */
	public void creerTourneeJusteUnLivraison(PointLivraison tempP, Intersection prePoint) throws Exception {
		Controleur.getInstance().getMaDemande().ajouterPoint(tempP.getId(), tempP);
		ArrayList<Intersection> interUn = AEtoile.getInstance().algoAEtoile(prePoint, tempP,
				Controleur.getInstance().getMonPlan());
		ArrayList<Troncon> tronUn = AEtoile.getInstance().traductionTrajet(interUn,
				Controleur.getInstance().getMonPlan());
		ArrayList<Intersection> interDeux = AEtoile.getInstance().algoAEtoile(tempP, prePoint,
				Controleur.getInstance().getMonPlan());
		ArrayList<Troncon> tronDeux = AEtoile.getInstance().traductionTrajet(interDeux,
				Controleur.getInstance().getMonPlan());
		Chemin cheminUn = new Chemin(interUn, tronUn);
		Chemin cheminDeux = new Chemin(interDeux, tronDeux);
		ArrayList<Chemin> liste = new ArrayList<Chemin>();
		liste.add(cheminUn);
		liste.add(cheminDeux);
		Tournee temp = new Tournee(liste);
		listeTournees.add(temp);
	}
	
	/**
	 * Obtenir l'id du point de livraison étant avant le point de livraison passé en paramètre dans une tournée
	 * @param id : id d'un point de livraison
	 * @return point livraison trouvé
	 */
	public Intersection getPrePointLivraisonId(long id) {
		int find = 0;
		Intersection retour = null;
		for (Tournee t : listeTournees) {
			if (find == 0) {
				ArrayList<Chemin> tempChemin = t.getListeChemins();
				for (Chemin c : tempChemin) {
					Intersection depart = c.getIntersectionDepart();
					Intersection dest = c.getIntersectionDest();

					if (dest.equals(id)) {
						find = 1;
						retour = depart;
						return retour;
					}
				}
			} else {
				break;
			}
		}
		return retour;
	}



}
