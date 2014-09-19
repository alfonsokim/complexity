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
 * Aqui esta el punto de entrada de la consola
 * 
 * @author Alfonso Kim
 */
public class Simulacion {

	/**
	 * Vacio
	 */
	public Simulacion() { }
	
	/**
	 * Ejecuta la simulacion, aqui irian los parametros
	 * datos por el usuario
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
	 * Punto de entrada de la consola
	 * 
	 * @param args Los argumentos de la linea de comandos
	 * 			   En un futuro aqui se parsean los parametros
	 * 			   del usuario.
	 */
	public static void main(String[] args) {
		new Simulacion().ejecutar();
	}

}
