

/**
 * @author Alfonso Kim
 *
 */
public class RandomMutationHillClimbing {

	/**
	 * 
	 */
	public RandomMutationHillClimbing() { }
	
	public ClimbResult climb(Function function, Genes genes, int numIterations){
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
		Function f1 = new DeJongsFunction();
		System.out.print("=================================== ");
		System.out.print(f1.getName());
		System.out.println(" ===================================");
		
		Genes newGenes = new Genes(4, 3, 4);
		System.out.println(newGenes.toString());
		
		ClimbResult result = climber.climb(f1, newGenes, 100);
		
		System.out.println(" ===================================");
		System.out.println("minimo: " + result.minimum);
		System.out.println(result.summit.toString());
		
		Function f2 = new RosenbrockFunction();
		System.out.print("=================================== ");
		System.out.print(f1.getName());
		System.out.println(" ===================================");
		
		Genes f2Genes = new Genes(2, 2, 0);
		System.out.println(f2Genes.toString());
		
		ClimbResult f2Result = climber.climb(f2, f2Genes, 500);
		
		System.out.println(" ===================================");
		System.out.println("minimo: " + f2Result.minimum);
		System.out.println(f2Result.summit.toString());
		
	}
	

}


class ClimbResult {
	
	public double minimum;
	public Genes summit;
	
}

