package vue.element;

import javafx.scene.control.TitledPane;

/** 
 * La classe de la vue de du panel livraison
 * @author H4404
 * @version 1.0
 * @since 1.0
*/
public class LivraisonPane extends TitledPane{
	private long id;
	
	/**
	 * Constructeur de la classe LivraisonPane
	 * @param unId : l'id du point de livraison auquel ce panneau correspond
	 */
	public LivraisonPane(long unId) {
		super();
		this.id = unId;
	}
	
	/**
	 * Méthode pour obtenir l'id du point de livraison auquel ce panneau correspond
	 * @return l'id du point de livraison auquel ce panneau correspond
	 */
	public long getLivraisonId() {
		return this.id;
	}
}
