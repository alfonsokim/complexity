/**
 * 
 */
package cyc.montecarlo;

import cyc.montecarlo.funcion.Funcion;
import cyc.montecarlo.funcion.FuncionPruebas;

/**
 * @author Alfonso Kim
 *
 */
public class Main {

	/**
	 * 
	 */
	public Main() { }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Funcion funcion = new FuncionPruebas(0, 100);
		System.out.println(funcion);
	}

}
