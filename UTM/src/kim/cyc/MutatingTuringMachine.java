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
	private List<State> states;
	private Random random;

	/**
	 * 
	 */
	public MutatingTuringMachine(boolean forceHaltState) { 
		random = new Random();
		states = new ArrayList<State>();
		if(forceHaltState){
			haltStateIndex = random.nextInt(NUM_STATES+1);
		} else {
			haltStateIndex = -1;
		}
		for(int state = 0; state < NUM_STATES; state++){
			StringBuilder stateBuilder = new StringBuilder();
			stateBuilder.append(getRandomState(state == haltStateIndex));
			System.out.println("nuevo estado: " + stateBuilder.toString());
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
	
	
	public String processTape(String tapeStr, int numIterations, int headPosition) {
		char[] tape = tapeStr.toCharArray();
		long iterations = 0;
		long productivity = 0;
		Transition transition = states.get(0).process(tape[headPosition]);
		
		while(iterations <= numIterations && transition.nextState != HALT){
			char write = transition.outSymbol;
			productivity += getProductivity(tape[headPosition], write);
			tape[headPosition] = write;
			headPosition += (transition.nextMove == 0 ? 1 : -1);
			
			if(headPosition < 0 || headPosition > tape.length){
				System.out.println("Exceeded tape bounds: " + headPosition);
				return "";
			}
			
			State nextState = states.get(transition.nextState);
			transition = nextState.process(tape[headPosition]);
			iterations++;
			
			if(iterations % 1000 == 0){
				System.out.println(String.valueOf(iterations));
			}
		}
		
		if(transition.nextState == HALT){
			System.out.println("HALT state was reached!!");
		}
		
		System.out.println("Productivity: " + productivity);
		return new String(tape);
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
			
			/**
			 * Constructor.
			 * 
			 * @param def Definicion del estado, de 16 caracteres
			 */
			State(String def){
				transitions = new Transition[] {
					new Transition(def.substring(0, 8)),
					new Transition(def.substring(8))
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
