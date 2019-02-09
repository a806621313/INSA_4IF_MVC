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
	 * Réinitialiser l´historique
	 */
	public void clear() {
		listeDeCommande.clear();
		indice = -1;
	}

	/**
	 * Méthode pour retourner l'indice actuel de commande de l'historique
	 * @return l'indice actuel
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * Méthode pour ajouter une commande dans l'historique
	 * 
	 * @param cmd commande à ajouter
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
	 * Méthode pour annuler la commande courante
	 */
	public void annuler() {
		if (indice >= 0) {
			Commande cmd = listeDeCommande.get(indice);
			indice--;
			cmd.undoCmd();
		}
	}

	/**
	 * Méthode pour effectuer la commande courante
	 */
	public void refaire() {
		if (indice < listeDeCommande.size() - 1) {
			indice++;
			Commande cmd = listeDeCommande.get(indice);
			cmd.doCmd();
		}
	}

	/**
	 * Méthode pour réinitialiser l'historique
	 */
	public void reinitialiser() {
		this.listeDeCommande = new LinkedList();
		this.indice = -1;
	}

	/**
	 * Méthode pour retourner la longueur de l'historique
	 * 
	 * @return la longueur de l'historique
	 */
	public int getLongueur() {
		return listeDeCommande.size();
	}

}
