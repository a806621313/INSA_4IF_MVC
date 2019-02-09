package vue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import org.joda.time.DateTime;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import modele.TourneeManager;
import modele.metier.Chemin;
import modele.metier.DemandeLivraison;
import modele.metier.Entrepot;
import modele.metier.Intersection;
import modele.metier.PointLivraison;
import modele.metier.Tournee;
//import vue.element.LivraisonPane;
import vue.element.LivraisonPane;

/**
 * La classe de la vue textuelle
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

public class VueTextuelle extends Parent implements Observer {

	private VueGraphique compagnie;
	private ApplicationDemo parent;
	private Label monLabel;
	private TabPane infos;
	private Tab nomRue;
	private VBox infoGeneral;
	private GridPane lesFormesSens;
	private Tab infosLivraison;
	private Tab infosTournee;
	private TitledPane filtreTournees;
	private GridPane conteneurFiltres;
	private Label infoPourIntersection;
	private Accordion conteneurInfoParTournee;
	private CheckBox[] lesFiltres = null;
	private TitledPane[] infoParTournee = null;
	private TitledPane infosTournees;
	private LivraisonPane[] infoParLivraison = null;
	private ScrollPane scrollLivraison;
	private VBox conteneurLivraison;

	/**
	 * Constructeur de la vue textuelle
	 * @param unParent : l'application qui contient cette vue textuelle
	 */
	public VueTextuelle(ApplicationDemo unParent) {
		// Intialisation de sa compagnie par defaut
		compagnie = null;
		parent = unParent;

		// Creation du separateur avec la vue graphique
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		separator.setMinHeight(1200);
		separator.setMaxWidth(20);
		separator.setLayoutX(300);
		infos = new TabPane();
		GridPane conteneurNomRue = new GridPane();
		monLabel = new Label("Bienvenue sur PLD Agile.");
		monLabel.setLayoutX(0);
		monLabel.setLayoutY(0);
		monLabel.setMaxWidth(300);
		monLabel.setMinWidth(300);
		monLabel.setWrapText(true);
		monLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		Label tempLabelRue = new Label("Nom de la rue choisie:");
		tempLabelRue.setLayoutX(0);
		tempLabelRue.setLayoutY(0);
		tempLabelRue.setMaxWidth(300);
		tempLabelRue.setMinWidth(300);
		tempLabelRue.setWrapText(true);
		tempLabelRue.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		conteneurNomRue.add(tempLabelRue, 1, 1);
		conteneurNomRue.add(monLabel, 1, 2);
		infoGeneral = new VBox();
		lesFormesSens = new GridPane();
		Circle signeLivraison = new Circle(8);
		signeLivraison.setFill(Color.BLUE);
		lesFormesSens.add(signeLivraison, 1, 1);
		lesFormesSens.add(new Label("Point de livraison"), 2, 1);
		Circle signeEntrepot = new Circle(8);
		signeEntrepot.setFill(Color.RED);
		lesFormesSens.add(signeEntrepot, 1, 2);
		lesFormesSens.add(new Label("Entrepot"), 2, 2);
		Circle signeSyncronise = new Circle(8);
		signeSyncronise.setFill(Color.ORANGE);
		lesFormesSens.add(signeSyncronise, 1, 3);
		lesFormesSens.add(new Label("Point synchronise"), 2, 3);
		Circle signeNoeudNormal = new Circle(8);
		signeNoeudNormal.setFill(Color.BLACK);
		lesFormesSens.add(signeNoeudNormal, 1, 4);
		lesFormesSens.add(new Label("Point normal"), 2, 4);
		Separator sep = new Separator();
		sep.setMinWidth(300);
		sep.setMaxWidth(300);
		Separator sep2 = new Separator();
		sep2.setMinWidth(300);
		sep2.setMaxWidth(300);
		GridPane conteneurInfo = new GridPane();
		infoPourIntersection = new Label("");
		infoPourIntersection.setLayoutX(0);
		infoPourIntersection.setLayoutY(0);
		infoPourIntersection.setMaxWidth(300);
		infoPourIntersection.setMinWidth(300);
		infoPourIntersection.setWrapText(true);
		infoPourIntersection.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		Label tempLabelIntersection = new Label("Info sur l'intersection cliquee(Clic droit):");
		tempLabelIntersection.setLayoutX(0);
		tempLabelIntersection.setLayoutY(0);
		tempLabelIntersection.setMaxWidth(300);
		tempLabelIntersection.setMinWidth(300);
		tempLabelIntersection.setWrapText(true);
		tempLabelIntersection.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		conteneurInfo.add(tempLabelIntersection, 1, 1);
		conteneurInfo.add(infoPourIntersection, 1, 2);
		infoGeneral.getChildren().addAll(conteneurNomRue, sep, lesFormesSens,sep2,conteneurInfo);
		// Creation des Tabs
		nomRue = new Tab();
		nomRue.setClosable(false);
		nomRue.setContent(infoGeneral);
		nomRue.setText("Info General");

		infosLivraison = new Tab();
		infosLivraison.setText("Livraisons");
		infosLivraison.setClosable(false);

		infosTournee = new Tab();
		infosTournee.setText("Tournees");
		ScrollPane scroll = new ScrollPane();
		scroll.setMaxHeight(800);
		scroll.setMinHeight(800);
		Accordion conteneur = new Accordion();
		filtreTournees = new TitledPane();
		filtreTournees.setText("Filtre");
		filtreTournees.setMaxWidth(300);
		filtreTournees.setMinWidth(300);
		conteneurFiltres = new GridPane();
		conteneurFiltres.setVgap(4);
		conteneurFiltres.setPadding(new Insets(5, 5, 5, 5));
		filtreTournees.setContent(conteneurFiltres);
		infosTournees = new TitledPane();
		infosTournees.setMaxWidth(300);
		infosTournees.setMinWidth(300);
		infosTournees.setText("Infos");
		conteneurInfoParTournee = new Accordion();
		infosTournees.setContent(conteneurInfoParTournee);
		conteneur.getPanes().addAll(filtreTournees, infosTournees);
		scroll.setContent(conteneur);
		infosTournee.setContent(scroll);

		infos.getTabs().addAll(nomRue, infosLivraison, infosTournee);

		scrollLivraison = new ScrollPane();
		scrollLivraison.setMaxHeight(800);
		scrollLivraison.setMinHeight(800);
		conteneurLivraison = new VBox();
		scrollLivraison.setContent(conteneurLivraison);
		infosLivraison.setContent(scrollLivraison);

		this.getChildren().add(infos);
		this.getChildren().add(separator);
	}

	/**
	 * Méthode qui fait la vue textuelle connaître la vue graphique qui l'accompagne
	 * @param vue : une vue graphique
	 */
	public void setCompagnie(VueGraphique vue) {
		this.compagnie = vue;
	}

	/**
	 * Méthode pour modifier le nom de rue affiché
	 * @param nomRue : un nom de rue
	 */
	public void setTabNomRue(String nomRue) {
		monLabel.setText(nomRue);
	}
	
	/**
	 * Méthode pour modifier la latitude et la lonfitude affichée
	 * @param texte : la latitude et la longitude à afficher
	 */
	public void setInfoIntersection(String texte) {
		infoPourIntersection.setText(texte);
	}
	
	/**
	 * Méthode qui rafraîchit le contenu de la vue textuelle selon le paramètre arg1 passé
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		String sujet = (String) arg1;
		int maximum;
		switch (sujet) {
		case "Plan":
			break;
		case "DemandeLivraison":
			ajouteTitledPane((DemandeLivraison) arg0);
			DemandeLivraison temp = (DemandeLivraison) arg0;
			maximum = temp.getNbLivreurMaximum();
			parent.setLabelNbLivreur(maximum);
			break;
		case "Tournees":
			ajouterTimeTableTournees((TourneeManager) arg0);
			ajouterFiltreTournees((TourneeManager) arg0);
			ajouterListeners();

			break;
		case "UniqueTournee":
			changerInfoTourneePane((TourneeManager) arg0);
			ajouteTitledPane(Controleur.getInstance().getMaDemande());
			try {
				maximum = Controleur.getInstance().getNbLivreurMaximum();
				parent.setLabelNbLivreur(maximum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			activerSynchronisationLivraison();
			break;
		case "SupprimerTournee":
			ajouterTimeTableTournees((TourneeManager) arg0);
			ajouterFiltreTournees((TourneeManager) arg0);
			ajouterListeners();
			ajouteTitledPane(Controleur.getInstance().getMaDemande());
			try {
				maximum = Controleur.getInstance().getNbLivreurMaximum();
				parent.setLabelNbLivreur(maximum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			activerSynchronisationLivraison();
			break;
		case "DeplacementSupprimerTournee":
			changerInfoTourneePaneSupprimer((TourneeManager) arg0);
			activerSynchronisationLivraison();
			break;
		case "DeplacementSansSupprimerTournee":
			changerInfoTourneePaneSansSupprimer((TourneeManager) arg0);
			activerSynchronisationLivraison();
			break;
		case "TourneesEtDemandeLivraison":
			ajouterTimeTableTournees((TourneeManager) arg0);
			ajouterFiltreTournees((TourneeManager) arg0);
			ajouterListeners();
			ajouteTitledPane(Controleur.getInstance().getMaDemande());
			try {
				maximum = Controleur.getInstance().getNbLivreurMaximum();
				parent.setLabelNbLivreur(maximum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Alert Temps":
			ajouterTimeTableTournees((TourneeManager) arg0);
			ajouterFiltreTournees((TourneeManager) arg0);
			ajouterListeners();

			break;

		}
	}

	/**
	 * Méthode qui ajoute les checkBoxs du filtre dans le panneau Tournee
	 * 
	 * @param manager : TourneeManager contenant la liste des tournées
	 */
	public void ajouterFiltreTournees(TourneeManager manager) {
		conteneurFiltres.getChildren().clear();
		int nbTournees = manager.getListeTournees().size();
		lesFiltres = new CheckBox[nbTournees + 1];
		for (int i = 0; i < nbTournees; i++) {
			CheckBox tempCheckBox = new CheckBox();
			tempCheckBox.setText("Tournee " + (i + 1));
			lesFiltres[i] = tempCheckBox;
			conteneurFiltres.add(tempCheckBox, 0, i);
		}
		CheckBox tempCheckBox = new CheckBox();
		tempCheckBox.setText("Toutes les tournees");
		lesFiltres[nbTournees] = tempCheckBox;
		conteneurFiltres.add(tempCheckBox, 0, nbTournees);
	}

	/**
	 * Méthode qui mettre à jour l'info d'une tournée
	 * 
	 * @param manager : TourneeManager qui contient la liste des tournées
	 */
	public void changerInfoTourneePane(TourneeManager manager) {
		int index = manager.getTourneeChangedIndex();
		DateTime depart = Controleur.getInstance().getActuelHeureDepart();
		Tournee t = manager.getListeTournees().get(index);
		Label tempLabel = new Label();
		tempLabel.setMaxWidth(300);
		tempLabel.setMinWidth(300);
		tempLabel.setWrapText(true);
		infoParTournee[index].setContent(tempLabel);
		ArrayList<Chemin> tempChemins = t.getListeChemins();
		DateTime tempTime = new DateTime(depart);
		String contenuLabel = "";
		for (Chemin c : tempChemins) {
			Intersection interDest = c.getIntersectionDest();
			Intersection interDepart = c.getIntersectionDepart();
			if (interDest instanceof Entrepot) {
				contenuLabel += "Depart du point de livraison(" + interDepart.getLatitude() + ","
						+ interDepart.getLongitude() + ") : ";
				contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
				tempTime = tempTime.plus(1000 * c.getDuree());
				contenuLabel += "Arrivee a l'entrepot";
				contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
			} else {
				if (interDepart instanceof Entrepot) {
					contenuLabel += "Depart de l'entrepot : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(1000 * c.getDuree());
					contenuLabel += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
							+ interDest.getLongitude() + ") : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(interDest.getDuree() * 1000);
				} else {
					contenuLabel += "Depart du point de livraison(" + interDepart.getLatitude() + ","
							+ interDepart.getLongitude() + ") : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(1000 * c.getDuree());
					contenuLabel += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
							+ interDest.getLongitude() + ") : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(interDest.getDuree() * 1000);
				}
			}
		}
		tempLabel.setText(contenuLabel);
	}

	/**
	 * Méthode qui met à jour l'info des tournées changées après le déplacement
	 * (Sans supprimer).
	 * 
	 * @param manager : TourneeManager qui contient la liste des tournées
	 */
	public void changerInfoTourneePaneSansSupprimer(TourneeManager manager) {
		int indexUn = manager.getTourneeAjouterIndex();
		int indexDeux = manager.getTourneeSupprimerIndex();
		DateTime depart = Controleur.getInstance().getActuelHeureDepart();
		Tournee t = manager.getListeTournees().get(indexUn);
		Label tempLabel = new Label();
		tempLabel.setMaxWidth(300);
		tempLabel.setMinWidth(300);
		tempLabel.setWrapText(true);
		infoParTournee[indexUn].setContent(tempLabel);
		ArrayList<Chemin> tempChemins = t.getListeChemins();
		DateTime tempTime = new DateTime(depart);
		String contenuLabel = "";
		for (Chemin c : tempChemins) {
			Intersection interDest = c.getIntersectionDest();
			Intersection interDepart = c.getIntersectionDepart();
			if (interDest instanceof Entrepot) {
				contenuLabel += "Depart du point de livraison(" + interDepart.getLatitude() + ","
						+ interDepart.getLongitude() + ") : ";
				contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
				tempTime = tempTime.plus(1000 * c.getDuree());
				contenuLabel += "Arrivee a l'entrepot";
				contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
			} else {
				if (interDepart instanceof Entrepot) {
					contenuLabel += "Depart de l'entrepot : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(1000 * c.getDuree());
					contenuLabel += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
							+ interDest.getLongitude() + ") : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(interDest.getDuree() * 1000);
				} else {
					contenuLabel += "Depart du point de livraison(" + interDepart.getLatitude() + ","
							+ interDepart.getLongitude() + ") : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(1000 * c.getDuree());
					contenuLabel += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
							+ interDest.getLongitude() + ") : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(interDest.getDuree() * 1000);
				}
			}
		}
		tempLabel.setText(contenuLabel);

		Tournee tDeux = manager.getListeTournees().get(indexDeux);
		Label tempLabelDeux = new Label();
		tempLabelDeux.setMaxWidth(300);
		tempLabelDeux.setMinWidth(300);
		tempLabelDeux.setWrapText(true);
		infoParTournee[indexDeux].setContent(tempLabelDeux);
		ArrayList<Chemin> tempCheminsDeux = tDeux.getListeChemins();
		DateTime tempTimeDeux = new DateTime(depart);
		String contenuLabelDeux = "";
		for (Chemin c : tempCheminsDeux) {
			Intersection interDest = c.getIntersectionDest();
			Intersection interDepart = c.getIntersectionDepart();
			if (interDest instanceof Entrepot) {
				contenuLabelDeux += "Depart du point de livraison(" + interDepart.getLatitude() + ","
						+ interDepart.getLongitude() + ") : ";
				contenuLabelDeux += tempTimeDeux.toString("MM-dd-yyyy hh:mm:ss") + "\n";
				tempTimeDeux = tempTimeDeux.plus(1000 * c.getDuree());
				contenuLabelDeux += "Arrivee a l'entrepot";
				contenuLabelDeux += tempTimeDeux.toString("MM-dd-yyyy hh:mm:ss") + "\n";
			} else {
				if (interDepart instanceof Entrepot) {
					contenuLabelDeux += "Depart de l'entrepot : ";
					contenuLabelDeux += tempTimeDeux.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTimeDeux = tempTimeDeux.plus(1000 * c.getDuree());
					contenuLabelDeux += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
							+ interDest.getLongitude() + ") : ";
					contenuLabelDeux += tempTimeDeux.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTimeDeux = tempTimeDeux.plus(interDest.getDuree() * 1000);
				} else {
					contenuLabelDeux += "Depart du point de livraison(" + interDepart.getLatitude() + ","
							+ interDepart.getLongitude() + ") : ";
					contenuLabelDeux += tempTimeDeux.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTimeDeux = tempTimeDeux.plus(1000 * c.getDuree());
					contenuLabelDeux += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
							+ interDest.getLongitude() + ") : ";
					contenuLabelDeux += tempTimeDeux.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTimeDeux = tempTimeDeux.plus(interDest.getDuree() * 1000);
				}
			}
		}
		tempLabelDeux.setText(contenuLabelDeux);
	}

	/**
	 * Méthode qui met à jour l'info des tournées changées après le déplacement
	 * (Quand on supprime une tournée).
	 * 
	 * @param manager : TourneeManager qui contient la liste des tournées
	 */
	public void changerInfoTourneePaneSupprimer(TourneeManager manager) {
		ajouterTimeTableTournees(manager);
		ajouterFiltreTournees(manager);
		ajouterListeners();

	}

	/**
	 * Méthode qui ajoute l'agenda planifié pour l'horaire dans le panneau infos
	 * 
	 * @param manager : TourneeManager qui contient la liste des tournées
	 */
	public void ajouterTimeTableTournees(TourneeManager manager) {
		conteneurInfoParTournee.getPanes().clear();
		ArrayList<Tournee> tournees = manager.getListeTournees();
		int nbTournees = tournees.size();
		DateTime depart = Controleur.getInstance().getActuelHeureDepart();
		infoParTournee = new TitledPane[nbTournees];
		int index = 0;
		for (Tournee t : tournees) {
			TitledPane tempPane = new TitledPane();
			tempPane.setText("Tournee" + (index + 1));
			tempPane.setMaxWidth(300);
			tempPane.setMinWidth(300);
			Label tempLabel = new Label();
			tempLabel.setMaxWidth(300);
			tempLabel.setMinWidth(300);
			tempLabel.setWrapText(true);
			tempPane.setContent(tempLabel);
			infoParTournee[index] = tempPane;
			conteneurInfoParTournee.getPanes().add(tempPane);
			ArrayList<Chemin> tempChemins = t.getListeChemins();
			DateTime tempTime = new DateTime(depart);
			String contenuLabel = "";
			for (Chemin c : tempChemins) {
				Intersection interDest = c.getIntersectionDest();
				Intersection interDepart = c.getIntersectionDepart();
				if (interDest instanceof Entrepot) {
					contenuLabel += "Depart du point de livraison(" + interDepart.getLatitude() + ","
							+ interDepart.getLongitude() + ") : ";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
					tempTime = tempTime.plus(1000 * c.getDuree());
					contenuLabel += "Arrivee a l'entrepot";
					contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
				} else {
					if (interDepart instanceof Entrepot) {
						contenuLabel += "Depart de l'entrepot : ";
						contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
						tempTime = tempTime.plus(1000 * c.getDuree());
						contenuLabel += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
								+ interDest.getLongitude() + ") : ";
						contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
						tempTime = tempTime.plus(interDest.getDuree() * 1000);
					} else {
						contenuLabel += "Depart du point de livraison(" + interDepart.getLatitude() + ","
								+ interDepart.getLongitude() + ") : ";
						contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
						tempTime = tempTime.plus(1000 * c.getDuree());
						contenuLabel += "Arrivee au point de Livraison(" + interDest.getLatitude() + ","
								+ interDest.getLongitude() + ") : ";
						contenuLabel += tempTime.toString("MM-dd-yyyy hh:mm:ss") + "\n";
						tempTime = tempTime.plus(interDest.getDuree() * 1000);
					}
				}
			}
			tempLabel.setText(contenuLabel);
			index++;
		}
	}

	/**
	 * Méthode qui ajoute des listeners nécéssaires pour les composants dans la vue textuelle
	 */
	public void ajouterListeners() {
		for (int i = 0; i < lesFiltres.length; i++) {
			lesFiltres[i].setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					for (int i = 0; i < infoParTournee.length; i++) {
						infoParTournee[i].setDisable(true);
					}

					CheckBox chk = (CheckBox) event.getSource();
					ArrayList<Integer> coches = new ArrayList<Integer>();
					boolean all = false;
					for (int i = 0; i < lesFiltres.length; i++) {
						if (lesFiltres[i].isSelected()) {
							if (lesFiltres[i].getText().equals("Toutes les tournees")) {
								all = true;
							} else {
								coches.add(i);
								infoParTournee[i].setDisable(false);
							}
						}
					}
					if (all == true) {
						coches.clear();
						for (int i = 0; i < infoParTournee.length; i++) {
							infoParTournee[i].setDisable(false);
							coches.add(i);
						}
					}
					compagnie.filtrerTournees(coches);
				}
			});
		}
	}

	/**
	 * Méthode qui efface les contenus de la vue textuelle
	 */
	public void clearVue() {
		conteneurFiltres.getChildren().clear();
		conteneurInfoParTournee.getPanes().clear();
		conteneurLivraison.getChildren().clear();
		infoPourIntersection.setText("");
		monLabel.setText("");
		parent.setInfo("");
	}
	
	/**
	 * Méthode qui ajoute les informations concernant les points de livraison dans la demande de livraison passée
	 * @param demande : une demande de livraison
	 */
	public void ajouteTitledPane(DemandeLivraison demande) {
		Collection<PointLivraison> lesPointLivraisons = demande.getAllPointLivraisons();
		conteneurLivraison.getChildren().clear();
		int index = 0;
		infoParLivraison = new LivraisonPane[lesPointLivraisons.size()];
		for (PointLivraison pointLivraison : lesPointLivraisons) {
			LivraisonPane tempPane = new LivraisonPane(pointLivraison.getId());

			tempPane.setText("Point de livraison" + index);
			tempPane.setMaxWidth(300);
			tempPane.setMinWidth(300);
			VBox content = new VBox();
			content.setMinWidth(300);
			content.setMaxWidth(300);

			Label label = new Label();
			String tempS = "";
			tempS += "Id :" + pointLivraison.getId() + "\n";
			tempS += "Latitude :" + pointLivraison.getLatitude() + "\n";
			tempS += "Longitude :" + pointLivraison.getLongitude() + "\n";
			tempS += "Duree :" + pointLivraison.getDuree() + " secondes \n";
			label.setText(tempS);
			label.setMinWidth(300);
			label.setMaxWidth(300);
			label.setWrapText(true);
			content.getChildren().add(label);
			tempPane.setContent(content);

			infoParLivraison[index] = tempPane;
			infoParLivraison[index].setExpanded(false);
			synchronisationLivraisonsVue(infoParLivraison[index]);
			index++;

		}
		conteneurLivraison.getChildren().addAll(infoParLivraison);
		scrollLivraison.setPrefViewportHeight(900);
		scrollLivraison.setContent(conteneurLivraison);
	}

	/**
	 * Méthode pour arrêter temporairement la synchronisation
	 */
	public void arreterSynchronisationLivraison() {
		for (int i = 0; i < infoParLivraison.length; i++) {
			infoParLivraison[i].setExpanded(false);
			infoParLivraison[i].setDisable(true);
		}
	}

	/**
	 * Méthode pour réactiver la synchronisation
	 */
	public void activerSynchronisationLivraison() {
		for (int i = 0; i < infoParLivraison.length; i++) {
			infoParLivraison[i].setDisable(false);
		}
	}

	/**
	 * Méthode pour synchroniser la vue graphique avec les panneaux des points de livraison
	 * 
	 * @param pane : panneau a synchroniser avec la vue graphique
	 */
	public void synchronisationLivraisonsVue(LivraisonPane pane) {
		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(final MouseEvent event) {
				MouseButton button = event.getButton();
				if (button.equals(MouseButton.PRIMARY)) {
					long id = pane.getLivraisonId();
					if (pane.isExpanded()) {
						compagnie.synchronisationLivraison(id, true);
					} else {
						compagnie.synchronisationLivraison(id, false);
					}
				}
			}
		});
	}

	/**
	 * Méthode pour synchroniser les panneaux des points de livraison avec la vue graphique
	 * 
	 * @param id : id du point de livraison (pour trouver le panneau correspondant)
	 * @param ordre : si ordre est true le pane est ouvert, sinon c'est fermee
	 */
	public void synchroniserLivraisonPane(long id, boolean ordre) {
		for (int i = 0; i < infoParLivraison.length; i++) {
			if (infoParLivraison[i].getLivraisonId() == id) {
				if (ordre) {
					infoParLivraison[i].setExpanded(true);
				} else {
					infoParLivraison[i].setExpanded(false);
				}
			}
		}
	}

}
