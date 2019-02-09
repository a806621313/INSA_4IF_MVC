package vue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import controleur.CommandeAjouterLivraison;
import controleur.CommandeSupprimeLivraison;
import controleur.Controleur;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import modele.TourneeManager;
import modele.metier.Chemin;
import modele.metier.DemandeLivraison;
import modele.metier.Entrepot;
import modele.metier.Intersection;
import modele.metier.IntersectionNormal;
import modele.metier.Plan;
import modele.metier.PointLivraison;
import modele.metier.Tournee;
import modele.metier.Troncon;
import vue.element.EntrepotVue;
import vue.element.IntersectionNormalVue;
import vue.element.PointLivraisonVue;
import vue.element.TourneeVue;
import vue.element.TronconVue;
import vue.element.VeloVue;
import vue.handler.GroupHandler;

/**
 * La classe de la vue graphique
 * 
 * @author H4404
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("restriction")
public class VueGraphique extends Parent implements Observer {

	private Group tronconGroup;
	private Group noeudGroup;
	private Group entrepotGroup;
	private Group livraisonGroup;
	private Group tourneesAfficheesGroup;
	private ArrayList<Group> tourneesGroup;
	private Group animationGroup;
	private VeloVue velo;
	private Group referenceAnima;
	private ContextMenu menuAnima;
	private MenuItem animation;
	private boolean animaStart;
	private VueTextuelle compagnie;
	private ApplicationDemo parent;
	private PointLivraisonVue ajoutPointChoisi = null;
	private IntersectionNormalVue ajoutInterChoisi = null;
	private double hauteur;
	private double largeur;
	private PointLivraisonVue deplacerPointChoisiUn = null;
	private PointLivraisonVue deplacerPointChoisiDeux = null;
	private boolean permisSynchronise;
	private static Color[] couleurs = { Color.CRIMSON, Color.CYAN, Color.FUCHSIA, Color.GREENYELLOW, Color.LIMEGREEN,
			Color.SKYBLUE };

	/**
	 * Constructeur de la vue graphique
	 * 
	 * @param lFenetre : la longueur de la fen�tre
	 * @param hFenetre : la hauteur de la fen�tre
	 * @param unParent : le parent (l'application)
	 */
	public VueGraphique(double lFenetre, double hFenetre, ApplicationDemo unParent) {
		// Intialisation de sa compagnie par defaut
		compagnie = null;
		parent = unParent;
		permisSynchronise = true;

		// Initialisation du contenu
		ScrollPane paneGraphique = new ScrollPane();
		BorderPane container = new BorderPane();
		Slider zoomSlider = new Slider();
		velo = new VeloVue(this);
		menuAnima = new ContextMenu();
		animation = new MenuItem("Regarder le parcours de cette tournee");
		animation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				animaStart = true;
				lancerAnimation(referenceAnima);
			}
		});
		menuAnima.getItems().add(animation);
		animaStart = false;

		zoomSlider.setMin(1);
		zoomSlider.setMax(3);
		zoomSlider.setValue(1);
		zoomSlider.setBlockIncrement(0.01);

		hauteur = hFenetre * 0.85;
		largeur = hFenetre * 0.85;
		paneGraphique.setPrefViewportHeight(hFenetre * 0.85);
		paneGraphique.setPrefViewportWidth(hFenetre * 0.85);
		noeudGroup = new Group();
		tronconGroup = new Group();
		entrepotGroup = new Group();
		livraisonGroup = new Group();
		tourneesAfficheesGroup = new Group();
		animationGroup = new Group();
		tourneesGroup = new ArrayList<Group>();
		Group rootGroup = new Group();
		GroupHandler handler = new GroupHandler(rootGroup);
		rootGroup.setOnMousePressed(handler);
		rootGroup.setOnMouseDragged(handler);

		// Ajouter les actions suivant les evenements

		rootGroup.getChildren().add(tronconGroup);
		rootGroup.getChildren().add(noeudGroup);
		rootGroup.getChildren().add(entrepotGroup);
		rootGroup.getChildren().add(livraisonGroup);
		rootGroup.getChildren().add(tourneesAfficheesGroup);
		rootGroup.getChildren().add(animationGroup);

		rootGroup.scaleXProperty().bind(zoomSlider.valueProperty());
		rootGroup.scaleYProperty().bind(zoomSlider.valueProperty());

		paneGraphique.setContent(rootGroup);
		container.setCenter(paneGraphique);

		container.setBottom(zoomSlider);

		// Ajouter le EventListner
		ajouterEventListner();

		this.getChildren().add(container);
	}
	
	/**
	 * M�thode pour mettre la valeur du boolean animaStart dans la vue graphique
	 * @param unBool true si l'animation est commenc�e false sinon
	 */
	public void setAnimaStart(boolean unBool) {
		animaStart = unBool;
	}

	/**
	 * M�thode pour obtenir le groupe qui contient les intersections normales affich�es
	 * @return le groupe qui contient les intersectionNormales affich�es
	 */
	public Group getNoeudGroup() {
		return this.noeudGroup;

	}

	/**
	 * M�thode pour obtenir le groupe qui contient les points de livraison affich�s
	 * @return le groupe qui contient les points de livraison affich�s
	 */
	public Group getLivraisonGroup() {
		return this.livraisonGroup;
	}
	
	/**
	 * M�thode pour lancer une animation pour visualiser comment une tourn�e est parcourue
	 * @param tournee : la tourn�e sur laquelle on lance l'animation
	 */
	public void lancerAnimation(Group tournee) {
		velo.creerPath(tournee, animationGroup);
		velo.start(animationGroup);
	}

	/**
	 * M�thode pour ajouter des listeners pour les fonctionnalit�s 
	 * ajouter/supprimer/d�placer un point de livraison et l'affichage du nom de rue d'un troncon
	 */
	public void ajouterEventListner() {
		tronconGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(final MouseEvent event) {
				if (event.getTarget() instanceof TronconVue) {
					MouseButton button = event.getButton();
					if (button.equals(MouseButton.PRIMARY)) {
						TronconVue temp = (TronconVue) event.getTarget();
						compagnie.setTabNomRue(temp.getNomRue());
					}
				}
			}
		});

		noeudGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(final MouseEvent event) {
				if (event.getTarget() instanceof IntersectionNormalVue) {
					MouseButton button = event.getButton();
					if(button.equals(MouseButton.PRIMARY)) {
						if (Controleur.getInstance().getEtatCourant().getClass().getSimpleName()
								.equals("EtatAjouterChoixNouvellePointLivraison")) {
							try {
								IntersectionNormalVue temp = (IntersectionNormalVue) event.getTarget();
								ajoutInterChoisi = temp;
								int duree = parent.getDuree();
								if (duree == Integer.MAX_VALUE) {
									Exception e = new Exception();
									throw e;
								}
								temp.setFill(Color.GREEN);
								temp.setRadius(8);

								CommandeAjouterLivraison cmd = new CommandeAjouterLivraison(ajoutPointChoisi.getIntersectionId(),ajoutInterChoisi.getIntersectionId(),duree);
								Controleur.getInstance().setTempAjout(cmd);
								Controleur.getInstance().setAjoutNouvellePoint(temp.getIntersectionId(), duree);
								parent.VerifierEtat(Controleur.getInstance());
								// Remettre a l'etat initial (couleur, radius)
								ajoutInterChoisi.setRadius(3);
								ajoutInterChoisi.setFill(Color.BLACK);
								ajoutPointChoisi.setFill(Color.BLUE);
								ajoutPointChoisi.setRadius(5);
								ajoutPointChoisi.setActiveChangerCouleurSelectionne(true);
								IntersectionNormal tempInter = Controleur.getInstance().getMonPlan()
										.getIntersectionNormal(temp.getIntersectionId());
								PointLivraisonVue tempPointLivraison = new PointLivraisonVue(
										Controleur.getInstance().transformerLongitude(tempInter.getLongitude(), largeur),
										Controleur.getInstance().transformerLatitude(tempInter.getLatitude(), hauteur), 5,
										tempInter.getId());
								livraisonGroup.getChildren().add(tempPointLivraison);

								parent.VerifierEtat(Controleur.getInstance());
								parent.setInfo("Point de livraison ajoute");
							} catch (Exception e) {
								System.out.println("Duree incorrecte ou probleme durant l'ajout");
								Label temp = parent.getLabelInfo();
								temp.setTextFill(Color.RED);
								temp.setText(e.getMessage());
								ajoutInterChoisi.setRadius(3);
								ajoutInterChoisi.setFill(Color.BLACK);
								ajoutPointChoisi.setFill(Color.BLUE);
								ajoutPointChoisi.setRadius(5);
								ajoutPointChoisi.setActiveChangerCouleurSelectionne(true);
								compagnie.activerSynchronisationLivraison();
								activerSynchronisationLivraison();
								Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPosteCalcul());
								parent.VerifierEtat(Controleur.getInstance());
								e.printStackTrace();
							}
						}
					}else {
						IntersectionNormalVue tempVue = (IntersectionNormalVue) event.getTarget();
						String temp = "";
						temp += "Latitude :"+Controleur.getInstance().getLatitudeIntersection(tempVue.getIntersectionId()) + "\n";
						temp += "Longitude :" + Controleur.getInstance().getLongititudeIntersection(tempVue.getIntersectionId());
						compagnie.setInfoIntersection(temp);
					}
					
				}
			}
		});
		livraisonGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(final MouseEvent event) {
				if (event.getTarget() instanceof PointLivraisonVue) {
					MouseButton button = event.getButton();
					if(button.equals(MouseButton.PRIMARY)) {
						if (!permisSynchronise) {
							if (Controleur.getInstance().getEtatCourant().getClass().getSimpleName()
									.equals("EtatAjouterChoixPointLivraison")) {
								try {
									PointLivraisonVue temp = (PointLivraisonVue) event.getTarget();
									ajoutPointChoisi = temp;
									temp.setActiveChangerCouleurSelectionne(false);
									temp.setFill(Color.GREEN);

									temp.setRadius(7);
									Controleur.getInstance().setAjoutDepart(temp.getIntersectionId());

									parent.VerifierEtat(Controleur.getInstance());

									parent.setInfo(
											"Entrez la duree de livraison et choisissez maintenant ou vous voulez rajouter un nouveau point de Livraison apres le point de livraison que vous venez de choisir.");
								} catch (Exception e) {
									System.out.println("Probleme durant l'ajout");
									e.printStackTrace();
								}
							} else if (Controleur.getInstance().getEtatCourant().getClass().getSimpleName()
									.equals("EtatSupprimerChoixPointLivraison")) {
								try {
									PointLivraisonVue temp = (PointLivraisonVue) event.getTarget();
									temp.setActiveChangerCouleurSelectionne(false);
									temp.setFill(Color.GREEN);
									temp.setRadius(7);

									PointLivraison tempPoint = Controleur.getInstance().getMaDemande().getPointLivraisonParId(temp.getIntersectionId());
									Intersection tempPrePoint = Controleur.getInstance().getPrePointLivraisonId(temp.getIntersectionId());
									CommandeSupprimeLivraison cmd = new CommandeSupprimeLivraison(tempPoint,tempPrePoint);
									Controleur.getInstance().setTempSupprimer(cmd);
									Controleur.getInstance().setSupprimerPointLivraison(temp.getIntersectionId());// methode
									livraisonGroup.getChildren().remove(temp);
									parent.VerifierEtat(Controleur.getInstance());

									parent.setInfo("Point de livraison supprime.");
								} catch (Exception e) {
									System.out.println("Probleme durant le processus pour supprimer");
									e.printStackTrace();
								}
							} else if (Controleur.getInstance().getEtatCourant().getClass().getSimpleName()
									.equals("EtatChoixPointLivraisonADeplacer")) {
								try {
									PointLivraisonVue temp = (PointLivraisonVue) event.getTarget();
									deplacerPointChoisiUn = temp;
									temp.setActiveChangerCouleurSelectionne(false);
									temp.setFill(Color.GREEN);
									temp.setRadius(7);
									Controleur.getInstance().setADeplacer(temp.getIntersectionId());
									parent.VerifierEtat(Controleur.getInstance());
									parent.setInfo(
											"Choisissez maintenant apres quel point de livraison vous voulez placer le point de livraison que vous venez de choisir..");
								} catch (Exception e) {
									System.out.println("Probleme durant le choix d'un point a deplacer");
									e.printStackTrace();
								}
							} else if (Controleur.getInstance().getEtatCourant().getClass().getSimpleName()
									.equals("EtatChoixPointLivraisonApresDeplacer")) {
								try {
									PointLivraisonVue temp = (PointLivraisonVue) event.getTarget();
									deplacerPointChoisiDeux = temp;
									temp.setActiveChangerCouleurSelectionne(false);
									temp.setFill(Color.GREEN);
									temp.setRadius(8);
									Controleur.getInstance().setApresDeplacer(temp.getIntersectionId());
									// Remettre a l'etat initial (couleur, radius)
									deplacerPointChoisiUn.setRadius(5);
									deplacerPointChoisiUn.setFill(Color.BLUE);
									deplacerPointChoisiUn.setActiveChangerCouleurSelectionne(true);
									deplacerPointChoisiDeux.setFill(Color.BLUE);
									deplacerPointChoisiDeux.setRadius(5);
									deplacerPointChoisiDeux.setActiveChangerCouleurSelectionne(true);
									Controleur.getInstance().getHistorique().clear();
									parent.VerifierEtat(Controleur.getInstance());
									parent.setInfo("Point de livraison deplace.");
								} catch (Exception e) {
									System.out.println(
											"Probleme durant le choix d'un point apres lequel nous placons un point");
									parent.setInfo(
											"Nous ne pouvons pas faire un deplacement sur deux points d'une meme tournee.");
									deplacerPointChoisiUn.setRadius(5);
									deplacerPointChoisiUn.setFill(Color.BLUE);
									deplacerPointChoisiUn.setActiveChangerCouleurSelectionne(true);
									deplacerPointChoisiDeux.setFill(Color.BLUE);
									deplacerPointChoisiDeux.setRadius(5);
									deplacerPointChoisiDeux.setActiveChangerCouleurSelectionne(true);
									Controleur.getInstance().setEtat(Controleur.getInstance().getEtatPosteCalcul());
									parent.VerifierEtat(Controleur.getInstance());
									e.printStackTrace();
								}
							}
						} else {
							PointLivraisonVue vue = (PointLivraisonVue) event.getTarget();
							if (!vue.isSynchronisee()) {
								vue.changerFormeSynchronise();
								compagnie.synchroniserLivraisonPane(vue.getIntersectionId(), true);
							} else {
								vue.setSynchronisee(false);
								vue.setOriginalColor(Color.BLUE);
								vue.changeRadius(5);
								vue.changerCouleurNonSelectionnee();
								compagnie.synchroniserLivraisonPane(vue.getIntersectionId(), false);
							}
						}
					}else{
						PointLivraisonVue tempVue = (PointLivraisonVue) event.getTarget();
						String temp = "";
						temp +="Latitude :"+ Controleur.getInstance().getLatitudeIntersection(tempVue.getIntersectionId()) + "\n";
						temp += "Longitude :"+Controleur.getInstance().getLongititudeIntersection(tempVue.getIntersectionId());
						compagnie.setInfoIntersection(temp);
					}
				}
			}
		});
	}
	
	
/**
 * M�thode pour ajouter les listeners sur les TourneeVues affich�es dans la vue graphique
 * @param g : le groupe sur lequel nous devons ajouter ce listener
 */
	public void ajouterListenerRueNom(Group g) {
		g.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(final MouseEvent event) {
				if (event.getTarget() instanceof TourneeVue) {
					MouseButton button = event.getButton();
					if (button.equals(MouseButton.PRIMARY)) {
						TourneeVue temp = (TourneeVue) event.getTarget();
						compagnie.setTabNomRue(temp.getNomRue());
					}else {
						if(animaStart == false) {
							referenceAnima = g;
							double x = event.getScreenX();
				            double y = event.getScreenY();
							menuAnima.show(g, x, y);
						}
					}
				}
			}
		});
	}

	/**
	 * M�thode qui fait la vue graphique conna�tre la vue textuelle qui l'accompagne
	 * @param vue : une vue textuelle
	 */
	public void setCompagnie(VueTextuelle vue) {
		this.compagnie = vue;
	}

	/**
	 * M�thode pour obtenir la hauteur de la vue graphique
	 * @return la hauteur de la vue graphique
	 */
	public double getHauteur() {
		return hauteur;

	}

	/**
	 * M�thode pour obtenir la largeur de la vue graphique
	 * @return la largeur de la vue graphique
	 */
	public double getLargeur() {
		return largeur;

	}

	/**
	 * M�thode pour dessiner le plan
	 * 
	 * @param monPlan : le plan � dessiner
	 */
	public void dessinerPlan(Plan monPlan) {
		clearVue();
		Collection<IntersectionNormal> lesIntersections = monPlan.getAllIntersectionNormals();
		Collection<ArrayList<Troncon>> lesTroncons = monPlan.getAllTroncons();
		int counter = 0;
		for (ArrayList<Troncon> troncons : lesTroncons) {
			for (Troncon troncon : troncons) {
				IntersectionNormal depart = troncon.getOrigine();
				IntersectionNormal destination = troncon.getDestination();
				TronconVue tempLigne = new TronconVue(monPlan.transformLongitude(depart.getLongitude(), largeur),
						monPlan.transformLatitude(depart.getLatitude(), hauteur),
						monPlan.transformLongitude(destination.getLongitude(), largeur),
						monPlan.transformLatitude(destination.getLatitude(), hauteur), troncon.getNomRue());
				tronconGroup.getChildren().add(tempLigne);
				counter++;
			}
		}
		for (IntersectionNormal intersection : lesIntersections) {
			IntersectionNormalVue tempPoint = new IntersectionNormalVue(
					monPlan.transformLongitude(intersection.getLongitude(), largeur),
					monPlan.transformLatitude(intersection.getLatitude(), hauteur), 3, intersection.getId());
			noeudGroup.getChildren().add(tempPoint);
		}

	}

	/**
	 * M�thode pour dessiner la demande de livraison
	 * 
	 * @param maDemande : une demande de livraison � dessiner
	 */
	public void dessinerDemandeLivraison(DemandeLivraison maDemande) {
		clearEntrepotLivraison();
		clearTournees();
		Collection<Entrepot> lesEntrepots = maDemande.getAllEntrepots();
		Collection<PointLivraison> lesPointLivraisons = maDemande.getAllPointLivraisons();

		for (Entrepot entrepot : lesEntrepots) {
			EntrepotVue tempEntrepot = new EntrepotVue(
					Controleur.getInstance().transformerLongitude(entrepot.getLongitude(), largeur),
					Controleur.getInstance().transformerLatitude(entrepot.getLatitude(), hauteur), 5, entrepot.getId());
			entrepotGroup.getChildren().add(tempEntrepot);
		}
		for (PointLivraison livraison : lesPointLivraisons) {
			PointLivraisonVue tempPointLivraison = new PointLivraisonVue(
					Controleur.getInstance().transformerLongitude(livraison.getLongitude(), largeur),
					Controleur.getInstance().transformerLatitude(livraison.getLatitude(), hauteur), 5,
					livraison.getId());
			livraisonGroup.getChildren().add(tempPointLivraison);
		}
	}

	/**
	 * M�thode pour g�n�rer des couleurs al�atoires que nous utilisons afin de dessiner les tourn�es
	 * 
	 * @param index : un index
	 * @return couleur calcule
	 */
	public Color genererCouleurs(int index) {
		return couleurs[index % couleurs.length];
	}

	/**
	 * M�thode pour dessiner les tourn�es
	 * 
	 * @param manager : l'objet TourneeManager qui stocke les tourn�es � dessiner
	 */
	public void dessinerTournees(TourneeManager manager) {
		clearTournees();
		ArrayList<Tournee> tournees = manager.getListeTournees();
		int index = 0;
		for (Tournee tournee : tournees) {
			Group tempGroup = new Group();
			Color tourneeCouleur = genererCouleurs(index);
			ArrayList<Chemin> tempChemins = tournee.getListeChemins();
			for (Chemin chemin : tempChemins) {
				ArrayList<Troncon> tempTroncons = chemin.getListeTroncons();
				for (Troncon troncon : tempTroncons) {
					IntersectionNormal depart = troncon.getOrigine();
					IntersectionNormal destination = troncon.getDestination();
					TourneeVue tempTronconTournee = new TourneeVue(
							Controleur.getInstance().transformerLongitude(depart.getLongitude(), largeur),
							Controleur.getInstance().transformerLatitude(depart.getLatitude(), hauteur),
							Controleur.getInstance().transformerLongitude(destination.getLongitude(), largeur),
							Controleur.getInstance().transformerLatitude(destination.getLatitude(), hauteur),
							troncon.getNomRue(), tourneeCouleur);
					tempGroup.getChildren().add(tempTronconTournee);
				}
			}
			ajouterListenerRueNom(tempGroup);
			tourneesGroup.add(tempGroup);
			tourneesAfficheesGroup.getChildren().add(tempGroup);
			index++;
		}
	}

	/**
	 * M�thode pour filtrer les tourn�es affich�es
	 * 
	 * @param afficheGroup : les num�ros des tourn�es � afficher
	 */
	public void filtrerTournees(ArrayList<Integer> afficheGroup) {
		clearAfficheeTournees();
		for (Integer i : afficheGroup) {
			tourneesAfficheesGroup.getChildren().add(tourneesGroup.get(i));
		}
	}

	/**
	 * M�thode pour effacer la vue
	 */
	public void clearVue() {
		clearPlan();
		clearEntrepotLivraison();
		clearTournees();
	}

	/**
	 * M�thode pour effacer le plan
	 */
	public void clearPlan() {
		tronconGroup.getChildren().clear();
		noeudGroup.getChildren().clear();
	}

	/**
	 * M�thode pour effacer les entrep�ts et les points de livraison
	 */
	public void clearEntrepotLivraison() {
		entrepotGroup.getChildren().clear();
		livraisonGroup.getChildren().clear();
	}

	/**
	 * M�thode pour effacer les tourn�es stockees et affich�es
	 */
	public void clearTournees() {
		tourneesAfficheesGroup.getChildren().clear();
		for (int i = 0; i < tourneesGroup.size(); i++) {
			tourneesGroup.get(i).getChildren().clear();
		}
		tourneesGroup.clear();
	}

	/**
	 * M�thode pour effacer juste les tourn�es affich�es
	 */
	public void clearAfficheeTournees() {
		tourneesAfficheesGroup.getChildren().clear();
	}

	/**
	 * M�thode pour synchroniser la vue graphique avec la vue textuelle pour une livraison
	 * 
	 * @param id       : id de PointLivraisonVue qui doit etre synchronis�e
	 * @param expanded : boolean qui indique si le TitledPane est apparent
	 */
	public void synchronisationLivraison(long id, boolean expanded) {
		ObservableList<Node> tempLivraisons = livraisonGroup.getChildren();
		for (Node e : tempLivraisons) {
			if (e instanceof PointLivraisonVue) {
				PointLivraisonVue tempReference = (PointLivraisonVue) e;
				tempReference.changerCouleurNonSelectionnee();
				if (tempReference.getIntersectionId() == id) {
					if (expanded) {
						tempReference.changerFormeSynchronise();
					} else {
						tempReference.setSynchronisee(false);
						tempReference.setOriginalColor(Color.BLUE);
						tempReference.changeRadius(5);
						tempReference.changerCouleurNonSelectionnee();
					}
				}
			}
		}
	}

	/**
	 * M�thode pour arr�ter temporairement la synchronisation
	 */
	public void arreterSynchronisationLivraison() {
		permisSynchronise = false;
		ObservableList<Node> tempLivraisons = livraisonGroup.getChildren();
		for (Node e : tempLivraisons) {
			PointLivraisonVue tempReference = (PointLivraisonVue) e;
			tempReference.setSynchronisee(false);
			tempReference.setOriginalColor(Color.BLUE);
			tempReference.changeRadius(5);
			tempReference.changerCouleurNonSelectionnee();
		}
		compagnie.arreterSynchronisationLivraison();
	}

	/**
	 * M�thode pour r�activer la synchronisation
	 */
	public void activerSynchronisationLivraison() {
		permisSynchronise = true;
	}

	/**
	 * M�thode pour changer juste l'affichage d'une tourn�e
	 * 
	 * @param manager : objet TourneeManager stock� dans le contr�leur
	 */
	public void changerVueUneTournee(TourneeManager manager) {
		int index = manager.getTourneeChangedIndex();
		Group tempReference = tourneesGroup.get(index);
		tempReference.getChildren().clear();
		Color tourneeCouleur = genererCouleurs(index);
		ArrayList<Chemin> tempChemins = manager.getListeTournees().get(index).getListeChemins();
		for (Chemin chemin : tempChemins) {
			ArrayList<Troncon> tempTroncons = chemin.getListeTroncons();
			for (Troncon troncon : tempTroncons) {
				IntersectionNormal depart = troncon.getOrigine();
				IntersectionNormal destination = troncon.getDestination();
				TourneeVue tempTronconTournee = new TourneeVue(
						Controleur.getInstance().transformerLongitude(depart.getLongitude(), largeur),
						Controleur.getInstance().transformerLatitude(depart.getLatitude(), hauteur),
						Controleur.getInstance().transformerLongitude(destination.getLongitude(), largeur),
						Controleur.getInstance().transformerLatitude(destination.getLatitude(), hauteur),
						troncon.getNomRue(), tourneeCouleur);
				tempReference.getChildren().add(tempTronconTournee);
			}
		}
		ajouterListenerRueNom(tempReference);
	}

	/**
	 * M�thode pour recharger la vue des deux tourn�es modifi�es(Sans supprimer).
	 * 
	 * @param manager : objet TourneeManager stock� dans le contr�leur
	 */
	public void changerVueTourneeSansSupprimer(TourneeManager manager) {
		int indexUn = manager.getTourneeAjouterIndex();
		int indexDeux = manager.getTourneeSupprimerIndex();
		Group tempReference = tourneesGroup.get(indexUn);
		tempReference.getChildren().clear();
		Color tourneeCouleur = genererCouleurs(indexUn);
		ArrayList<Chemin> tempChemins = manager.getListeTournees().get(indexUn).getListeChemins();
		for (Chemin chemin : tempChemins) {
			ArrayList<Troncon> tempTroncons = chemin.getListeTroncons();
			for (Troncon troncon : tempTroncons) {
				IntersectionNormal depart = troncon.getOrigine();
				IntersectionNormal destination = troncon.getDestination();
				TourneeVue tempTronconTournee = new TourneeVue(
						Controleur.getInstance().transformerLongitude(depart.getLongitude(), largeur),
						Controleur.getInstance().transformerLatitude(depart.getLatitude(), hauteur),
						Controleur.getInstance().transformerLongitude(destination.getLongitude(), largeur),
						Controleur.getInstance().transformerLatitude(destination.getLatitude(), hauteur),
						troncon.getNomRue(), tourneeCouleur);
				tempReference.getChildren().add(tempTronconTournee);
			}
		}
		ajouterListenerRueNom(tempReference);

		tempReference = tourneesGroup.get(indexDeux);
		tempReference.getChildren().clear();
		tourneeCouleur = genererCouleurs(indexDeux);
		tempChemins = manager.getListeTournees().get(indexDeux).getListeChemins();
		for (Chemin chemin : tempChemins) {
			ArrayList<Troncon> tempTroncons = chemin.getListeTroncons();
			for (Troncon troncon : tempTroncons) {
				IntersectionNormal depart = troncon.getOrigine();
				IntersectionNormal destination = troncon.getDestination();
				TourneeVue tempTronconTournee = new TourneeVue(
						Controleur.getInstance().transformerLongitude(depart.getLongitude(), largeur),
						Controleur.getInstance().transformerLatitude(depart.getLatitude(), hauteur),
						Controleur.getInstance().transformerLongitude(destination.getLongitude(), largeur),
						Controleur.getInstance().transformerLatitude(destination.getLatitude(), hauteur),
						troncon.getNomRue(), tourneeCouleur);
				tempReference.getChildren().add(tempTronconTournee);
			}
		}
		ajouterListenerRueNom(tempReference);
	}

	/**
	 * M�thode pour changer l'affichage des deux tourn�es chang�es(Avec Supprimer) apr�s le deplacement
	 * 
	 * @param manager : objet TourneeManager stock� dans le contr�leur
	 */
	public void changerVueTourneeSupprimer(TourneeManager manager) {
		dessinerTournees(manager);
	}

	/**
	 * M�thode pour supprimer une tourn�e dans la vue graphique
	 * 
	 * @param manager : objet TourneeManager qui stocke les tourn�es.
	 */
	public void supprimerUneTournee(TourneeManager manager) {
		int index = manager.getTourneeChangedIndex();
		Group tempReference = tourneesGroup.get(index);
		tourneesAfficheesGroup.getChildren().remove(tempReference);
		tourneesGroup.remove(tempReference);
	}

	/**
	 * M�thode qui rafra�chit le contenu de la vue graphique selon le param�tre arg1 pass�
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub\
		String sujet = (String) arg;
		switch (sujet) {
		case "Plan":
			dessinerPlan((Plan) o);
			break;
		case "DemandeLivraison":
			dessinerDemandeLivraison((DemandeLivraison) o);
			break;
		case "Tournees":
			dessinerTournees((TourneeManager) o);
			break;
		case "UniqueTournee":
			changerVueUneTournee((TourneeManager) o);
			activerSynchronisationLivraison();
			break;
		case "SupprimerTournee":
			supprimerUneTournee((TourneeManager) o);
			activerSynchronisationLivraison();
			break;
		case "DeplacementSupprimerTournee":
			changerVueTourneeSupprimer((TourneeManager) o);
			activerSynchronisationLivraison();
			break;
		case "DeplacementSansSupprimerTournee":
			changerVueTourneeSansSupprimer((TourneeManager) o);
			activerSynchronisationLivraison();
			break;
		case "TourneesEtDemandeLivraison":
			dessinerDemandeLivraison(Controleur.getInstance().getMaDemande());
			dessinerTournees((TourneeManager) o);
			break;
		case "Alert Temps":
			dessinerTournees((TourneeManager) o);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Attention");
			alert.setContentText(
					"Limite de temps dépassée ! Une solution est affichee mais elle n'est pas optimale. Augmenter la limite du temps afin d'obtenir la meilleure solution!");
			alert.show();
			break;
		}
	}
}
