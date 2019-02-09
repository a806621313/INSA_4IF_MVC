package vue.element;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/** 
 * La classe de la vue du tronçon
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class TronconVue extends Line{
	
	private String rueName;
	private Color originalColor;
	
	/**
	 * Constructeur de la vue tronçon
	 * @param startX : coordonnee X du point de debut de cette ligne
	 * @param startY : coordonnee Y du point de fin de cette ligne
	 * @param endX : coordonnee X du point de debut de cette ligne
	 * @param endY : coordonnee Y du point de fin de cette ligne
	 * @param name : le nom de rue auquel ce tronçon d'une tournée correspond
	 */
	public TronconVue(double startX, double startY, double endX, double endY, String name) {
		super(startX,startY,endX,endY);
		this.rueName = name;
		this.originalColor = Color.web("0xaaaaaa",1.0);
		this.setStroke(Color.web("0xaaaaaa",1.0));
		this.setStrokeWidth(3);
		ajouterListner();
	}
	
	/**
	 * Méthode pour ajouter des listeners
	 */
	public void ajouterListner() {
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent event) {
                changerCouleurSelectionnee();
            }
        });
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent event) {
            	changerCouleurNonSelectionnee();
            }
        });
	}
	
	/**
	 * Méthode pour changer la couleur de la ligne qui correspond à un tronçon en mode selectionné
	 */
	public void changerCouleurSelectionnee() {
		this.setStroke(Color.ORANGE);
	}
	
	/**
	 * Méthode pour changer la couleur  de cette ligne qui correspond à un tronçon en mode non selectionné
	 */
	public void changerCouleurNonSelectionnee() {
		this.setStroke(originalColor);
	}
	
	/**
	 * Méthode pour mettre la couleur originale de cette TronconVue
	 * @param c : une couleur.
	 */
	public void setOriginalColor(Color c) {
		this.originalColor = c;
	}
	
	/**
	 * Methode pour retourner le nom de la rue auquel ce troncon correspond
	 * @return le nom de rue correspondant
	 */
	public String getNomRue() {
		return rueName;
	}
}
