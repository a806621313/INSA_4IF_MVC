package controleur;

/**
 * La classe de l'état du choix du point de livraison à appeler après l'état EtatChoixPointLivraisonADeplacer
 * @author H4404
 */
public class EtatChoixPointLivraisonApresDeplacer extends EtatDefaut{
	
	/**
	 * Méthode pour effectuer le déplacement et revenir à l'état EtatPosteCalcul
	 * @see Etat
	 */
	@Override
	public void effectuerDeplacement(long idADeplacer, long idApres) throws Exception {
		Controleur.getInstance().getMonManager().deplacerPointLivraison(idADeplacer, idApres);
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
