/**
 * 
 */
package cyc.simulacion;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cyc.simulacion.util.Stats;

/**
 * Representa una distribucion
 * 
 * @author Alfonso Kim
 */
public class Distribucion {
	
	private List<Cuantil> cuantiles;
	private long tamMuestra;

	/**
	 * Guarda una lista de cuantiles dentro de la distribucion
	 * y el tamanio de la muesta
	 * 
	 * @param cuantiles Los cuantiles en la distribucion
	 * @param tamMuesta El tamanio de la muestra de la distribucion
	 */
	public Distribucion(List<Cuantil> cuantiles, long tamMuestra) {
		this.cuantiles = cuantiles;
		this.tamMuestra = tamMuestra;
	}
	
	/**
	 * @return La media aritmetica de la distribucion
	 */
	public double getMedia(){
		List<Double> numeros = new ArrayList<Double>();
		for(Cuantil cuantil: cuantiles){
			numeros.add(new Double(cuantil.getObservacionesAcumuladas()) / tamMuestra);
		}
		return Stats.media(numeros);
	}
	
	/**
	 * @return La desviacion estandar de la distribucion
	 */
	public double getDesviacionStd(){
		List<Double> numeros = new ArrayList<Double>();
		for(Cuantil cuantil: cuantiles){
			numeros.add(new Double(cuantil.getObservacionesAcumuladas()) / tamMuestra);
		}
		return Stats.desviacionStd(numeros);
	}
	
	/**
	 * 		   Los datos teoricos son los que se generaron durante
	 *         el experimento, y los oservados son cuando se genero
	 *         la muestra.
	 * @return La chi cuadrada de la distribucion 
	 */
	public double chiCuadrada(){
		List<Double> sumatoria = new ArrayList<Double>();
		for(Cuantil cuantil: cuantiles){
			double teorico = Stats.suma(cuantil.getObservaciones()) / cuantil.getObservaciones().size();
			double observado = cuantil.getObservacionesAcumuladas() / tamMuestra;
			double pow = Math.pow((observado - teorico), 2);
			sumatoria.add(new Double(pow / teorico));
		}
		return Stats.suma(sumatoria);
	}
	
	/**
	 * String d ela distribucion
	 */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(Cuantil limite: cuantiles){
			double pxObsercado= (new Double(limite.getObservacionesAcumuladas()) / tamMuestra);
			builder.append("\n").append(limite).append(", pxObservado=").append(pxObsercado);
		}
		return builder.toString();
	}

	
	/**
	 * Para escribir la distribucion a un CSV (texto separado por comas)
	 * 
	 * @param streamSalida La salida al archivo CSV
	 */
	public void toCsv(OutputStream streamSalida){
		PrintWriter salida = new PrintWriter(streamSalida);
		salida.println(new StringBuilder("tamMuestra").append(",").append(tamMuestra));
		salida.println(new StringBuilder("media").append(",").append(getMedia()));
		salida.println(new StringBuilder("desviacionStd").append(",").append(getDesviacionStd()));
		salida.println(new StringBuilder("chiCuadrada").append(",").append(chiCuadrada()));
		salida.println("i,x,fx,px,pdf,pdfObservado");
		for(Cuantil cuantil: cuantiles){
			double pxObsercado= (new Double(cuantil.getObservacionesAcumuladas()) / tamMuestra);
			salida.println(new StringBuilder()
				.append(cuantil.getI()).append(",")
				.append(cuantil.getX()).append(",")
				.append(cuantil.getFx()).append(",")
				.append(cuantil.getPx()).append(",")
				.append(cuantil.getPdf()).append(",")
				.append(pxObsercado)
				.toString()
			);
		}
		salida.close();
	}
	

}
