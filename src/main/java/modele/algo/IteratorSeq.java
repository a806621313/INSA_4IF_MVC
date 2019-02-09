package modele.algo;

import java.util.Collection;
import java.util.Iterator;

/** 
 * La classe de l'itérateur
 * @author H4404
 * @version 1.0
 * @since 1.0
*/
public class IteratorSeq implements Iterator<Integer> {

	private Integer[] candidats;
	private int nbCandidats;

	/**
	 * Crée un itérateur pour itérer sur l'ensemble des sommets non-vus
	 * @param nonVus sommets non vus
	 * @param sommetCrt le sommet courant
	 */
	public IteratorSeq(Collection<Integer> nonVus, int sommetCrt){
		this.candidats = new Integer[nonVus.size()];
		nbCandidats = 0;
		for (Integer s : nonVus){
			candidats[nbCandidats++] = s;
		}
	}
	
	
	@Override
	public boolean hasNext() {
		return nbCandidats > 0;
	}

	@Override
	public Integer next() {
		return candidats[--nbCandidats];
	}

	@Override
	public void remove() {}

}
