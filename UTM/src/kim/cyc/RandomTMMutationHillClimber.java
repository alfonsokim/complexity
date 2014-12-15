/**
 * 
 */
package kim.cyc;

/**
 * @author Alfonso Kim
 *
 */
public class RandomTMMutationHillClimber {
	
	private MutatingTuringMachine turingMachine;
	private long climbIterations;
	private long machineTransitions;
	private int extremeMutationThreshold;
	private int extremeMutationCount;
	private int freedomDegree;

	/**
	 * 
	 */
	public RandomTMMutationHillClimber(MutatingTuringMachine turingMachine, long climbIterations, long machineTransitions) {
		this.turingMachine = turingMachine;
		this.climbIterations = climbIterations;
		this.machineTransitions = machineTransitions;
		this.extremeMutationThreshold = 1000;
		this.extremeMutationCount = 1000;
		this.freedomDegree = 5;
	}
	
	/**
	 * Escala la funcion de similitud entre dos cadenas para aproximar la maquina
	 * de Turing que aproxima la cadena dada.
	 * 
	 * @param initialTape	La cinta a aproximar
	 * @return				La cinta aproximada
	 */
	public String climb(String initialTape){
		TapeComparator comparator = new TapeComparator(initialTape, freedomDegree);
		String startTape = ByteUtils.getRandomString(initialTape.length());
		int iterations = 0;
		double fitness = 0;
		double stillCount = 0;
		String tape = startTape;
		while(iterations < climbIterations){
			turingMachine.mutate();
			try {
				String result = turingMachine.processTape(tape, machineTransitions, tape.length() / 2);
				double likeness = comparator.getLikeness(result);
				if(likeness >= fitness){
					tape = result;
					fitness = likeness;
					turingMachine.commit();
				} else {
					stillCount++;
					turingMachine.restore();
				}
				if(stillCount >= extremeMutationThreshold){
					stillCount = 0;
					extremeMutationThreshold = extremeMutationThreshold / 2;
					for(int i = 0; i < extremeMutationCount; i++){
						turingMachine.mutate();
					}
				}
				if(iterations % 100 == 0){
					System.out.println("it: " + iterations + ", fitness = " + fitness);
				}
				if(turingMachine.isHalted()){
					//System.out.println("Rebuilding TM from HALT");
					turingMachine.mutate();
				}
			} catch (RuntimeException e){
				turingMachine.mutate();
			}
			
			iterations++;
		}
		System.out.println("original: " + initialTape);
		System.out.println("final: " + tape);
		System.out.println("inicial: " + startTape);
		
		turingMachine.restore();
		//System.out.println("terminando proceso con [" + turingMachine.getMachineDefinition() + "]");
		
		return tape;
	}

	/**
	 * @return the extremeMutationThreshold
	 */
	public int getExtremeMutationThreshold() {
		return extremeMutationThreshold;
	}

	/**
	 * @param extremeMutationThreshold the extremeMutationThreshold to set
	 */
	public void setExtremeMutationThreshold(int extremeMutationThreshold) {
		this.extremeMutationThreshold = extremeMutationThreshold;
	}

	/**
	 * @return the extremeMutationCount
	 */
	public int getExtremeMutationCount() {
		return extremeMutationCount;
	}

	/**
	 * @param extremeMutationCount the extremeMutationCount to set
	 */
	public void setExtremeMutationCount(int extremeMutationCount) {
		this.extremeMutationCount = extremeMutationCount;
	}

	/**
	 * @return the freedomDegree
	 */
	public int getFreedomDegree() {
		return freedomDegree;
	}

	/**
	 * @param freedomDegree the freedomDegree to set
	 */
	public void setFreedomDegree(int freedomDegree) {
		this.freedomDegree = freedomDegree;
	}

}
