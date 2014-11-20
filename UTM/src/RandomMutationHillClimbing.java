

/**
 * 
 */


/**
 * @author Alfonso Kim
 *
 */
public class RandomMutationHillClimbing {

	/**
	 * 
	 */
	public RandomMutationHillClimbing() {
	}
	
	
	public static void main(String[] args){
		char[] gene = Gene.getRandomGene(16);
		double value = Gene.asDouble(gene, 5);
		System.out.println("valor: " + value);
		Gene.mutate(gene);
		value = Gene.asDouble(gene, 5);
		System.out.println("valor mutado: " + value);
	}

}
