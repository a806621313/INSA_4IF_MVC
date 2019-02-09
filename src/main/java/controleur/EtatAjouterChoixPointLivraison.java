package controleur;

/**
 * La classe de l'�tat ajouter le choix de livraison
 * @author H4404
 */
public class EtatAjouterChoixPointLivraison extends EtatDefaut{
	/**
	 * M�thode pour passer a l'�tat suivant pour choisir le point de livraison � ajouter
	 * @see Etat
	 */
	@Override
	public void choixNouveauPointLivraison() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatAjouterChoixNouvellePointLivraison());
	}
	
	/**
	 * M�thode pour obtenir le nombre maximum de livreur
	 * @see Etat
	 */
	@Override
	public int getNbLivreurMaximum() {
		return Controleur.getInstance().getMaDemande().getNbLivreurMaximum();
	}
}
