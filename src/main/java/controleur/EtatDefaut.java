package controleur;

import java.io.File;
import java.io.FileNotFoundException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/**
 * La classe de l'état par défaut implémentant l'interface de Etat 
 * 
 * @author H4404
 */
public class EtatDefaut implements Etat {

	/**
	 *  Méthode pour charger un plan selon le fichier XML passé
	 *  @see Etat
	 */
	@Override
	public void chargerFichierPlan(File f) throws Exception {

	}

	/**
	 * Méthode pour calculer les tournées
	 * @see Etat
	 */
	@Override
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception {

	}

	/**
	 * Méthode pour lire un fichier XML qui contient une demande de livraison
	 * @see Etat
	 */
	@Override
	public void lectureLivraisonEntrepotXML(File f) throws Exception {

	}

	/**
	 *  Méthode pour obtenir le nombre maximum de livreur
	 * @see Etat
	 */
	@Override
	public int getNbLivreurMaximum() {
		return 0;
	}

	/**
	 * Méthode pour activer le listener permettant à l'utilisateur 
	 * d'ajouter des points de livraison(qui a une duree 0) avant le calcul des tournées
	 * @see Etat
	 */
	@Override
	public void ajouterListenerOnClick() {

	}

	/**
	 * Méthode pour passer le contrôleur dans l'état EtatAjouterChoixPointLivraison
	 * @see Etat
	 */
	@Override
	public void ajouterPointLivraison() throws Exception {

	}

	/**
	 * Méthode pour passer le contrôleur dans l'état EtatAjouterChoixNouvellePointLivraison
	 * @see Etat
	 */
	@Override
	public void choixNouveauPointLivraison() throws Exception {

	}

	/**
	 * Méthode pour ajouter un point de livraison
	 * @see Etat
	 */
	@Override
	public void effectuerAjoutPointLivraison(long idDepart, long idNouvelle, int duree) throws Exception {

	}

	/**
	 * Méthode pour supprimer un point de livraison
	 * @see Etat
	 */
	@Override
	public void effectuerSupprimerPointLivraison(long id) throws Exception {

	}

	/**
	 * Méthode pour passer le contrôleur dans l'état EtatChoixPointLivraisonApresDeplacer
	 * @see Etat
	 */
	@Override
	public void choixPointLivraisonApresDeplacer() throws Exception {

	}

	/**
	 * Méthode pour effectuer le déplacement
	 * @see Etat
	 */
	@Override
	public void effectuerDeplacement(long idADeplacer, long idApres) throws Exception {

	}

	/**
	 * Méthode pour passer le contrôleur dans l'état EtatSupprimerChoixPointLivraison
	 * @see Etat
	 */
	@Override
	public void supprimerPointLivraison() throws Exception {

	}

	/**
	 * Méthode pour passer le contrôleur dans l'état EtatChoixPointLivraisonADeplacer
	 * @see Etat
	 */
	@Override
	public void deplacerPointLivraison() throws Exception {

	}

	/**
	 * Méthode pour effectuer une commande
	 * @see Etat
	 */
	@Override
	public void undo() {

	}

	/**
	 * Méthode pour annuler une commande
	 * @see Etat
	 */
	@Override
	public void redo() {

	}

	/**
	 * Méthode pour exporter la feuille de route
	 * @see Etat
	 */
	@Override
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException {
		return new Document();
	}

}
