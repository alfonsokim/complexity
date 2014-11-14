import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


/**
 * @author Alfonso Kim
 *
 */
public class RandomClimber {
	
	private char[] controlUnit;
	private char[] tape;
	private Random random;

	/**
	 * 
	 */
	public RandomClimber(char[] controlUnit, char[] tape) {
		this.controlUnit = controlUnit;
		this.tape = tape;
		this.random = new Random();
	}
	
	public void climb(int headPos, int maxIterations){
		
		char[] previousTape = copy(controlUnit);
		modifyControlUnit(controlUnit);
		long iteration = 1;
		
		while(iteration <= maxIterations) {
			String newTape = "";
			try {
				newTape = UTM.NewTape(new String(controlUnit), new String(tape), maxIterations, headPos);
			} catch (Exception e){
				System.out.println("Descartando unidad de control: " + e.getMessage());
				controlUnit = previousTape;
				modifyControlUnit(controlUnit);
				continue;
			}
			long distance = hammingDistance(tape, newTape.toCharArray());
			if(distance == 0){
				System.out.println("Se encontro la cadena en " + iteration + " iteraciones!!");
				break;
			}
			
			previousTape = copy(controlUnit);
			modifyControlUnit(controlUnit);
			iteration++;
		}
	}
	
	private char[] copy(char[] string){
		char[] theCopy = new char[string.length];
		System.arraycopy(string, 0, theCopy, 0, string.length);
		return theCopy;
	}
	
	private void modifyControlUnit(char[] controlUnit){
		controlUnit[random.nextInt(controlUnit.length)] = random.nextBoolean() ? '1': '0';
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
	
	private static char[] readBinaryAsciiFile(String fileName) throws IOException{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName))));
			String line = reader.readLine();
			reader.close();
			return line.toCharArray();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char[] tape = null;
		char[] controlUnit = null;
		
		try {
			
			tape = readBinaryAsciiFile("cinta.txt");
			controlUnit = readBinaryAsciiFile("castor.txt");
			
		} catch (Exception e) {
			System.out.println("Error de lectura: " + e);
		}
		
		System.out.println("Leidos " + tape.length + " caracteres de cinta");
		System.out.println("Leidos " + controlUnit.length + " caracteres de unidad de control");
		
		int headPos = new Long(tape.length / 2).intValue();
		new RandomClimber(controlUnit, tape).climb(headPos, 10);
		
	}


}
