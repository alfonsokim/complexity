/**
 * 
 */

/**
 * @author Alfonso Kim
 *
 */
public class Gene {
	
	public enum Sign { POSITIVE, NEGATIVE };
	
	private Sign sign;
	private int numIntegerDigits;
	private int numDecimalDigits;

	/**
	 * 
	 */
	public Gene(Sign sign, int numIntegerDigits, int numDecimalDigits) {
		this.sign = sign;
		this.numIntegerDigits = numIntegerDigits;
		this.numDecimalDigits = numDecimalDigits;
	}

}
