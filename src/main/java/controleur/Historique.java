package controleur;

import java.util.LinkedList;

/**
 * La classe de l'historique du commande
 * 
 * @author H4404
 */
public class Historique {
	protected LinkedList<Commande> listeDeCommande;
	protected int indice;

	/**
	 * Constructeur de la classe Historique
	 */
	public Historique() {
		listeDeCommande = new LinkedList<Commande>();
		indice = -1;

	}

	/**
	 * R�initialiser l�historique
	 */
	public void clear() {
		listeDeCommande.clear();
		indice = -1;
	}

	/**
	 * M�thode pour retourner l'indice actuel de commande de l'historique
	 * @return l'indice actuel
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * M�thode pour ajouter une commande dans l'historique
	 * 
	 * @param cmd commande � ajouter
	 */
	public void ajouteCmd(Commande cmd) {
		int i = indice + 1;
		if (i < listeDeCommande.size()) {
			listeDeCommande.remove(i);
		}
		indice++;
		listeDeCommande.add(cmd);
		System.out.println("cmd list length:" + listeDeCommande.size());
	}

	/**
	 * M�thode pour annuler la commande courante
	 */
	public void annuler() {
		if (indice >= 0) {
			Commande cmd = listeDeCommande.get(indice);
			indice--;
			cmd.undoCmd();
		}
	}

	/**
	 * M�thode pour effectuer la commande courante
	 */
	public void refaire() {
		if (indice < listeDeCommande.size() - 1) {
			indice++;
			Commande cmd = listeDeCommande.get(indice);
			cmd.doCmd();
		}
	}

	/**
	 * M�thode pour r�initialiser l'historique
	 */
	public void reinitialiser() {
		this.listeDeCommande = new LinkedList();
		this.indice = -1;
	}

	/**
	 * M�thode pour retourner la longueur de l'historique
	 * 
	 * @return la longueur de l'historique
	 */
	public int getLongueur() {
		return listeDeCommande.size();
	}

}
