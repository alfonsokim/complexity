/**
 * 
 */
package cyc.simulacion.funcion;

/**
 * Implementacion de la funcion de pruebas
 * 
 * Posteriormente estas clases se van a generar mediante un parser
 * y que el usuario pueda escribir su propia funcion sin necesidad
 * de recompilar
 * 
 * @author Alfonso Kim
 */
public class FuncionPruebas implements Funcion {
	
	/**
	 * Vacio
	 */
	public FuncionPruebas() { }

	/**
	 * @see cyc.simulacion.funcion.Funcion#fX(double)
	 */
	@Override
	public double fX(double x) {
		return 1 - (2*x*x) + (3*x*x*x);
	}
	
	/**
	 * Devuelve el string de la funcion. Puede ser la entrada
	 * de usuario
	 */
	@Override
	public String toString(){
		return "f(x) = 1 - (2*x*x) + (3*x*x*x)";
	}

}
