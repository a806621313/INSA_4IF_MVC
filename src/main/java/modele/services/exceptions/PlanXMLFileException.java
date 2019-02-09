package modele.services.exceptions;

/**
 * La classe des exceptions lors de la lecture d'un fichier XML ne correspondant pas � un plan
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class PlanXMLFileException extends Exception {
	static String EXCEPTION_MESSAGE = "Le fichier XML fourni ne correspond pas a un plan.";

	/**
	 * Constructeur de la classe PlanXMLFileException
	 * L'exception est lanc�e quand le fichier XML ne correspond pas � un plan
	 */
	public PlanXMLFileException() {
		super(EXCEPTION_MESSAGE);
	}
}
