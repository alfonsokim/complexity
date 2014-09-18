/**
 * 
 */
package cyc.simulacion.funcion;

/**
 * @author Alfonso
 *
 */
public class FuncionPruebas implements Funcion {

	/**
	 * 
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
	 * 
	 */
	public String toString(){
		return "f(x) = 1 - (2*x*x) + (3*x*x*x)";
	}

}
