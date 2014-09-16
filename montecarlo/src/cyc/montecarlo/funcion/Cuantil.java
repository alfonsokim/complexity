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
	
	public void addObservacionAcumuladaCuantil(long numObs){
		numObservacionesAcumuladasCuantil += numObs;
	}
	
	public long getNumObservacionesAcumuladas(){
		return numObservacionesAcumuladasCuantil;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Cuantil [");
		builder.append("numObsAcumuladasCuantil=").append(numObservacionesAcumuladasCuantil);
		builder.append(", numObservacionesCuantil=").append(numObservacionesCuantil);
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
