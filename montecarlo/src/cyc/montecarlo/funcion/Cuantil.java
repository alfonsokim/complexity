/**
 * 
 */
package cyc.montecarlo.funcion;

/**
 * @author Alfonso Kim
 *
 */
public class Cuantil extends Elemento {
	
	private long numObservacionesCuantil;
	private long numObservacionesAcumuladasCuantil;

	/**
	 * 
	 */
	public Cuantil() { 
		numObservacionesCuantil = 0;
		numObservacionesAcumuladasCuantil = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public Cuantil clone(){
		Cuantil c = new Cuantil();
		c.numObservacionesCuantil = numObservacionesCuantil;
		c.numObservacionesAcumuladasCuantil = numObservacionesAcumuladasCuantil;
		return c;
	}

	/**
	 * @return the numObservacionesCuantil
	 */
	public long getNumObservacionesCuantil() {
		return numObservacionesCuantil;
	}

	/**
	 * @param numObservacionesCuantil the numObservacionesCuantil to set
	 */
	public void nuevaObservacionCuantil(double observacion) {
		this.numObservacionesCuantil += 1;
	}
	
	/**
	 * 
	 * @param numObs
	 */
	public void addObservacionAcumuladaCuantil(long numObs){
		numObservacionesAcumuladasCuantil += numObs;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getNumObservacionesAcumuladas(){
		return numObservacionesAcumuladasCuantil;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getDeltaObservaciones(){
		return Math.abs(numObservacionesAcumuladasCuantil - numObservacionesCuantil);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Cuantil [");
		builder.append("numObsAcumuladasCuantil=").append(numObservacionesAcumuladasCuantil);
		builder.append(", numObservacionesCuantil=").append(numObservacionesCuantil);
		builder.append(", detaObservaciones=").append(getDeltaObservaciones());
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * 
	 */
	public String elementoToString(){
		return super.toString();
	}

}
