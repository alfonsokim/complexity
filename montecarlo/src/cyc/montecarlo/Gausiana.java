/**
 * 
 */
package cyc.montecarlo;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import cyc.montecarlo.funcion.Cuantil;

/**
 * @author Alfonso Kim
 *
 */
public class Gausiana {
	
	private List<Cuantil> cuantiles;
	private Random random;

	/**
	 * 
	 */
	public Gausiana() { 
		cuantiles = new ArrayList<Cuantil>();
		random = new Random();
	}
	
	/**
	 * 
	 */
	public Gausiana clone(){
		Gausiana g = new Gausiana();
		for(Cuantil cuantil: cuantiles){
			g.addCuantil(cuantil.clone());
		}
		return g;
	}
	
	/**
	 * 
	 * @param cuantil
	 */
	public void addCuantil(Cuantil cuantil){
		cuantiles.add(cuantil);
	}
	
	/**
	 * 
	 * @param numMuestras
	 * @param tamMuestra
	 */
	public ResultadoMuestra muestrear(long tamMuestra){
		Gausiana g = clone();
		for(int i = 0; i < tamMuestra; i++){
			g.agregarObservacion(random.nextDouble());
		}
		ResultadoMuestra rm = new ResultadoMuestra();
		rm.setMedia(g.getMedia() / tamMuestra);
		rm.setDesviacionStd(g.getDesviacionStd() / tamMuestra);
		return rm;
	}
	
	/**
	 * 
	 * @param probX
	 */
	public void agregarObservacion(double probX){
		if(probX < 0 || probX > 1){
			throw new RuntimeException("observacion fuera de rango [0, 1]");
		}
		
		Collections.sort(cuantiles, new Comparator<Cuantil>(){
			@Override
			public int compare(Cuantil a, Cuantil b) {
				return new Double(a.getPdf()).compareTo(new Double(b.getPdf()));
			}
		});
		
		Cuantil observado = cuantiles.get(0);
		for(Cuantil elemento: cuantiles){
			if(elemento.getPdf() > probX){
				break;	// Esto se puede hacer en un ciclo sin cuerpo
			} 
			observado = elemento;
		}
		observado.nuevaObservacionCuantil(probX);
		
	}
	
	/**
	 * 
	 */
	public double getMedia(){
		double media = 0;
		for(Cuantil cuantil: cuantiles){
			media += cuantil.getDeltaObservaciones();
		}
		return (media / cuantiles.size());
	}
	
	/**
	 * 
	 */
	public double getDesviacionStd(){
		double acumulado = 0;
		double media = getMedia();
		for(Cuantil cuantil : cuantiles){
			double a = media - cuantil.getDeltaObservaciones();
			acumulado += (a * a);
		}
		return Math.sqrt(acumulado / cuantiles.size());
	}

	/**
	 * @return the cuantiles
	 */
	public List<Cuantil> getCuantiles() {
		return cuantiles;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Gausiana [cuantiles=");
		for(Cuantil cuantil: cuantiles){
			builder.append(cuantil).append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
	
	public void toCsv(OutputStream streamSalida){
		PrintWriter salida = new PrintWriter(streamSalida);
		salida.println("i,px,pdf,numObservaciones,numObservacionesAcumuladas");
		//Lo siguiente es una aberracion de lo mal estructurado del codigo
		for(Cuantil cuantil: cuantiles){
			salida.println(new StringBuilder()
				.append(cuantil.getI()).append(",")
				.append(cuantil.getPx()).append(",")
				.append(cuantil.getPdf()).append(",")
				.append(cuantil.getNumObservacionesAcumuladas()).append(",")
				.append(cuantil.getNumObservacionesCuantil())
				.toString()
			);
		}
		salida.close();
	}

}