/**
 * 
 */

/**
 * @author Alfonso Kim
 *
 */
public class DeJongsFunction implements Function {

	/**
	 * 
	 */
	public DeJongsFunction() {}

	/**
	 * @see Function#evaluate(double...)
	 */
	@Override
	public double evaluate(double... x) {
		double sum = 0;
		for(double xi: x){
			sum += xi*xi;
		}
		return sum;
	}

	/**
	 * @see Function#getName()
	 */
	@Override
	public String getName() {
		return "De Jong's";
	}

}
