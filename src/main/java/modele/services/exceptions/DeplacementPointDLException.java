package modele.services.exceptions;
/** 
 * La classe des exceptions lorsqu'on déplace un point
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class DeplacementPointDLException extends Exception {
	static String EXCEPTION_MESSAGE = "Un point de livraison ne peut pas etre deplace vers la meme tournee.";
	
	/**
	 * Constructeur de la classe DeplacementPointDLException
	 * L'exception est lancée quand le déplacement se passe au sein d'une même tournée
	 */
	public DeplacementPointDLException(){
        super(EXCEPTION_MESSAGE);
    }
}
