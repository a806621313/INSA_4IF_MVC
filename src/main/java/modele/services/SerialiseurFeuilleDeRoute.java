package modele.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import controleur.Controleur;
import modele.metier.Chemin;
import modele.metier.Entrepot;
import modele.metier.Intersection;
import modele.metier.Tournee;

/**
 * @author baptiste.thivend
 * Classe permettant l'export des tourn�es dans un fichier pdf
 */
public class SerialiseurFeuilleDeRoute {
	public static Document feuilleDeRoute;

	/**
	 * M�thode pour exporter une liste de tourn�e en fichier pdf
	 * @param tournees:  le resultat final des tourn�es calcul�es
	 * @throws FileNotFoundException: exception lors de la cr�ation d'un fichier
	 * @throws DocumentException: exception lors de l'�criture d'un document   
	 * @return document: le fichier qu'on �crit l'info des tourn�es calcul�es   
	 */
	public static Document exportFeuilleDeRoute(ArrayList<Tournee> tournees)
			throws FileNotFoundException, DocumentException {
		feuilleDeRoute = new Document();
		String date = new SimpleDateFormat("MM.dd.HH.mm.ss").format(new Date());
		File f = new File("feuilleDeRoute " + date + ".pdf");
		FileOutputStream fop = new FileOutputStream(f);
		PdfWriter.getInstance(feuilleDeRoute, fop);
		feuilleDeRoute.open();

		Paragraph title = new Paragraph("Recapitulatif des tournees");
		title.setAlignment(Element.ALIGN_CENTER);

		feuilleDeRoute.add(title);
		feuilleDeRoute.add(new Paragraph());
		feuilleDeRoute.add(new Paragraph());
		String content = "";
		content = ecrireTournee(tournees,content);
		feuilleDeRoute.add(new Paragraph(content));


		feuilleDeRoute.close();
		System.out.println(feuilleDeRoute.toString());
		return feuilleDeRoute;
	}

	/**
	 * M�thode pour �crire une tourn�e dans le document pdf
	 * @param tourn�es : le r�sultat final des tourn�es calcul�es
	 * @param contenuLabel : l'info du resultat des tourn�es calcul�es
	 * @return  l'info du r�sultat des tourn�es calcul�es
	 * @throws DocumentException : l'exception lors de l'�criture d'un document
	 */
	public static String ecrireTournee(ArrayList<Tournee> tournees,String contenuLabel) throws DocumentException {
		int index = 0;
		for (Tournee t : tournees) {
			index++;
			contenuLabel += System.lineSeparator()+"Tournee "+new Integer(index).toString()+" :"+System.lineSeparator();
			ArrayList<Chemin> tempChemins = t.getListeChemins();
			DateTime depart = Controleur.getInstance().getActuelHeureDepart();
			DateTime tempTime = new DateTime(depart);
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
		}
		return contenuLabel;
	}
}
