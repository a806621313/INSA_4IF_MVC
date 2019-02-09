package controleur;


/**
 * La classe de l'état pour supprimer le point de livraison choisi
 * @author H4404
 *
 */
public class EtatSupprimerChoixPointLivraison extends EtatDefaut{
	/**
	 * M;ethode pour supprimer un point de livraison selon l'id passé
	 * @see Etat
	 */
	@Override
	public void effectuerSupprimerPointLivraison(long id) throws Exception {
		boolean supprime = Controleur.getInstance().getMonManager().supprimerPointLivraison(id);
		Controleur.getInstance().getTempSupprimer().setSupprime(supprime);
		Controleur.getInstance().getHistorique().ajouteCmd(Controleur.getInstance().getTempSupprimer());
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
}
