/**
 * 
 */
package kim.cyc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Alfonso Kim
 *
 */
public class MutatingTuringMachine {

	private final static int HALT = 63;
	private final static int STATE_LENGTH = 16;
	private final static int NUM_STATES = 64;
	private int haltStateIndex;
	private int mutatedState; 
	private int mutatedBit;
	private long productivity;
	private List<State> states;
	private Random random;
	private boolean haltStateReached;

	/**
	 * 
	 */
	public MutatingTuringMachine(boolean forceHaltState) { 
		random = new Random();
		states = new ArrayList<State>();
		mutatedState = -1;
		mutatedBit = -1;
		if(forceHaltState){
			haltStateIndex = random.nextInt(NUM_STATES+1);
		} else {
			haltStateIndex = -1;
		}
		for(int state = 0; state < NUM_STATES; state++){
			StringBuilder stateBuilder = new StringBuilder();
			stateBuilder.append(getRandomState(state == haltStateIndex));
			states.add(new State(stateBuilder.toString()));
		}

	}

	private String getRandomState(boolean haltState){
		StringBuilder builder = new StringBuilder();
		int stateLength = STATE_LENGTH - (haltState ? 6 : 0);
		for(int i = 0; i < stateLength; i++){
			builder.append(random.nextBoolean() ? '1' : '0');
		}
		if(haltState){
			builder.append("111111");
		}
		return builder.toString();
	}

	public void mutate(){
		mutatedState = haltStateIndex;
		while(mutatedState == haltStateIndex){
			mutatedState = random.nextInt(NUM_STATES);
		}
		mutatedBit = random.nextInt(STATE_LENGTH);
		states.get(mutatedState).mutate(mutatedBit);
	}
	
	public void restore(){
		if(mutatedState >= 0 && mutatedBit >= 0) {
			states.get(mutatedState).mutate(mutatedBit);
		}
	}


	public String processTape(String tapeStr, int numIterations, int headPosition) {
		haltStateReached = false;
		productivity = 0;
		char[] tape = tapeStr.toCharArray();
		long iterations = 0;
		Transition transition = states.get(0).process(tape[headPosition]);

		while(iterations <= numIterations && transition.nextState != HALT){
			char write = transition.outSymbol;
			productivity += getProductivity(tape[headPosition], write);
			tape[headPosition] = write;
			headPosition += (transition.nextMove == 0 ? 1 : -1);

			if(headPosition < 0 || headPosition > tape.length){
				throw new RuntimeException("Exceeded tape bounds: " + headPosition);
			}

			State nextState = states.get(transition.nextState);
			transition = nextState.process(tape[headPosition]);
			iterations++;

		}

		if(transition.nextState == HALT){
			haltStateReached = true;
		}

		return new String(tape);
	}
	
	public boolean isHalted(){
		return haltStateReached;
	}
	
	public long getProductivity(){
		return productivity;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		int stateIdx = 0;
		for(State state: states){
			builder.append("State ").append(stateIdx++)
			.append("=").append(state.definition).append(",");
		}
		return builder.toString();
	}

	private int getProductivity(char before, char after){
		return Integer.parseInt(String.valueOf(after)) - 
				Integer.parseInt(String.valueOf(before));
	}

	// **************************************************************************
	/**
	 * Estado de la unidad de control de la UTM
	 * 
	 * Contiene el arreglo de transiciones segun la lectura del simbolo,
	 * Si se lee un '0' la transicion esta en la posicion 0
	 */
	private static class State {

		Transition[] transitions;
		String definition;

		/**
		 * Constructor.
		 * 
		 * @param def Definicion del estado, de 16 caracteres
		 */
		State(String def){
			definition = def;
			setUp();
		}
		
		void setUp(){
			transitions = new Transition[] {
					new Transition(definition.substring(0, 8)),
					new Transition(definition.substring(8))
			};
		}

		/**
		 * Devuelve la siguiente transicion segun el caracter leido
		 * 
		 * @param read	El caracter leido
		 * @return		La siguiente transicion
		 */
		Transition process(char read){
			int value = read == '0' ? 0 : 1;
			return transitions[value];
		}
		
		void mutate(int idx){
			char[] sequence = definition.toCharArray();
			char bit = sequence[idx];
			sequence[idx] = bit == '1' ? '0' : '1';
			definition = new String(sequence);
			setUp();
		}

		/**
		 * Valida que el siguiente estado sea un estado valido en la maquina
		 * @param numStates El numero de estados definidos en la maquina
		 */
		void validateStates(int numStates){
			for(Transition t: transitions){
				t.validateNextState(numStates);
			}
		}

		/**
		 * @return Indicador si la transicion lleva al estado de halt
		 */
		boolean hasHaltState(){
			for(Transition t: transitions){
				if(t.hasHaltState()){
					return true;
				}
			}
			return false;
		}

	}

	// **************************************************************************
	/**
	 * Transicion de un estado
	 */
	private static class Transition {

		/** Simbolo de escritura de la transicion actual */
		private char outSymbol;

		/** Direccion del siguiente movimiento: 0 = derecha, 1 = izquiera */
		private int nextMove;

		/** Siguiente estado de la transicion */
		private int nextState;

		/**
		 * La definicion del estadod ebe cumplir ciertas reglas:
		 * 		- Debe ser de 8 caracteres
		 * 		- Caracter cero es el simbolo de salida
		 * 		- Caracter uno es el movimiento de la cabeza
		 * 		- Los ultimos 6 caracteres es el numero binario del siguiente estado
		 * 
		 * @param def La definicion de la transicion
		 */
		Transition(String def){
			outSymbol = def.charAt(0);
			nextMove = Integer.parseInt(String.valueOf(def.charAt(1)));
			nextState = Integer.parseInt(def.substring(2), 2);
		}

		/**
		 * Valida que el siguiente estado sea un estado valido en la maquina
		 * @param numStates El numero de estados definidos en la maquina
		 */
		void validateNextState(int numStates){
			if(!hasHaltState() && nextState >= numStates){
				throw new RuntimeException("Invalid next state: " + nextState + 
						". Current machine has " + numStates + " defined");
			}
		}

		/**
		 * @return Indicador si la transicion lleva al estado de halt
		 */
		boolean hasHaltState(){
			return nextState == HALT;
		}

	}

}
