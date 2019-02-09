package controleur;

/**
 * La classe de l'état ajouter le choix de livraison
 * @author H4404
 */
public class EtatAjouterChoixPointLivraison extends EtatDefaut{
	/**
	 * Méthode pour passer a l'état suivant pour choisir le point de livraison à ajouter
	 * @see Etat
	 */
	@Override
	public void choixNouveauPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatAjouterChoixNouvellePointLivraison());
	}
	
	/**
	 * Méthode pour obtenir le nombre maximum de livreur
	 * @see Etat
	 */
	@Override
	public int getNbLivreurMaximum() {
		return Controleur.getInstance().getMaDemande().getNbLivreurMaximum();
	}
}
