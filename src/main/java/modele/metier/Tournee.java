package modele.metier;

import java.util.ArrayList;

/** 
 * La classe de la tournée
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class Tournee {
	
	
	
	private ArrayList<Chemin> listeChemins;
	
	/**
	 * Constructeur paramètre de la Tournée en utilisant une liste des chemins
	 * @param uneListeChemins : une liste de chemins d'une tournée
	 */
	public Tournee(ArrayList<Chemin> uneListeChemins) {
		this.listeChemins = uneListeChemins;
	}
	
	/**
	 * Constructeur paramètre de la Tournée en utilisant une tournée existante
	 * @param t : une tournée existante
	 */
	public Tournee(Tournee t) {
		listeChemins = t.listeChemins;
	}

	/**
	 * Méthode pour obtenir les chemins de cette tournée
	 * @return une liste des chemins
	 */
	public ArrayList<Chemin> getListeChemins() {
		return listeChemins;
	}

	
}
