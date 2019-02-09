package modele.metier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import org.joda.time.DateTime;

/** 
 * La classe de la demande de livraison
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class DemandeLivraison extends Observable{
	private HashMap<Long,PointLivraison> livraisons;
	private HashMap<Long,Entrepot> entrepots;
	
	/**
	 * Constructeur par défaut de la classe de demande de livraison
	 */
	public DemandeLivraison() {
		livraisons = new HashMap<Long,PointLivraison>();
		entrepots = new HashMap<Long,Entrepot>();
	}
	
	/**
	 * Méthode pour initialiser la demande de livraison
	 * @param livraisons : le dictionnaire des points de livraison
	 * @param entrepots : le dictionnaire des entrepôts
	 */
	public void intialiserDemandeLivraison(HashMap<Long,PointLivraison> livraisons, HashMap<Long,Entrepot> entrepots) {
		if(this.livraisons != null && this.entrepots != null) {
			this.clear();
			System.out.println("Clear demandelivraison");
		}
		this.livraisons = livraisons;
		this.entrepots = entrepots;
		setChanged();
		notifyObservers("DemandeLivraison");
	}
	
	/**
	 * Methode pour ajouter un entrepot.
	 * @param id : identifiant de l'entrepot.
	 * @param latitude : latitude de l'entrepot.
	 * @param longitude : longitude de l'entrepot.
	 * @param heure : heure de debut de la tournee.
	 * @param minute : minute de debut de la tournee.
	 * @param seconde : secondes de debut de la tournee.
	 */
	public void ajouterEntrepot(long id, double latitude, double longitude,int heure,int minute,int seconde) {
		Entrepot tempObject = new Entrepot(id,latitude,longitude,heure,minute,seconde);
		entrepots.put(id,tempObject);
		setChanged();
		notifyObservers("DemandeLivraison");
	}
	
	/**
	 * Methode pour supprimer un point de livraison et notifier les vues.
	 * @param id : id du point de livraison a supprimer.
	 */
	public void supprimerPoint(long id) {
		livraisons.remove(id);
		setChanged();
		notifyObservers("DemandeLivraison");
	
	}
	
	/**
	 * Methode pour ajouter un point de livraison.
	 * @param id : id du point a ajouter
	 * @param latitude : latitude de ce point.
	 * @param longitude : longitude de ce point.
	 */
	public void ajouterPoint(long id,double latitude, double longitude) {
		PointLivraison tempObject = new PointLivraison(id,latitude,longitude);
		livraisons.put(id,tempObject);
		setChanged();
		notifyObservers("DemandeLivraison");
	
	}
	
	/**
	 * Methode permettant de retourner id du premier entrepot.
	 * @return id du premier entrepot
	 */
	public long getIdEntrepot() {
		long retour = 0;
		Set<Long> idEntrepots = entrepots.keySet();
		for(Long id : idEntrepots) {
			retour = id;
			break;
		}
		return retour;
	}
	
	/**
	 * Methode pour ajouter un point de livraison selon un objet PointLivraison existant.
	 * @param id : l'id de ce point de livraison.
	 * @param p : l'objet PointLivraison existant.
	 */
	public void ajouterPoint(long id,PointLivraison p) {
		livraisons.put(id, p);
		setChanged();
		notifyObservers("DemandeLivraison");
	}
	/**
	 * Methode permettant de liberer l'entreprot et les points de livraison.
	 */
	public void clear() {
		entrepots.clear();
		livraisons.clear();
	}
	
	/**
	 * Methode pour acceder a l'entrepot par l'ID.
	 * @param id : identifiant de l'entrepot.
	 * @return l'entrepot voulu.
	 */
	public Entrepot getEntrepotParId(long id) {
		return entrepots.get(id);
	}
	
	/**
	 * Methode permettant de retourner tous les entrepots.
	 * @return la collection de tous les entrepots.
	 */
	public Collection<Entrepot> getAllEntrepots(){
		return entrepots.values();
	}
	
	/**
	 * Methode pour ajouter un point de livraison sans notifier les vues.
	 * @param id : identifiant du point du livraison.
	 * @param latitude : la latitude du point de livraison.
	 * @param longitude : la longitude du point de livraison.
	 * @param duree : la duree de livraison.
	 */
	public void ajouterPointLivraisonMetier(long id, double latitude, double longitude,int duree) {
		PointLivraison tempObject = new PointLivraison(id,latitude,longitude,duree);
		livraisons.put(id,tempObject);
	}
	
	/**
	 * Methode pour supprimer un point de livraison sans notifier les vues.
	 * @param id : identifiant du point du livraison.
	 */
	public void supprimerPointLivraisonMetier(long id) {
		livraisons.remove(id);
	}
	
	/**
	 * Methode pour obtenir un point de livraison.
	 * @param id : l'id du point de livraison voulu.
	 * @return le point de livraison correspondant.
	 */
	public PointLivraison getPointLivraisonParId(long id) {
		return livraisons.get(id);
	}
	
	/**
	 * Methode permettant de retourner la collection des points de livraisons.
	 * @return la collection des points de livraison.
	 */
	public Collection<PointLivraison> getAllPointLivraisons(){
		return livraisons.values();
	}
	
	/**
	 * Methode permettant de retourner le map des points de livraison.
	 * @return le map des points de livraison.
	 */
	public HashMap<Long, PointLivraison> getLivraisons() {
		return livraisons;
	}
	
	/**
	 * Methode permettant de retourner le nombre maximum de livreur.
	 * @return le nombre maximum de livreur.
	 */
	public int getNbLivreurMaximum(){
		return livraisons.size();
	}

	/**
	 * Methode permettant de retourner l'heure de debut
	 * @return l'heure de debut.
	 */
	public DateTime getDebutTime(){
		if(entrepots.size() == 1) {
			DateTime temp = null;
			for(Entrepot e : entrepots.values()) {
				temp = e.getHeureDeaprt();
				break;
			}
			return temp;
		}
		return null;
	}
	
	
}