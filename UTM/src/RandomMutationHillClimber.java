import java.util.ArrayList;
import java.util.List;


/**
 * @author Alfonso Kim
 *
 */
public class RandomMutationHillClimber {

	/**
	 * 
	 */
	public RandomMutationHillClimber() { }
	
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
		
		RandomMutationHillClimber climber = new RandomMutationHillClimber();
		
		List<Climber> experiments = new ArrayList<Climber>();
		experiments.add(new Climber(
				new DeJongsFunction(),
				new Genes(4, 3, 4),
				100));
		experiments.add(new Climber(
				new RosenbrockFunction(),
				new Genes(2, 2, 0),
				500));
		experiments.add(new Climber(
				new SchwefelFunction(),
				new Genes(3, 8, 0),
				500));
		experiments.add(new Climber(
				new AckleyFunction(),
				new Genes(3, 5, 7),
				100));
		
		for(Climber exp : experiments){
			System.out.println(exp.function.getName());
			ClimbResult result = climber.climb(exp.function, exp.genes, exp.numIterations);
			System.out.println("minimo: " + result.minimum);
			System.out.println(result.summit.toString());
		}
		
	}
	

}

class Climber {
	
	public Climber(Function function, Genes genes, int numIterations){
		this.function = function;
		this.genes = genes;
		this.numIterations = numIterations;
	}
	
	public Function function;
	public Genes genes;
	public int numIterations;
	
}


class ClimbResult {
	
	public double minimum;
	public Genes summit;
	
}

