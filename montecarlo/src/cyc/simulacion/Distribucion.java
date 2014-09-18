/**
 * 
 */
package cyc.simulacion;

import java.util.List;

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

}
