package modele.algo;

import modele.services.exceptions.AlgoException;

/** 
 * La classe du TSP
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public interface TSP {
		
	/**
	 * @return true si chercheSolution s'est terminée parce que la limite de temps avait été atteinte, avant d'avoir pu explorer tout l'espace de recherche
	 */
	public Boolean getTempsLimiteAtteint();
	
	/**
	 * Cherche un circuit de durée minimale passant par chaque sommet (compris entre 0 et nbSommets-1)
	 * @param tpsLimite : limite (en ms) sur le temps d'exécution de chercheSolution
	 * @param nbSommets : nombre de sommets du graphe
	 * @param cout : cout[i][j] = durée pour aller de i a j, avec 0 <= i <= nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = durée pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @param nbLivreur : nombre de livreurs
	 * @throws AlgoException :  l'exception lors on cherche solution
	 */
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree, int nbLivreur) throws AlgoException;
	
	/**
	 * @param i la position dans la solution calculée
	 * @return le sommet visité en i-ème position dans la solution calculée par chercheSolution
	 */
	public Integer getMeilleureSolution(int i);
	
	/** 
	 * @return la durée de la solution calculée par chercheSolution
	 */
	public int getCoutMeilleureSolution();
}
