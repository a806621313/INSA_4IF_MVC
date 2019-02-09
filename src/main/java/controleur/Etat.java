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
	 * M�thode pour charger un plan selon le fichier XML pass�
	 * 
	 * @param f : le fichier XML � charger
	 * @throws Exception si le fichier XML n'est pas correctement form�
	 */
	public void chargerFichierPlan(File f) throws Exception;

	/**
	 * M�thode pour calculer les tourn�es selon le nombre de livreur et le mode pass�
	 * 
	 * @param nbLivreur : le nombre de livreurs
	 * @param mode :      le mode choisi par l'utilisateur pour calculer les tourn�es
	 * @throws Exception si un point de livraison n'est pas livrable
	 */
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception;

	/**
	 * M�thode pour charger une demande de livraison selon le fichier XML pass�
	 * 
	 * @param f : le fichier XML � charger
	 * @throws Exception si le fichier XML n'est pas correctement form�
	 */
	public void lectureLivraisonEntrepotXML(File f) throws Exception;

	/**
	 * M�thode pour obtenir le nombre de livreur maximum
	 * 
	 * @return retourne le nombre de livreur maximum
	 */
	public int getNbLivreurMaximum();

	/**
	 * M�thode pour activer le listener permettant � l'utilisateur 
	 * d'ajouter des points de livraison(qui a une duree 0) avant le calcul des tourn�es
	 */
	public void ajouterListenerOnClick();

	/**
	 * M�thode pour passer le contr�leur dans l'�tat EtatAjouterChoixPointLivraison
	 * 
	 * @throws Exception si erreur durant le passage d'�tat
	 */
	public void ajouterPointLivraison() throws Exception;

	/**
	 * M�thode pour passer le contr�leur dans d'�tat EtatSupprimerChoixPointLivraison
	 * 
	 * @throws Exception si erreur durant le passage d'�tat
	 */
	public void supprimerPointLivraison() throws Exception;

	/**
	 * M�thode pour passer le contr�leur dans d'�tat EtatChoixPointLivraisonADeplacer
	 * 
	 * @throws Exception si erreur durant le passage d'�tat
	 */
	public void deplacerPointLivraison() throws Exception;

	/**
	 * M�thode pour passer le contr�leur dans d'�tat EtatAjouterChoixNouvellePointLivraison
	 * 
	 * @throws Exception si erreur durant le passage d'�tat
	 */
	public void choixNouveauPointLivraison() throws Exception;

	/**
	 * M�thode pour ajouter un point de livraison 
	 * et passer le contr�leur dans d'�tat EtatPosteCalcul
	 * 
	 * @param idDepart :   id du point de livraison apr�s lequel on veut ajouter le nouveau point de livraison
	 * @param idNouvelle : id du point de livraison � ajouter
	 * @param duree :      la dur�e du nouveau point de livraison
	 * @throws Exception si le point de livraison � ajouter n'est pas livrable
	 */
	public void effectuerAjoutPointLivraison(long idDepart, long idNouvelle, int duree) throws Exception;

	/**
	 *  M�thode pour supprimer un point de livraison 
	 * et passer le contr�leur dans d'�tat EtatPosteCalcul
	 * 
	 * @param id : id du point de livraison � supprimer
	 * @throws Exception si erreur lors de la suppression du point de livraison
	 */
	public void effectuerSupprimerPointLivraison(long id) throws Exception;

	/**
	 * M�thode pour passer le contr�leur dans l'�tat EtatChoixPointLivraisonApresDeplacer
	 * 
	 * @throws Exception si erreur durant le passage d'�tat
	 */
	public void choixPointLivraisonApresDeplacer() throws Exception;

	/**
	 * * M�thode pour d�placer un point de livraison 
	 * et passer le contr�leur dans l'�tat EtatPosteCalcul
	 * 
	 * @param idADeplacer : id du point de livraison � deplacer
	 * @param idApres :     id du point de livraison apr�s lequel on veut mettre le point de livraison � d�placer
	 * @throws Exception si le d;eplacement est au sein d'une meme tourn�e
	 */
	public void effectuerDeplacement(long idADeplacer, long idApres) throws Exception;

	/**
	 * M�thode pour annuler une commande
	 */
	public void undo();

	/**
	 * M�thode pour faire une commande
	 */
	public void redo();

	/**
	 * M�thode pour exporter la feuille de route
	 * @throws FileNotFoundException : Exception si fichier est introuvable
	 * @throws DocumentException : Exception lors de l'�criture un document
	 * @return le document �crit
	 */
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException;
}