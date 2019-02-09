package controleur;

import java.io.File;
import java.io.FileNotFoundException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/**
 * La classe de l'�tat par d�faut impl�mentant l'interface de Etat 
 * 
 * @author H4404
 */
public class EtatDefaut implements Etat {

	/**
	 *  M�thode pour charger un plan selon le fichier XML pass�
	 *  @see Etat
	 */
	@Override
	public void chargerFichierPlan(File f) throws Exception {

	}

	/**
	 * M�thode pour calculer les tourn�es
	 * @see Etat
	 */
	@Override
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception {

	}

	/**
	 * M�thode pour lire un fichier XML qui contient une demande de livraison
	 * @see Etat
	 */
	@Override
	public void lectureLivraisonEntrepotXML(File f) throws Exception {

	}

	/**
	 *  M�thode pour obtenir le nombre maximum de livreur
	 * @see Etat
	 */
	@Override
	public int getNbLivreurMaximum() {
		return 0;
	}

	/**
	 * M�thode pour activer le listener permettant � l'utilisateur 
	 * d'ajouter des points de livraison(qui a une duree 0) avant le calcul des tourn�es
	 * @see Etat
	 */
	@Override
	public void ajouterListenerOnClick() {

	}

	/**
	 * M�thode pour passer le contr�leur dans l'�tat EtatAjouterChoixPointLivraison
	 * @see Etat
	 */
	@Override
	public void ajouterPointLivraison() throws Exception {

	}

	/**
	 * M�thode pour passer le contr�leur dans l'�tat EtatAjouterChoixNouvellePointLivraison
	 * @see Etat
	 */
	@Override
	public void choixNouveauPointLivraison() throws Exception {

	}

	/**
	 * M�thode pour ajouter un point de livraison
	 * @see Etat
	 */
	@Override
	public void effectuerAjoutPointLivraison(long idDepart, long idNouvelle, int duree) throws Exception {

	}

	/**
	 * M�thode pour supprimer un point de livraison
	 * @see Etat
	 */
	@Override
	public void effectuerSupprimerPointLivraison(long id) throws Exception {

	}

	/**
	 * M�thode pour passer le contr�leur dans l'�tat EtatChoixPointLivraisonApresDeplacer
	 * @see Etat
	 */
	@Override
	public void choixPointLivraisonApresDeplacer() throws Exception {

	}

	/**
	 * M�thode pour effectuer le d�placement
	 * @see Etat
	 */
	@Override
	public void effectuerDeplacement(long idADeplacer, long idApres) throws Exception {

	}

	/**
	 * M�thode pour passer le contr�leur dans l'�tat EtatSupprimerChoixPointLivraison
	 * @see Etat
	 */
	@Override
	public void supprimerPointLivraison() throws Exception {

	}

	/**
	 * M�thode pour passer le contr�leur dans l'�tat EtatChoixPointLivraisonADeplacer
	 * @see Etat
	 */
	@Override
	public void deplacerPointLivraison() throws Exception {

	}

	/**
	 * M�thode pour effectuer une commande
	 * @see Etat
	 */
	@Override
	public void undo() {

	}

	/**
	 * M�thode pour annuler une commande
	 * @see Etat
	 */
	@Override
	public void redo() {

	}

	/**
	 * M�thode pour exporter la feuille de route
	 * @see Etat
	 */
	@Override
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException {
		return new Document();
	}

}
