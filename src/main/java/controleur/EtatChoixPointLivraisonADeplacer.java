package controleur;

/**
 * La classe de l'état du choix du point de livraison à déplacer
 * @author H4404
 */
public class EtatChoixPointLivraisonADeplacer extends EtatDefaut{
	/**
	 * Méthode pour passer a l'état suivant pour choisir le point de livraison 
	 * après lequel on veut mettre le point de livraison à déplacer
	 * @see Etat
	 */
	@Override
	public void choixPointLivraisonApresDeplacer() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatChoixPointLivraisonApresDeplacer());
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
