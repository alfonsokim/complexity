/**
 * 
 */
package cyc.simulacion.util;

import java.util.List;


/**
 * Utilerias para las estadisticas basicas
 * 
 * @author Alfonso Kim
 */
public class Stats {

	/**
	 * Como solo tiene metodos estaticos no se necesitan instancias
	 */
	private Stats() { }
	
	/**
	 * Suma una lista de numeros
	 * @param numeros	La lista a sumar
	 * @return			La suma
	 */
	public static double suma(List<Double> numeros){
		double acumulado = 0;
		for(Double d: numeros){
			acumulado += d;
		}
		return acumulado;
	}
	
	/**
	 * Calcula la media de una lista de numeros
	 * 
	 * @param numeros Los numeros a calcular la media
	 * @return		  La media
	 */
	public static double media(List<Double> numeros){
		
		if(numeros.size() == 0){
			return 0;
		}
		
		return suma(numeros) / numeros.size();
	}
	
	/**
	 * Calcula la desviacion estandar de una lista de numeos
	 * 
	 * @param numeros	La lista a calcular la desviacion
	 * @return			La desviacion estandar
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
