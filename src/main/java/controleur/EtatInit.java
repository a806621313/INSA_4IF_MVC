package controleur;

import java.io.File;

import modele.services.LecteurDeXML;

/**
 * La classe de l'�tat initial.
 * @author H4404
 */
public class EtatInit extends EtatDefaut {

	
	/**
	 * M�thode pour charger un plan selon le fichier XML pass�
	 * @see Etat
	 */
	@Override
	public void chargerFichierPlan(File f) throws Exception {
		LecteurDeXML.getInstance().lecturePlanXML(f);
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPlanCharge());
	}
}