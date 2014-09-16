/**
 * 
 */
package cyc.montecarlo.funcion;

/**
 * @author Alfonso Kim
 *
 */
public class Cuantil extends Elemento {
	
	private long numObservacionesCuantil;

	/**
	 * 
	 */
	public Cuantil() { }

	/**
	 * @return the numObservacionesCuantil
	 */
	public long getNumObservacionesCuantil() {
		return numObservacionesCuantil;
	}

	/**
	 * @param numObservacionesCuantil the numObservacionesCuantil to set
	 */
	public void nuevaObservacionesCuantil() {
		this.numObservacionesCuantil += 1;
	}

}
