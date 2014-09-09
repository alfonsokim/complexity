/**
 * 
 */
package cyc.montecarlo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alfonso Kim
 */
public class Cuantiles {
	
	private List<Cuantil> cuantiles;

	/**
	 * 
	 */
	public Cuantiles(Configuracion conf) { 
		cuantiles = new ArrayList<Cuantil>();
		int numCuantil = 1;
		for(double c = conf.getMinimo(); c < conf.getMaximo() - conf.getDelta(); c += conf.getDelta()){
			cuantiles.add(new Cuantil(c, c + conf.getDelta(), numCuantil++));
		}
	}
	
	/**
	 * 
	 * @param aleatorio
	 */
	public void agregar(double aleatorio){
		for(Cuantil cuantil : cuantiles){
			if(aleatorio >= cuantil.getLimiteInferior() && 
			   aleatorio <= cuantil.getLimiteSuperior()){
				cuantil.agregar(aleatorio);
				return;
			}
		}
		// TODO: Mejor validar esto antes de entrar al metodo
		throw new RuntimeException("el numero aleatorio se sale de los limites");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cuantiles [").append(cuantiles).append("]");
		return builder.toString();
	}


}
