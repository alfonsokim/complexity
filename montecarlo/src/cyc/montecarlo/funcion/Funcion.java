/**
 * 
 */
package cyc.montecarlo.funcion;

/**
 * @author Alfonso Kim
 *
 */
public interface Funcion {
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	public double fX(double x);
	
	/**
	 * 
	 * @param probX
	 */
	public void observacion(double probX);
	
	/**
	 * 
	 * @param numObservaciones
	 */
	public void setValoresObservados(long numObservaciones);

}