package testunitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Observable;
import java.util.Observer;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import modele.metier.DemandeLivraison;
import modele.metier.PointLivraison;

public class TestDemandeLivraison {
	
	Observer observer;
	boolean updateAppele;
	
	@Before
	public void setUp(){
		updateAppele = false;
		observer = new Observer(){public void update(Observable o, Object arg){updateAppele = true;}};
	}
	
	@Test
	public void testAjouterEntrepot() {
		DemandeLivraison dl = new DemandeLivraison();
		dl.addObserver(observer);
		dl.ajouterEntrepot(48830472, 45.75406, 4.857418, 8,0,0);
		assert(updateAppele);
	}
	
	@Test
	public void testAjouterPointLivraisonMetier() {
		DemandeLivraison dl = new DemandeLivraison();
		dl.ajouterPointLivraisonMetier(48830472, 45.75406, 4.857418, 360);
		PointLivraison test = new PointLivraison(48830472, 45.75406, 4.857418, 360);
		assert(dl.getAllPointLivraisons().contains(test));
	}
	
	@Test
	public void testClear() {
		DemandeLivraison dl = new DemandeLivraison();
		dl.ajouterEntrepot(48830472, 45.75406, 4.857418, 8,0,0);
		dl.ajouterPointLivraisonMetier(48830471, 45.75406, 4.857418, 360);
		dl.clear();	
		assertNull(dl.getEntrepotParId(48830472));
		assertNull(dl.getPointLivraisonParId(48830471));
	}
	
	@Test
	public void testAjouterPoint() {
		DemandeLivraison dl = new DemandeLivraison();
		dl.addObserver(observer);
		dl.ajouterPoint(48830472, 45.75406, 4.857418);
		assert(updateAppele);
	}
	
	@Test
	public void testSupprimerPoint() {
		DemandeLivraison dl = new DemandeLivraison();
		dl.addObserver(observer);
		dl.ajouterPoint(48830472, 45.75406, 4.857418);
		updateAppele = false;
		dl.supprimerPoint(48830472);
		assert(updateAppele);
		assertNull(dl.getPointLivraisonParId(48830472));
	}
	
	@Test
	public void testGetNbLivreurMaximum() {
		DemandeLivraison dl = new DemandeLivraison();
		dl.ajouterEntrepot(48830472, 45.75406, 4.857418, 8,0,0);
		dl.ajouterPointLivraisonMetier(48830471, 45.75406, 4.857418, 360);
		dl.ajouterPointLivraisonMetier(48830471, 45.75406, 4.857418, 360);
		int nblivreur = dl.getNbLivreurMaximum();
		assertEquals(nblivreur,1);
	}
	
	/**
	 * On doit changer DateTime test toujours au date de test (aujourd'hui)
	 */
	@Test
	public void testGetDebutTime() {
		DemandeLivraison dl = new DemandeLivraison();
		dl.ajouterEntrepot(48830472, 45.75406, 4.857418, 8,0,0);
		DateTime test = new DateTime(2018,12,13,8,0,0);
		DateTime timeDepart = dl.getDebutTime();
		assertEquals(test,timeDepart);
	}
	
	

}
