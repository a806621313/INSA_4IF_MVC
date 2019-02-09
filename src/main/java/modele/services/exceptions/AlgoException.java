package modele.services.exceptions;

/** 
 * La classe des exceptions d'algorithmes
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class AlgoException extends Exception {

	/**
	 * Constructeur de la classe AlgoException
	 * @param msg message de l'exception
	 */
	public AlgoException(String msg){
        super(msg);
    }
}