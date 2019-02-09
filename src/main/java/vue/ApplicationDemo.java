package vue;

import java.io.File;

import controleur.Controleur;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * La classe de la démonstration de l'application
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

enum ETAT {
	EtatInit, EtatPlanCharge, EtatDemandeLivraison, EtatPostCalcul, EtatAjouterChoixPointLivraison,
	EtatAjouterChoixNouvellePointLivraison, EtatSupprimerChoixPointLivraison, EtatChoixPointLivraisonADeplacer,
	EtatChoixPointLivraisonApresDeplacer;

}

public class ApplicationDemo extends Application {
	private VueGraphique graph;
	private VueTextuelle texte;
	private Button buttonChargePlan;
	private Button buttonChargeDemandeLivraison;
	private Button buttonCalculer;
	private Button buttonEffacer;
	private Button buttonEffacerDemande;
	private Button buttonSupprimerLivraison;
	private Button buttonAjouterLivraison;
	private Button buttonDeplacerLivraison;
	private Button buttonRedo;
	private Button buttonUndo;
	private Button buttonExport;

	private Label labelNombreLivreurs;
	private TextField textFieldnombreLivreur;
	private Label labelError;

	private Label labelDuree;
	private TextField textFieldDuree;
	private Label labelDureeError;

	private ComboBox<String> timeLimite;
	private ToggleGroup radioGroup;   
	private RadioButton modeSansClustering;   
	private RadioButton modeClustering;   

	private Label labelInfo;

	private MenuBar menuBar;
	private Menu menuFile;
	private Menu menuTournee;
	private Menu menuLivraison;
	private MenuItem itemChargerPlan;
	private MenuItem itemChargerDemandeLivraison;
	private MenuItem itemCalculerTournees;
	private MenuItem itemEffacer;
	private MenuItem itemEffacerDemande;
	private MenuItem itemAjouterLivraison;
	private MenuItem itemSupprimerLivraison;
	private MenuItem itemDeplacerLivraison;

	/**
	 * Méthode permettant de commencer l'application
	 * @param primaryStage : la fenêtre principale
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		graph = new VueGraphique(1400, 900, this);
		texte = new VueTextuelle(this);
		Controleur.getInstance().addObserver(graph, texte);
		Controleur.getInstance().setGraph(graph);
		Controleur.getInstance().setTexte(texte);
		graph.setCompagnie(texte);
		texte.setCompagnie(graph);
		BorderPane pane = new BorderPane();

		// vbox pour les buttons
		VBox vbox = new VBox(8); // spacing = 8
		vbox.setMinWidth(300);
		buttonChargePlan = new Button("Charger un plan");
		buttonChargePlan.setMinWidth(300);
		buttonChargePlan.setMaxWidth(300);
		buttonChargeDemandeLivraison = new Button("Charger une demande");
		buttonChargeDemandeLivraison.setMinWidth(300);
		buttonChargeDemandeLivraison.setMaxWidth(300);
		buttonCalculer = new Button("Calculer");
		buttonCalculer.setMinWidth(300);
		buttonCalculer.setMaxWidth(300);

		labelDuree = new Label("Duree(en seconde) :");
		labelDuree.setMinWidth(300);
		labelDuree.setMaxWidth(300);
		labelDuree.setWrapText(true);

		textFieldDuree = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (!text.matches("[a-z]")) {
					super.replaceText(start, end, text);
				}
			}

			@Override
			public void replaceSelection(String text) {
				if (!text.matches("[a-z]")) {
					super.replaceSelection(text);
				}
			}
		};

		textFieldDuree.setMinWidth(300);
		textFieldDuree.setMaxWidth(300);

		labelDureeError = new Label();
		labelDureeError.setMinWidth(300);
		labelDureeError.setMaxWidth(300);
		labelDureeError.setWrapText(true);
		labelDureeError.setTextFill(Color.web("#FF0000"));

		labelNombreLivreurs = new Label("Nombre de livreur:");
		labelNombreLivreurs.setMinWidth(300);
		labelNombreLivreurs.setMaxWidth(300);
		labelNombreLivreurs.setWrapText(true);

		textFieldnombreLivreur = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				if (!text.matches("[a-z]")) {
					super.replaceText(start, end, text);
				}
			}

			@Override
			public void replaceSelection(String text) {
				if (!text.matches("[a-z]")) {
					super.replaceSelection(text);
				}
			}
		};
		textFieldnombreLivreur.setMinWidth(300);
		textFieldnombreLivreur.setMaxWidth(300);

		labelError = new Label();
		labelError.setMinWidth(300);
		labelError.setMaxWidth(300);
		labelError.setWrapText(true);
		labelError.setTextFill(Color.web("#FF0000"));
		
		timeLimite = new ComboBox<String>();
		timeLimite.getItems().addAll("10 secondes",
			    "20 secondes",
			    "30 secondes",
			    "40 secondes",
			    "50 secondes",
			    "60 secondes",
			    "70 secondes",
			    "80 secondes",
			    "90 secondes",
			    "100 secondes",
			    "110 secondes",
			    "120 secondes");
		timeLimite.getSelectionModel().select(0);
		timeLimite.setMaxWidth(300);
		timeLimite.setMinWidth(300);
		GridPane conteneurMode = new GridPane();
		radioGroup = new ToggleGroup();   
		modeSansClustering = new RadioButton();
		modeSansClustering.setSelected(true);
		modeSansClustering.setText("Solution optimale      ");
		modeSansClustering.setToggleGroup(radioGroup);
		modeClustering = new RadioButton();
		modeClustering.setSelected(true);
		modeClustering.setText("Solution clustering");
		modeClustering.setToggleGroup(radioGroup);
		conteneurMode.add(modeSansClustering, 1, 1);
		conteneurMode.add(modeClustering, 2, 1);
		
		buttonEffacer = new Button("Effacer tout");
		buttonEffacer.setMinWidth(300);
		buttonEffacer.setMaxWidth(300);

		buttonEffacerDemande = new Button("Effacer les points de livraison");
		buttonEffacerDemande.setMinWidth(300);
		buttonEffacerDemande.setMaxWidth(300);

		buttonSupprimerLivraison = new Button("Supprimer un point de livraison");
		buttonSupprimerLivraison.setMinWidth(300);
		buttonSupprimerLivraison.setMaxWidth(300);

		buttonAjouterLivraison = new Button("Ajouter un point de livraison");
		buttonAjouterLivraison.setMinWidth(300);
		buttonAjouterLivraison.setMaxWidth(300);

		buttonDeplacerLivraison = new Button("Deplacer un point de livraison");

		buttonDeplacerLivraison.setMinWidth(300);
		buttonDeplacerLivraison.setMaxWidth(300);

		buttonRedo = new Button("Refaire");
		buttonRedo.setMinWidth(300);
		buttonRedo.setMaxWidth(300);

		buttonUndo = new Button("Annuler");
		buttonUndo.setMinWidth(300);
		buttonUndo.setMaxWidth(300);

		
		buttonExport =  new Button("Export en feuille de route");
		buttonExport.setMinWidth(300);
		buttonExport.setMaxWidth(300);
		
		labelInfo = new Label();
		labelInfo.setMinWidth(300);
		labelInfo.setMaxWidth(300);
		labelInfo.setWrapText(true);
		//Ajout de la barre de menu
        Controleur.getInstance().setEtat(Controleur.getInstance().getEtatInit());
        AjouterBarreNavigateur(pane,primaryStage,Controleur.getInstance());


		Separator separator = new Separator();
		separator.setMinWidth(300);
		separator.setMaxWidth(300);

		vbox.getChildren().addAll(buttonChargePlan, buttonChargeDemandeLivraison, labelNombreLivreurs,
				textFieldnombreLivreur, labelError, new Label("Limite de temps : "), timeLimite, conteneurMode,buttonCalculer,buttonAjouterLivraison, labelDuree, textFieldDuree,
				labelDureeError, buttonSupprimerLivraison, buttonDeplacerLivraison, buttonEffacer, buttonEffacerDemande,
				buttonRedo, buttonUndo, buttonExport, separator, labelInfo);

		// Ajout de la barre de menu
		Controleur.getInstance().setEtat(Controleur.getInstance().getEtatInit());
		AjouterBarreNavigateur(pane, primaryStage, Controleur.getInstance());

		VerifierEtat(Controleur.getInstance());

		// Placement de differents vues
		pane.setLeft(texte);
		pane.setCenter(graph);
		pane.setRight(vbox);
		BorderPane.setAlignment(graph, Pos.TOP_CENTER);
		BorderPane.setMargin(graph, new Insets(30, 0, 0, 0));
		BorderPane.setMargin(vbox, new Insets(30, 0, 0, 0));

		// Creation de la scene
		Scene scene = new Scene(pane, 1450, 900);
		scene.setFill(Color.BLACK);

		primaryStage.setScene(scene);
		primaryStage.setTitle("PLD Agile");
		primaryStage.show();

	}

	/**
	 * Méthode pour obtenir la durée saisie par l'utilisateur
	 * @return la durée que l'utilisateur a saisi
	 */
	public int getDuree() {
		String text = textFieldDuree.getText();
		if (text.equals("")) {
			labelDureeError.setText("Entrer une valeur, s'il vous plait.");
			return Integer.MAX_VALUE;
		} else {
			labelDureeError.setText("");
			return Integer.parseInt(text);
		}
	}

	/**
	 * Méthode pour modifier l'information dans le labelInfo
	 * 
	 * @param texte : info a mettre dans le labelInfo
	 */
	public void setInfo(String texte) {
		labelInfo.setTextFill(Color.BLACK);
		labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		labelInfo.setText(texte);
	}
	
	/**
	 * Méthode pour retourner labelInfo de la fenêtre
	 * @return label : labelInfo de cette fenêtre
	 */
	public Label getLabelInfo() {
		return labelInfo;
	}

	/**
	 * Méthode pour ajouter la barre de navigation
	 * 
	 * @param pane : pane dans IHM
	 * @param primaryStage : la fenêtre de l'application
	 * @param controleur : un controleur
	 */
	public void AjouterBarreNavigateur(BorderPane pane, Stage primaryStage, Controleur controleur) {

		menuBar = new MenuBar();

		// Ajout de l'onglet Fichiers
		menuFile = new Menu("Fichiers");
		itemChargerDemandeLivraison = new MenuItem("Charger DemandeLivraison");
		itemChargerDemandeLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("./fichiersXMLDL/"));
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Fichiers", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						Controleur.getInstance().chargerFichierDemandeLivraison(file);
						Controleur.getInstance().ajouterListenerOnClick();
						labelInfo.setText("");
						textFieldnombreLivreur.setText("1");
						VerifierEtat(controleur);
					} catch (Exception e) {
						labelInfo.setTextFill(Color.web("#FF0000"));
						labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
						if(e.getMessage() == null) {
							labelInfo.setText("Il existe des points de livraison ou des entrepots en dehors du plan.");
						}else {
							labelInfo.setText(e.getMessage());
						}
						
						e.printStackTrace();
					}
				}

			}
		});

		// Verifier que le fichier XML est bien forme (pas de caractÃ¨re absent ou
		// manquant)
		// sinon : throw new XMLMalFormeException
		// Verifier que le fichier XML correspond Ã  une demande de livraison
		// (<demandeDeLivraisons> puis <entrepot> puis <livraison>)
		// sinon : throw new DemandeLivraisonXMLFileException

		buttonChargeDemandeLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("./fichiersXMLDL/"));
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Fichiers", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						Controleur.getInstance().chargerFichierDemandeLivraison(file);
						Controleur.getInstance().ajouterListenerOnClick();
						labelInfo.setText("");
						textFieldnombreLivreur.setText("1");
						VerifierEtat(controleur);
					} catch (Exception e) {
						labelInfo.setTextFill(Color.web("#FF0000"));
						labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
						if(e.getMessage() == null) {
							labelInfo.setText("Il existe des points de livraison ou des entrepots en dehors du plan.");
						}else {
							labelInfo.setText(e.getMessage());
						}
						e.printStackTrace();
					}
				}
			}
		});

		// Verifier que le fichier XML est bien forme (pas de caractÃ¨re absent ou
		// manquant)
		// sinon : throw new XMLMalFormeException
		// Verifier que le fichier XML correspond Ã  un plan ( <reseau> puis <noeud> et
		// <troncon>)
		// sinon : throw new PlanXMLFileException

		itemChargerPlan = new MenuItem("Charger Plan");

		itemChargerPlan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("./fichiersXMLPlan/"));
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Fichiers", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						Controleur.getInstance().chargerFichierPlan(file);
						labelInfo.setText("");
						VerifierEtat(controleur);
					} catch (Exception e) {
						labelInfo.setTextFill(Color.web("#FF0000"));
						labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
						labelInfo.setText(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		});

		buttonChargePlan.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("./fichiersXMLPlan/"));
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Fichiers", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						Controleur.getInstance().chargerFichierPlan(file);
						labelInfo.setText("");
						VerifierEtat(controleur);

					} catch (Exception e) {
						labelInfo.setTextFill(Color.RED);
						labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
						labelInfo.setText(e.getMessage());
						e.printStackTrace();
					}

				}
			}
		});

		menuFile.getItems().addAll(itemChargerPlan, itemChargerDemandeLivraison);

		// Ajout de l'onglet Operation
		menuTournee = new Menu("Tournee");
		itemCalculerTournees = new MenuItem("Calculer les tournees");

		itemCalculerTournees.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					int maximum = Controleur.getInstance().getNbLivreurMaximum();
					String contenu = textFieldnombreLivreur.getText();
					if (contenu.equals("")) {
						labelError.setText("Il n'y a pas d'entree, " + "veuillez specifier une valeur.");
					} else {
						int nbLivreur = Integer.parseInt(contenu);
						if (nbLivreur > maximum || nbLivreur < 1) {
							labelError.setText("Le nombre de livreurs saisi est superieur au nombre "
									+ "maximum de livreurs (" + maximum
									+ " livreurs) ou inferieur Ã  1,  veuillez specifier une valeur valide.");
						} else {
							try {
								String time = timeLimite.getValue();
								String[] value = time.split(" ");
								Controleur.getInstance().setTimeLimite(Integer.parseInt(value[0]));
								if(modeClustering.isSelected()) {
									Controleur.getInstance().calculerLesTournees(nbLivreur,2);
								}else {
									Controleur.getInstance().calculerLesTournees(nbLivreur,1);
								}
								
								Controleur.getInstance().getHistorique().clear();
								labelError.setText("");
								VerifierEtat(controleur);
							} catch (Exception e) {
								labelInfo.setTextFill(Color.RED);
								labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
								labelInfo.setText(e.getMessage());
								e.printStackTrace();
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		/**
		 * Bouton calculer qui permet le calcul des tournées
		 */
		buttonCalculer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {

					int maximum = Controleur.getInstance().getNbLivreurMaximum();
					String contenu = textFieldnombreLivreur.getText();
					if (contenu.equals("")) {
						labelError.setText("Il n'y a pas d'entree, " + "veuillez specifier une valeur.");
					} else {
						int nbLivreur = Integer.parseInt(contenu);
						if (nbLivreur > maximum || nbLivreur < 1) {
							labelError.setText("Le nombre de livreurs donnee est plus grand que le nombre "
									+ "maximum de livreurs (" + maximum
									+ " livreurs) ou inferieur Ã  1, veuillez specifier une valeur valide.");
						} else {
							try {
								String time = timeLimite.getValue();
								String[] value = time.split(" ");
								Controleur.getInstance().setTimeLimite(Integer.parseInt(value[0])*1000);
								if(modeClustering.isSelected()) {
									Controleur.getInstance().calculerLesTournees(nbLivreur,2);
								}else {
									Controleur.getInstance().calculerLesTournees(nbLivreur,1);
								}
								
								Controleur.getInstance().getHistorique().clear();
								labelError.setText("");
								VerifierEtat(controleur);
							} catch (Exception e) {
								labelInfo.setTextFill(Color.RED);
								labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
								labelInfo.setText(e.getMessage());
								e.printStackTrace();
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		itemEffacer = new MenuItem("Effacer tout");
		itemEffacerDemande = new MenuItem("Effacer la demande de livraison");

		itemEffacer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				graph.clearVue();
				texte.clearVue();
				Controleur.getInstance().getHistorique().clear();
				labelNombreLivreurs.setText("Nombre de livreurs :");
				textFieldnombreLivreur.setText("");
				Controleur.getInstance().setEtat(Controleur.getInstance().getEtatInit());
				VerifierEtat(controleur);
			}
		});

		itemEffacerDemande.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				graph.clearEntrepotLivraison();
				graph.clearTournees();
				texte.clearVue();
				Controleur.getInstance().getHistorique().clear();
				labelNombreLivreurs.setText("Nombre de livreurs :");
				textFieldnombreLivreur.setText("");
				Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPlanCharge());
				VerifierEtat(controleur);
			}
		});

		buttonEffacer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				graph.clearVue();
				texte.clearVue();
				Controleur.getInstance().getHistorique().clear();
				labelNombreLivreurs.setText("Nombre de livreurs :");
				textFieldnombreLivreur.setText("");
				Controleur.getInstance().setEtat(Controleur.getInstance().getEtatInit());
				Controleur.getInstance().getHistorique().reinitialiser();
				VerifierEtat(controleur);
			}
		});

		buttonEffacerDemande.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				graph.clearEntrepotLivraison();
				graph.clearTournees();
				texte.clearVue();
				Controleur.getInstance().getHistorique().clear();
				labelNombreLivreurs.setText("Nombre de livreurs :");
				textFieldnombreLivreur.setText("");
				Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPlanCharge());
				Controleur.getInstance().getHistorique().reinitialiser();
				VerifierEtat(controleur);
			}
		});

		menuTournee.getItems().addAll(itemCalculerTournees, itemEffacer, itemEffacerDemande);

		// Ajout de l'onglet View(Composant prevu pour apres)
		menuLivraison = new Menu("Livraison");
		itemAjouterLivraison = new MenuItem("Ajouter un point de livraison");
		itemSupprimerLivraison = new MenuItem("Supprimer un point de livraison");
		itemDeplacerLivraison = new MenuItem("Deplacer un point de livraison");

		itemAjouterLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if(Controleur.getInstance().getHistorique().getIndice() >= 0 
							&& Controleur.getInstance().getHistorique().getIndice() < Controleur.getInstance().getHistorique().getLongueur()-1) {
						Controleur.getInstance().getHistorique().clear();
					}
					Controleur.getInstance().ajouterPointLivraison();
					VerifierEtat(controleur);
					graph.arreterSynchronisationLivraison();
					texte.arreterSynchronisationLivraison();
					textFieldDuree.setText("0");
					labelInfo.setTextFill(Color.BLACK);
					labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
					labelInfo.setText("Choisissez un point de livraison deja existant.");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		itemSupprimerLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if(Controleur.getInstance().getHistorique().getIndice() >= 0 
							&& Controleur.getInstance().getHistorique().getIndice() < Controleur.getInstance().getHistorique().getLongueur()-1) {
						Controleur.getInstance().getHistorique().clear();
					}
					Controleur.getInstance().setEtat(Controleur.getInstance().getEtatSupprimerChoixPointLivraison());
					VerifierEtat(controleur);
					graph.arreterSynchronisationLivraison();
					texte.arreterSynchronisationLivraison();
					labelInfo.setTextFill(Color.BLACK);
					labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
					labelInfo.setText("Choisissez le point de livraison que vous voulez supprimer.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		itemDeplacerLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Controleur.getInstance().deplacerPointLivraison();
					VerifierEtat(controleur);
					graph.arreterSynchronisationLivraison();
					texte.arreterSynchronisationLivraison();
					labelInfo.setTextFill(Color.BLACK);
					labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
					labelInfo.setText("Choisissez le point de livraison que vous voulez deplacer.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		buttonAjouterLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if(Controleur.getInstance().getHistorique().getIndice() >= 0 
							&& Controleur.getInstance().getHistorique().getIndice() < Controleur.getInstance().getHistorique().getLongueur()-1) {
						Controleur.getInstance().getHistorique().clear();
					}
					Controleur.getInstance().ajouterPointLivraison();
					VerifierEtat(controleur);
					graph.arreterSynchronisationLivraison();
					texte.arreterSynchronisationLivraison();
					textFieldDuree.setText("0");
					labelInfo.setTextFill(Color.BLACK);
					labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
					labelInfo.setText("Choisissez un point de livraison deja existant.");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		buttonSupprimerLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if(Controleur.getInstance().getHistorique().getIndice() >= 0 
							&& Controleur.getInstance().getHistorique().getIndice() < Controleur.getInstance().getHistorique().getLongueur()-1) {
						Controleur.getInstance().getHistorique().clear();
					}
					Controleur.getInstance().supprimerPointLivraison();
					VerifierEtat(controleur);
					graph.arreterSynchronisationLivraison();
					texte.arreterSynchronisationLivraison();
					labelInfo.setTextFill(Color.BLACK);
					labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
					labelInfo.setText("Choisissez le point de livraison que vous voulez supprimer.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		buttonDeplacerLivraison.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Controleur.getInstance().deplacerPointLivraison();
					VerifierEtat(controleur);
					graph.arreterSynchronisationLivraison();
					texte.arreterSynchronisationLivraison();
					labelInfo.setTextFill(Color.BLACK);
					labelInfo.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
					labelInfo.setText("Choisissez le point de livraison que vous voulez deplacer.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		buttonRedo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Controleur.getInstance().redo();
					VerifierEtat(controleur);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		buttonUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Controleur.getInstance().undo();
					VerifierEtat(controleur);

				} catch (Exception e) {
					e.printStackTrace();
				}
	         }
	      }); 
        
        buttonExport.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					FileChooser fileChooser = new FileChooser();
		        	fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Succes");
					alert.setContentText("feuille de route creee dans: "+ fileChooser.getInitialDirectory());
					alert.show();
					} catch (Exception e) {
						e.printStackTrace();
				}
			}
		});

		menuLivraison.getItems().addAll(itemAjouterLivraison, itemSupprimerLivraison, itemDeplacerLivraison);

		menuBar.getMenus().addAll(menuFile, menuTournee, menuLivraison);
		pane.setTop(menuBar);
	}


	/**
	 * Méthode pour modifier le nombre de livreur maximum affiché dans le label
	 * @param nbLivreur : nombre de livreur maximum à afficher
	 */
	public void setLabelNbLivreur(int nbLivreur) {
		labelNombreLivreurs.setText("Nombre de livreurs (maximum :" + nbLivreur + ") :");
	}
	/**
	 * Méthode pour activer ou désactiver des boutons en vérifiant l'état du contrôleur
	 * @param c : contrôleur de l'application
	 */
	public void VerifierEtat(Controleur c) {
		ETAT e = ETAT.valueOf(c.getEtatCourant().getClass().getSimpleName());

		switch (e) {
		case EtatInit:
			itemChargerPlan.setDisable(false);
			buttonChargePlan.setDisable(false);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			textFieldnombreLivreur.setDisable(true);
			itemEffacer.setDisable(true);
			buttonEffacer.setDisable(true);
			itemEffacerDemande.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			buttonExport.setDisable(true);
			break;
		case EtatPlanCharge:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(false);
			buttonChargeDemandeLivraison.setDisable(false);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			textFieldnombreLivreur.setDisable(true);
			itemEffacer.setDisable(false);
			buttonEffacer.setDisable(false);
			itemEffacerDemande.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			buttonExport.setDisable(true);
			break;
		case EtatDemandeLivraison:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(false);
			buttonCalculer.setDisable(false);
			textFieldnombreLivreur.setDisable(false);
			itemEffacer.setDisable(false);
			buttonEffacer.setDisable(false);
			itemEffacerDemande.setDisable(false);
			buttonEffacerDemande.setDisable(false);
			buttonAjouterLivraison.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(false);
			modeSansClustering.setDisable(false);
			modeClustering.setDisable(false);
			buttonSupprimerLivraison.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			buttonExport.setDisable(true);
			break;

		case EtatPostCalcul:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(false);
			itemEffacer.setDisable(false);
			buttonEffacer.setDisable(false);
			itemEffacerDemande.setDisable(false);
			buttonEffacerDemande.setDisable(false);
			buttonAjouterLivraison.setDisable(false);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(false);
			modeSansClustering.setDisable(false);
			modeClustering.setDisable(false);
			buttonSupprimerLivraison.setDisable(false);
			buttonDeplacerLivraison.setDisable(false);
			itemDeplacerLivraison.setDisable(false);
			itemSupprimerLivraison.setDisable(false);
			itemAjouterLivraison.setDisable(false);
			buttonExport.setDisable(false);
			if (c.getHistorique().getLongueur() > 0) {
				buttonRedo.setDisable(false);
				buttonUndo.setDisable(false);
			}
			break;

		case EtatAjouterChoixPointLivraison:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			itemEffacer.setDisable(true);
			buttonEffacer.setDisable(true);
			itemEffacerDemande.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonExport.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			break;

		case EtatAjouterChoixNouvellePointLivraison:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			itemEffacer.setDisable(true);
			buttonEffacer.setDisable(true);
			itemEffacerDemande.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			textFieldDuree.setDisable(false);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonExport.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			break;

		case EtatSupprimerChoixPointLivraison:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			itemEffacer.setDisable(true);
			buttonEffacer.setDisable(true);
			itemEffacerDemande.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonExport.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			break;

		case EtatChoixPointLivraisonADeplacer:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			itemEffacer.setDisable(true);
			buttonEffacer.setDisable(true);
			itemEffacerDemande.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonExport.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			break;

		case EtatChoixPointLivraisonApresDeplacer:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			itemEffacer.setDisable(true);
			buttonEffacer.setDisable(true);
			itemEffacerDemande.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			buttonDeplacerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonExport.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			break;

		default:
			itemChargerPlan.setDisable(true);
			buttonChargePlan.setDisable(true);
			itemChargerDemandeLivraison.setDisable(true);
			buttonChargeDemandeLivraison.setDisable(true);
			itemCalculerTournees.setDisable(true);
			buttonCalculer.setDisable(true);
			textFieldnombreLivreur.setDisable(true);
			itemEffacer.setDisable(true);
			buttonEffacer.setDisable(true);
			textFieldDuree.setDisable(true);
			timeLimite.setDisable(true);
			modeSansClustering.setDisable(true);
			modeClustering.setDisable(true);
			buttonEffacerDemande.setDisable(true);
			buttonAjouterLivraison.setDisable(true);
			buttonSupprimerLivraison.setDisable(true);
			itemDeplacerLivraison.setDisable(true);
			itemSupprimerLivraison.setDisable(true);
			itemAjouterLivraison.setDisable(true);
			buttonExport.setDisable(true);
			buttonRedo.setDisable(true);
			buttonUndo.setDisable(true);
			break;
		}
	}

	/**
	 * La méthode main pour lancer l'application
	 * 
	 * @param args les arguments de la ligne de commande
	 */

	public static void main(String[] args) {
		launch(args);
	}
}
