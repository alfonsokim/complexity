import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;


/**
 * @author Alfonso Kim
 *
 */
public class VeryRandomClimber {
	
	private int numStates;
	private char[] tape;
	private Random random;

	/**
	 * 
	 */
	public VeryRandomClimber(int numStates, char[] tape) {
		this.numStates = numStates;
		this.tape = tape;
		this.random = new Random();
	}
	
	public void climb(int headPos, int maxIterations){
		
		char[] controlUnit = buildRandomControlUnit();
		long iteration = 1;
		
		while(iteration <= maxIterations) {
			String newTape = "";
			try {
				newTape = UTM.NewTape(new String(controlUnit), new String(tape), maxIterations, headPos);
			} catch (Exception e){
				System.out.println("Descartando unidad de control: " + e.getMessage());
				controlUnit = buildRandomControlUnit();
				continue;
			}
			long distance = hammingDistance(tape, newTape.toCharArray());
			if(distance == 0){
				System.out.println("Se encontro la cadena en " + iteration + " iteraciones!!");
				break;
			}
			controlUnit[random.nextInt(controlUnit.length)] = random.nextBoolean() ? '1': '0';
			iteration++;
		}
	}
	
	private char[] buildRandomControlUnit(){
		char[] controlUnit = new char[numStates * 16];
		for(int c = 0; c < controlUnit.length; c++){
			controlUnit[c] = random.nextBoolean() ? '1' : '0';
		}
		return controlUnit;
	}
	
	private long hammingDistance(char[] tapeOne, char[] tapeTwo){
		
		System.out.println("cinta1 [" + new String(tapeOne) + "]");
		System.out.println("cinta2 [" + new String(tapeTwo) + "]");
		
		long distance = 0;
		if(tapeOne.length != tapeTwo.length){
			throw new RuntimeException("Cannot compare tapes of different length");
		}
		for(int c = 0; c < tapeOne.length; c++){
			if(tapeOne[c] != tapeTwo[c]){
				distance++;
			}
		}
		return distance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String tape = "";
		
		try {
			BufferedReader tapeReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("cinta.txt"))));
			
			tape = tapeReader.readLine();
			tapeReader.close();
			
		} catch (Exception e) {
			System.out.println("Error de lectura: " + e);
		}
		
		System.out.println("Leidos " + tape.length() + " caracteres de cinta");
		
		int headPos = new Long(tape.length() / 2).intValue();
		new VeryRandomClimber(5, tape.toCharArray()).climb(headPos, 1);
		
	}


}
