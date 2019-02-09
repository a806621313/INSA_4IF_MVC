package controleur;

import java.io.File;
import java.io.FileNotFoundException;

import org.joda.time.DateTime;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import modele.TourneeManager;
import modele.metier.DemandeLivraison;
import modele.metier.Intersection;
import modele.metier.Plan;
import vue.VueGraphique;
import vue.VueTextuelle;

/**
 * La classe contr�leur.
 * @author H4404
 */
public class Controleur {
	private Etat etat;
	private Plan monPlan;
	private DemandeLivraison maDemande;
	private TourneeManager monManager;
	private VueGraphique graph;
	@SuppressWarnings("unused")
	private VueTextuelle texte;
	private Historique historique;
	private long ajoutIdDepartPointLivraison;
	private long ajoutIdNouvellePointLivraison;
	private long idADeplacerPointLivraison;
	private long idApresDeplacerPointLivraison;
	private CommandeAjouterLivraison tempAjout;
	private CommandeSupprimeLivraison tempSupprimer;
	private static Controleur instance = null;
	private EtatPlanCharge etatPlanCharge;
	private EtatInit etatInit;
	private EtatDemandeLivraison etatDemandeLivraison;
	private EtatPostCalcul etatPosteCalcul;
	private EtatAjouterChoixPointLivraison etatAjouterChoixPointLivraison;
	private EtatAjouterChoixNouvellePointLivraison etatAjouterChoixNouvellePointLivraison;
	private EtatSupprimerChoixPointLivraison etatSupprimerChoixPointLivraison;
	private EtatChoixPointLivraisonADeplacer etatChoixPointLivraisonADeplacer;
	private EtatChoixPointLivraisonApresDeplacer etatChoixPointLivraisonApresDeplacer;

	/**
	 * Constructeur du controleur.
	 */
	private Controleur() {
		monPlan = new Plan();
		maDemande = new DemandeLivraison();
		monManager = new TourneeManager();
		historique = new Historique();
		etatPlanCharge = new EtatPlanCharge();
		etatInit = new EtatInit();
		etatDemandeLivraison = new EtatDemandeLivraison();
		etatPosteCalcul = new EtatPostCalcul();
		etatAjouterChoixPointLivraison = new EtatAjouterChoixPointLivraison();
		etatAjouterChoixNouvellePointLivraison = new EtatAjouterChoixNouvellePointLivraison();
		etatSupprimerChoixPointLivraison = new EtatSupprimerChoixPointLivraison();
		etatChoixPointLivraisonADeplacer = new EtatChoixPointLivraisonADeplacer();
		etatChoixPointLivraisonApresDeplacer = new EtatChoixPointLivraisonApresDeplacer();
		etat = etatInit;
		tempAjout = null;
		tempSupprimer = null;
	}
	
  /**
   * M�thode pour mettre le nouveau point de livraison � ajouter et effectuer cette op�ration
   * @param id : Id du point de livraison � ajouter
   * @param duree : la dur�e  de cette livraison
   * @throws Exception si erreur durant l'op�ration pour ajouter un nouveau point de livraison
   */
	public void setAjoutNouvellePoint(long id,int duree) throws Exception{
		this.ajoutIdNouvellePointLivraison = id;
		etat.effectuerAjoutPointLivraison(ajoutIdDepartPointLivraison, ajoutIdNouvellePointLivraison, duree);
	}
	
  /**
	 * M�thode pour mettre le contr�leur sur l'�tat EtatAjouterChoixPointLivraison
	 * @throws Exception si erreur durant le passage � un nouvel �tat
	 */
	public void ajouterPointLivraison() throws Exception{
		etat.ajouterPointLivraison();
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'etat EtatSupprimerChoixPointLivraison
	 * @throws Exception si erreur durant le passage � un nouvel etat
	 */
	public void supprimerPointLivraison() throws Exception{
		etat.supprimerPointLivraison();
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'etat EtatChoixPointLivraisonADeplacer
	 * @throws Exception si erreur durant le passage � un nouvel �tat
	 */
	public void deplacerPointLivraison() throws Exception{
		etat.deplacerPointLivraison();
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'�tat EtatPlanCharge et charger le plan dans le fichier XML pass� en param�tre.
	 * @param f : le fichier XML.
	 * @throws Exception si le fichier XML n'est pas correctement form�.
	 */
	public void chargerFichierPlan(File f) throws Exception {
		etat.chargerFichierPlan(f);
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'�tat EtatDemandeLivraison et charger la demande de livraison dans le fichier XML qui est passe en param�tre.
	 * @param f : le fichier XML.
	 * @throws Exception si le fichier XML n'est pas correctement form�.
	 */
	public void chargerFichierDemandeLivraison(File f) throws Exception{
		etat.lectureLivraisonEntrepotXML(f);
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'�tat EtatPosteCalcul et calculer les tourn�es
	 * selon le nombre de livreur et le mode pass�
	 * @param nbLivreur : nombre de livreurs
	 * @param mode : mode utilis� pour calculer les tourn�es(1 pour sans clustering et 2
	 *                pour clustering)
	 * @throws Exception l'exception au calcul des tourn�e(Existence d'un point de livraison non livrable).
	 */
	public void calculerLesTournees(int nbLivreur, int mode) throws Exception{
		etat.CalculerLesTournees(nbLivreur,mode);
	}
	
	/**
	 * M�thode pour retourner le nombre maximum de livreurs
	 * @return le nombre maximum de livreurs
	 * @throws Exception si erreur durant l'execution
	 */
	public int getNbLivreurMaximum() throws Exception {
		return etat.getNbLivreurMaximum();
	}

	/**
	 * M�thode pour obtenir le plan stock� dans le contr�leur
	 * @return le plan
	 */
	public Plan getMonPlan() {
		return monPlan;
	}

	/**
	 * M�thode pour obtenir la demande de livraison stock�e dans le contr�leur
	 * @return la demande de livraison
	 */
	public DemandeLivraison getMaDemande() {
		return maDemande;
	}
	
	/**
	 * M�thode pour obtenir l�instance de TourneeManager stock�e dans le contr�leur
	 * @return l�instance de TourneeManager
	 */
	public TourneeManager getMonManager() {
		return monManager;
	}

	/**
	 * M�thode pour obtenir la dur�e d'un point de livraison
	 * @param id : Id du point de livraison dont on veut obtenir la dur�e
	 * @return la dur�e de ce point de livraison
	 */
	public int getDureePointLivraison(long id) {
		return maDemande.getPointLivraisonParId(id).getDuree();
	}

	/**
	 * M�thode pour obtenir un point de livraison
	 * @param id : Id du point de livraison
	 * @return le point de livraison correspondant
	 * @throws Exception : id du point pr�c�dent
	 */
	public Intersection getPrePointLivraisonId(long id) throws Exception{
		return monManager.getPrePointLivraisonId(id);
	}
	
	/**
	 * M�thode pour d�finir la limite du temps pour calculer les tourn�es
	 * @param time : une limite de temps
	 */
	public void setTimeLimite(int time){
		monManager.setTimeLimite(time);;
	}
	
  	/**
	 * M�thode pour avoir l'�tat etatDemandeLivraison
	 * @return l'�tat de la demande de livraison
	 */
	public Etat getEtatDemandeLivraison() {
		return etatDemandeLivraison;
	}

	/**
	 * M�thode pour avoir l'�tat etatAjouterChoixNouvellePointLivraison
	 * @return l'�tat etatAjouterChoixNouvellePointLivraison
	 */
	public EtatAjouterChoixNouvellePointLivraison getEtatAjouterChoixNouvellePointLivraison() {
		return etatAjouterChoixNouvellePointLivraison;
	}
	
	/**
	 * M�thode pour avoir l'�tat actuel du contr�leur
	 * @return l'�tat actuel
	 */
	public Etat getEtatCourant() {
		return etat;
	}
	
	/**
	 * M�thode pour avoir l'�tat initial
	 * @return l'�tat etatInit
	 */
	public Etat getEtatInit() {
		return etatInit;
	}

	/**
	 * M�thode pour l'�tat plan charge
	 * @return l'�tat etatPlanCharge
	 */
	public Etat getEtatPlanCharge() {
		return etatPlanCharge;
	}
	
	/**
	 * M�thode pour avoir l'�tat AjouterChoixPointLivraison
	 * @return l'�tat etatAjouterChoixPointLivraison
	 */
	public Etat getEtatAjouterChoixPointLivraison() {
		return etatAjouterChoixPointLivraison;
	}

	/**
	 * M�thode pour avoir l'�tat etatSupprimerChoixPointLivraison
	 * @return l'�tat etatSupprimerChoixPointLivraison
	 */
	public EtatSupprimerChoixPointLivraison getEtatSupprimerChoixPointLivraison() {
		return etatSupprimerChoixPointLivraison;
	}
	
	/**
	 * M�thode pour avoir l'�tat etatChoixPointLivraisonADeplacer
	 * @return l'�tat etatChoixPointLivraisonADeplacer
	 */
	public EtatChoixPointLivraisonADeplacer getEtatChoixPointLivraisonADeplacer() {
		return etatChoixPointLivraisonADeplacer;
	}
	
	/**
	 * M�thode pour avoir l'�tat etatChoixPointLivraisonApresDeplacer
	 * @return l'�tat etatChoixPointLivraisonApresDeplacer
	 */
	public EtatChoixPointLivraisonApresDeplacer getEtatChoixPointLivraisonApresDeplacer() {
		return etatChoixPointLivraisonApresDeplacer;
	}

	/**
	 * M�thode pour avoir l'heure actuelle de d�part
	 * @return le temps de d�but de la demande
	 */
	public DateTime getActuelHeureDepart() {
		return maDemande.getDebutTime();
	}

	/**
	 * M�thode pour avoir l'�tat etatPosteCalcul
	 * @return l'�tat du poste de calcul
	 */
	public Etat getEtatPosteCalcul() {
		return etatPosteCalcul;
	}
	
	/**
	 * M�thode pour retourner la vue graphique
	 * @return la vue graphique
	 */
	public VueGraphique getGraph() {
		return this.graph;
	}
	
	/**
	 * M�thode pour avoir l'instance du contr�leur
	 * @return retourne l'instance
	 */
	public static Controleur getInstance() {
		if(instance == null) {
			instance = new Controleur();
		}
		return instance;
	}
	

	/**
	 * M�thode pour obtenir l'id du point de livraison apr�s lequel on veut ajouter un point de livraison
	 * @return l'id du point de livraison
	 * @throws Exception si erreur durant l'ex�cution
	 */
	public long getIdAjoutDepart()throws Exception{
		return this.ajoutIdDepartPointLivraison;
	}
	

  /**
   * M�thode pour obtenir l'id du point de livraison � ajouter
   * @return l'id de ce point de livraison
   */
	public long getIdAjoutNouvellePoint() {
		return ajoutIdNouvellePointLivraison;
	}
  
	/**
	 * M�thode pour transformer la latitude en une coordonn�e X dans le plan selon la hauteur de la vue graphique
	 * @param latitude : une latitude
	 * @param hauteur : la hauteur de la vue graphique
	 * @return la coordonn�e X calcul�e
	 */
	public double transformerLatitude(double latitude, double hauteur) {
		return monPlan.transformLatitude(latitude, hauteur);
	}
	
	/**
	 * M�thode pour transformer la longitude en une coordonn�e Y dans le plan selon la largeur de la vue graphique
	 * @param longitude : une longitude
	 * @param largeur : la largeur de la vue graphique
	 * @return la coordonn�e Y calcul�e
	 */
	public double transformerLongitude(double longitude, double largeur) {
		return monPlan.transformLongitude(longitude, largeur);
	}
	
	/**
	 * M�thode pour renverser le processus de la m�thode transformerLatitude
	 * @param latitudeTransforme : la coordonn�e Y
	 * @param hauteur : la hauteur de la vue graphique
	 * @return la latitude calcul�e
	 * @throws Exception si erreur durant la transformation
	 */
	public double reverseTransformLatitude(double latitudeTransforme,double hauteur)throws Exception{
		return monPlan.reverseTransformLatitude(latitudeTransforme,hauteur);
	}
	
	/**
	 * M�thode pour renverser le processus de la m�thode transformerLongitude
	 * @param longitudeTransforme : une coordonn�e X
	 * @param largeur : la largeur de la vue graphique
	 * @return la longitude calcul�e
	 * @throws Exception si erreur durant la transformation
	 */
	public double reverseTransformLongitude(double longitudeTransforme,double largeur)throws Exception {
		return monPlan.reverseTransformLongitude(longitudeTransforme,largeur);
	}
	
	/**
	 * M�thode pour ajouter l'observateur sur les objets observables stock�s dans le contr�leur
	 * @param graph : la vue graphique
	 * @param texte : la vue textuelle
	 */
	public void addObserver(VueGraphique graph, VueTextuelle texte) {
		monPlan.addObserver(graph);
		maDemande.addObserver(graph);
		monManager.addObserver(graph);
		monPlan.addObserver(texte);
		maDemande.addObserver(texte);
		monManager.addObserver(texte);
	}
	
	/**
	 * M�thode pour ajouter le listener permettant � l'utilisateur d�ajouter des points de livraison (avec dur�e 0)
	 * avant le calcul des tourn�es
	 */
	public void ajouterListenerOnClick() {
		etat.ajouterListenerOnClick();
	}

	/**
	 * M�thode pour mettre le contr�leur sur l'�tat etatAjouterChoixNouvellePointLivraison et d�finir l'id du point 
	 * de livraison apr�s lequel nous voulons r�aliser un ajout
	 * @param id : l'id du point de livraison apr�s lequel on veut ajouter un nouveau point de livraison
	 * @throws Exception si erreur lors de l'ajout de la livraison
	 */
	public void setAjoutDepart(long id) throws Exception{
		this.ajoutIdDepartPointLivraison = id;
		etat.choixNouveauPointLivraison();
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'etat etatPosteCalcul et effectuer l'ajout d'un nouveau point de livraison
	 * @param id : id du nouveau point de livraison � ajouter
	 * @param dur�e : la dur�e du nouveau point de livraison
	 * @throws Exception si erreur durant l'ajout du nouveau point de livraison
	 */
	public void setAjoutNouveauPoint(long id,int duree) throws Exception{
		this.ajoutIdNouvellePointLivraison = id;
		etat.effectuerAjoutPointLivraison(ajoutIdDepartPointLivraison, ajoutIdNouvellePointLivraison, duree);
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'�tat etatChoixPointLivraisonApresDeplacer.
	 * @param id : l'id du point de livraison � d�placer
	 * @throws Exception si erreur durant l'ex�cution
	 */
	public void setADeplacer(long id) throws Exception{
		this.idADeplacerPointLivraison = id;
		etat.choixPointLivraisonApresDeplacer();
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'�tat etatPosteCalcul et effectuer le d�placement.
	 * @param id : l'id du point de livraison apr�s lequel nous voulons mettre le point de livraison � d�placer
	 * @throws Exception si le d�placement n'est pas realisable(un d�placement dans la meme tourn�e)
	 */
	public void setApresDeplacer(long id) throws Exception{
		this.idApresDeplacerPointLivraison = id;
		etat.effectuerDeplacement(idADeplacerPointLivraison, idApresDeplacerPointLivraison);
	}
	
	/**
	 * M�thode pour mettre sur le contr�leur sur l'�tat etatSupprimerChoixPointLivraison et supprimer le point
	 * de livraison ayant l'id passe en param�tre
	 * @param id : l'id du point de livraison � supprimer
	 * @throws Exception si erreur lors de l'ex�cution
	 */
	public void setSupprimerPointLivraison(long id) throws Exception{
		etat.effectuerSupprimerPointLivraison(id);
	}
	
	/**
	 * M�thode pour mettre le contr�leur sur l'�tat pass� en param�tre
	 * @param etatCrt : un �tat
	 */
	public void setEtat(Etat etatCrt) {
		Controleur.getInstance().etat = etatCrt;
	}
	
	/**
	 * M�thode pour mettre la vue graphique
	 * @param graph : une vue graphique
	 */
	public void setGraph(VueGraphique graph) {
		Controleur.getInstance().graph = graph;
	}

	/**
	 * M�thode pour mettre la vue textuelle
	 * @param texte : une vue textuelle
	 */
	public void setTexte(VueTextuelle texte) {
		Controleur.getInstance().texte = texte;
	}
	
	/**
	 * M�thode pour obtenir la longitude d'une intersection
	 * @param id: id de l'intersection
	 * @return longitude de cette intersection
	 */
	public double getLongititudeIntersection(long id) {
		return monPlan.getIntersectionNormal(id).getLongitude();
	}
	
	/**
	 * M�thode pour obtenir latitude d'une intersection
	 * @param id: id de l'intersection
	 * @return latitude de cette intersection
	 */
	public double getLatitudeIntersection(long id) {
		return monPlan.getIntersectionNormal(id).getLatitude();
	}

	/**
	 * M�thode pour faire une commande
	 * @see contr�leur.Commande
	 */
	public void undo() {
		etat.undo();
	}
	
	/**
	 * M�thode pour annuler une commande
	 * @see  contr�leur.Commande
	 */
	public void redo() {
		etat.redo();
	}

	/**
	 * M�thode pour obtenir l'historique des commandes
	 * @return l'historique des commandes
	 */
	public Historique getHistorique() {
		return historique;
	}

	/**
	 * M�thode pour exporter une feuille de route dans l'espace de travail
	 * @return le document �crit
	 * @throws FileNotFoundException : exception du fichier non trouv�
	 * @throws DocumentException :  exception de l�erreur d��criture du document
	 */
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException {
		return etat.exportFeuilleDeRoute();
	}
	
	/**
	 * M�thode pour effacer l'historique des commandes
	 */
	public void clearHistorique() {
		historique.clear();
	}

	/**
	 * M�thode pour obtenir la commande stock�e actuellement dans le contr�leur pour ajouter un point de livraison
	 * @return la commande qui ajoute un point de livraison
	 */
	public CommandeAjouterLivraison getTempAjout() {
		return tempAjout;
	}

	/**
	 * M�thode pour mettre la commande pass�e an param�tre dans le contr�leur
	 * @param tempAjout : une commande pour ajouter un point de livraison
	 */
	public void setTempAjout(CommandeAjouterLivraison tempAjout) {
		this.tempAjout = tempAjout;
	}

	/**
	 * M�thode pour obtenir la commande stock�e actuellement dans le contr�leur pour supprimer un point de livraison
	 * @return la commande qui supprime un point de livraison
	 */
	public CommandeSupprimeLivraison getTempSupprimer() {
		return tempSupprimer;
	}

	/**
	 * M�thode pour mettre la commande pass�e en param�tre dans le contr�leur
	 * @param tempSupprimer : une commande pour supprimer un point de livraison
	 */
	public void setTempSupprimer(CommandeSupprimeLivraison tempSupprimer) {
		this.tempSupprimer = tempSupprimer;
	}
}
