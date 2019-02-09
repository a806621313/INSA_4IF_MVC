package modele.services.exceptions;

/**
 * La classe des exceptions lors de l'annulation de la dernière demande de livraison
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UndoRedoNoPointDLException extends Exception {
	static String EXCEPTION_MESSAGE = "Tous les points de livraisons ont ete supprimes, vous ne pouvez annuler la suppression.";

	/**
	 * Constructeur de la classe UndoRedoNoPointDLException
	 * L'exception est lancée quand les actions Annuler/Refaire sont irréalisables
	 */
	public UndoRedoNoPointDLException() {
		super(EXCEPTION_MESSAGE);
	}
}
