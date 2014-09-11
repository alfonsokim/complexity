package cyc.montecarlo.funcion;

/**
 * 
 * @author Alfonso Kim
 */
public abstract class FuncionBase implements Funcion {
	
	protected double minimo;
	protected double maximo;
	
	/**
	 * 
	 * @param minimo
	 * @param maximo
	 */
	protected FuncionBase(double minimo, double maximo) {
		this.minimo = minimo;
		this.maximo = maximo;
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


}
