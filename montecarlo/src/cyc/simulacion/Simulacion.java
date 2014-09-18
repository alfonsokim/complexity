/**
 * 
 */
package cyc.simulacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

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
		try {
			experimento.toCsv(new FileOutputStream(new File("experimento.csv")));
			long tamMuestra = 1000;
			Muestra muestra = experimento.generarMuestra(tamMuestra);
			System.out.println(muestra);
			List<Cuantil> limitesGausiana = muestra.calculaLimitesCuantiles(10);
			System.out.println("Limites Gausiana:");
			for(Cuantil limite: limitesGausiana){
				double pxObsercado= (new Double(limite.getObservacionesAcumuladas()) / tamMuestra);
				System.out.println(limite + ", pxObservado=" + pxObsercado);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Imposible escribir archivos de salida " + e.getMessage());
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Simulacion().ejecutar();
	}

}
