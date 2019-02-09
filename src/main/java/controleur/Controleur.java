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
 * La classe contrôleur.
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
   * Méthode pour mettre le nouveau point de livraison à ajouter et effectuer cette opération
   * @param id : Id du point de livraison à ajouter
   * @param duree : la durée  de cette livraison
   * @throws Exception si erreur durant l'opération pour ajouter un nouveau point de livraison
   */
	public void setAjoutNouvellePoint(long id,int duree) throws Exception{
		this.ajoutIdNouvellePointLivraison = id;
		etat.effectuerAjoutPointLivraison(ajoutIdDepartPointLivraison, ajoutIdNouvellePointLivraison, duree);
	}
	
  /**
	 * Méthode pour mettre le contrôleur sur l'état EtatAjouterChoixPointLivraison
	 * @throws Exception si erreur durant le passage à un nouvel état
	 */
	public void ajouterPointLivraison() throws Exception{
		etat.ajouterPointLivraison();
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'etat EtatSupprimerChoixPointLivraison
	 * @throws Exception si erreur durant le passage à un nouvel etat
	 */
	public void supprimerPointLivraison() throws Exception{
		etat.supprimerPointLivraison();
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'etat EtatChoixPointLivraisonADeplacer
	 * @throws Exception si erreur durant le passage à un nouvel état
	 */
	public void deplacerPointLivraison() throws Exception{
		etat.deplacerPointLivraison();
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'état EtatPlanCharge et charger le plan dans le fichier XML passé en paramètre.
	 * @param f : le fichier XML.
	 * @throws Exception si le fichier XML n'est pas correctement formé.
	 */
	public void chargerFichierPlan(File f) throws Exception {
		etat.chargerFichierPlan(f);
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'état EtatDemandeLivraison et charger la demande de livraison dans le fichier XML qui est passe en paramètre.
	 * @param f : le fichier XML.
	 * @throws Exception si le fichier XML n'est pas correctement formé.
	 */
	public void chargerFichierDemandeLivraison(File f) throws Exception{
		etat.lectureLivraisonEntrepotXML(f);
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'état EtatPosteCalcul et calculer les tournées
	 * selon le nombre de livreur et le mode passé
	 * @param nbLivreur : nombre de livreurs
	 * @param mode : mode utilisé pour calculer les tournées(1 pour sans clustering et 2
	 *                pour clustering)
	 * @throws Exception l'exception au calcul des tournée(Existence d'un point de livraison non livrable).
	 */
	public void calculerLesTournees(int nbLivreur, int mode) throws Exception{
		etat.CalculerLesTournees(nbLivreur,mode);
	}
	
	/**
	 * Méthode pour retourner le nombre maximum de livreurs
	 * @return le nombre maximum de livreurs
	 * @throws Exception si erreur durant l'execution
	 */
	public int getNbLivreurMaximum() throws Exception {
		return etat.getNbLivreurMaximum();
	}

	/**
	 * Méthode pour obtenir le plan stocké dans le contrôleur
	 * @return le plan
	 */
	public Plan getMonPlan() {
		return monPlan;
	}

	/**
	 * Méthode pour obtenir la demande de livraison stockée dans le contrôleur
	 * @return la demande de livraison
	 */
	public DemandeLivraison getMaDemande() {
		return maDemande;
	}
	
	/**
	 * Méthode pour obtenir l´instance de TourneeManager stockée dans le contrôleur
	 * @return l´instance de TourneeManager
	 */
	public TourneeManager getMonManager() {
		return monManager;
	}

	/**
	 * Méthode pour obtenir la durée d'un point de livraison
	 * @param id : Id du point de livraison dont on veut obtenir la durée
	 * @return la durée de ce point de livraison
	 */
	public int getDureePointLivraison(long id) {
		return maDemande.getPointLivraisonParId(id).getDuree();
	}

	/**
	 * Méthode pour obtenir un point de livraison
	 * @param id : Id du point de livraison
	 * @return le point de livraison correspondant
	 * @throws Exception : id du point précédent
	 */
	public Intersection getPrePointLivraisonId(long id) throws Exception{
		return monManager.getPrePointLivraisonId(id);
	}
	
	/**
	 * Méthode pour définir la limite du temps pour calculer les tournées
	 * @param time : une limite de temps
	 */
	public void setTimeLimite(int time){
		monManager.setTimeLimite(time);;
	}
	
  	/**
	 * Méthode pour avoir l'état etatDemandeLivraison
	 * @return l'état de la demande de livraison
	 */
	public Etat getEtatDemandeLivraison() {
		return etatDemandeLivraison;
	}

	/**
	 * Méthode pour avoir l'état etatAjouterChoixNouvellePointLivraison
	 * @return l'état etatAjouterChoixNouvellePointLivraison
	 */
	public EtatAjouterChoixNouvellePointLivraison getEtatAjouterChoixNouvellePointLivraison() {
		return etatAjouterChoixNouvellePointLivraison;
	}
	
	/**
	 * Méthode pour avoir l'état actuel du contrôleur
	 * @return l'état actuel
	 */
	public Etat getEtatCourant() {
		return etat;
	}
	
	/**
	 * Méthode pour avoir l'état initial
	 * @return l'état etatInit
	 */
	public Etat getEtatInit() {
		return etatInit;
	}

	/**
	 * Méthode pour l'état plan charge
	 * @return l'état etatPlanCharge
	 */
	public Etat getEtatPlanCharge() {
		return etatPlanCharge;
	}
	
	/**
	 * Méthode pour avoir l'état AjouterChoixPointLivraison
	 * @return l'état etatAjouterChoixPointLivraison
	 */
	public Etat getEtatAjouterChoixPointLivraison() {
		return etatAjouterChoixPointLivraison;
	}

	/**
	 * Méthode pour avoir l'état etatSupprimerChoixPointLivraison
	 * @return l'état etatSupprimerChoixPointLivraison
	 */
	public EtatSupprimerChoixPointLivraison getEtatSupprimerChoixPointLivraison() {
		return etatSupprimerChoixPointLivraison;
	}
	
	/**
	 * Méthode pour avoir l'état etatChoixPointLivraisonADeplacer
	 * @return l'état etatChoixPointLivraisonADeplacer
	 */
	public EtatChoixPointLivraisonADeplacer getEtatChoixPointLivraisonADeplacer() {
		return etatChoixPointLivraisonADeplacer;
	}
	
	/**
	 * Méthode pour avoir l'état etatChoixPointLivraisonApresDeplacer
	 * @return l'état etatChoixPointLivraisonApresDeplacer
	 */
	public EtatChoixPointLivraisonApresDeplacer getEtatChoixPointLivraisonApresDeplacer() {
		return etatChoixPointLivraisonApresDeplacer;
	}

	/**
	 * Méthode pour avoir l'heure actuelle de départ
	 * @return le temps de début de la demande
	 */
	public DateTime getActuelHeureDepart() {
		return maDemande.getDebutTime();
	}

	/**
	 * Méthode pour avoir l'état etatPosteCalcul
	 * @return l'état du poste de calcul
	 */
	public Etat getEtatPosteCalcul() {
		return etatPosteCalcul;
	}
	
	/**
	 * Méthode pour retourner la vue graphique
	 * @return la vue graphique
	 */
	public VueGraphique getGraph() {
		return this.graph;
	}
	
	/**
	 * Méthode pour avoir l'instance du contrôleur
	 * @return retourne l'instance
	 */
	public static Controleur getInstance() {
		if(instance == null) {
			instance = new Controleur();
		}
		return instance;
	}
	

	/**
	 * Méthode pour obtenir l'id du point de livraison après lequel on veut ajouter un point de livraison
	 * @return l'id du point de livraison
	 * @throws Exception si erreur durant l'exécution
	 */
	public long getIdAjoutDepart()throws Exception{
		return this.ajoutIdDepartPointLivraison;
	}
	

  /**
   * Méthode pour obtenir l'id du point de livraison à ajouter
   * @return l'id de ce point de livraison
   */
	public long getIdAjoutNouvellePoint() {
		return ajoutIdNouvellePointLivraison;
	}
  
	/**
	 * Méthode pour transformer la latitude en une coordonnée X dans le plan selon la hauteur de la vue graphique
	 * @param latitude : une latitude
	 * @param hauteur : la hauteur de la vue graphique
	 * @return la coordonnée X calculée
	 */
	public double transformerLatitude(double latitude, double hauteur) {
		return monPlan.transformLatitude(latitude, hauteur);
	}
	
	/**
	 * Méthode pour transformer la longitude en une coordonnée Y dans le plan selon la largeur de la vue graphique
	 * @param longitude : une longitude
	 * @param largeur : la largeur de la vue graphique
	 * @return la coordonnée Y calculée
	 */
	public double transformerLongitude(double longitude, double largeur) {
		return monPlan.transformLongitude(longitude, largeur);
	}
	
	/**
	 * Méthode pour renverser le processus de la méthode transformerLatitude
	 * @param latitudeTransforme : la coordonnée Y
	 * @param hauteur : la hauteur de la vue graphique
	 * @return la latitude calculée
	 * @throws Exception si erreur durant la transformation
	 */
	public double reverseTransformLatitude(double latitudeTransforme,double hauteur)throws Exception{
		return monPlan.reverseTransformLatitude(latitudeTransforme,hauteur);
	}
	
	/**
	 * Méthode pour renverser le processus de la méthode transformerLongitude
	 * @param longitudeTransforme : une coordonnée X
	 * @param largeur : la largeur de la vue graphique
	 * @return la longitude calculée
	 * @throws Exception si erreur durant la transformation
	 */
	public double reverseTransformLongitude(double longitudeTransforme,double largeur)throws Exception {
		return monPlan.reverseTransformLongitude(longitudeTransforme,largeur);
	}
	
	/**
	 * Méthode pour ajouter l'observateur sur les objets observables stockés dans le contrôleur
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
	 * Méthode pour ajouter le listener permettant à l'utilisateur d´ajouter des points de livraison (avec durée 0)
	 * avant le calcul des tournées
	 */
	public void ajouterListenerOnClick() {
		etat.ajouterListenerOnClick();
	}

	/**
	 * Méthode pour mettre le contrôleur sur l'état etatAjouterChoixNouvellePointLivraison et définir l'id du point 
	 * de livraison après lequel nous voulons réaliser un ajout
	 * @param id : l'id du point de livraison après lequel on veut ajouter un nouveau point de livraison
	 * @throws Exception si erreur lors de l'ajout de la livraison
	 */
	public void setAjoutDepart(long id) throws Exception{
		this.ajoutIdDepartPointLivraison = id;
		etat.choixNouveauPointLivraison();
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'etat etatPosteCalcul et effectuer l'ajout d'un nouveau point de livraison
	 * @param id : id du nouveau point de livraison à ajouter
	 * @param durée : la durée du nouveau point de livraison
	 * @throws Exception si erreur durant l'ajout du nouveau point de livraison
	 */
	public void setAjoutNouveauPoint(long id,int duree) throws Exception{
		this.ajoutIdNouvellePointLivraison = id;
		etat.effectuerAjoutPointLivraison(ajoutIdDepartPointLivraison, ajoutIdNouvellePointLivraison, duree);
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'état etatChoixPointLivraisonApresDeplacer.
	 * @param id : l'id du point de livraison à déplacer
	 * @throws Exception si erreur durant l'exécution
	 */
	public void setADeplacer(long id) throws Exception{
		this.idADeplacerPointLivraison = id;
		etat.choixPointLivraisonApresDeplacer();
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'état etatPosteCalcul et effectuer le déplacement.
	 * @param id : l'id du point de livraison après lequel nous voulons mettre le point de livraison à déplacer
	 * @throws Exception si le déplacement n'est pas realisable(un déplacement dans la meme tournée)
	 */
	public void setApresDeplacer(long id) throws Exception{
		this.idApresDeplacerPointLivraison = id;
		etat.effectuerDeplacement(idADeplacerPointLivraison, idApresDeplacerPointLivraison);
	}
	
	/**
	 * Méthode pour mettre sur le contrôleur sur l'état etatSupprimerChoixPointLivraison et supprimer le point
	 * de livraison ayant l'id passe en paramètre
	 * @param id : l'id du point de livraison à supprimer
	 * @throws Exception si erreur lors de l'exécution
	 */
	public void setSupprimerPointLivraison(long id) throws Exception{
		etat.effectuerSupprimerPointLivraison(id);
	}
	
	/**
	 * Méthode pour mettre le contrôleur sur l'état passé en paramètre
	 * @param etatCrt : un état
	 */
	public void setEtat(Etat etatCrt) {
		Controleur.getInstance().etat = etatCrt;
	}
	
	/**
	 * Méthode pour mettre la vue graphique
	 * @param graph : une vue graphique
	 */
	public void setGraph(VueGraphique graph) {
		Controleur.getInstance().graph = graph;
	}

	/**
	 * Méthode pour mettre la vue textuelle
	 * @param texte : une vue textuelle
	 */
	public void setTexte(VueTextuelle texte) {
		Controleur.getInstance().texte = texte;
	}
	
	/**
	 * Méthode pour obtenir la longitude d'une intersection
	 * @param id: id de l'intersection
	 * @return longitude de cette intersection
	 */
	public double getLongititudeIntersection(long id) {
		return monPlan.getIntersectionNormal(id).getLongitude();
	}
	
	/**
	 * Méthode pour obtenir latitude d'une intersection
	 * @param id: id de l'intersection
	 * @return latitude de cette intersection
	 */
	public double getLatitudeIntersection(long id) {
		return monPlan.getIntersectionNormal(id).getLatitude();
	}

	/**
	 * Méthode pour faire une commande
	 * @see contrôleur.Commande
	 */
	public void undo() {
		etat.undo();
	}
	
	/**
	 * Méthode pour annuler une commande
	 * @see  contrôleur.Commande
	 */
	public void redo() {
		etat.redo();
	}

	/**
	 * Méthode pour obtenir l'historique des commandes
	 * @return l'historique des commandes
	 */
	public Historique getHistorique() {
		return historique;
	}

	/**
	 * Méthode pour exporter une feuille de route dans l'espace de travail
	 * @return le document écrit
	 * @throws FileNotFoundException : exception du fichier non trouvé
	 * @throws DocumentException :  exception de l´erreur d´écriture du document
	 */
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException {
		return etat.exportFeuilleDeRoute();
	}
	
	/**
	 * Méthode pour effacer l'historique des commandes
	 */
	public void clearHistorique() {
		historique.clear();
	}

	/**
	 * Méthode pour obtenir la commande stockée actuellement dans le contrôleur pour ajouter un point de livraison
	 * @return la commande qui ajoute un point de livraison
	 */
	public CommandeAjouterLivraison getTempAjout() {
		return tempAjout;
	}

	/**
	 * Méthode pour mettre la commande passée an paramètre dans le contrôleur
	 * @param tempAjout : une commande pour ajouter un point de livraison
	 */
	public void setTempAjout(CommandeAjouterLivraison tempAjout) {
		this.tempAjout = tempAjout;
	}

	/**
	 * Méthode pour obtenir la commande stockée actuellement dans le contrôleur pour supprimer un point de livraison
	 * @return la commande qui supprime un point de livraison
	 */
	public CommandeSupprimeLivraison getTempSupprimer() {
		return tempSupprimer;
	}

	/**
	 * Méthode pour mettre la commande passée en paramètre dans le contrôleur
	 * @param tempSupprimer : une commande pour supprimer un point de livraison
	 */
	public void setTempSupprimer(CommandeSupprimeLivraison tempSupprimer) {
		this.tempSupprimer = tempSupprimer;
	}
}
