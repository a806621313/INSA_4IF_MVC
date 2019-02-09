package vue.element;

import controleur.Controleur;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** 
 * La classe de la vue de l'intersection normale
 * @author H4404
 * @version 1.0
 * @since 1.0
*/
public class IntersectionNormalVue extends Circle{
	
	private long idInter;
	private boolean selectionnee;
	private Color originalColor;
	private double copieX;
	private double copieY;
	
	/**
	 * Constructeur de la vue de l'intersection normale
	 * @param x : coordonnee X de ce cercle
	 * @param y : coordonnee Y de ce cercle
	 * @param radius : rayon de ce cercle
	 * @param unId : l'id de l'objet IntersectionNormal auquel ce cercle correspond
	 */
	public IntersectionNormalVue(double x, double y, double radius, long unId) {
		super(x,y,radius);
		this.setFill(Color.BLACK);
		this.originalColor = Color.BLACK;
		this.idInter = unId;
		this.selectionnee = false;
		this.copieX = x;
		this.copieY = y;
		ajouterListener();
	}
	
	/**
	 * Méthode pour obtenir l'id de l'objet IntersectionNormal auquel ce cercle correspond
	 * @return l'id de l'objet IntersectionNormal auquel ce cercle correspond
	 */
	public long getIntersectionId() {
		return idInter;
	}
	
	/**
	 * Méthode pour verifier si l'IntersectionVue est selectionnée
	 * @return true si selectionnée, sinon false
	 */
	public boolean isSelectionnee() {
		return selectionnee;
	}

	/**
	 * Méthode pour renseigner la valeur du boolean selectionnee de cette vue
	 * @param selectionnee : un boolean
	 */
	public void setSelectionnee(boolean selectionnee) {
		this.selectionnee = selectionnee;
	}

	/**
	 * Méthode pour ajouter des listeners nécessaires
	 */
	public void ajouterListener() {
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
	 * Méthode pour ajouter des listeners nécessaires
	 */
	public void ajouterListenerOnClick() {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent event) {
                try {
                	if(Controleur.getInstance().getEtatCourant().getClass().getSimpleName().equals("EtatDemandeLivraison")) {
                		Controleur.getInstance().getMaDemande().ajouterPoint(idInter,
            					Controleur.getInstance().reverseTransformLatitude(copieY, Controleur.getInstance().getGraph().getLargeur()),
            					Controleur.getInstance().reverseTransformLongitude(copieX, Controleur.getInstance().getGraph().getHauteur())
            					);
                	}else {
                		
                	}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  
        });
	}
	
	/**
	 * Méthode pour changer la couleur de cette vue en mode selectionnee
	 */
	public void changerCouleurSelectionnee() {
		this.setFill(Color.YELLOW);
	}
	
	/**
	 * Méthode pour changer la couleur de cette vue en mode non selectionnee
	 */
	public void changerCouleurNonSelectionnee() {
		this.setFill(originalColor);
	}
}
