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
		System.out.println("Cinta de entrada: " + inTape);
		String outTape = turingMachine.processTape(inTape, 1000000, inTape.length() / 2);
		System.out.println("Cinta de salida: " + outTape);
	}

}
