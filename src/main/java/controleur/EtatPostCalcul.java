package controleur;


import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import modele.metier.Tournee;
import modele.services.SerialiseurFeuilleDeRoute;


/**
 * La classe de l'�tat post calcul.
 * @author H4404
 */

public class EtatPostCalcul extends EtatDefaut {
	
	/**
	 * M�thode pour passer � l'�tat suivant pour choisir le point de livraison � ajouter
	 * @see Etat
	 */
	@Override
	public void ajouterPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatAjouterChoixPointLivraison());
	}
	
	/**
	 * M�thode pour passer � l'�tat suivant pour choisir le point de livraison � d�placer
	 * @see Etat
	 */
	@Override
	public void deplacerPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatChoixPointLivraisonADeplacer());
	}
	
	/**
	 * M�thode pour passer  � l'�tat suivant pour choisir le point de livraison � supprimer
	 * @see Etat
	 */
	@Override
	public void supprimerPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatSupprimerChoixPointLivraison());
	}
	
	/**
	 * M�thode pour obtenir le nombre maximum de livreur
	 * @see Etat
	 */
	@Override
	public int getNbLivreurMaximum() {
		return Controleur.getInstance().getMaDemande().getNbLivreurMaximum();
	}
	
	/**
	 * M�thode pour calculer les tourn�es selon le nombre de livreur pass� et le mode pass�
	 * @see Etat
	 */
	@Override
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception{
		Controleur.getInstance().getMonManager().calculerLesTourneesSelonMode(Controleur.getInstance().getMaDemande(), Controleur.getInstance().getMonPlan(), nbLivreur,mode);
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPosteCalcul());
	}
	
	/**
	 * M�thode pour annuler une commande
	 */
	public void undo() {
		Controleur.getInstance().getHistorique().annuler();
	}
	
	/**
	 * M�thode pour effectuer une commande
	 */
	public void redo() {
		Controleur.getInstance().getHistorique().refaire();

	}

	/**
	 * M�thode pour exporter la feuille de route
	 * @see Etat
	 */
	@Override
	public Document exportFeuilleDeRoute() throws FileNotFoundException, DocumentException {
		ArrayList<Tournee> listeTournees = Controleur.getInstance().getMonManager().getListeTournees();
		Document feuilleDeRoute = SerialiseurFeuilleDeRoute.exportFeuilleDeRoute(listeTournees);
		return feuilleDeRoute;
	}
}
