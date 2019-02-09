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
	 * Constructeur param�tre du point de livraison sans dur�e
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
	 * Constructeur param�tre du point de livraison avec une dur�e
	 * 
	 * @param id : l'identifiant du point de livraison
	 * @param latitude :  la latitude du point de livraison
	 * @param longitude : la longitude du point de livraison
	 * @param uneDuree :  la dur�e du point de livraison
	 */
	public PointLivraison(long id, double latitude, double longitude, int uneDuree) {
		super(id, latitude, longitude);
		this.heureDepart = null;
		this.heureArrivee = null;
		duree = uneDuree;
	}

	/**
	 * M�thode pour obtenir la dur�e de ce point de livraison
	 * @see modele.metier.Intersection
	 */
	@Override
	public int getDuree() {
		return duree;
	}

	/**
	 * Surcharge de la m�thode toString de la classe Intersection
	 */
	@Override
	public String toString() {
		return "PointLivraison " + " duree=" + duree + "s, id=" + id + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

}