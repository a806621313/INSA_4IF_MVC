package testunitaire;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import controleur.Controleur;
import modele.metier.*;
import modele.services.LecteurDeXML;
import modele.services.exceptions.*;

public class TestLecteurDeXML {
	
	private static LecteurDeXML lecture = LecteurDeXML.getInstance();
	private static Controleur controleur = Controleur.getInstance();
	
	@Before
	public void setUp(){
		controleur.setEtat(controleur.getEtatInit());
	}
	

/*--------------------------------------Test Lecture Plan----------------------------------------------*/	

	@Test
	public void testLecturePlanXML() throws Exception{
		System.out.println("test Lecture Plan XML");
		Plan plan = controleur.getMonPlan();
		File fichierTestPlan=new File("fichiersXML2018/fichiersTest/testPlan.xml");	
		lecture.lecturePlanXML(fichierTestPlan);
		assertEquals(plan.getIntersectionNormals().size(),4);
		assertEquals(plan.getTroncons().size(),2);
	}
	
	
	//------------------------verification construction du document XML----------------------------------
	
	@Test(expected = PlanXMLFileException.class)
	public void testLecturePlanXML_RacineFausse() throws Exception{
		System.out.println("test Lecture Plan XML Racine Fausse:");
		File fichierTestPlan=new File("fichiersXML2018/fichiersTest/testPlan_RacineFausse.xml");
		lecture.lecturePlanXML(fichierTestPlan);
	}
	
	@Test(expected = Exception.class)
	public void testLecturePlanXML_NoeudManqueAttribut() throws Exception{
		System.out.println("test Lecture Plan XML Noeud Manque Attribut:");
		File fichierTestPlan=new File("fichiersXML2018/fichiersTest/testPlan_NoeudManqueAttribut.xml");
		lecture.lecturePlanXML(fichierTestPlan);
	}
	
	//------------------------verification construnction de l’objet Plan---------------------------------
	
	@Test(expected = XMLMalFormeException.class)
	public void testLecturePlanXML_NoeudFormatFausse() throws Exception{
		System.out.println("test Lecture Plan XML Noeud Format Fausse:");
		File fichierTestPlan=new File("fichiersXML2018/fichiersTest/testPlan_NoeudFormatFausse.xml");
		lecture.lecturePlanXML(fichierTestPlan);
	}
	
/*-------------------------------Test Lecture Livraison Entrepot---------------------------------------*/
	
	@Test
	public void testLectureLivraisonEntrepotXML() throws Exception{
		System.out.println("test Lecture Livraison Entrepot XML");
		File fichierTestPlan=new File("fichiersXML2018/petitPlan.xml");	
		lecture.lecturePlanXML(fichierTestPlan);
		DemandeLivraison dl = controleur.getMaDemande();
		File fichierTestLivraison=new File("fichiersXML2018/fichiersTest/dl-test-3.xml");
		lecture.lectureLivraisonEntrepotXML(fichierTestLivraison);
		assertEquals(dl.getAllEntrepots().size(),1);
		assertEquals(dl.getAllPointLivraisons().size(),3);
	}
	
	
	//------------------------verification construction du document XML----------------------------------

	@Test(expected = Exception.class)
	public void testLectureLivraisonEntrepotXML_LivraisonFormatFausse() throws Exception{
		System.out.println("test Lecture Livraison Entrepot XML Livraison Format Fausse:");
		File fichierTestLivraison=new File("fichiersXML2018/fichiersTest/dl-test-3-LivraisonFormatFausse.xml");
		lecture.lectureLivraisonEntrepotXML(fichierTestLivraison);
	}
	
	@Test(expected = XMLMalFormeException.class)
	public void testLectureLivraisonEntrepotXML_LivraisonNodeFausse() throws Exception{
		System.out.println("test Lecture Livraison Entrepot XML Livraison Node Fausse:");
		File fichierTestLivraison=new File("fichiersXML2018/fichiersTest/dl-test-3-LivraisonNodeFausse.xml");
		lecture.lectureLivraisonEntrepotXML(fichierTestLivraison);
	}
	
	
	@Test(expected = DemandeLivraisonXMLFileException.class)
	public void testLectureLivraisonEntrepotXML_RacineFausse() throws Exception{
		System.out.println("test Lecture Livraison Entrepot XML Racine Fausse:");
		File fichierTestLivraison=new File("fichiersXML2018/fichiersTest/dl-test-3-RacineFausse.xml");
		lecture.lectureLivraisonEntrepotXML(fichierTestLivraison);
	}
	

	@After
	public void after() {
		System.out.println("-----------------------------------------------------------------------------");
	}
	 
	@AfterClass
	public static void clean() {
		lecture=null;
		System.out.println("");
	}
	
}
