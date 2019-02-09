package modele.services.exceptions;

/**
 * La classe des exceptions lorsqu'on ajoute un point inatteignable
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
public class IntersectionNonLivrableException extends Exception {
	static String EXCEPTION_MESSAGE = "Un des points de livraison n'est pas livrable..";

	/**
	 * Constructeur de la classe IntersectionNonLivrableException.
	 * L'exception est lancée quand un point de livraison est inatteignable
	 */
	public IntersectionNonLivrableException() {
		super(EXCEPTION_MESSAGE);
	}
}
