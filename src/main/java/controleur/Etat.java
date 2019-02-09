package controleur;

import java.io.File;
import java.io.FileNotFoundException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/**
 * La classe Etat
 * 
 * @author H4404
 */
public interface Etat {

	/**
	 * Méthode pour charger un plan selon le fichier XML passé
	 * 
	 * @param f : le fichier XML à charger
	 * @throws Exception si le fichier XML n'est pas correctement formé
	 */
	public void chargerFichierPlan(File f) throws Exception;

	/**
	 * Méthode pour calculer les tournées selon le nombre de livreur et le mode passé
	 * 
	 * @param nbLivreur : le nombre de livreurs
	 * @param mode :      le mode choisi par l'utilisateur pour calculer les tournées
	 * @throws Exception si un point de livraison n'est pas livrable
	 */
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception;

	/**
	 * Méthode pour charger une demande de livraison selon le fichier XML passé
	 * 
	 * @param f : le fichier XML à charger
	 * @throws Exception si le fichier XML n'est pas correctement formé
	 */
	public void lectureLivraisonEntrepotXML(File f) throws Exception;

	/**
	 * Méthode pour obtenir le nombre de livreur maximum
	 * 
	 * @return retourne le nombre de livreur maximum
	 */
	public int getNbLivreurMaximum();

	/**
	 * Méthode pour activer le listener permettant à l'utilisateur 
	 * d'ajouter des points de livraison(qui a une duree 0) avant le calcul des tournées
	 */
	public void ajouterListenerOnClick();

	/**
	 * Méthode pour passer le contrôleur dans l'état EtatAjouterChoixPointLivraison
	 * 
	 * @throws Exception si erreur durant le passage d'état
	 */
	public void ajouterPointLivraison() throws Exception;

	/**
	 * Méthode pour passer le contrôleur dans d'état EtatSupprimerChoixPointLivraison
	 * 
	 * @throws Exception si erreur durant le passage d'état
	 */
	public void supprimerPointLivraison() throws Exception;

	/**
	 * Méthode pour passer le contrôleur dans d'état EtatChoixPointLivraisonADeplacer
	 * 
	 * @throws Exception si erreur durant le passage d'état
	 */
	public void deplacerPointLivraison() throws Exception;

	/**
	 * Méthode pour passer le contrôleur dans d'état EtatAjouterChoixNouvellePointLivraison
	 * 
	 * @throws Exception si erreur durant le passage d'état
	 */
	public void choixNouveauPointLivraison() throws Exception;

	/**
	 * Méthode pour ajouter un point de livraison 
	 * et passer le contrôleur dans d'état EtatPosteCalcul
	 * 
	 * @param idDepart :   id du point de livraison après lequel on veut ajouter le nouveau point de livraison
	 * @param idNouvelle : id du point de livraison à ajouter
	 * @param duree :      la durée du nouveau point de livraison
	 * @throws Exception si le point de livraison à ajouter n'est pas livrable
	 */
	public void effectuerAjoutPointLivraison(long idDepart, long idNouvelle, int duree) throws Exception;

	/**
	 *  Méthode pour supprimer un point de livraison 
	 * et passer le contrôleur dans d'état EtatPosteCalcul
	 * 
	 * @param id : id du point de livraison à supprimer
	 * @throws Exception si erreur lors de la suppression du point de livraison
	 */
	public void effectuerSupprimerPointLivraison(long id) throws Exception;

	/**
	 * Méthode pour passer le contrôleur dans l'état EtatChoixPointLivraisonApresDeplacer
	 * 
	 * @throws Exception si erreur durant le passage d'état
	 */
	public void choixPointLivraisonApresDeplacer() throws Exception;

	/**
	 * * Méthode pour déplacer un point de livraison 
	 * et passer le contrôleur dans l'état EtatPosteCalcul
	 * 
	 * @param idADeplacer : id du point de livraison à deplacer
	 * @param idApres :     id du point de livraison apràs lequel on veut mettre le point de livraison à déplacer
	 * @throws Exception si le d;eplacement est au sein d'une meme tournée
	 */
	public void effectuerDeplacement(long idADeplacer, long idApres) throws Exception;

	/**
	 * Méthode pour annuler une commande
	 */
	public void undo();

	/**
	 * Méthode pour faire une commande
	 */
	public void redo();

	/**
	 * Méthode pour exporter la feuille de route
	 * @throws FileNotFoundException : Exception si fichier est introuvable
	 * @throws DocumentException : Exception lors de l'écriture un document
	 * @return le document écrit
	 */
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException;
}