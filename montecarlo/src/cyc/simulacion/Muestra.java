package cyc.simulacion;

import java.util.ArrayList;
import java.util.List;

import cyc.simulacion.Cuantil;
import cyc.simulacion.util.Stats;


/**
 * Una muestra generada a partir de un experimento
 * 
 * @author Alfonso Kim
 */
public class Muestra {
	
	private List<Cuantil> cuantiles;
	private List<Double> todasObservaciones;
	
	/**
	 * Constructor de muestra
	 * 
	 * @param cuantiles Los cuantiles muestreados
	 */
	public Muestra(List<Cuantil> cuantiles) {
		this.cuantiles = cuantiles;
		this.todasObservaciones = new ArrayList<Double>();

		for(Cuantil c : cuantiles){
			// Aqui se podrian calcular la media y desviacion
			// y ahorrase muchas iteraciones adicionales
			todasObservaciones.addAll(c.getObservaciones());
		}
	}
	
	/**
	 * Convierte la muestra a una distribucion
	 * 
	 * @param numCuantiles	Cuantos cuantiles se quieren vern en la 
	 * 						distribucion generada
	 * @return				La distrbucion generada con la muestra
	 */
	public Distribucion comoDistribucion(int numCuantiles){
		//TODO: Validar que numCuantiles sea inpar
		
		List<Cuantil> limites = new ArrayList<Cuantil>();
		
		long observacionesEsperadas = new Double(Math.ceil(getNumObservaciones() / numCuantiles)).longValue();
		long obsAcumuladas = 0;
		for(Cuantil cuantil : cuantiles){
			obsAcumuladas += cuantil.getNumObservaciones();
			if(obsAcumuladas + cuantil.getNumObservaciones() >= observacionesEsperadas){
				cuantil.addObservacionesAcumuladas(obsAcumuladas);
				limites.add(cuantil);
				obsAcumuladas = 0;
			}
		}
		return new Distribucion(limites, todasObservaciones.size());
	}
	
	/**
	 * @return La media de la muestra
	 */
	public double getMedia(){
		return Stats.media(todasObservaciones);
	}
	
	/**
	 * @return La desviacion de la muestra
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
		return todasObservaciones.size();
	}


}
