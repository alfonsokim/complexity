/**
 * 
 */
package cyc.montecarlo;

import cyc.montecarlo.utils.Aleatorios;

/**
 * @author Alfonso Kim
 */
public class Montecarlo {

	/**
	 * 
	 */
	public Montecarlo() { }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuracion conf = Configuracion.getConfiguracionPruebas();
		
		Cuantiles cuantiles = new Cuantiles(conf);
		
		for(int i = 0; i < conf.getNumPromedios(); i++){
			double promedio = Aleatorios.promedioDeUniformes(conf.getNumPromedios());	
			cuantiles.agregar(promedio);
		}
		
		System.out.println(cuantiles);
	}

}
