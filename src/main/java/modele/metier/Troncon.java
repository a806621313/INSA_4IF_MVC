package modele.metier;

/** 
 * La classe du tron�on
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
	 * Constructeur parametre du tron�on
	 * @param destination : la destination du tron�on
	 * @param origine : l'origine du tron�on
	 * @param longueur : la longueur du tron�on
	 * @param nomRue : le nom de la rue du tron�on
	 */
	public Troncon(IntersectionNormal destination, IntersectionNormal origine, double longueur, String nomRue) {
		this.destination = destination;
		this.longueur = longueur;
		this.nomRue = nomRue;
		this.origine = origine;
	}

	/**
	 * Surcharge de la m�thode toString
	 */
	@Override
	public String toString() {
		return "Troncon [destination=" + destination + ", longueur=" + longueur + ", nomRue=" + nomRue + ", origine="
				+ origine + "]";
	}

	/**
	 * M�thode pour obtenir la destination de tron�on
	 * @return IntersectionNormal qui est la destination du tron�on
	 */
	public IntersectionNormal getDestination() {
		return destination;
	}

	
	/**
	 * M�thode pour obtenir la longueur du tron�on
	 * @return la longueur du tron�on
	 */
	public double getLongueur() {
		return longueur;
	}

	
	/**
	 * M�thode pour obtenir le nom de rue du tron�on
	 * @return le nom de rue
	 */
	public String getNomRue() {
		return nomRue;
	}

	
	/**
	 * M�thode pour obtenir l'origine de tron�on
	 * @return IntersectionNormal qui est l'origine du tron�on
	 */
	public IntersectionNormal getOrigine() {
		return origine;
	}

	
	
}
