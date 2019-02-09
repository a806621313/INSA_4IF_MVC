package vue.element;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/** 
 * La classe de la vue de la trounee.
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class TourneeVue extends Line{
	
	private String rueName;
	private Color originalColor;
	
	/**
	 * Constructeur de la vue tournee
	 * @param startX : coordonnee X du point de debut de cette ligne
	 * @param startY : coordonnee Y du point de fin de cette ligne
	 * @param endX : coordonnee X du point de debut de cette ligne
	 * @param endY : coordonnee Y du point de fin de cette ligne
	 * @param name : le nom de rue auquel ce troncon d'une tournee correspond
	 * @param couleur : la couleur choisie
	 */
	public TourneeVue(double startX, double startY, double endX, double endY, String name, Color couleur) {
		super(startX,startY,endX,endY);
		this.rueName = name;
		this.originalColor = couleur;
		this.setStroke(couleur);
		this.setStrokeWidth(2);
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
	 * Méthode pour changer la couleur de cette ligne correspondant à un tronçon d'une tournée en mode selectionnee
	 */
	public void changerCouleurSelectionnee() {
		this.setStroke(Color.ORANGE);
	}
	
	/**
	 * Méthode pour changer la couleur de cette ligne correspondant à un tronçon d'une tournée en mode non selectionnee
	 */
	public void changerCouleurNonSelectionnee() {
		this.setStroke(originalColor);
	}
	
	/**
	 * Méthode pour mettre la couleur originale de cette TourneeVue
	 * @param c : une couleur
	 */
	public void setOriginalColor(Color c) {
		this.originalColor = c;
	}
	
	/**
	 * Méthode pour retourner le nom de rue auquel ce troncon d'une tournee correspond
	 * @return le nom de rue correspondant
	 */
	public String getNomRue() {
		return rueName;
	}
}
