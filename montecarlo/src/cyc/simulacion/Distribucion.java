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
 * @author Alfonso Kim
 *
 */
public class Distribucion {
	
	private List<Cuantil> cuantiles;
	private long tamMuestra;

	/**
	 * 
	 */
	public Distribucion(List<Cuantil> cuantiles, long tamMuestra) {
		this.cuantiles = cuantiles;
		this.tamMuestra = tamMuestra;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMedia(){
		List<Double> numeros = new ArrayList<Double>();
		for(Cuantil cuantil: cuantiles){
			numeros.add(new Double(cuantil.getObservacionesAcumuladas()) / tamMuestra);
		}
		return Stats.media(numeros);
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDesviacionStd(){
		List<Double> numeros = new ArrayList<Double>();
		for(Cuantil cuantil: cuantiles){
			numeros.add(new Double(cuantil.getObservacionesAcumuladas()) / tamMuestra);
		}
		return Stats.desviacionStd(numeros);
	}
	
	/**
	 * 
	 * @return
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
	 * 
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
	 * 
	 * @param streamSalida
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
