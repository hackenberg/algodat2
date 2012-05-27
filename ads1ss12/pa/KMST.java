package ads1ss12.pa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Klasse zum Berechnen eines k-MST mittels Branch-and-Bound. Hier sollen Sie
 * Ihre Lösung implementieren.
 */
public class KMST extends AbstractKMST {

	private int numNodes;
	private int numEdges;
	private HashSet<Edge> edges;
	private int k;

	private HashSet<Integer> nodes;
	private ArrayList<PriorityQueue<Integer>> al; // Adjazenzliste

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

		// Knotenliste intitialisieren
		this.nodes = new HashSet<Integer>();
		for(Edge e : edges) {
			this.nodes.add(e.node1);
			this.nodes.add(e.node2);
		}

		// Adjazenzliste initialisieren
		al = new ArrayList<PriorityQueue<Integer>>();
		for(Integer n : nodes) {
			al.add(n, new PriorityQueue<Integer>());
		}
		for(Edge e : edges) {
			al.get(e.node1).add(e.node2);
			al.get(e.node2).add(e.node1);
		}

		this.setSolution(Integer.MAX_VALUE, edges);
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

		int startNode = 0;

		// navigiere mit Depth-First-Search zu jedem Knoten
		for(Integer n : depthFirstSearch1(startNode)) {
			prim(n);
		}
	}

	/**
	 * Implementierung des Algorithmus von Prim zur Findung eines kMST.
	 * Dieser Algorithmus enthält bereits BnB spezifische Modifizierungen.
	 */
	public void prim(int node) {

		HashSet<Edge> mst = new HashSet<Edge>(); 		// Lösungsmenge
		HashSet<Integer> mstNodes = new HashSet<Integer>();	// In der Lösungsmenge enthaltenen Knoten
		int localLowerBound = 0; 				// lokale untere Schranke
		mstNodes.add(node);

		prim(mst, mstNodes, edges, localLowerBound);
		return;
	}

	public void prim(HashSet<Edge> mst, HashSet<Integer> mstNodes, HashSet<Edge> remainingEdges, int localLowerBound) {

		// beenden, falls k Knoten verbunden
		if(mst.size() >= k) {
			this.setSolution(localLowerBound, mst);
			return;
		}

		// abbrechen falls Lösung nicht mehr optimal
		else if(localLowerBound > this.getSolution().getUpperBound()) {
			return;
		}

		// kleinste, mit der Lösungsmenge verbundene, Kante suchen
		Edge minEdge = null;
		int minWeight = Integer.MAX_VALUE;
		for(Edge e : remainingEdges) {
			if(e.weight < minWeight && (mstNodes.contains(e.node1) ^ mstNodes.contains(e.node2))) {
				minEdge = e;
				minWeight = e.weight;
			}
		}

		// minEdge wird auf jeden Fall entfernt
		HashSet<Edge> newRemainingEdges = new HashSet<Edge>(remainingEdges);
		remainingEdges.remove(minEdge);

		// mit minEdge fortfahren
		HashSet<Edge> newMst = new HashSet<Edge>(mst);
		HashSet<Integer> newMstNodes = new HashSet<Integer>(mstNodes);

		newMst.add(minEdge);
		newMstNodes.add(minEdge.node1);
		newMstNodes.add(minEdge.node2);
		int newLocalLowerBound = localLowerBound + minWeight;

		prim(newMst, newMstNodes, newRemainingEdges, newLocalLowerBound);

		// ohne minEdge fortfahren
		newMst = new HashSet<Edge>(mst);
		newMstNodes = new HashSet<Integer>(mstNodes);
		newLocalLowerBound = localLowerBound;

		prim(newMst, newMstNodes, newRemainingEdges, localLowerBound);
	}


	public ArrayList<Integer> depthFirstSearch1(int node) {
		boolean[] marked = new boolean[numNodes];
		for(int i = 0; i < numNodes; i++) {
			marked[i] = false;
		}

		dfs1(node, marked);

		// Ausgabe
		ArrayList<Integer> out = new ArrayList<Integer>();
		for(int i = 0; i < numNodes; i++) {
			if(marked[i])
				out.add(i);
		}
		return out;
	}
	public void dfs1(int node, boolean[] marked) {
		marked[node] = true;
		for(Integer n : al.get(node)) {
			if(!marked[n])
				dfs1(n, marked);
		}
	}

} // class KMST
