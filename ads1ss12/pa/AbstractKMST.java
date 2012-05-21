package ads1ss12.pa;

import java.util.Set;

/**
 * Abstrakte Klasse zum Berechnen eines k-MST mittels Branch-and-Bound.
 * 
 * <p>
 * <b>WICHTIG:</b> Nehmen Sie keine Änderungen in dieser Klasse vor. Bei
 * der Abgabe werden diese Änderungen verworfen und es könnte dadurch
 * passieren, dass Ihr Programm somit nicht mehr korrekt funktioniert.
 * </p>
 */
public abstract class AbstractKMST implements Runnable {
	
	/** Die bisher beste Lösung */
	private BnBSolution sol;

	/**
	 * Diese Methode setzt einen neue (beste) Lösung.
	 * 
	 * <p>
	 * <strong>ACHTUNG:</strong> die Lösung wird nur übernommen wenn
	 * <code>newUpperBound</code> niedriger ist als {@link #upperBound}.
	 * </p>
	 * 
	 * @param newUpperBound
	 *            neue obere Grenze
	 * @param newSoluton
	 *            neue beste Lösung
	 * @return Wahr wenn die Lösung übernommen wurde.
	 */
	final public synchronized boolean setSolution(int newUpperBound, Set<Edge> newSoluton) {
		if (sol == null || newUpperBound < sol.getUpperBound()) {
			sol = new BnBSolution(newUpperBound, newSoluton);
			return true;
		}
		return false;
	}
	
	/**
	 * Gibt die bisher beste gefundene Lösung zurück.
	 * 
	 * @return Die bisher beste gefundene Lösung.
	 */
	final public BnBSolution getSolution() {
		return sol;
	}
	
	public final class BnBSolution {

		private int upperBound = Integer.MAX_VALUE;
		private Set<Edge> bestSolution;
		
		public BnBSolution(int newUpperBound, Set<Edge> newSoluton) {
			upperBound = newUpperBound;
			bestSolution = newSoluton;
		}

		/**
		 * @return Die obere Schranke
		 */
		public int getUpperBound() {
			return upperBound;
		}

		/**
		 * @return Die Kanten der bisher besten Lösung
		 */
		public Set<Edge> getBestSolution() {
			return bestSolution;
		}
		
	}

}
