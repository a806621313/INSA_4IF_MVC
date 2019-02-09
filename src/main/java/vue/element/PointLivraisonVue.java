package vue.element;

import controleur.Controleur;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** 
 * La classe de la vue du point de livraison
 * @author H4404
 * @version 1.0
 * @since 1.0
*/
public class PointLivraisonVue extends Circle{
	
	private long idPointLivraison;
	private boolean selectionnee;
	private boolean synchronisee;
	private boolean activeChangerCouleurSelectionne;
	private Color originalColor;
	
	
	/**
	 * Constructeur de la vue du point de livraison
	 * @param x : coordonnee X de ce cercle
	 * @param y : coordonnee Y de ce cercle
	 * @param radius : rayon de ce cercle
	 * @param unId : l'id de l'objet PointLivraison auquel ce cercle correspond
	 */
	public PointLivraisonVue(double x, double y, double radius, long unId) {
		super(x,y,radius);
		this.setFill(Color.BLUE);
		this.originalColor = Color.BLUE;
		this.idPointLivraison = unId;
		this.selectionnee = false;
		this.synchronisee = false;
		activeChangerCouleurSelectionne = true;
		ajouterListener();
	}
	
	/**
	 * Méthode pour mettre la valeur du boolean activeChangerCouleurSelectionne
	 * (le boolean qui décide si le changement de couleur est actif)
	 * @param activeChangerCouleurSelectionne : un boolean
	 */
	public void setActiveChangerCouleurSelectionne(boolean activeChangerCouleurSelectionne) {
		this.activeChangerCouleurSelectionne = activeChangerCouleurSelectionne;
	}

	/**
	 * Méthode pour obtenir l'id du point de livraison auquel cette vue correspond
	 * @return l'id du point de livraison auquel cette vue correspond
	 */
	public long getIntersectionId() {
		return idPointLivraison;
	}

	/**
	 * Méthode pour verifier si le PointLivraisonVue est selectionné
	 * @return true si selectionnee, sinon false
	 */
	public boolean isSelectionnee() {
		return selectionnee;
	}
	/**
	 * Méthode pour verifier si le PointLivraisonVue est synchronisee
	 * @return true si synchronisee, sinon false.
	 */
	public boolean isSynchronisee() {
		return synchronisee;
	}

	/**
	 * Méthode pour mettre la valeur du boolean synchronisee de cette vue
	 * @param bool : un boolean
	 */
	public void setSynchronisee(boolean bool) {
		synchronisee = bool;
	}
	
	/**
	 * Change la valeur de l'angle de ce PointLivraisonVue
	 * @param radius  la nouvelle valeur du radius
	 */
	public void changeRadius(double radius) {
		this.setRadius(radius);
	}
	
	/**
	 * Méthode pour mettre la valeur du boolean selectionnée de cette vue
	 * @param selectionnee : un boolean.
	 */
	public void setSelectionnee(boolean selectionnee) {
		this.selectionnee = selectionnee;
	}
	
	/**
	 * Méthode pour mettre cette vue qui représente un point de livraison en mode synchronise
	 */
	public void changerFormeSynchronise() {
		synchronisee = true;
		this.setRadius(8);
		this.setFill(Color.ORANGE);
		this.originalColor = Color.ORANGE;
	}
	
	/**
	 * Méthode pour mettre la couleur originale de cette TourneeVue
	 * @param couleur : une couleur.
	 */
	public void setOriginalColor(Color couleur) {
		this.originalColor = couleur;
	}
	
	/**
	 * Méthode pour ajouter des listeners
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
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
	        public void handle(final MouseEvent event) {
	            System.out.println(idPointLivraison);
	            try {
	            	if(Controleur.getInstance().getEtatCourant().getClass().getSimpleName().equals("EtatDemandeLivraison")) {
	            		//Controleur.getInstance().getMaDemande().supprimerPoint(idPointLivraison);
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
	 * Méthode pour changer la couleur du cercle correspondant à un point de livraison en mode selectionnee
	 */
	public void changerCouleurSelectionnee() {
		if(activeChangerCouleurSelectionne == true) {
			this.setFill(Color.YELLOW);
		}
	}
	
	/**
	 * Méthode pour changer la couleur de ce cercle qui correspond a un point de livraison en mode non selectionnee
	 */
	public void changerCouleurNonSelectionnee() {
		if(activeChangerCouleurSelectionne == true) {
			this.setFill(originalColor);
		}
	}

	
	
}
