/**
 * 
 */
package cyc.montecarlo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Experimento experimento = new Experimento(new FuncionPruebas(0, 10000, 100));
		experimento.generarObservaciones(1000);
		experimento.toCsv(new FileOutputStream(new File("experimento.csv")));
		Gausiana gausiana = experimento.calculaGausiana(10);
		gausiana.toCsv(new FileOutputStream(new File("cuantil.csv")));
		for(long tamMuestra = 100; tamMuestra < 10000000; tamMuestra *= 10){
			ResultadoMuestra rm = gausiana.muestrear(tamMuestra);
			System.out.println("Muestra tam " + tamMuestra + ": " + rm);
		}
	}

}
