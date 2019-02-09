package controleur;

/**
 * La classe de l'�tat du choix du point de livraison � appeler apr�s l'�tat EtatChoixPointLivraisonADeplacer
 * @author H4404
 */
public class EtatChoixPointLivraisonApresDeplacer extends EtatDefaut{
	
	/**
	 * M�thode pour effectuer le d�placement et revenir � l'�tat EtatPosteCalcul
	 * @see Etat
	 */
	@Override
	public void effectuerDeplacement(long idADeplacer, long idApres) throws Exception {
		Controleur.getInstance().getMonManager().deplacerPointLivraison(idADeplacer, idApres);
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
}
