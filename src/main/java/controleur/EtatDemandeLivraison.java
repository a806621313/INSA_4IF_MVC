package controleur;

import javafx.scene.Node;
import vue.element.IntersectionNormalVue;

/**
 * La classe de l'�tat de demande de livraison
 * 
 * @author H4404
 */
public class EtatDemandeLivraison extends EtatDefaut {

	/**
	 * M�thode pour lancer le calcul de tourn�es selon le nombre de livreur et le mode pass�
	 * @see Etat
	 */
	@Override
	public void CalculerLesTournees(int nbLivreur, int mode) throws Exception {

		Controleur.getInstance().getMonManager().calculerLesTourneesSelonMode(Controleur.getInstance().getMaDemande(),
				Controleur.getInstance().getMonPlan(), nbLivreur, mode);
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPosteCalcul());
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
	 * M�thode pour ajouter le listener qui permet � l'utilisateur d'ajouter des points de livraison avant le calcul des tourn�es
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
