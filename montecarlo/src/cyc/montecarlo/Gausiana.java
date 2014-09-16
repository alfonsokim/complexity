/**
 * 
 */
package cyc.montecarlo;

import java.util.ArrayList;
import java.util.List;

import cyc.montecarlo.funcion.Cuantil;

/**
 * @author Alfonso Kim
 *
 */
public class Gausiana {
	
	List<Cuantil> cuantiles;

	/**
	 * 
	 */
	public Gausiana() { 
		cuantiles = new ArrayList<Cuantil>();
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
	 */
	public void muestrear(int numMuestras){
		
	}

	/**
	 * @return the cuantiles
	 */
	public List<Cuantil> getCuantiles() {
		return cuantiles;
	}

}
