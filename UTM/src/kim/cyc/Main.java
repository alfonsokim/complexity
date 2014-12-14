/**
 * 
 */
package kim.cyc;

/**
 * @author Alfonso Kim
 *
 */
public class Main {

	/**
	 * 
	 */
	public Main() { }
	
	public static void main(String[] args){
		MutatingTuringMachine turingMachine = new MutatingTuringMachine(true);
		String inTape = ByteUtils.toBinaryString("zAyBxC");
		//TapeComparator comparator = new TapeComparator(inTape, 10);
		//String startTape = ByteUtils.getRandomString(inTape.length());
		RandomTMMutationHillClimber hillClimber = new RandomTMMutationHillClimber(
				turingMachine, 100000, 1000
		);
		hillClimber.setFreedomDegree(10);
		hillClimber.climb(inTape);

	}

}
