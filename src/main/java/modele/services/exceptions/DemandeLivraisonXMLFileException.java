package modele.services.exceptions;
/** 
 * La classe des exceptions lorsqu'on lire une fichier XML ne correspondant pas à une demande de livraison
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

@SuppressWarnings("serial")
public class DemandeLivraisonXMLFileException extends Exception {
	static String EXCEPTION_MESSAGE = "Le fichier XML ne correspond pas a une demande de livraison.";
	
	/**
	 * Constructeur de la classe DemandeLivraisonXMLFileException.
	 * L'exception est lancée quand le fichier XML ne correspond pas à une demande de livraison
	 */
	public DemandeLivraisonXMLFileException(){
        super(EXCEPTION_MESSAGE);
    }
}
