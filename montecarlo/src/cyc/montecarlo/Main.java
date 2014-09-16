/**
 * 
 */
package cyc.montecarlo;

import cyc.montecarlo.funcion.Elemento;
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
		Experimento experimento = new Experimento(new FuncionPruebas(0, 10000, 100));
		experimento.generarObservaciones(1000);
		System.out.println(experimento);
		Gausiana gausiana = experimento.calculaGausiana(10);
		System.out.println("Cuantiles:");
		for(Elemento limite: gausiana.getCuantiles()){
			System.out.println(limite);
		}
	}

}
