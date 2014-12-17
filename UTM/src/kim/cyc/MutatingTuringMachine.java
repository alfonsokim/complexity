/**
 * 
 */
package kim.cyc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * @author Alfonso Kim
 *
 */
public class MutatingTuringMachine {

	private final static int HALT = 63;
	private final static int STATE_LENGTH = 16;
	private final static int NUM_STATES = 64;
	private String freeze;
	private char[] machine;
	private int haltStateIndex;
	private long productivity;
	private List<State> states;
	private Random random;
	private boolean haltStateReached;

	/**
	 * 
	 */
	public MutatingTuringMachine(boolean forceHaltState) { 
		random = new Random();
		if(forceHaltState){
			haltStateIndex = random.nextInt(NUM_STATES+1);
		} else {
			haltStateIndex = -1;
		}
		StringBuilder machineBuilder = new StringBuilder();
		for(int stateIdx = 0; stateIdx < NUM_STATES; stateIdx++){
			machineBuilder.append(getRandomState(stateIdx == haltStateIndex));
		}
		freeze = machineBuilder.toString();
		states = parseMachine(machineBuilder.toString());
	}
	
	/**
	 * 
	 * @param haltState
	 * @return
	 */
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
	
	/**
	 * 
	 */
	public void mutate(){
		int mutatedState = haltStateIndex;
		while(mutatedState == haltStateIndex){
			mutatedState = random.nextInt(NUM_STATES);
		}
		int mutatedBit = random.nextInt(STATE_LENGTH);
		machine = freeze.toCharArray();
		char newChar = states.get(mutatedState).mutate(mutatedBit);
		machine[(mutatedState * 16) + mutatedBit] = newChar;
	}
	
	public void commit(){
		freeze = new String(machine);
	}
	
	public void restore(){
		machine = freeze.toCharArray();
		states = parseMachine(freeze);
	}
	
	public void setMachineDefinition(String definition){
		states = parseMachine(definition);
	}
	
	public String getMachineDefinition(){
		return new String(machine);
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
	private List<State> parseMachine(String machine){
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

	/**
	 * Calcula la cinta de salida de una UTM dados los parametros:
	 * 
	 * @param tapeStr 			Cinta inicial
	 * @param numIterations 	Numero maximo de de iteraciones
	 * @param headPosition		Posicion inicial de la cinta
	 * @return		La cinta calculada despues de ejecutar la unidad de
	 * 				control <code>TT</code> sobre la cinta <code>Tape</code>
	 * 				empezando en la posicion <code>P</code> hasta
	 * 				<code>N</code> iteraciones. 
	 * 				Si se alcanzan las N iteraciones se devuelve una 
	 * 				cinta vacia
	 */
	public String processTape(String tapeStr, long numIterations, int headPosition) {
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

			if(headPosition < 0 || headPosition >= tape.length){
				return new String(tape);
			}

			State nextState = states.get(transition.nextState);
			transition = nextState.process(tape[headPosition]);
			iterations++;

		}

		if(transition.nextState == HALT){
			haltStateReached = true;
			return new String(tape);
		}
		return new String(tape);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isHalted(){
		return haltStateReached;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getProductivity(){
		return productivity;
	}
	
	/** 
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		int stateIdx = 0;
		for(State state: states){
			builder.append("State ").append(stateIdx++)
			.append("=").append(state.definition).append(",");
		}
		return builder.toString();
	}
	
	
	public MutatingTuringMachine reduce(){
		MutatingTuringMachine turingMachine = new MutatingTuringMachine(true);
		Node node0 = new Node(states.get(0));
		node0.originalState = 0;
		node0.newState = 0;
		Set<Node> graph = new HashSet<Node>();
		graph.add(node0);
		buildGraph(node0, graph, 0);
		System.out.println("Numero de estados usados: " + graph.size());
		/*
		System.out.println("Grafo: " + graph);
		for(Node node: graph){
			System.out.println("Nodo " + node.originalState + " sigue a " + node.transitions[0] + " en 0 y a " + node.transitions[1] + " por 1");
		}
		*/
		turingMachine.setMachineDefinition("0011111100111111");
		return turingMachine;
	}
	
	public void buildGraph(Node node, Set<Node> graph, int newState){
		for(int i = 0; i < node.state.transitions.length; i++){
			Transition transition = node.state.transitions[i];
			State next = states.get(transition.nextState);
			if(next.used){
				Node newNode = new Node(next);
				newNode.originalState = transition.nextState;
				node.transitions[i] = newNode;
				if(! graph.contains(newNode)){
					newNode.newState = newState + 1;
					graph.add(newNode);
					buildGraph(newNode, graph, (newState + 1));
				}
			}
		}
	}
	
	/**
	 * Metodo recursivo que agrega al mapa de memoria los estados utiles y
	 * sus transiciones relacionadas
	 * 
	 * @param stateOffset	Identificador del estado procesado. Si es mayor o
	 * 						igual a 64 entonces es el estado de la transicion 1
	 * @param memory		La memoria de estados procesados. La llave es el
	 * 						identificador de estado en la maquina original y
	 * 						el valor es el identificador del estado en la nueva
	 * 						maquina de Turing
	 */
	private void recursiveReduce(int stateOffset, Map<Integer, Integer> memory){
		int state = stateOffset % 64;
		if(states.get(state).used && ! memory.containsKey(state)){
			memory.put(stateOffset, memory.size());
			recursiveReduce(states.get(state).transitions[0].nextState, memory);
			recursiveReduce(states.get(state).transitions[1].nextState + 64, memory);
			/*
			for(int transition = 0; transition < 2; transition++){
				int nextState = states.get(state).transitions[transition].nextState;
				System.out.println("de " + state + " a " + nextState);
				recursiveReduce(nextState, memory);
			}
			*/
		}
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
		boolean used;

		/**
		 * Constructor.
		 * 
		 * @param def Definicion del estado, de 16 caracteres
		 */
		State(String def){
			definition = def;
			setUp();
		}
		
		/**
		 * 
		 */
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
			used = true;
			int value = read == '0' ? 0 : 1;
			return transitions[value];
		}
		
		/**
		 * 
		 * @param idx
		 * @return
		 */
		char mutate(int idx){
			used = true;
			char[] sequence = definition.toCharArray();
			char bit = sequence[idx];
			char newBit = bit == '1' ? '0' : '1';
			sequence[idx] = newBit;
			definition = new String(sequence);
			setUp();
			return newBit;
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
		
		@Override
		public int hashCode(){
			return definition.hashCode();
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof State){
				State s = (State)other;
				return definition.equals(s.definition);
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
	
	// **************************************************************************
	private static class Node {
		
		State state;
		Node[] transitions;
		int originalState;
		int newState;
		
		Node(State state){
			this.state = state;
			transitions = new Node[2];
			originalState = -1;
			newState = -1;
		}
		
		@Override
		public int hashCode(){
			return state.hashCode();
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof Node){
				Node n = (Node)other;
				return state.equals(n.state);
			}
			return false;
		}
		
		@Override
		public String toString(){
			return new StringBuilder()
			.append("originalState: ").append(originalState).append(" ")
			.append("newState: ").append(newState).append(" ")
			.toString();
		}
		
	}

}
