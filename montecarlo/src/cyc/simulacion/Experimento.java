/**
 * 
 */
package cyc.simulacion;

import java.util.ArrayList;
import java.util.List;

import cyc.simulacion.funcion.Funcion;

/**
 * @author Alfonso Kim
 *
 */
public class Experimento {
	
	private Funcion funcion;
	private double minimo;
	private double maximo;
	private int segmentos;
	private List<Cuantil> cuantiles;
	
	/**
	 * 
	 * @param funcion
	 * @param minimo
	 * @param maximo
	 * @param segmentos
	 */
	public Experimento(Funcion funcion, double minimo, double maximo, int segmentos) {
		super();
		this.funcion = funcion;
		this.minimo = minimo;
		this.maximo = maximo;
		this.segmentos = segmentos;
		this.cuantiles = new ArrayList<Cuantil>();
		
		double delta = (maximo - minimo) / segmentos;
		
		double x = minimo;
		double sumFx = 0;
		for(int i = 0; i < segmentos; i++){
			double fx = funcion.fX(x);
			Cuantil elemento = new Cuantil();
			elemento.setI(i);
			elemento.setX(x);
			sumFx += fx;
			elemento.setFx(fx);
			cuantiles.add(elemento);
			x += delta;
		}
		
		if(cuantiles.isEmpty()){
			throw new RuntimeException("No se generaron elementos");
		}
		
		double sumPx = 0;
		for(Cuantil elemento: cuantiles){
			double px = elemento.getFx() / sumFx;
			elemento.setPx(px);
			elemento.setPdf(sumPx);
			sumPx += px;
		}
		
		if(new Double(sumPx).intValue() != 1){
			throw new RuntimeException("probabilidades no suman 1:" + sumPx);
		}
	}
	
	/**
	 * 
	 * @param funcion
	 * @param minimo
	 * @param maximo
	 */
	public Experimento(Funcion funcion, double minimo, double maximo){
		this(funcion, minimo, maximo, 100);
	}

	/**
	 * @return the funcion
	 */
	public Funcion getFuncion() {
		return funcion;
	}

	/**
	 * @return the minimo
	 */
	public double getMinimo() {
		return minimo;
	}

	/**
	 * @return the maximo
	 */
	public double getMaximo() {
		return maximo;
	}

	/**
	 * @return the segmentos
	 */
	public int getSegmentos() {
		return segmentos;
	}

	/**
	 * @return the cuantiles
	 */
	public List<Cuantil> getCuantiles() {
		return cuantiles;
	}

	/**
	 * toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Experimento [funcion=").append(funcion)
				.append(", minimo=").append(minimo)
				.append(", maximo=").append(maximo)
				.append(", segmentos=").append(segmentos)
				.append(", cuantiles=");
		for(Cuantil cuantil : cuantiles){
			builder.append("\n").append(cuantil);
		}
		builder.append("]");
		return builder.toString();
	}


}
