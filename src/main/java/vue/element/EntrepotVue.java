package vue.element;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** 
 * La classe de la vue de l'entrep�t
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class EntrepotVue extends Circle{
	
	private long idEntrepot;
	
	/**
	 * Contructeur de la vue de l'entrep�t
	 * @param x : coordonnee X de ce cercle
	 * @param y : coordonnee Y de ce cercle
	 * @param radius : rayon de ce cercle
	 * @param unId : l'id de l'objet Entrep�t auquel ce cercle correspond
	 */
	public EntrepotVue(double x, double y, double radius, long unId) {
		super(x,y,radius);
		this.setFill(Color.RED);
		this.idEntrepot = unId;
	}
	
	/**
	 * M�thode pour obtenir l'id de l'entrep�t auquel cette vue correspond
	 * @return l'id de l'entrep�t auquel cette vue correspond
	 */
	public long getIntersectionId() {
		return idEntrepot;
	}
	
}
