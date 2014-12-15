/**
 * 
 */
package kim.cyc;

import java.io.File;

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
		String inTape = ByteUtils.readFileAsBitString(new File("test.txt"));
		RandomTMMutationHillClimber hillClimber = new RandomTMMutationHillClimber(
				turingMachine, 100000, 1000
		);
		hillClimber.setFreedomDegree(10);
		String outTape = hillClimber.climb(inTape);
		ByteUtils.writeBitStringAsFile(new File("salidaTuring.txt"), outTape);
		
		//System.out.println("Probando con: [" + turingMachine.getMachineDefinition() + "]");
		
		String testTape = turingMachine.processTape(inTape, 100, inTape.length() / 2);
		
		//System.out.println("Esperada: " + outTape);
		//System.out.println("Obtenida: " + testTape);
		
		//TODO: Validar que sean correctas
		
		//turingMachine.reduce();

	}

}
