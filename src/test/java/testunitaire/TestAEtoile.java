package testunitaire;

import org.junit.*;

import controleur.Controleur;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import modele.algo.*;
import modele.metier.*;
import modele.services.exceptions.IntersectionNonLivrableException;

public class TestAEtoile {
	
	private static AEtoile aEtoile = AEtoile.getInstance();
	private static Controleur controleur = Controleur.getInstance();
	
	@Before
	public void setUp(){
		controleur.setEtat(controleur.getEtatInit());
	}
	
	@Test
	public void testAlgoAEtoile1() throws Exception {	
		System.out.println("test Algo A Etoile 1:");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testPlan.xml");	
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan monPlan = controleur.getMonPlan();
		Intersection depart = new IntersectionNormal(26079801,45.754852,4.8574104);
		Intersection dest = new IntersectionNormal(2117622723,45.75425,4.8591485);
		ArrayList<Intersection> chemin = aEtoile.algoAEtoile(depart, dest, monPlan);
		Intersection test = new IntersectionNormal(25175791,45.75406,4.857418);
		assertEquals(chemin.size(),3);
		assertEquals(chemin.get(1),test);
	}
	
	@Test
	public void testAlgoAEtoile2() throws Exception {
		System.out.println("test Algo A Etoile 2:");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testAEtoilePlan.xml");	
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan monPlan = controleur.getMonPlan();
		Intersection depart = new IntersectionNormal(1,0.0,0.0);
		Intersection dest = new IntersectionNormal(4,0.0,5.0);
		ArrayList<Intersection> chemin = aEtoile.algoAEtoile(depart, dest, monPlan);
		Intersection test = new IntersectionNormal(5,0.0,2.5);
		Intersection test2 = new IntersectionNormal(8,0.0,3.5);
		assertEquals(chemin.size(),4);
		assertEquals(chemin.get(1),test);
		assertEquals(chemin.get(2),test2); //latitude?Longer?
	}
	
	@Test
	public void testAlgoAEtoile3() throws Exception {
		System.out.println("test Algo A Etoile 2:");
		File fichierTestPlan = new File("fichiersXML2018/grandPlan.xml");	
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan monPlan = controleur.getMonPlan();
		Intersection depart = new IntersectionNormal(48830472,45.758003,4.874931);
		Intersection dest = new IntersectionNormal(208769499,45.760597,4.87622);
		ArrayList<Intersection> chemin = aEtoile.algoAEtoile(depart, dest, monPlan);
		Intersection test = new IntersectionNormal(55475052,45.758472,4.8751354);
		assertEquals(chemin.get(1),test);
	}
	
	/**
	 * Tester algorithme A* en presentant une intersection inaccessible par les autre intersections 
	 * @throws IntersectionNonLivrableException : l'ntersection choisie n'est pas livrable 
	 */
	@Test(expected = IntersectionNonLivrableException.class)
	public void testAlgoAEtoile_NonAtteint() throws Exception{
		System.out.println("test Algo A Etoile Non Atteint:");
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testAEtoilePlan2.xml");	
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan monPlan = controleur.getMonPlan();
		Intersection depart = new IntersectionNormal(48830472,45.758003,4.874931);
		Intersection dest = new IntersectionNormal(208769499,45.760597,4.87622);
		ArrayList<Intersection> chemin = aEtoile.algoAEtoile(depart, dest, monPlan);
	}
	
	@Test
	public void testTraductionTrajet() throws Exception {
		File fichierTestPlan = new File("fichiersXML2018/fichiersTest/testPlan.xml");	
		controleur.chargerFichierPlan(fichierTestPlan);
		Plan monPlan = controleur.getMonPlan();
		IntersectionNormal depart = new IntersectionNormal(26079801,45.754852,4.8574104);
		IntersectionNormal dest = new IntersectionNormal(2117622723,45.75425,4.8591485);
		ArrayList<Intersection> chemin = aEtoile.algoAEtoile(depart, dest, monPlan);
		ArrayList<Troncon> troncons = aEtoile.traductionTrajet(chemin, monPlan);
		IntersectionNormal inter = new IntersectionNormal(25175791,45.75406,4.857418);
		assertEquals(troncons.get(1).getNomRue(),"Rue de l'Abondance");
	}
	
}
