

/**
 * @author Alfonso Kim
 *
 */
public class RandomMutationHillClimbing {

	/**
	 * 
	 */
	public RandomMutationHillClimbing() { }
	
	
	public double climb(Function function, char[][] genes, int numIterations){
		double min = Double.MAX_VALUE;
		for(int iteration = 1; iteration <= numIterations; iteration++){
			char[][] copy = Gene.copyGenes(genes);
			Gene.mutate(genes);
			double[] values = Gene.asDoubles(3, genes);
			double eval = function.evaluate(values);
			if(eval < min){
				min = eval;
			} else { // Si la mutacion no funciona regresar a la anterior
				genes = copy;
			}
		}
		return min;
	}
	
	
	public static void main(String[] args){
		char[] gene = Gene.getRandomGene(16);
		double value = Gene.asDouble(5, gene);
		System.out.println("valor: " + value);
		Gene.mutate(gene);
		value = Gene.asDouble(5, gene);
		System.out.println("valor mutado: " + value);
		
		char[][] genes = Gene.getRandomGenes(4, 8);
		for(char[] oneGene: genes){
			value = Gene.asDouble(3, oneGene);
			System.out.println("valor: " + value);
		}
		
		double min = new RandomMutationHillClimbing().climb(new DeJongsFunction(), genes, 100);
		
		System.out.println("minimo: " + min);
		
	}
	
	

}
