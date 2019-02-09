package modele.metier;

/** 
 * La classe de l'intersection.
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public abstract class Intersection {
	protected long id;
	protected double latitude;
	protected double longitude;
	
	/**
	 * Constructeur d'intersection par identifiant(sans latitude et longitude)
	 * @param id : identifiant de l'intersection
	 */
	public Intersection(long id) {
		this.id = id;
	}
	
	/**
	 * Constructeur param�tre de l'intersection(avec latitude et longitude)
	 * @param id : identifiant de l'intersection
	 * @param latitude : latitude de l'intersection
	 * @param longitude  :longitude de l'intersection
	 */
	public Intersection(long id, double latitude, double longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Surcharge de la methode toString de la classe Intersection.
	 */
	@Override
	public abstract String toString();
	
	/**
	 * M�thode pour obtenir la dur�e de l'intersection correspondante
	 * @return la dur�e de cette intersection
	 */
	public abstract int getDuree();

	/**
	 * M�thode pour obtenir l'id de l'intersection
	 * @return id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * M�thode pour renseigner l'id de l'intersection
	 * @param id : une id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * M�thode pour obtenir la latitude de l'intersection
	 * @return latitude de l'intersection
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * M�thode pour mettre la latitude de l'intersection
	 * @param latitude : une latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * M�thode pour obtenir la longitude de l'intersection
	 * @return longitude de l'intersection
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * M�thode pour mettre la longitude de l'intersection
	 * @param longitude : une longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Surcharge de la m�thode equals entre intersections
	 * @param obj : l'intersection � comparer
	 * @return l'�galite entre deux intersections
	 */
	@Override
	public boolean equals(Object obj) {
		Intersection unInter = (Intersection)obj;
		return (this.id == unInter.getId());
	}
	
	/**
	 * Surcharge de la m�thode hashCode de la classe Intersection
	 */
	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}

	/**
	 * Surcharge de la m�thode equals entre intersections sur l'identifiant
	 * @param unId : Id � comparer
	 * @return : l'�galite entre deux intersections
	 */
	public boolean equals(long unId) {
		boolean retour = false;
		if(this.id == unId) {
			retour = true;
		}
		return retour;	
	}
}
