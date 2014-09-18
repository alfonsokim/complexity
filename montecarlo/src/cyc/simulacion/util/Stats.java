/**
 * 
 */
package cyc.simulacion.util;

import java.util.List;


/**
 * @author Alfonso Kim
 *
 */
public class Stats {

	/**
	 * 
	 */
	private Stats() { }
	
	/**
	 * 
	 * @param numeros
	 * @return
	 */
	public static double suma(List<Double> numeros){
		double acumulado = 0;
		for(Double d: numeros){
			acumulado += d;
		}
		return acumulado;
	}
	
	/**
	 * 
	 * @param numeros
	 * @return
	 */
	public static double media(List<Double> numeros){
		
		if(numeros.size() == 0){
			return 0;
		}
		
		return suma(numeros) / numeros.size();
	}
	
	/**
	 * 
	 * @param numeros
	 * @return
	 */
	public static double desviacionStd(List<Double> numeros){
		
		if(numeros.size() == 0){
			return 0;
		}
		
		double acumulado = 0;
		double media = media(numeros);
		for(Double d : numeros){
			double a = media - d;
			acumulado += (a * a);
		}
		return Math.sqrt(acumulado / numeros.size());

	}

}
