package modele.metier;

/**
 * La classe demande d'une intersection normale et non abstraite
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class IntersectionNormal extends Intersection {

	/**
	 * Constructeur d'une intersection normale avec identifiant
	 * 
	 * @param id identifiant de l'intersection
	 */
	public IntersectionNormal(long id) {
		super(id);
	}

	/**
	 * Constructeur paramètre d'une intersection normale
	 * 
	 * @param id        identifiant de l'intersection
	 * @param latitude  latitude de l'intersection
	 * @param longitude longitude de l'intersection
	 */
	public IntersectionNormal(long id, double latitude, double longitude) {
		super(id, latitude, longitude);
	}

	@Override
	public String toString() {
		return "IntersectionNormal [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	/**
	 * Méthode pour obtenir la durée de l'intersection correspondante
	 * 
	 * @see modele.metier.Intersection
	 */
	public int getDuree() {
		return 0;
	}

}
