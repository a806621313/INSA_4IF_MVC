package modele.metier;

/** 
 * La classe du tronçon
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class Troncon {
	
	protected IntersectionNormal destination;
	protected double longueur;
	protected String nomRue;
	protected IntersectionNormal origine;
	
	/**
	 * Constructeur parametre du tronçon
	 * @param destination : la destination du tronçon
	 * @param origine : l'origine du tronçon
	 * @param longueur : la longueur du tronçon
	 * @param nomRue : le nom de la rue du tronçon
	 */
	public Troncon(IntersectionNormal destination, IntersectionNormal origine, double longueur, String nomRue) {
		this.destination = destination;
		this.longueur = longueur;
		this.nomRue = nomRue;
		this.origine = origine;
	}

	/**
	 * Surcharge de la méthode toString
	 */
	@Override
	public String toString() {
		return "Troncon [destination=" + destination + ", longueur=" + longueur + ", nomRue=" + nomRue + ", origine="
				+ origine + "]";
	}

	/**
	 * Méthode pour obtenir la destination de tronçon
	 * @return IntersectionNormal qui est la destination du tronçon
	 */
	public IntersectionNormal getDestination() {
		return destination;
	}

	
	/**
	 * Méthode pour obtenir la longueur du tronçon
	 * @return la longueur du tronçon
	 */
	public double getLongueur() {
		return longueur;
	}

	
	/**
	 * Méthode pour obtenir le nom de rue du tronçon
	 * @return le nom de rue
	 */
	public String getNomRue() {
		return nomRue;
	}

	
	/**
	 * Méthode pour obtenir l'origine de tronçon
	 * @return IntersectionNormal qui est l'origine du tronçon
	 */
	public IntersectionNormal getOrigine() {
		return origine;
	}

	
	
}
