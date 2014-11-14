import java.util.ArrayList;
import java.util.List;

/**
 * Maquina de Turing Universal (UTM)
 * 
 * Ejecuta la unidad de control de una UTM dada la definicion de estados,
 * la cinta y posicion iniciales.
 * 
 * OJO: Al compilar esta clase se generan 3 archivos .class:
 * 		- UTM.class: Clase principal
 * 		- UTM$Transition.class: Representacion de una transicion
 * 		- UTM$State.class: Representacion de un estado
 * Para correr esta clase con FeedTM es necesario contar con los 3 
 * archivos class en el mismo directorio que FeedTM.class
 * 
 * @author Alfonso Kim, Complejidad y Computabilidad. 2014
 */
public class UTM {
	
	private final static String EMPTY = "";
	private final static int HALT = 63;
	
	/**
	 * No se necesitan instancias de la UTM, interaccion solo
	 * por el metodo estatico NewTape
	 */
	private UTM() {}
	
	/**
	 * Calcula la cinta de salida de una UTM dados los parametros:
	 * 
	 * @param TT 	Definicion de la unidad de control, en binario
	 * @param Tape 	Cinta inicial
	 * @param N 	Numero maximo de de iteraciones
	 * @param P		Posicion inicial de la cinta
	 * @return		La cinta calculada despues de ejecutar la unidad de
	 * 				control <code>TT</code> sobre la cinta <code>Tape</code>
	 * 				empezando en la posicion <code>P</code> hasta
	 * 				<code>N</code> iteraciones. 
	 * 				Si se alcanzan las N iteraciones se devuelve una 
	 * 				cinta vacia
	 */
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
				System.out.println("Exceeded tape bounds: " + P);
				return EMPTY;
			}
			
			State nextState = states.get(transition.nextState);
			transition = nextState.process(tape[P]);
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
	
	/**
	 * Calcula la productividad de una transicion, esto evita volver
	 * a barrer la cinta al concluir el procesamiento.
	 * 
	 * @param before	Simbolo en la cinta antes de la ejecucion
	 * @param after		Simbolo en la cinta despues de la ejecucion
	 * @return			La productividad de la transicion:
	 * 						 1 Si se escribio de 0 a 1
	 * 						-1 Si se escribio de 1 a 0
	 * 						 0 Si no hubo cambios de escritura
	 */
	private static int getProductivity(char before, char after){
		if('1' == before && '0' == after){
			return -1;
		} else if ('0' == before && '1' == after){
			return 1;
		}
		return 0;
	}
	
	/**
	 * Parsea la unidad de control de la UTM en formato binario
	 * a una lista de estados.
	 * 
	 * La unidad de control debe cumplir ciertas reglas:
	 * 		- La longitud de la cadena debe ser multiplo de 16
	 * 		- Cada 16 caracteres es un estado de la unidad de control
	 * 		- Los primeros 8 caracteres son cuando se lee un simbolo '0'
	 * 		- Los ultimos  8 caracteres son cuando se lee un simbolo '1'
	 * 
	 * @param machine	Definicion de la unidad de control
	 * @return			Lista de estados de la UTM, el identificador de
	 * 					cada estado es el de la posicion de la lista, de tal
	 * 					forma que el estado 5 esta en la posicion 5 (basado en cero)
	 */
	private static List<State> parseMachine(String machine){
		if(machine.length() % 16 != 0){
			throw new RuntimeException("Invalid state string [" + machine + "]");
		}
		List<State> states = new ArrayList<State>();
		
		for(int idx = 0; idx < machine.length(); idx += 16){
			states.add(new State(machine.substring(idx, idx + 16)));
		}
		
		// Validacion de la maquina
		boolean haltState = false;
		for(State state: states){
			state.validateStates(states.size());
			if(! haltState && state.hasHaltState()){
				haltState = true;
			}
		}
		if(! haltState){
			throw new RuntimeException("Halt state not defined");
		}
		
		return states;
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
