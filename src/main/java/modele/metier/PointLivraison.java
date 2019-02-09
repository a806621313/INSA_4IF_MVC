package modele.metier;

import org.joda.time.DateTime;

/**
 * La classe point de livraison
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class PointLivraison extends Intersection {
	protected DateTime heureDepart;
	protected DateTime heureArrivee;
	private int duree;

	/**
	 * Constructeur paramètre du point de livraison sans durée
	 * 
	 * @param id : l'identifiant du point de livraison
	 * @param latitude :  la latitude du point de livraison
	 * @param longitude : la longitude du point de livraison
	 */
	public PointLivraison(long id, double latitude, double longitude) {
		super(id, latitude, longitude);
		this.heureDepart = null;
		this.heureArrivee = null;
	}

	/**
	 * Constructeur paramètre du point de livraison avec une durée
	 * 
	 * @param id : l'identifiant du point de livraison
	 * @param latitude :  la latitude du point de livraison
	 * @param longitude : la longitude du point de livraison
	 * @param uneDuree :  la durée du point de livraison
	 */
	public PointLivraison(long id, double latitude, double longitude, int uneDuree) {
		super(id, latitude, longitude);
		this.heureDepart = null;
		this.heureArrivee = null;
		duree = uneDuree;
	}

	/**
	 * Méthode pour obtenir la durée de ce point de livraison
	 * @see modele.metier.Intersection
	 */
	@Override
	public int getDuree() {
		return duree;
	}

	/**
	 * Surcharge de la méthode toString de la classe Intersection
	 */
	@Override
	public String toString() {
		return "PointLivraison " + " duree=" + duree + "s, id=" + id + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

}