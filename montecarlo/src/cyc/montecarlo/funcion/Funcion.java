/**
 * 
 */
package cyc.montecarlo.funcion;

import cyc.montecarlo.Gausiana;

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
	
	/**
	 * 
	 * @return
	 */
	public double getMedia();
	
	/**
	 * 
	 * @return
	 */
	public double getDesviacionStd();
	
	/**
	 * 
	 * @param numCuantiles
	 * @return
	 */
	public Gausiana calculaGausiana(int numCuantiles);

}
