package ads1ss12.pa;

import java.util.HashSet;

/**
 * Klasse zum Berechnen eines k-MST mittels Branch-and-Bound. Hier sollen Sie
 * Ihre Lösung implementieren.
 */
public class KMST extends AbstractKMST {

	private Integer startNode = 0;

	private int numNodes;
	private int numEdges;
	private HashSet<Edge> edges;
	private int k;

	private HashSet<Edge> mst;
	private HashSet<Integer> mstNodes;
	private int localLowerBound;

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

		// dieser Konstruktor dient nur der ersten Initialisierung
		mst = new HashSet<Edge>();
		mstNodes = new HashSet<Integer>();
		localLowerBound = 0;

		// obere Schranke setzen
		this.setSolution(Integer.MAX_VALUE, edges);
	}

	public KMST(HashSet<Edge> mst, HashSet<Integer> mstNodes, HashSet<Edge> remainingEdges, int localLowerBound) {
		this.mst = mst;
		this.mstNodes = mstNodes;
		this.edges = remainingEdges;
		this.localLowerBound = localLowerBound;
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

		// abbrechen falls Lösung nicht mehr optimal
		if(localLowerBound > this.getSolution().getUpperBound()) {
			return;
		}

		// beenden, falls k Knoten verbunden
		if(mst.size() >= k) {
			this.setSolution(localLowerBound, mst);
			return;
		}



		// Startknoten aus dem Konstruktor zur Lösungsmenge hinzufügen; sonst NullPointerException
		mstNodes.add(startNode);

		// kleinste, mit der Lösungsmenge verbundene, Kante suchen
		Edge minEdge = null;
		int minWeight = Integer.MAX_VALUE;
		for(Edge e : edges) {
			if(e.weight < minWeight && (mstNodes.contains(e.node1) ^ mstNodes.contains(e.node2))) {
				minEdge = e;
				minWeight = e.weight;
			}
		}

		// minEdge wird auf jeden Fall entfernt
		HashSet<Edge> remainingEdges = new HashSet<Edge>(edges);
		remainingEdges.remove(minEdge);



		// mit minEdge fortfahren
		HashSet<Edge> newMst = new HashSet<Edge>(mst);
		newMst.add(minEdge);
		HashSet<Integer> newMstNodes = new HashSet<Integer>(mstNodes);
		newMstNodes.add(minEdge.node1);
		newMstNodes.add(minEdge.node2);
		int newLocalLowerBound = localLowerBound + minWeight;


		KMST newKMST = new KMST(newMst, newMstNodes, remainingEdges, newLocalLowerBound);
		new Thread(newKMST, "k-mst Thread").start();



		// ohne minEdge fortfahren
		newMst = new HashSet<Edge>(mst);
		newMstNodes = new HashSet<Integer>(mstNodes);

		newKMST = new KMST(newMst, newMstNodes, remainingEdges, localLowerBound);
		new Thread(newKMST, "k-mst Thread").start();
	}

} // class KMST
