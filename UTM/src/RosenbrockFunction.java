
/**
 * Funcion de Rosenbrock a optimizar
 * 
 * @author Alfonso Kim
 */
public class RosenbrockFunction implements Function {

	public RosenbrockFunction() {}

	/**
	 * @see Function#getName()
	 */
	@Override
	public String getName() {
		return "Rosenbrock";
	}

	/**
	 * @see Function#evaluate(double[])
	 */
	@Override
	public double evaluate(double... x) {
		double sum = 0;
		for(int i = 1; i < x.length; i++){
			double a = x[i-1] - (x[i] * x[i]) * 100;
			double b = 1 - x[i];
			sum += ((a * a) + (b * b));
		}
		return sum;
	}

}
