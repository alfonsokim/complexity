/**
 * 
 */
package cyc.montecarlo.funcion;

/**
 * @author Alfonso Kim
 *
 */
public class FuncionPruebas extends FuncionBase {

	/**
	 * 
	 */
	public FuncionPruebas(double minimo, double maximo) {
		this(minimo, maximo, 10);
	}
	
	/**
	 * 
	 */
	public FuncionPruebas(double minimo, double maximo, long cuantiles) {
		super(minimo, maximo, cuantiles);
	}

	@Override
	public double fX(double x) {
		validar(x);
		return 1 - (2*x*x) + (3*x*x*x); 
	}
	
	@Override
	public void observacion(double probX){
		agregarObservacion(probX);
	}


}
