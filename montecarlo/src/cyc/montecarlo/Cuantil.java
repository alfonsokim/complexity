/**
 * 
 */
package cyc.montecarlo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alfonso Kim
 *
 */
public class Cuantil {
	
	private final static DecimalFormat DF = new DecimalFormat( "#0.000" );
	
	private double limiteInferior;
	private double limiteSuperior;
	private long numCuantil;
	private List<Double> elementos;
	

	/**
	 * 
	 * @param limiteInferior
	 * @param limiteSuperior
	 * @param numCuantil
	 */
	public Cuantil(double limiteInferior, double limiteSuperior, long numCuantil) {
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
		this.numCuantil = numCuantil;
		this.elementos = new ArrayList<Double>();
	}

	public void agregar(double aleatorio){
		elementos.add(aleatorio);
	}


	/**
	 * @return the limiteInferior
	 */
	public double getLimiteInferior() {
		return limiteInferior;
	}

	/**
	 * @param limiteInferior the limiteInferior to set
	 */
	public void setLimiteInferior(double limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	/**
	 * @return the limiteSuperior
	 */
	public double getLimiteSuperior() {
		return limiteSuperior;
	}

	/**
	 * @param limiteSuperior the limiteSuperior to set
	 */
	public void setLimiteSuperior(double limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

	/**
	 * @return the numCuantil
	 */
	public long getNumCuantil() {
		return numCuantil;
	}

	/**
	 * @param numCuantil the numCuantil to set
	 */
	public void setNumCuantil(long numCuantil) {
		this.numCuantil = numCuantil;
	}

	/**
	 * @return the elementos
	 */
	public List<Double> getElementos() {
		return elementos;
	}

	/**
	 * @param elementos the elementos to set
	 */
	public void setElementos(List<Double> elementos) {
		this.elementos = elementos;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cuantil [limiteInferior=").append(DF.format(limiteInferior))
				.append(", limiteSuperior=").append(DF.format(limiteSuperior))
				.append(", numCuantil=").append(numCuantil)
				.append(", elementos=").append(elementos.size()).append("]\n");
		return builder.toString();
	}

	

}
