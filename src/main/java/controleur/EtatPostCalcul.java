package controleur;


import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import modele.metier.Tournee;
import modele.services.SerialiseurFeuilleDeRoute;


/**
 * La classe de l'état post calcul.
 * @author H4404
 */

public class EtatPostCalcul extends EtatDefaut {
	
	/**
	 * Méthode pour passer à l'état suivant pour choisir le point de livraison à ajouter
	 * @see Etat
	 */
	@Override
	public void ajouterPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatAjouterChoixPointLivraison());
	}
	
	/**
	 * Méthode pour passer à l'état suivant pour choisir le point de livraison à déplacer
	 * @see Etat
	 */
	@Override
	public void deplacerPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatChoixPointLivraisonADeplacer());
	}
	
	/**
	 * Méthode pour passer  à l'état suivant pour choisir le point de livraison à supprimer
	 * @see Etat
	 */
	@Override
	public void supprimerPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatSupprimerChoixPointLivraison());
	}
	
	/**
	 * Méthode pour obtenir le nombre maximum de livreur
	 * @see Etat
	 */
	@Override
	public int getNbLivreurMaximum() {
		return Controleur.getInstance().getMaDemande().getNbLivreurMaximum();
	}
	
	/**
	 * Méthode pour calculer les tournées selon le nombre de livreur passé et le mode passé
	 * @see Etat
	 */
	@Override
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception{
		Controleur.getInstance().getMonManager().calculerLesTourneesSelonMode(Controleur.getInstance().getMaDemande(), Controleur.getInstance().getMonPlan(), nbLivreur,mode);
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPosteCalcul());
	}
	
	/**
	 * Méthode pour annuler une commande
	 */
	public void undo() {
		Controleur.getInstance().getHistorique().annuler();
	}
	
	/**
	 * Méthode pour effectuer une commande
	 */
	public void redo() {
		Controleur.getInstance().getHistorique().refaire();

	}

	/**
	 * Méthode pour exporter la feuille de route
	 * @see Etat
	 */
	@Override
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException {
		ArrayList<Tournee> listeTournees = Controleur.getInstance().getMonManager().getListeTournees();
		Document feuilleDeRoute = SerialiseurFeuilleDeRoute.exportFeuilleDeRoute(listeTournees);
		return feuilleDeRoute;
	}
}
