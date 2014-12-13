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
		TapeComparator comparator = new TapeComparator(inTape, 5);
		int iterations = 0;
		double fitness = 0;
		String tape = turingMachine.processTape(inTape, 1000000, inTape.length() / 2);
		while(iterations < 1000){
			turingMachine.mutate();
			try {
				tape = turingMachine.processTape(tape, 1000000, tape.length() / 2);
				double likeness = comparator.getLikeness(tape);
				if(likeness > fitness){
					fitness = likeness;
				} else {
					turingMachine.restore();
				}
				System.out.println("likeness = " + fitness);
				if(turingMachine.isHalted()){
					System.out.println("Rebuilding TM from HALT");
					turingMachine = new MutatingTuringMachine(true);
					tape = turingMachine.processTape(tape, 1000000, tape.length() / 2);
				}
			} catch (RuntimeException e){
				System.out.println("Restoring TM from error: " + e.getMessage());
				turingMachine.mutate();
			}
			
			iterations++;
		}
		System.out.println("original: " + inTape);
		System.out.println("final: " + tape);
	}

}
