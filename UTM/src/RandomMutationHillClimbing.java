

/**
 * @author Alfonso Kim
 *
 */
public class RandomMutationHillClimbing {

	/**
	 * 
	 */
	public RandomMutationHillClimbing() { }
	
	
	public ClimbResult climb(Function function, char[][] genes, int numIterations){
		double min = Double.MAX_VALUE;
		for(int iteration = 1; iteration <= numIterations; iteration++){
			char[][] copy = Genes.copyGenes(genes);
			Genes.mutate(genes);
			double[] values = Genes.asDoubles(3, genes);
			double eval = function.evaluate(values);
			if(eval < min){
				min = eval;
			} else { // Si la mutacion no funciona regresar a la anterior
				genes = copy;
			}
		}
		ClimbResult result = new ClimbResult();
		result.minimum = min;
		//result.summit = genes;
		return result;
	}
	
	public ClimbResult climb2(Function function, Genes genes, int numIterations){
		double min = Double.MAX_VALUE;
		ClimbResult result = new ClimbResult();
		for(int iteration = 1; iteration <= numIterations; iteration++){
			Genes clon = genes.clone();
			genes.mutate();
			double[] values = genes.asDoubles();
			double eval = function.evaluate(values);
			if(eval < min){
				min = eval;
			} else {
				genes = clon;
			}
		}
		result.minimum = min;
		result.summit = genes;
		return result;
	}
	
	
	public static void main(String[] args){
		
		RandomMutationHillClimbing climber = new RandomMutationHillClimbing();
		
		Genes newGenes = new Genes(4, 3, 4);
		System.out.println(newGenes.toString());
		
		ClimbResult result = climber.climb2(new DeJongsFunction(), newGenes, 100);
		System.out.println("minimo: " + result.minimum);
		System.out.println(result.summit.toString());
		
	}
	

}


class ClimbResult {
	
	public double minimum;
	public Genes summit;
	
}

