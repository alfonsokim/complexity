package cyc.simulacion;

import java.util.ArrayList;
import java.util.List;

import cyc.simulacion.util.Stats;


/**
 * @author Alfonso Kim
 *
 */
public class Muestra {
	
	private List<Cuantil> cuantiles;
	private long numObservaciones;
	private List<Double> todasObservaciones;
	
	/**
	 * 
	 */
	public Muestra(List<Cuantil> cuantiles) {
		this.cuantiles = cuantiles;
		this.todasObservaciones = new ArrayList<Double>();
		
		long no = 0;
		for(Cuantil c : cuantiles){
			no += c.getNumObservaciones();
			// Aqui se pueden calcular la media y desviacion
			todasObservaciones.addAll(c.getObservaciones());
		}
		this.numObservaciones = no;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMedia(){
		return Stats.media(todasObservaciones);
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDesviacionStd(){
		return Stats.desviacionStd(todasObservaciones);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Muestra [media=").append(getMedia())
				.append(", desviacionStd=").append(getDesviacionStd())
		        .append(", cuantiles=");
		for(Cuantil c: cuantiles){
			builder.append("\n").append(c);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the numObservaciones
	 */
	public long getNumObservaciones() {
		return numObservaciones;
	}


}
