package vue.element;

import javafx.scene.shape.Path;
import javafx.util.Duration;
import vue.VueGraphique;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.Node;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


/**
 * La classe de l'algorithme VeloVue
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */
public class VeloVue extends Circle{

	private Path monPath;
	private PathTransition maTransition;
	private VueGraphique parent;
	
	/**
	 * Constructeur de la classe VeloVue
	 * @param unParent : la vue graphique qui contient cet objet
	 */
	public VeloVue(VueGraphique unParent) {
		super(7);
		monPath = new Path();
		parent = unParent;
	}
	
	/**
	 * Méthode pour créer un chemin d'animation
	 * @param tournees : le groupe qui contient la tournée pour laquelle nous voulons créer un chemin
	 * @param animationGroup : le groupe qui affiche l'animation
	 */
	public void creerPath(Group tournees, Group animationGroup) {
		monPath = new Path();
		monPath.getElements().clear();
		this.setFill(Color.DARKSALMON);
		int index = 0;
		for(Node e : tournees.getChildren()) {
			TourneeVue temp = (TourneeVue)e;
			if(index == 0) {
				monPath.getElements().add(new MoveTo(temp.getStartX(),temp.getStartY()));
			}
			monPath.getElements().add(new LineTo(temp.getEndX(),temp.getEndY()));
			index++;
		}
		maTransition = new PathTransition();
		maTransition.setDuration(Duration.seconds(4.0));
		maTransition.setDelay(Duration.seconds(.5));
		maTransition.setPath(monPath);
		maTransition.setNode(this);
		maTransition
	        .setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
	}
	
	/**
	 * Méthode pour lancer l'animation
	 * @param animationGroup : le groupe qui affiche l'animation
	 */
	public void start(Group animationGroup) {
		animationGroup.getChildren().add(this);
		animationGroup.getChildren().add(monPath);
		maTransition.play();
		maTransition.setOnFinished(new EventHandler<ActionEvent>() {  
			  
            @Override  
            public void handle(ActionEvent arg0) {  
                animationGroup.getChildren().clear();
                parent.setAnimaStart(false);
            }  
        });  
	}
}
