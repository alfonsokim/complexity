/**
 * 
 */
package cyc.montecarlo.utils;

import java.util.Random;

import cyc.montecarlo.Configuracion;

/**
 * @author Alfonso Kim
 *
 */
public class Aleatorios {
	
	private final static Random RANDOM = new Random();

	/**
	 * 
	 */
	private Aleatorios() { }
	
	/**
	 * Usar otro random en caso de semilla particular
	 * @return El generador de aletorios a usar
	 */
	private static Random getRandom(){
		return RANDOM;
	}
	
	/**
	 * 
	 * @param numUniformes
	 * @return
	 */
	public static double promedioDeUniformes(long numUniformes){
		if(numUniformes <= 0){
			throw new RuntimeException("Numero de aletorios debe ser mayor a cero");
		}
		
		double suma = 0d;
		for(int i = 0; i < numUniformes; i++){
			suma += getRandom().nextDouble();
		}
		
		return (suma / numUniformes);
	}
	
	/**
	 * 
	 * @param conf
	 * @param aleatorio
	 * @return
	 */
	public static double numCuantil(Configuracion conf, double aleatorio){
		double c, cuantil;
		for(c = conf.getMinimo(), cuantil = conf.getMinimo(); 
			c <= conf.getMaximo() && c < aleatorio;
			cuantil = c, c += conf.getDelta());
		return cuantil;
	}

}
