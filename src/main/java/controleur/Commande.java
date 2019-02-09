package controleur;

/**
 * L'interface commande.
 * @author H4404
 */
public interface Commande {

  /**
	 * Réaliser la commande.
	 */
	 void doCmd();
	
	 /**
	  * Annuler la commande.
	  */
	 void undoCmd();
  
}
