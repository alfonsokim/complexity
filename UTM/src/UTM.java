import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Alfonso Kim
 */
public class UTM {

	private final static String EMPTY = "";
	private final static int HALT = 63;
	
	/**
	 * 
	 */
	private UTM() {}
	
	public static String NewTape(String TT, String Tape, int N, int P) {
		List<State> states = parseMachine(TT);
		char[] tape = Tape.toCharArray();
		long iterations = 0;
		long productivity = 0;
		Transition transition = states.get(0).process(tape[P]);
		while(iterations <= N && transition.nextState != HALT){
			char write = transition.outSymbol;
			productivity += getProductivity(tape[P], write);
			tape[P] = write;
			P += (transition.nextMove == 0 ? 1 : -1);
			if(P < 0 || P > tape.length){
				System.out.println("Tape bounds exceeded: " + P);
				return EMPTY;
			}
			State nextState = states.get(transition.nextState);
			transition = nextState.process(tape[P]);
			iterations++;
			if(iterations % 1000 == 0){
				System.out.println(String.valueOf(iterations));
			}
		}
		System.out.println("Productivity: " + productivity);
		return new String(tape);
	}
	
	private static int getProductivity(char before, char after){
		if('1' == before && '0' == after){
			return -1;
		} else if ('0' == before && '1' == after){
			return 1;
		}
		return 0;
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
		
		Transition process(char read){
			int value = read == '0' ? 0 : 1;
			return transitions[value];
		}
		
		public String toString(){
			return Arrays.toString(transitions);
		}
		
	}

}
