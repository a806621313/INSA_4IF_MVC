package controleur;

import java.io.File;

import modele.services.LecteurDeXML;

/**
 * La classe de l'état plan chargé
 * @author H4404
 */
public class EtatPlanCharge extends EtatDefaut {

	/**
	 * Méthode pour charger une demande de livraison selon le fichier XML passé
	 * @see Etat
	 */
	@Override
	public void lectureLivraisonEntrepotXML(File f) throws Exception {
		LecteurDeXML.getInstance().lectureLivraisonEntrepotXML(f);
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatDemandeLivraison());
	}
}
