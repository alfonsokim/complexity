/**
 * 
 */
package cyc.simulacion.funcion;


/**
 * Interfaz que representa una funcion a simular.
 * 
 * La idea es que posteriormente se incorpore un parser de funciones
 * que devuelval objetos que implementan esta interfaz.
 * 
 * @author Alfonso Kim
 */
public interface Funcion {
	
	/**
	 * El metodo prncipal, calcula y como f(x)
	 * Depende de la implementacion de la clase el comportamiento
	 * de este metodo.
	 * 
	 * @param x El valor x a calcular
	 * @return	y o f(x)
	 */
	public double fX(double x);
	

}
