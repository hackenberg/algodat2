package ads1ss12.pa;

import java.util.HashSet;

/**
 * Klasse zum Berechnen eines k-MST mittels Branch-and-Bound. Hier sollen Sie
 * Ihre Lösung implementieren.
 */
public class KMST extends AbstractKMST {

	/**
	 * Der Konstruktor. Hier ist die richtige Stelle für die
	 * Initialisierung Ihrer Datenstrukturen.
	 * 
	 * @param numNodes
	 *            Die Anzahl der Knoten
	 * @param numEdges
	 *            Die Anzahl der Kanten
	 * @param edges
	 *            Die Menge der Kanten
	 * @param k
	 *            Die Anzahl der Knoten, die Ihr MST haben soll
	 */
	public KMST(Integer numNodes, Integer numEdges, HashSet<Edge> edges, int k) {
		// TODO: Hier ist der richtige Platz fuer Initialisierungen
	}

	/**
	 * Diese Methode bekommt vom Framework maximal 30 Sekunden Zeit zur
	 * Verfügung gestellt um einen gültigen k-MST zu finden.
	 * 
	 * <p>
	 * Fügen Sie hier Ihre Implementierung des Branch-and-Bound Algorithmus
	 * ein.
	 * </p>
	 */
	@Override
	public void run() {
		
		// günstigste Kante auswählen, die keinen Kreis schließt
	/*	Edge min = null;
		for(Edge e : edges) {
			if(!usedNodes.contains(e.node1) && !usedNodes.contains(e.node2)) {
				if(e < min)
					min = e;
			}
		}

		// günstigste Kante hinzufügen
		usedNodes.add(min.node1);
		usedNodes.add(min.node2);
		usedEdges.add(min);

		// Schranken anpassen
		l += min.weight;


		// Rekursion ausführen oder abbrechen
		if(usedNodes.size() == k)
			return;
		else
			run();
*/


	}

	/**
	 * Implementierung des Algorithmus von Prim zur Findung eines kMST.
	 */
	public HashSet<Edge> prim(int node, int k, HashSet<Edge> graph) {

		HashSet<Edge> mst = new HashSet<Edge>();
		HashSet<Integer> nodes = new HashSet<Integer>();

		nodes.add(node);

		while(nodes.size() < k) {
			Edge min = null;
			for(Edge e : graph) {
				if(nodes.contains(e.node1) && e.weight < min.weight)
					min = e;
			}
			mst.add(min);
			nodes.add(min.node2);
		}
		
		return mst;
	}

} // class KMST
