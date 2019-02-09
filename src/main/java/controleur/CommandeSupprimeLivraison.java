package controleur;

import modele.metier.Intersection;
import modele.metier.PointLivraison;

/**
 * La classe de Commande pour supprimer une livraison
 * 
 * @author H4404
 *
 */
public class CommandeSupprimeLivraison implements Commande {
	private PointLivraison livraisonSupprime;
	private Intersection prePoint;
	private boolean supprime;

	/**
	 * Constructeur d'une commande qui supprime un point de livraison
	 * 
	 * @param livraisonSupprimee : le point de livraison à supprimer
	 * @param prePoint           : le point de livraison précédant le point de
	 *                           livraison à supprimer dans la tournée
	 */
	public CommandeSupprimeLivraison(PointLivraison livraisonSupprimee, Intersection prePoint) {
		this.livraisonSupprime = livraisonSupprimee;
		this.prePoint = prePoint;
		supprime = false;
	}

	/**
	 * Setter pour l´attribut supprime
	 * 
	 * @param supprime
	 */
	public void setSupprime(boolean supprime) {
		this.supprime = supprime;
	}

	/**
	 * Méthode pour exécuter la commande supprimant un point de livraison
	 */
	@Override
	public void doCmd() {
		try {
			Controleur.getInstance().getMonManager().supprimerPointLivraisonMetier(livraisonSupprime.getId());
			Controleur.getInstance().getMonManager().notifyVue();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Méthode pour annuler la commande qui supprime un point de livraison
	 */
	@Override
	public void undoCmd() {
		try {
			if (supprime == true) {
				Controleur.getInstance().getMonManager().creerTourneeJusteUnLivraison(livraisonSupprime, prePoint);
			} else {
				Controleur.getInstance().getMonManager().ajouterPointLivraisonMetier(prePoint.getId(),
						livraisonSupprime.getId(), livraisonSupprime.getDuree());
			}
			Controleur.getInstance().getMonManager().notifyVue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
