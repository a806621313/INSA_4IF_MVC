package vue.element;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** 
 * La classe de la vue de l'entrepôt
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class EntrepotVue extends Circle{
	
	private long idEntrepot;
	
	/**
	 * Contructeur de la vue de l'entrepôt
	 * @param x : coordonnee X de ce cercle
	 * @param y : coordonnee Y de ce cercle
	 * @param radius : rayon de ce cercle
	 * @param unId : l'id de l'objet Entrepôt auquel ce cercle correspond
	 */
	public EntrepotVue(double x, double y, double radius, long unId) {
		super(x,y,radius);
		this.setFill(Color.RED);
		this.idEntrepot = unId;
	}
	
	/**
	 * Méthode pour obtenir l'id de l'entrepôt auquel cette vue correspond
	 * @return l'id de l'entrepôt auquel cette vue correspond
	 */
	public long getIntersectionId() {
		return idEntrepot;
	}
	
}
