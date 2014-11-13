import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;


/**
 * @author Alfonso Kim
 *
 */
public class RandomClimber {
	
	private String controlUnit;
	private String initialTape;
	private Random random;

	/**
	 * 
	 */
	public RandomClimber(String controlUnit, String tape) {
		this.controlUnit = controlUnit;
		this.initialTape = tape;
		this.random = new Random();
	}
	
	public void climb(int headPos, int maxIterations){
		StringBuilder tapeBuilder = new StringBuilder();
		for(int c = 0; c < initialTape.length(); c++){
			tapeBuilder.append(random.nextBoolean() ? "1": "0");
		}
		String randomTape = tapeBuilder.toString();
		long iteration = 0;
		while(iteration <= maxIterations && !randomTape.equals(initialTape)){
			String newTape = UTM.NewTape(controlUnit, initialTape, maxIterations, headPos);
			long distance = hammingDistance(initialTape, newTape);
			System.out.println("distancia: " + distance);
			char[] charTape = randomTape.toCharArray();
			charTape[random.nextInt(newTape.length())] = random.nextBoolean() ? '1': '0';
			randomTape = new String(charTape);
		}
	}
	
	private long hammingDistance(String tapeOne, String tapeTwo){
		long distance = 0;
		if(tapeOne.length() > tapeTwo.length()){
			distance += tapeOne.length() - tapeTwo.length();
			tapeOne.subSequence(0, tapeTwo.length());
		} else if(tapeTwo.length() > tapeOne.length()){
			distance += tapeTwo.length() - tapeOne.length();
			tapeTwo.subSequence(0, tapeOne.length());
		}
		for(int c = 0; c < tapeOne.length(); c++){
			if(tapeOne.charAt(c) != tapeTwo.charAt(c)){
				distance++;
			}
		}
		return distance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String controlUnit = "";
		String tape = "";
		try {
			BufferedReader tapeReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("cinta.txt"))));
			BufferedReader machineReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("castor.txt"))));
			
			tape = tapeReader.readLine();
			controlUnit = machineReader.readLine();
			
			tapeReader.close();
			machineReader.close();
		} catch (Exception e) {
			System.out.println("Error de lectura: " + e);
		}
		
		System.out.println("Leidos " + controlUnit.length() + " caracteres de unidad de control");
		System.out.println("Leidos " + tape.length() + " caracteres de cinta");
		
		int headPos = new Long(tape.length() / 2).intValue();
		
		new RandomClimber(controlUnit, tape).climb(headPos, 10000);
		
	}


}
