package modele.metier;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * La classe de l'entrepôt
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class Entrepot extends Intersection {

	private DateTime heureDepart;

	/**
	 * Constructeur de l'entrepôt
	 * 
	 * @param id        : identifiant de l'entrepôt
	 * @param latitude  : latitude de l'entrepôt
	 * @param longitude : longitude de l'entrepôt
	 * @param heure     : heure du premier passage par l'entrepôt
	 * @param minute    : minute du premier passage par l'entrepôt
	 * @param seconde   : seconde du premier passage par l'entrepôt
	 */
	@SuppressWarnings("deprecation")
	public Entrepot(long id, double latitude, double longitude, int heure, int minute, int seconde) {
		super(id, latitude, longitude);
		Date sys = new Date();
		this.heureDepart = new DateTime(sys.getYear() + 1900, sys.getMonth() + 1, sys.getDate(), heure, minute,
				seconde);

	}

	@Override
	public String toString() {
		return "Entrepot [heureDepart=" + heureDepart + ", id=" + id + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

	/**
	 * Méthode qui retourne l'heure du départ
	 * @return l'heure depart
	 */
	public DateTime getHeureDeaprt() {
		return heureDepart;
	}

	/**
	 * @see modele.metier.Intersection
	 * @return durée
	 */
	public int getDuree() {
		return 0;
	}

}
