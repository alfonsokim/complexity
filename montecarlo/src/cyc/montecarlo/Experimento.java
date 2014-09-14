/**
 * 
 */
package cyc.montecarlo;

import java.util.Random;

import cyc.montecarlo.funcion.Funcion;

/**
 * @author Alfonso Kim
 *
 */
public class Experimento {
	
	private Funcion funcion;
	private Random random;

	/**
	 * 
	 * @param funcion
	 */
	public Experimento(Funcion funcion) {
		this.funcion = funcion;
		this.random = new Random();
	}
	
	/**
	 * 
	 * @param numObservaciones
	 */
	public void generarObservaciones(int numObservaciones){
		for(int i = 0; i < numObservaciones; i++){
			funcion.observacion(random.nextDouble());
		}
		funcion.setValoresObservados(numObservaciones);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Experimento [funcion=").append(funcion).append("]");
		return builder.toString();
	}
	
	

}
