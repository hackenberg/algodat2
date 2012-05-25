package ads1ss12.pa;

import java.util.HashSet;

/**
 * Klasse zum Berechnen eines k-MST mittels Branch-and-Bound. Hier sollen Sie
 * Ihre Lösung implementieren.
 */
public class KMST extends AbstractKMST {

	private int numNodes;
	private int numEdges;
	private HashSet<Edge> edges;
	private int k;

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
		this.numNodes = numNodes;
		this.numEdges = numEdges;
		this.edges = edges;
		this.k = k;
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

		// Menge nodes mit allen Knoten erstellen
		HashSet<Integer> nodes = new HashSet<Integer>();
		for(Edge e : edges) {
			if(!nodes.contains(e.node1))
				nodes.add(e.node1);
			if(!nodes.contains(e.node2))
				nodes.add(e.node2);
		}

		for(Integer n : nodes) {
			prim(n, k, edges);
		}
	}

	/**
	 * Implementierung des Algorithmus von Prim zur Findung eines kMST.
	 * Dieser Algorithmus enthält bereits BnB spezifische Modifizierungen.
	 */
	public HashSet<Edge> prim(int node, int k, HashSet<Edge> graph) {

		HashSet<Edge> mst = new HashSet<Edge>();
		HashSet<Integer> nodes = new HashSet<Integer>();
		int newUpperBound = 0; // Gewicht jener Kanten, die in der Lösungsmenge enthalten sind

		nodes.add(node);

		while(nodes.size() < k) {
			Edge min = null;
			for(Edge e : graph) {
				if(nodes.contains(e.node1) && e < min)
					min = e;
			}
			newUpperBound += min.weight;

			// abbrechen falls Lösung nicht mehr optimal
			if(newUpperBound > this.getSolution().getUpperBound())
				break;

			mst.add(min);
			nodes.add(min.node2);
		}

		//-----------------------
		

		for(Edge e : mst) {
			newUpperBound += e.weight;
		}

		this.setSolution(newUpperBound, mst);
		
		return mst;
	}

} // class KMST
