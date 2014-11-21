
/**
 * Funcion de Ackley a optimizar
 * 
 * @author Alfonso Kim
 */
public class AckleyFunction implements Function {

	public AckleyFunction() {}

	/**
	 * @see Function#getName()
	 */
	@Override
	public String getName() {
		return "Ackley";
	}

	/**
	 * @see Function#evaluate(double[])
	 */
	@Override
	public double evaluate(double... x) {
		double a = 20;
		double b = 0.2;
		double c = 2 * Math.PI;
		double sumSquared = 0;
		double sumCos = 0;
		for(double xi : x){
			sumSquared += xi * xi;
			sumCos += Math.cos(c * xi);
		}
		double expB = -b * (Math.sqrt((1 / x.length) * sumSquared));
		double exp = (1 / x.length) * sumCos;
		return (-a * Math.exp(expB)) - Math.exp(exp) + a + Math.exp(1);
	}

}
