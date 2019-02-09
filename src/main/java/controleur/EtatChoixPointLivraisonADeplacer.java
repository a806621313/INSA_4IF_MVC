package controleur;

/**
 * La classe de l'�tat du choix du point de livraison � d�placer
 * @author H4404
 */
public class EtatChoixPointLivraisonADeplacer extends EtatDefaut{
	/**
	 * M�thode pour passer a l'�tat suivant pour choisir le point de livraison 
	 * apr�s lequel on veut mettre le point de livraison � d�placer
	 * @see Etat
	 */
	@Override
	public void choixPointLivraisonApresDeplacer() throws Exception {
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatChoixPointLivraisonApresDeplacer());
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
