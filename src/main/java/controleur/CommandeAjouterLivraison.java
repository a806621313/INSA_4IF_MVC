package controleur;


/**
 * La classe de commande pour ajouter un point de livraison
 * @author H4404
 *
 */
public class CommandeAjouterLivraison implements Commande {
	private long prePoint;
	private long nouvellePoint;
	private int duree;
	
	/**
	 * Constructeur d'une commande qui ajoute un point de livraison
	 * @param prePoint : le point de livraison pr�c�dant le point de livraison � ajouter
	 * @param nouveauPoint : le nouveau point de livraison � ajouter
	 * @param duree : la dur�e de la livraison � ajouter
	 */
	public CommandeAjouterLivraison(long prePoint, long nouveauPoint, int duree) {
		this.prePoint = prePoint;
		this.nouvellePoint = nouveauPoint;
		this.duree = duree;
	}

	/**
	 * M�thode pour ex�cuter la commande ajoutant un point de livraison
	 */
	@Override
	public void doCmd() {
		try {
			Controleur.getInstance().getMonManager().ajouterPointLivraisonMetier(prePoint, nouvellePoint, duree);
			Controleur.getInstance().getMonManager().notifyVue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode pour annuler la commande ajoutant un point de livraison
	 */
	@Override
	public void undoCmd() {
		try {
			Controleur.getInstance().getMonManager().supprimerPointLivraisonMetier(nouvellePoint);
			Controleur.getInstance().getMonManager().notifyVue();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
