/**
 * 
 */
package cyc.simulacion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
		OutputStream salidaExperimento = null;
		OutputStream salidaMuestra = null;
		try {
			salidaExperimento = new FileOutputStream(new File("experimento.csv"));
			experimento.toCsv(salidaExperimento);
			long tamMuestra = 1000;
			Muestra muestra = experimento.generarMuestra(tamMuestra);
			Distribucion limitesGausiana = muestra.comoDistribucion(10);
			salidaMuestra = new FileOutputStream(new File("muestra.csv"));
			limitesGausiana.toCsv(salidaMuestra);
			salidaExperimento.close();
			salidaMuestra.close();
		} catch (IOException e) {
			System.err.println("Imposible escribir archivos de salida " + e.getMessage());
		} finally { }
		System.out.println("Ejecucion finalizada");

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Simulacion().ejecutar();
	}

}
