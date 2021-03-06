/**
 * 
 */
package kim.cyc;

import java.util.List;

/**
 * @author Alfonso Kim
 *
 */
public class TapeComparator {
	
	private String original;
	private int freedom;
	private List<Integer> sequences;

	/**
	 * 
	 */
	public TapeComparator(String original, int freedom) {
		this.original = original;
		this.freedom = freedom;
		this.sequences = ByteUtils.binarySequence(this.original);
	}
	
	/**
	 * Encuentra la similitud entre la cadena con la que se construyo el comparador
	 * y una cadena dada
	 * 
	 * @param other La cadena aproximada a comparar
	 * @return		Un grado de similitud entre las 2 cadenas
	 */
	public double getLikeness(String other){
		List<Integer> otherSequences = ByteUtils.binarySequence(other);
		double likeness = 0;
		for(int degree = 0; degree <= freedom; degree++){
			List<Integer> lcs = LongestCommonSubsequence.forIntegers(sequences, otherSequences, degree);
			double factor = (lcs.size() / (double)sequences.size() / (degree+1));
			likeness += factor;
		}
		return likeness;
	}

}
