
/**
 * Funcion de ajuste a optimizar por el escalador
 * 
 * @author Alfonso Kim
 */
public interface Function {
	
	/**
	 * @return El nombre de la funcion
	 */
	public String getName();
	
	/**
	 * Evalua los valores x de la funcion y devuelve el valor de la funcion
	 * en ese punto
	 * 
	 * @param x Los valores del punto a evaluar
	 * @return	El resultado de la evaluacion
	 */
	public double evaluate(double... x);
	
}
