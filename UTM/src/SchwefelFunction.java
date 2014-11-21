/**
 * 
 */

/**
 * @author Alfonso Kim
 *
 */
public class SchwefelFunction implements Function {

	/**
	 * 
	 */
	public SchwefelFunction() {}

	/**
	 * @see Function#getName()
	 */
	@Override
	public String getName() {
		return "Schwefel";
	}

	/**
	 * @see Function#evaluate(double[])
	 */
	@Override
	public double evaluate(double... x) {
		double sum = 0;
		for(double xi: x){
			sum += -1 * xi * Math.sin(Math.sqrt(Math.abs(xi)));
		}
		return sum;
	}

}
