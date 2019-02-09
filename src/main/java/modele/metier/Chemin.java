package modele.metier;

import java.util.ArrayList;

/** 
 * La classe du chemin
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class Chemin {
	
	private ArrayList <Intersection> listeIntersections;
	private ArrayList <Troncon> listeTroncons;
	private int duree;
	
	/**
	 * Constructeur paramètre du chemin
	 * @param uneListeIntersections : une liste d'intersections
	 * @param uneListeTroncons : une liste des tronçons
	 */
	public Chemin(ArrayList <Intersection> uneListeIntersections, ArrayList <Troncon> uneListeTroncons) {
		this.listeIntersections = uneListeIntersections;
		this.listeTroncons = uneListeTroncons;
	}
	
	//Getter et Setter
	public ArrayList<Intersection> getListeIntersections() {
		return listeIntersections;
	}
	
	public ArrayList<Troncon> getListeTroncons(){
		return listeTroncons;
	}
	
	public int getDuree() {
		return duree;
	}
	
	public void setDuree(int uneDuree) {
		this.duree = uneDuree;
	}
	
	/**
	 * Méthode pour obtenir l'origine du chemin
	 * @return Intersection qui étant l'origine du chemin
	 */
	public Intersection getIntersectionDepart() {
		return listeIntersections.get(0);
	}
	
	/**
	 * Méthode pour obtenir la destination du chemin
	 * @return Intersection qui est la destination du chemin
	 */
	public Intersection getIntersectionDest() {
		return listeIntersections.get(listeIntersections.size()-1);
	}
	
	/**
	 * Méthode pour obtenir le cot du chemin.
	 * @return un double(la distance) qui représente le coût du chemin
	 */
	public double getCout() {
		double somme = 0.0;
		for(Troncon troncon : listeTroncons) {
			somme += troncon.getLongueur();
		}
		return somme;
	}
}

