package modele.metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;

/**
 * La classe du Plan.
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class Plan extends Observable {

	private HashMap<Long, IntersectionNormal> intersectionNormals;
	private HashMap<Long, ArrayList<Troncon>> troncons;
	private double maxLong;
	private double minLong;
	private double maxLat;
	private double minLat;

	/**
	 * Constructeur par défaut du Plan
	 */
	public Plan() {
		super();
		intersectionNormals = new HashMap<Long, IntersectionNormal>();
		troncons = new HashMap<Long, ArrayList<Troncon>>();
	}

	/**
	 * Méthode permettant l'initialisation d'un plan
	 * 
	 * @param intersections : les intersections d'un plan
	 * @param troncons      : les troncons d'un plan
	 * @param maxLong       : la longitude maximale d'un plan
	 * @param minLong       : la longitude minimale d'un plan
	 * @param maxLat        : la latitude maximale d'un plan
	 * @param minLat        : la latitude minimale d'un plan
	 */
	public void initialiserPlan(HashMap<Long, IntersectionNormal> intersections,
			HashMap<Long, ArrayList<Troncon>> troncons, double maxLong, double minLong, double maxLat, double minLat) {
		if (this.intersectionNormals != null && this.troncons != null) {
			clear();
			System.out.println("Clear Plan");
		}
		this.intersectionNormals = intersections;
		this.troncons = troncons;
		this.maxLong = maxLong;
		this.minLong = minLong;
		this.maxLat = maxLat;
		this.minLat = minLat;
		setChanged();
		notifyObservers("Plan");
	}

	/**
	 * Méthode permettant de libérer les intersections et les troncons dans le plan
	 */
	public void clear() {
		this.intersectionNormals.clear();
		this.troncons.clear();
	}

	/**
	 * Méthode pour obtenir une intersection dans le plan par Id
	 * 
	 * @param id : l'id de l'intersection voulue
	 * @return IntersectionNormal trouvée
	 */
	public IntersectionNormal getIntersectionNormal(long id) {
		return this.intersectionNormals.get(id);
	}

	/**
	 * Méthode pour obtenir un troncon selon l'id de l'origine
	 * 
	 * @param origine : l'id de l'intersection étant l'origine
	 * @return la liste des tronçons trouvés
	 */
	public ArrayList<Troncon> getTronconsParOrigine(long origine) {
		return this.troncons.get(origine);
	}

	/**
	 * Méthode pour obtenir la map des intersections
	 * 
	 * @return le map des intersections
	 */
	public HashMap<Long, IntersectionNormal> getIntersectionNormals() {
		return intersectionNormals;
	}

	/**
	 * Méthode pour obtenir la collection des intersections
	 * 
	 * @return la collection des intersections
	 */
	public Collection<IntersectionNormal> getAllIntersectionNormals() {
		return intersectionNormals.values();
	}

	/**
	 * Méthode pour obtenir la collection des tronçons
	 * 
	 * @return la collection des tronçons
	 */
	public Collection<ArrayList<Troncon>> getAllTroncons() {
		return troncons.values();
	}

	/**
	 * Méthode pour obtenir le map des tronçons
	 * 
	 * @return le map des tronçons
	 */
	public HashMap<Long, ArrayList<Troncon>> getTroncons() {
		return troncons;
	}

	/**
	 * Méthode pour transformer la longitude en une coordonnée Y selon ce plan
	 * 
	 * @param longitude : une longitude
	 * @param largeur   : la largeur de la vue graphique
	 * @return la coordonnée Y calculée
	 */
	public double transformLongitude(double longitude, double largeur) {
		return (longitude - minLong) * largeur / (maxLong - minLong);
	}

	/**
	 * Méthode pour transformer la latitude en une coordonnée X selon ce plan
	 * 
	 * @param latitude : une latitude
	 * @param hauteur  : la hauteur de la vue graphique
	 * @return la coordonnée X calculée
	 */

	public double transformLatitude(double latitude, double hauteur) {
		return (maxLat - latitude) * hauteur / (maxLat - minLat);
	}

	/**
	 * Méthode pour renverser le processus de la méthode transformerLongitude
	 * 
	 * @param longitudeTransforme : une coordonnée X
	 * @param largeur             : la largeur de la vue graphique
	 * @return la longitude calculée
	 */
	public double reverseTransformLongitude(double longitudeTransforme, double largeur) {
		return longitudeTransforme * (maxLong - minLong) / largeur + minLong;
	}

	/**
	 * Méthode pour renverser le processus de la methode transformerLatitude
	 * 
	 * @param latitudeTransforme : la coordonnée Y
	 * @param hauteur            : la hauteur de la vue graphique
	 * @return la latitude calculée
	 */
	public double reverseTransformLatitude(double latitudeTransforme, double hauteur) {
		return maxLat - (latitudeTransforme * (maxLat - minLat) / hauteur);
	}
}
