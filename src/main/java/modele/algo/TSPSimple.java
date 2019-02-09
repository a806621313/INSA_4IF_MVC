package modele.algo;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * La classe du simple TSP
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class TSPSimple extends TemplateTSP {
	/**
	 * @see TSP.
	 */
	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		return new IteratorSeq(nonVus, sommetCrt);
	}
	
	/**
	 * M�thode pour calculer une heuristique afin d'�laguer les branches
	 * @param sommetCourant : sommet actuel que nous sommes en train de explorer
	 * @param nonVus : la liste des intersections pas encore exploit�es
	 * @param duree : la liste des dur�es des points de livraison
	 * @param nbTourneeAvantDest : le nombre de tourn�e qui doit �tre fait avant atteindre la destination(Entrep�t)
	 * @param tourneeFaite : le nombre de tourn�e d�j� fait
	 * @param nbPointLivraisonParLivreur : le tableau qui contient le nombre des points de livraison pour chaque livreur
	 * @param compteurNbLivraisonsActuels : le nombre des points de livraison d�j� livr�s dans la tourn�e actuelle
	 */
	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree, int nbTourneeAvantDest, int tourneeFaite, int[] nbPointLivraisonParLivreur, int compteurNbLivraisonsActuels) {
		int min = Integer.MAX_VALUE;
		int nbNonVu = nonVus.size();
		int minVersDest = Integer.MAX_VALUE;
		for(Integer nonVu : nonVus) {
			if(cout[nonVu][0] < minVersDest) {
				minVersDest = cout[nonVu][0];
			}
		}
		for(Integer nonVu : nonVus) {
			if(cout[sommetCourant][nonVu] < min) {
				min = cout[sommetCourant][nonVu];
			}
		}
		for(int i = 0; i < nbNonVu; i++) {
			for(int j = 0; j < nbNonVu; j++) {
				if(i != j) {
					if(cout[nonVus.get(i)][nonVus.get(j)] < min) {
						min = cout[nonVus.get(i)][nonVus.get(j)];
					}
				}
			}
		}
		if(tourneeFaite < nbTourneeAvantDest) {
			if(compteurNbLivraisonsActuels == nbPointLivraisonParLivreur[tourneeFaite]) {
				if(cout[sommetCourant][0] < min) {
					min = cout[sommetCourant][0];
				}
			}
			if(minVersDest < min) {
				min = minVersDest;
			}
		}
		min = min * (nbNonVu + nbTourneeAvantDest - tourneeFaite) ;
		for(Integer nonVu : nonVus) {
			min += duree[nonVu];
		}
		min += duree[0] * (nbTourneeAvantDest - tourneeFaite);
		min += minVersDest;
		return min;
	}

}

