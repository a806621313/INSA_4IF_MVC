package testunitaire;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import modele.services.LecteurDeXML;

import controleur.Controleur;
import modele.*;
import modele.metier.*;

public class TestTourneeManager {

	private static Controleur controleur = Controleur.getInstance();
	private TourneeManager tm;
	private Observer observer;
	private boolean updateAppele;

	@Before
	public void setUp() {
		controleur.setEtat(controleur.getEtatInit());
		tm = new TourneeManager();
		updateAppele = false;
		observer = new Observer() {
			public void update(Observable o, Object arg) {
				updateAppele = true;
			}
		};
		tm.addObserver(observer);
		System.out.println("-------------------------------------------------------------------");
	}

	@Test
	public void testCalculerLesTournees1() throws Exception {
		System.out.println("-----------------------------testCalculerLesTournees1---------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan1.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl1.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 1);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 1);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().size(), 2);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().size(), 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(1).getListeIntersections().size(), 3);
		assert (updateAppele);
	}

	@Test
	public void testCalculerLesTournees2() throws Exception {
		System.out.println("-----------------------------testCalculerLesTournees2---------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan1.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl2.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 2);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 2);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().size(), 2);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().size(), 2);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(1).getListeIntersections().size(), 2);
		assertEquals(tm.getListeTournees().get(1).getListeChemins().get(0).getListeIntersections().size(), 3);
		assertEquals(tm.getListeTournees().get(1).getListeChemins().get(1).getListeIntersections().size(), 3);
		assert (updateAppele);
	}

	@Test
	public void testCalculerLesTournees3() throws Exception {
		System.out.println("-----------------------------testCalculerLesTournees3---------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().size(), 3);
		Intersection test1 = new IntersectionNormal(26057085, 45.756638, 4.8683963);
		Intersection test2 = new PointLivraison(26057084, 45.756714, 4.8673143, 60);

		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(2), test1);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(3), test2);

		// System.out.println(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections());

		assert (updateAppele);
	}

	/**
	 * Pour tester CalculerLesTournees de temps passer la limite, il faut changer
	 * l'attribut TIME_LIMITE = 1;
	 * 
	 * @throws Exception lors calcule les tournees
	 */
	@Test
	public void testCalculerLesTournees_TempsPasserLimite() throws Exception {
		System.out.println(
				"-----------------------------testCalculerLesTournees_TempsPasserLimite---------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/grandPlan.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/dl-grand-12.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		tm.setTimeLimite(1);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTourneesSelonMode(demande, unPlan, 3, 1);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 3);
		assert (updateAppele);
	}

	@Test
	public void testCalculerLesTourneesClustering() throws Exception {
		System.out.println(
				"-----------------------------testCalculerLesTourneesClustering---------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTourneesClustering(demande, unPlan, 3);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().size(), 3);
		Intersection test1 = new IntersectionNormal(26057085, 45.756638, 4.8683963);
		Intersection test2 = new PointLivraison(26057084, 45.756714, 4.8673143, 60);

		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(2), test1);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(3), test2);

	}

	/**
	 * Pour tester CalculerLesTournees de temps passer la limite, il faut changer
	 * l'attribut TIME_LIMITE = 1;
	 * 
	 * @throws Exception lor calcule les tournees en clustering
	 */
	@Test
	public void testCalculerLesTourneesClustering_TempsPasserLimite() throws Exception {
		System.out.println(
				"-----------------------------testCalculerLesTournees_TempsPasserLimite---------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/grandPlan.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/dl-grand-12.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		tm.setTimeLimite(1);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTourneesSelonMode(demande, unPlan, 1, 0);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 1);
		assert (updateAppele);
	}

	@Test
	public void testAjouterPointLivraison() throws Exception {
		System.out.println("--------------------------testAjouterPointLivraison-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl4.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);

		System.out.println("-------------------------------------------------------------------");
		tm.ajouterPointLivraison(26057085, 26057084, 60);

		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 3);

		assertEquals(tm.getListeTournees().get(2).getListeChemins().size(), 3);
		Intersection test1 = new IntersectionNormal(26057085, 45.756638, 4.8683963);
		Intersection test2 = new PointLivraison(26057084, 45.756714, 4.8673143, 60);

		assertEquals(tm.getListeTournees().get(2).getListeChemins().get(2).getListeIntersections().get(1), test1);
		/*
		 * System.out.println("size31="+tm.getListeTournees().get(2).getListeChemins().
		 * get(0).getListeIntersections().size());
		 * System.out.println("size32="+tm.getListeTournees().get(2).getListeChemins().
		 * get(1).getListeIntersections().size());
		 * System.out.println("size33="+tm.getListeTournees().get(2).getListeChemins().
		 * get(2).getListeIntersections().size());
		 */
		assertEquals(tm.getListeTournees().get(2).getListeChemins().get(2).getListeIntersections().get(0), test2);
		/*
		 * System.out.println(
		 * "tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections()"
		 * ); System.out.println(tm.getListeTournees().get(2).getListeChemins().get(2).
		 * getListeIntersections());
		 */

		assert (updateAppele);

	}

	@Test(expected = Exception.class)
	public void testAjouterPointLivraison_Exception() throws Exception {
		System.out.println("--------------------------testAjouterPointLivraison-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl4.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);

		System.out.println("-------------------------------------------------------------------");
		tm.ajouterPointLivraison(26057085, 26057088, 60);

	}

	@Test
	public void testAjouterPointLivraisonMetier() throws Exception {
		System.out.println("--------------------------testAjouterPointLivraison-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl4.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);

		System.out.println("-------------------------------------------------------------------");
		tm.ajouterPointLivraisonMetier(26057085, 26057084, 60);

		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 3);
		/*
		 * System.out.println("size1="+tm.getListeTournees().get(0).getListeChemins().
		 * size());
		 * System.out.println("size2="+tm.getListeTournees().get(1).getListeChemins().
		 * size());
		 * System.out.println("size3="+tm.getListeTournees().get(2).getListeChemins().
		 * size());
		 */
		assertEquals(tm.getListeTournees().get(2).getListeChemins().size(), 3);
		Intersection test1 = new IntersectionNormal(26057085, 45.756638, 4.8683963);
		Intersection test2 = new PointLivraison(26057084, 45.756714, 4.8673143, 60);

		assertEquals(tm.getListeTournees().get(2).getListeChemins().get(2).getListeIntersections().get(1), test1);
		/*
		 * System.out.println("size31="+tm.getListeTournees().get(2).getListeChemins().
		 * get(0).getListeIntersections().size());
		 * System.out.println("size32="+tm.getListeTournees().get(2).getListeChemins().
		 * get(1).getListeIntersections().size());
		 * System.out.println("size33="+tm.getListeTournees().get(2).getListeChemins().
		 * get(2).getListeIntersections().size());
		 */
		assertEquals(tm.getListeTournees().get(2).getListeChemins().get(2).getListeIntersections().get(0), test2);
		/*
		 * System.out.println(
		 * "tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections()"
		 * ); System.out.println(tm.getListeTournees().get(2).getListeChemins().get(2).
		 * getListeIntersections());
		 */

		assert (updateAppele);

	}

	@Test(expected = Exception.class)
	public void testAjouterPointLivraisonMetier_Exception() throws Exception {
		System.out.println("--------------------------testAjouterPointLivraison-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl4.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);

		System.out.println("-------------------------------------------------------------------");
		tm.ajouterPointLivraisonMetier(26057085, 26057088, 60);

	}

	@Test
	public void testSupprimerPointLivraison() throws Exception {
		System.out.println("--------------------------testSupprimerPointLivraison-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		Intersection test1 = new IntersectionNormal(26057085, 45.756638, 4.8683963);
		Intersection test2 = new PointLivraison(26057084, 45.756714, 4.8673143, 60);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().size(), 4);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(2), test1);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(3), test2);
		tm.supprimerPointLivraison(26057084);

		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().size(), 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(2), test1);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().size(), 2);

		assert (updateAppele);

	}

	@Test
	public void testSupprimerPointLivraison2() throws Exception {
		System.out.println("--------------------------testSupprimerPointLivraison2-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		tm.supprimerPointLivraison(26079655);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 2);
	}

	/**
	 * Tester supprimer un point de livraison qui n'existe pas
	 * @throws Exception : l'exception lors on supprime un point n'existe pas
	 */
	@Test(expected = Exception.class)
	public void testSupprimerPointLivraison_exception() throws Exception {
		System.out.println("--------------------------testSupprimerPointLivraison2-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		tm.supprimerPointLivraison(260796);

	}

	@Test
	public void testSupprimerPointLivraisonMetier() throws Exception {
		System.out.println("--------------------------testSupprimerPointLivraison-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		Intersection test1 = new IntersectionNormal(26057085, 45.756638, 4.8683963);
		Intersection test2 = new PointLivraison(26057084, 45.756714, 4.8673143, 60);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().size(), 4);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(2), test1);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(3), test2);
		tm.supprimerPointLivraisonMetier(26057084);

		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().size(), 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(2), test1);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().size(), 2);

		assert (updateAppele);

	}

	/**
	 * Tester Methode SupprimerPointLivraisonMetier pour quand supprimer un point de
	 * livraison, supprimer aussi une tournee
	 * 
	 * @throws Exception : l'exception lors on supprime un point de livraison
	 */
	@Test
	public void testSupprimerPointLivraisonMetier2() throws Exception {
		System.out.println("--------------------------testSupprimerPointLivraison2-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		tm.supprimerPointLivraisonMetier(26079655);
		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 2);
	}

	/**
	 * Tester supprimer un point de livraison qui n'existe pas
	 * @throws Exception : l'exception lors le point supprime n'existe pas
	 */
	@Test(expected = Exception.class)
	public void testSupprimerPointLivraisonMetier_exception() throws Exception {
		System.out.println("--------------------------testSupprimerPointLivraison2-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		tm.supprimerPointLivraisonMetier(260796);

	}

	@Test
	public void testDeplacerPointLivraison() throws Exception {
		System.out.println("--------------------------testDeplacerPointLivraison-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		tm.deplacerPointLivraison(26057084, 26079654);

		Intersection test1 = new IntersectionNormal(26057085, 45.756638, 4.8683963);
		Intersection test2 = new PointLivraison(26057084, 45.756714, 4.8673143, 60);

		assertEquals(tm.getListeTournees().get(2).getListeChemins().size(), 3);
		assertEquals(tm.getListeTournees().get(0).getListeChemins().get(0).getListeIntersections().get(2), test1);
		assertEquals(tm.getListeTournees().get(2).getListeChemins().get(2).getListeIntersections().get(0), test2);

	}

	@Test
	public void testDeplacerPointLivraison2() throws Exception {
		System.out.println("--------------------------testDeplacerPointLivraison2-------------------------------");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan2.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl3.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 3);
		tm.deplacerPointLivraison(26079655, 26079654);

		int nbTournee = tm.getListeTournees().size();
		assertEquals(nbTournee, 2);

	}

	@Test
	public void testCreerTourneeJusteUnLivraison() throws Exception {
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testPlan.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		PointLivraison tempP = new PointLivraison(25175791, 45.75406, 4.857418);
		Intersection prePoint = new IntersectionNormal(25175778);
		tm.creerTourneeJusteUnLivraison(tempP, prePoint);
		assertEquals(tm.getListeTournees().size(), 1);
	}

	@Test
	public void testGetPrePointLivraisonId() throws Exception {
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testTournee_plan1.xml");
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan unPlan = controleur.getMonPlan();
		File fichierTestDemandeLivraison = new File("fichiersXML2018/fichiersTest/testTournee_dl1.xml");
		controleur.chargerFichierDemandeLivraison(fichierTestDemandeLivraison);
		DemandeLivraison demande = controleur.getMaDemande();
		tm.calculerLesTournees(demande, unPlan, 1);
		Intersection test = new PointLivraison(26057085, 45.756638, 4.8683963, 60);
		Intersection livraison = tm.getPrePointLivraisonId(26316513);
		assertEquals(test, livraison);
	}

}
