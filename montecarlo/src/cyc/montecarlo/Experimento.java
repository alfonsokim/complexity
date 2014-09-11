/**
 * 
 */
package cyc.montecarlo;

/**
 * @author Alfonso Kim
 *
 */
public class Experimento {
	
	private long minimo;
	private long maximo;
	private double normalizador;

	/**
	 * Seria bueno recibir una ecuacion al constructor
	 */
	public Experimento(long minimo, long maximo) {
		//TODO: Verificar que el maximo sea mayor al minimo
		this.minimo = minimo;
		this.maximo = maximo;
		this.normalizador = fX(maximo);
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	public double probabilidadObservacion(long x){
		if(x < minimo || x > maximo){
			throw new RuntimeException("x fuera de los limites");
		}
		return fX(x) / normalizador;
	}

	
	/**
	 * @param x
	 * @return f(x)
	 */
	private double fX(long x){
		return (1 - (2*x*x) + (3*x*x*x));
	}

}
