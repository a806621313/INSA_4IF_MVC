package controleur;

import javafx.scene.Node;
import vue.element.IntersectionNormalVue;

/**
 * La classe de l'état de demande de livraison
 * 
 * @author H4404
 */
public class EtatDemandeLivraison extends EtatDefaut {

	/**
	 * Méthode pour lancer le calcul de tournées selon le nombre de livreur et le mode passé
	 * @see Etat
	 */
	@Override
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception {

		Controleur.getInstance().getMonManager().calculerLesTourneesSelonMode(Controleur.getInstance().getMaDemande(),
				Controleur.getInstance().getMonPlan(), nbLivreur, mode);
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPosteCalcul());
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
	 * Méthode pour ajouter le listener qui permet à l'utilisateur d'ajouter des points de livraison avant le calcul des tournées
	 * @see Etat
	 */
	public void ajouterListenerOnClick() {
		for (Node vue : Controleur.getInstance().getGraph().getNoeudGroup().getChildren()) {
			if (vue instanceof IntersectionNormalVue) {

				IntersectionNormalVue temp = (IntersectionNormalVue) vue;
				temp.ajouterListenerOnClick();

			}
		}
	}

}
