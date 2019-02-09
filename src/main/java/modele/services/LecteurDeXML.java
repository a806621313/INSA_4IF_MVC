package modele.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import controleur.Controleur;
import modele.metier.Entrepot;
import modele.metier.IntersectionNormal;
import modele.metier.PointLivraison;
import modele.metier.Troncon;
import modele.services.exceptions.DemandeLivraisonXMLFileException;
import modele.services.exceptions.PlanXMLFileException;
import modele.services.exceptions.XMLMalFormeException;

/** 
 * La classe du lecteur XML
 * @author H4404
 * @version 1.0
 * @since 1.0
*/

public class LecteurDeXML {
	
	private static LecteurDeXML instance = null;
	
	/**
	 * Constructeur de la classe LecteurDeXML
	 */
	private LecteurDeXML() {}
	
	/**
	 * Méthode pour obtenir une instance de la classe LecteurDeXML
	 * @return l'instance de la classe LecteurDe XML
	 */
	public static LecteurDeXML getInstance() {
		if(instance == null) instance = new LecteurDeXML();
		return instance;
	}
	
	/**
	 * Méthode permettant la lecture d'un fichier XML qui contient une demande de livraison
	 * @param f : le fichier XML
	 * @throws Exception si le fichier XML est mal formé
	 */
	public void lectureLivraisonEntrepotXML(File f) throws Exception{ 
		HashMap<Long,PointLivraison> tempLivraisons = new HashMap<Long,PointLivraison>();
		HashMap<Long,Entrepot> tempEntrepots = new HashMap<Long,Entrepot>();
		try {   
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
			DocumentBuilder builder = factory.newDocumentBuilder();   
			Document doc = builder.parse(f);   
			doc.getDocumentElement().normalize();
			if(doc.getDocumentElement().getNodeName().equals("demandeDeLivraisons")) {
				NodeList nl = doc.getElementsByTagName("demandeDeLivraisons");   
				for (int temp = 0; temp < nl.getLength(); temp++) {   
					Node nNode = nl.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						NodeList children = nNode.getChildNodes();
						int nbChildren = children.getLength();
						for(int i = 0; i < nbChildren; i++) {
							Node child = children.item(i);
							if (child.getNodeType() == Node.ELEMENT_NODE) {
								Element elemChild = (Element) child;
								if(elemChild.getTagName().equals("entrepot")) {
									long tempId = Long.parseUnsignedLong(elemChild.getAttribute("adresse"));
									String heureDepart = elemChild.getAttribute("heureDepart");
									String[] times= heureDepart.split(":");
									int heure = Integer.parseInt(times[0]);
									int minute = Integer.parseInt(times[1]);
									int seconde = Integer.parseInt(times[2]);
									IntersectionNormal tempInter = Controleur.getInstance().getMonPlan().getIntersectionNormal(tempId);
									Entrepot tempObject = new Entrepot(tempId,tempInter.getLatitude(),tempInter.getLongitude(),heure,minute,seconde);
									tempEntrepots.put(tempId,tempObject);
								}else if(elemChild.getTagName().equals("livraison")) {
									long tempId = Long.parseUnsignedLong(elemChild.getAttribute("adresse"));
									int duree = Integer.parseInt(elemChild.getAttribute("duree"));
									IntersectionNormal tempInter = Controleur.getInstance().getMonPlan().getIntersectionNormal(tempId);
									PointLivraison tempObject = new PointLivraison(tempId,tempInter.getLatitude(),tempInter.getLongitude(),duree);
									tempLivraisons.put(tempId,tempObject);
								}else {
									XMLMalFormeException e = new XMLMalFormeException();
									System.out.println("Erreur de l'architecture du fichier xml");
									throw e;
								}
							}
						}
					}
			    }   
			}else{
				DemandeLivraisonXMLFileException e = new DemandeLivraisonXMLFileException();
				System.out.println("Erreur de l'architecture du fichier xml");
				throw e;
			}
		    } catch (Exception e) {   
		    	e.printStackTrace();
		    	throw e;
		    } 
		Controleur.getInstance().getMaDemande().intialiserDemandeLivraison(tempLivraisons, tempEntrepots);
	}

	/**
	 * Méthode permettant la lecture d'un fichier XML qui contient un plan
	 * @param f : le fichier XML
	 * @throws Exception si le fichier XML est mal formé
	 */
	public void lecturePlanXML(File f) throws Exception{
		HashMap<Long,IntersectionNormal> tempIntersections = new HashMap<Long,IntersectionNormal>();  
		HashMap<Long,ArrayList<Troncon>> tempTroncons = new HashMap<Long,ArrayList<Troncon>>();
		double maxLong = 0;
		double maxLat = 0;
		double minLong = 0;
		double minLat = 0;
		try {     
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
			DocumentBuilder builder = factory.newDocumentBuilder();   
			Document doc = builder.parse(f);   
			doc.getDocumentElement().normalize();
			if(doc.getDocumentElement().getNodeName().equals("reseau")) {
				NodeList nl = doc.getElementsByTagName("reseau");   
				for (int temp = 0; temp < nl.getLength(); temp++) {  
					boolean first = true;
					Node nNode = nl.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						NodeList children = nNode.getChildNodes();
						int nbChildren = children.getLength();
						for(int i = 0; i < nbChildren; i++) {
							Node child = children.item(i);
							if (child.getNodeType() == Node.ELEMENT_NODE) {
								Element elemChild = (Element) child;
								if(elemChild.getTagName().equals("noeud")) {
									IntersectionNormal tempIntersection = null;
									long tempId = Long.parseUnsignedLong(elemChild.getAttribute("id"));
									double tempLatitude = Double.parseDouble(elemChild.getAttribute("latitude"));
									double tempLongitude = Double.parseDouble(elemChild.getAttribute("longitude"));
									if(first) {
										maxLong = tempLongitude;
										minLong = tempLongitude;
										maxLat = tempLatitude;
										minLat = tempLatitude;
										first = false;
									}else {
										if(tempLongitude > maxLong) {
											maxLong = tempLongitude;
										}else if(tempLongitude < minLong) {
											minLong = tempLongitude;
										}
										if(tempLatitude > maxLat) {
											maxLat = tempLatitude;
										}else if(tempLatitude < minLat) {
											minLat = tempLatitude;
										}
									}
									tempIntersection = new IntersectionNormal(tempId,tempLatitude,tempLongitude);
									tempIntersections.put(tempId, tempIntersection);
								}else if(elemChild.getTagName().equals("troncon")) {
									Troncon tempTroncon = null;
									long idDest = Long.parseUnsignedLong(elemChild.getAttribute("destination"));
									double longueur = Double.parseDouble(elemChild.getAttribute("longueur"));
									String nomRue = elemChild.getAttribute("nomRue");
									long idOrigine = Long.parseUnsignedLong(elemChild.getAttribute("origine"));
									IntersectionNormal dest = new IntersectionNormal(idDest);
									IntersectionNormal origine = new IntersectionNormal(idOrigine);
									tempTroncon = new Troncon(dest,origine,longueur,nomRue);
									if(tempTroncons.containsKey(idOrigine)) {
										tempTroncons.get(idOrigine).add(tempTroncon);
									}else {
										ArrayList<Troncon> tempListe = new ArrayList<Troncon>();
										tempListe.add(tempTroncon);
										tempTroncons.put(idOrigine, tempListe);
									}
								}else {
									XMLMalFormeException e = new XMLMalFormeException();
									System.out.println("Erreur de l'architecture du fichier xml");
									throw e;
								}
							}
						}
					}
					setLatLongDesTroncons(tempIntersections, tempTroncons);
			    }   
			}else {
				PlanXMLFileException e = new PlanXMLFileException();
				System.out.println("Erreur de l'architecture du fichier xml");
				throw e;
			}
			
		    } catch (Exception e) {  
		    	throw e;   
		    }
		Controleur.getInstance().getMonPlan().initialiserPlan(tempIntersections, tempTroncons, maxLong, minLong, maxLat, minLat);
	}
	
	/**
	 * Méthode permettant l'affectation des longitudes et latitudes des troncons
	 * @param tempIntersections : les intersections
	 * @param tempTroncons : les tronçons.
	 * @throws java.lang.Exception : exception lors de la configuration de la position des tronçons
	 */
	public void setLatLongDesTroncons(HashMap<Long,IntersectionNormal> tempIntersections, HashMap<Long,ArrayList<Troncon>> tempTroncons) throws Exception{
		for(HashMap.Entry<Long,ArrayList<Troncon>> entry: tempTroncons.entrySet())
        {
			for(Troncon c : entry.getValue()) {
				IntersectionNormal origine = tempIntersections.get(c.getOrigine().getId());
				IntersectionNormal dest = tempIntersections.get(c.getDestination().getId());
				c.getOrigine().setLatitude(origine.getLatitude());
				c.getOrigine().setLongitude(origine.getLongitude());
				c.getDestination().setLatitude(dest.getLatitude());
				c.getDestination().setLongitude(dest.getLongitude());
			}
        }
	}
}



