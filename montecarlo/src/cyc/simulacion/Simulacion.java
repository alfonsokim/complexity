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
		System.out.println(experimento);
		Muestra muestra = experimento.generarMuestra(1000);
		System.out.println(muestra);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Simulacion().ejecutar();
	}

}
