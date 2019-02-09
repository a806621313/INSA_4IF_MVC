package modele.metier;

import java.util.ArrayList;

/** 
 * La classe de la tourn�e
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class Tournee {
	
	
	
	private ArrayList<Chemin> listeChemins;
	
	/**
	 * Constructeur param�tre de la Tourn�e en utilisant une liste des chemins
	 * @param uneListeChemins : une liste de chemins d'une tourn�e
	 */
	public Tournee(ArrayList<Chemin> uneListeChemins) {
		this.listeChemins = uneListeChemins;
	}
	
	/**
	 * Constructeur param�tre de la Tourn�e en utilisant une tourn�e existante
	 * @param t : une tourn�e existante
	 */
	public Tournee(Tournee t) {
		listeChemins = t.listeChemins;
	}

	/**
	 * M�thode pour obtenir les chemins de cette tourn�e
	 * @return une liste des chemins
	 */
	public ArrayList<Chemin> getListeChemins() {
		return listeChemins;
	}

	
}
