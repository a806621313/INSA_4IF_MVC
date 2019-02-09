package modele.metier;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * La classe de l'entrep�t
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class Entrepot extends Intersection {

	private DateTime heureDepart;

	/**
	 * Constructeur de l'entrep�t
	 * 
	 * @param id        : identifiant de l'entrep�t
	 * @param latitude  : latitude de l'entrep�t
	 * @param longitude : longitude de l'entrep�t
	 * @param heure     : heure du premier passage par l'entrep�t
	 * @param minute    : minute du premier passage par l'entrep�t
	 * @param seconde   : seconde du premier passage par l'entrep�t
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
	 * M�thode qui retourne l'heure du d�part
	 * @return l'heure depart
	 */
	public DateTime getHeureDeaprt() {
		return heureDepart;
	}

	/**
	 * @see modele.metier.Intersection
	 * @return dur�e
	 */
	public int getDuree() {
		return 0;
	}

}
