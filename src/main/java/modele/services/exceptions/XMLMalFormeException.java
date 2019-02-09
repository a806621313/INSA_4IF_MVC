package modele.services.exceptions;

/**
 * La classe pour traiter l'exception de lecture d'un fichier XML mal form�e
 * @author H4404
 *
 */
@SuppressWarnings("serial")
public class XMLMalFormeException extends Exception {
	static String EXCEPTION_MESSAGE = "Le fichier XML contient une erreur.(mal forme)";
	
	/**
	 * Constructeur de la classe XMLMalFormeException
	 * L'exception est lanc�e quand le fichier XML est mal form�e
	 */
	public XMLMalFormeException(){
		super(EXCEPTION_MESSAGE);
    }
}