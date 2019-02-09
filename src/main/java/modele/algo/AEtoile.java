package modele.algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import modele.metier.Intersection;
import modele.metier.Plan;
import modele.metier.Troncon;
import modele.services.exceptions.IntersectionNonLivrableException;

/**
 * La classe de l'algorithme A*
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class AEtoile {

	private static AEtoile instance = null;

	private static final double RAYON_TERRE = 6378.137;

	/**
	 * Constructeur de la classe AEtoile
	 */
	private AEtoile() {
	}

	/**
	 * Méthode pour obtenir l'instance de l'algorithme AEtoile
	 * 
	 * @return l'instance
	 */
	public static AEtoile getInstance() {
		if (instance == null)
			instance = new AEtoile();
		return instance;
	}

	/**
	 * Méthode pour traduire un trajet
	 * 
	 * @param chemin : le chemin en intersections
	 * @param unPlan : le plan de la ville
	 * @return la traduction du chemin en tronçons
	 */
	public ArrayList<Troncon> traductionTrajet(ArrayList<Intersection> chemin, Plan unPlan) {
		ArrayList<Troncon> traduction = new ArrayList<Troncon>();
		int length = chemin.size();
		for (int i = 0; i < length - 1; i++) {
			ArrayList<Troncon> tempListe = unPlan.getTronconsParOrigine(chemin.get(i).getId());
			for (int j = 0; j < tempListe.size(); j++) {
				Intersection tempDest = tempListe.get(j).getDestination();
				if (tempDest.equals(chemin.get(i + 1))) {
					traduction.add(tempListe.get(j));
					break;
				}
			}
		}
		return traduction;
	}

	/**
	 * Méthode permettant de trouver les voisins d'une intersection
	 * 
	 * @param idCourant : l'identifiant de l'intersection en cours
	 * @param monPlan   : le plan de la ville
	 * @return la liste des voisins exprimée en tronçons
	 */
	private ArrayList<Troncon> trouverVosins(final long idCourant, Plan monPlan) {
		return monPlan.getTronconsParOrigine(idCourant);
	}

	/**
	 * Méthode de l'algorithme A*
	 * 
	 * @param depart  : l'intersection de départ
	 * @param dest    : l'intersection de la destination
	 * @param monPlan : la plan de la ville
	 * @throws Exception: l'exception lors calcul en A*
	 * @return la liste des intersections du plus court chemin trouvé
	 */
	public ArrayList<Intersection> algoAEtoile(Intersection depart, Intersection dest, Plan monPlan) throws Exception {
		if (atteignable(dest, monPlan)) {

			ArrayList<Intersection> meilleurChemin = new ArrayList<Intersection>();

			HashMap<Intersection, Intersection> parents = new HashMap<Intersection, Intersection>();
			parents.put(depart, depart);
			HashMap<Intersection, Double> distanceEstimeeF = new HashMap<Intersection, Double>();
			distanceEstimeeF.put(depart, heuristique(depart, dest));

			ArrayList<Intersection> noir = new ArrayList<Intersection>();

			Map<Intersection, Double> gris = new HashMap<Intersection, Double>();
			gris.put(depart, heuristique(depart, dest));

			ArrayList<Troncon> voisins = new ArrayList<Troncon>();

			while (!gris.isEmpty()) {
				List<Entry<Intersection, Double>> list = new ArrayList<Entry<Intersection, Double>>(gris.entrySet());

				Collections.sort(list, new Comparator<Map.Entry<Intersection, Double>>() {
					public int compare(Entry<Intersection, Double> o1, Entry<Intersection, Double> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				});
				Map.Entry<Intersection, Double> elemCourant = premierElement(list);
				Intersection interCourant = elemCourant.getKey();

				if (dest.equals(interCourant)) {
					meilleurChemin.clear();
					meilleurChemin.add(0, dest);
					interCourant = parents.get(interCourant);
					while (!depart.equals(interCourant)) {
						meilleurChemin.add(0, interCourant);
						interCourant = parents.get(interCourant);
					}
					meilleurChemin.add(0, depart);
					return meilleurChemin;
				}

				noir.add(interCourant);
				gris.remove(interCourant);

				voisins = trouverVosins(interCourant.getId(), monPlan);

				if (voisins != null) {
					for (Troncon voisin : voisins) {
						if (noir.contains(voisin.getDestination()))
							continue;
						Intersection interVoisin = voisin.getDestination();
						double nouvelleDistance = distanceEstimeeF.get(interCourant) + voisin.getLongueur()
								- heuristique(interCourant, dest) + heuristique(interVoisin, dest);
						if (isGris(gris, voisin.getDestination().getId())) {

							if (distanceEstimeeF.get(interVoisin) > nouvelleDistance) {
								distanceEstimeeF.remove(interVoisin);
								distanceEstimeeF.put(interVoisin, nouvelleDistance);
								parents.remove(interVoisin);
								parents.put(interVoisin, interCourant);
								gris.remove(interVoisin);
								gris.put(interVoisin, nouvelleDistance);

							}
						} else {
							distanceEstimeeF.put(interVoisin, nouvelleDistance);
							parents.put(interVoisin, interCourant);
							gris.put(interVoisin, nouvelleDistance);
						}
					}
				}
			}

			return meilleurChemin;
		} else {
			IntersectionNonLivrableException e = new IntersectionNonLivrableException();
			System.out.println("Non atteignable");
			throw e;
		}
	}

	/**
	 * Méthode pour obtenir le premier élément dans la liste des noeuds gris
	 * 
	 * @param list : la liste des entites de map triées
	 * @return le premier élément de la liste
	 */
	public Entry<Intersection, Double> premierElement(List<Entry<Intersection, Double>> list) {
		Entry<Intersection, Double> retour = null;
		for (Entry<Intersection, Double> element : list) {
			retour = element;
			break;
		}
		return retour;
	}

	/**
	 * Méthode pour determiner si la destination est atteignable
	 * 
	 * @param dest   : Intersection pour laquelle nous voulons déterminer si elle est atteignable
	 * @param unPlan : le plan où toutes les informations des intersections et des tronçons sont stockées
	 * @return boolean qui indique le point est atteignable ou pas
	 */
	public boolean atteignable(Intersection dest, Plan unPlan) {
		boolean retour = false;
		Collection<ArrayList<Troncon>> lesTroncons = unPlan.getAllTroncons();
		for (ArrayList<Troncon> liste : lesTroncons) {
			if (retour == false) {
				for (Troncon c : liste) {
					if (c.getDestination().getId() == dest.getId()) {
						retour = true;
						break;
					}
				}
			} else {
				break;
			}
		}
		return retour;
	}

	/**
	 * Méthode de l'heuristique
	 * 
	 * @param depart : intersection de départ
	 * @param dest   : intersection de destination
	 * @return retourne la distance estimée entre le départ et la destination
	 */
	public double heuristique(Intersection depart, Intersection dest) {
		return getDistance(depart.getLatitude(), depart.getLongitude(), dest.getLatitude(), dest.getLongitude());
	}

	/**
	 * Méthode pour savoir si le noeud est gris
	 * 
	 * @param gris   : map des noeuds gris
	 * @param voisin : id du voisin (noeud actuel considéré)
	 * @return retourne vrai si le noeud est gris
	 */
	public boolean isGris(Map<Intersection, Double> gris, Long voisin) {
		boolean retour = false;
		Set<Entry<Intersection, Double>> tempSet = gris.entrySet();
		for (Entry<Intersection, Double> element : tempSet) {
			if (element.getKey().getId() == voisin) {// .getId()
				retour = true;
			}
		}
		return retour;
	}

	/**
	 * Méthode pour calculer la distance entre deux points
	 * 
	 * @param latStart  : la latitude de début
	 * @param longStart : la longitude du début
	 * @param latEnd    : la latitude d'arrivée
	 * @param longEnd   : la longitude d'arrivée
	 * @return la distance entre les deux points
	 */
	public double getDistance(double latStart, double longStart, double latEnd, double longEnd) {
		double radLat1 = rad(latStart);
		double radLat2 = rad(latEnd);
		double a = radLat1 - radLat2;
		double b = rad(longStart) - rad(longEnd);

		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * RAYON_TERRE * 1000;
		s = Math.round(s * 10000) / 10000;
		return s;

	}

	/**
	 * Méthode pour convertir un angle en degré en radian
	 * 
	 * @param d : le degré de l'angle
	 * @return la valeur en radian
	 */
	private double rad(double d) {
		return d * Math.PI / 180.0;
	}

}
