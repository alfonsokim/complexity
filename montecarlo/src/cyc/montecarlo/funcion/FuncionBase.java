package cyc.montecarlo.funcion;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Alfonso Kim
 */
public abstract class FuncionBase implements Funcion {
	
	protected double minimo;
	protected double maximo;
	protected long cuantiles;
	private List<Elemento> elementos;
	private double delta;
	
	
	protected FuncionBase(double minimo, double maximo) {
		this(minimo, maximo, 10);
	}
	
	
	/**
	 * 
	 * @param minimo
	 * @param maximo
	 * @param cuantiles
	 */
	protected FuncionBase(double minimo, double maximo, long cuantiles) {
		this.minimo = minimo;
		this.maximo = maximo;
		this.cuantiles = cuantiles;
		this.elementos = new ArrayList<Elemento>();
		this.delta = (maximo - minimo) / cuantiles;
		
		double x = minimo;
		double sumFx = 0;
		for(int i = 0; i < cuantiles; i++){
			double fx = fX(x);
			Elemento elemento = new Elemento();
			elemento.setI(i);
			elemento.setX(x);
			sumFx += fx;
			elemento.setFx(fx);
			elementos.add(elemento);
			x += delta;
		}
		
		double sumPx = 0;
		for(Elemento elemento: elementos){
			double px = elemento.getFx() / sumFx;
			elemento.setPx(px);
			sumPx += px;
			elemento.setPdf(sumPx);
		}
		
		if(new Double(sumPx).intValue() != 1){
			throw new RuntimeException("probabilidades no suman 1:" + sumPx);
		}
		
	}
	
	
	/**
	 * 
	 * @param x
	 */
	protected void validar(double x){
		if(x < minimo || x > maximo){
			throw new RuntimeException("x fuera de los limites");
		}
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FuncionBase [elementos=").append(elementos).append("]");
		return builder.toString();
	}


}
