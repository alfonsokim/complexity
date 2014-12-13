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
		TapeComparator comparator = new TapeComparator(inTape, 0);
		String startTape = ByteUtils.getRandomString(inTape.length());
		int iterations = 0;
		double fitness = 0;
		double stillCount = 0;
		int numTransitions = 100;
		int mutationThreshold = 1000;
		String tape = startTape;
		while(iterations < 1000000){
			turingMachine.mutate();
			try {
				String result = turingMachine.processTape(tape, numTransitions, tape.length() / 2);
				double likeness = comparator.getLikeness(result);
				if(likeness >= fitness){
					tape = result;
					fitness = likeness;
					turingMachine.commit();
				} else {
					stillCount++;
					turingMachine.restore();
				}
				if(stillCount >= mutationThreshold){
					stillCount = 0;
					mutationThreshold = mutationThreshold / 2;
					//System.out.println("zupermutate");
					for(int i = 0; i < 1000; i++){
						turingMachine.mutate();
					}
				}
				if(iterations % 100 == 0){
					System.out.println("fitness = " + fitness);
				}
				if(turingMachine.isHalted()){
					//System.out.println("Rebuilding TM from HALT");
					turingMachine.mutate();
					//turingMachine = new MutatingTuringMachine(true);
					//tape = turingMachine.processTape(tape, numTransitions, tape.length() / 2);
				}
			} catch (RuntimeException e){
				//System.out.println("Restoring TM from error: " + e.getMessage());
				turingMachine.mutate();
			}
			
			iterations++;
		}
		System.out.println("original: " + inTape);
		System.out.println("inicial: " + startTape);
		System.out.println("final: " + tape);
	}

}
