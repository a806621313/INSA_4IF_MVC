package modele.services.exceptions;

/**
 * La classe pour traiter l'exception de lecture d'un fichier XML mal formée
 * @author H4404
 *
 */
@SuppressWarnings("serial")
public class XMLMalFormeException extends Exception {
	static String EXCEPTION_MESSAGE = "Le fichier XML contient une erreur.(mal forme)";
	
	/**
	 * Constructeur de la classe XMLMalFormeException
	 * L'exception est lancée quand le fichier XML est mal formée
	 */
	public XMLMalFormeException(){
		super(EXCEPTION_MESSAGE);
    }
}