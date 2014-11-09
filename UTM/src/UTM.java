import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */

/**
 * @author Alfonso Kim
 *
 */
public class UTM {

	private final static String EMPTY = "";
	
	/**
	 * 
	 */
	private UTM() {}
	
	public static String NewTape(String TT, String Tape, int N, int P) {
		List<State> states = parseMachine(TT);
		return EMPTY;
	}
	
	
	private static List<State> parseMachine(String machine){
		if(machine.length() % 16 != 0){
			throw new RuntimeException("Invalid state string [" + machine + "]");
		}
		List<State> states = new ArrayList<State>();
		for(int idx = 0; idx < machine.length(); idx += 16){
			states.add(new State(machine.substring(idx, idx + 16)));
		}
		return states;
	}
	
	
	// ***************************************************************
	private static class Transition {
		
		private char outSymbol;
		private int nextMove;
		private int nextState;
		
		Transition(String def){
			outSymbol = def.charAt(0);
			nextMove = Integer.parseInt(String.valueOf(def.charAt(1)));
			nextState = Integer.parseInt(def.substring(2), 2);
		}
		
		public String toString(){
			return new StringBuilder()
			.append("out: ").append(outSymbol)
			.append(", nextMove: ").append(nextMove)
			.append(", nextState: ").append(nextState)
			.toString();
		}
		
	}
	
	// ***************************************************************
	private static class State {
		
		Transition[] transitions;
		
		State(String def){
			transitions = new Transition[] {
				new Transition(def.substring(0, 8)),
				new Transition(def.substring(8))
			};
		}
		
		Transition process(String read){
			int value = Integer.parseInt(read);
			if (value != 0 || value != 1){
				throw new RuntimeException("Invalid state: " + read);
			}
			return transitions[value];
		}
		
		public String toString(){
			return Arrays.toString(transitions);
		}
		
	}

}
