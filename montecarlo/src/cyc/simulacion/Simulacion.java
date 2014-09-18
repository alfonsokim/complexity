/**
 * 
 */
package cyc.simulacion;

import cyc.simulacion.funcion.FuncionPruebas;

/**
 * @author Alfonso Kim
 *
 */
public class Simulacion {

	/**
	 * 
	 */
	public Simulacion() { }
	
	/**
	 * 
	 */
	public void ejecutar(){
		Experimento experimento = new Experimento(new FuncionPruebas(), 0, 100);
		System.out.println(experimento.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Simulacion().ejecutar();
	}

}
