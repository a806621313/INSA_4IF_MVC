package modele.algo;

public class Paire {
	private int numeroLivraison;
	private int cout;
	
	/**
	 * Constructeur de la classe Paire
	 * @param cout : co�t entre deux points de livraison
	 * @param numero : le num�ro du point de livraison �tant la destination
	 */
	public Paire(int cout, int numero) {
		this.cout = cout;
		this.numeroLivraison = numero;
	}

	//Getters et Setters
	public int getNumeroLivraison() {
		return numeroLivraison;
	}

	public void setNumeroLivraison(int numeroLivraison) {
		this.numeroLivraison = numeroLivraison;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}
	
	
}
