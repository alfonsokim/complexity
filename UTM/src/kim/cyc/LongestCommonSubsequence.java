/**
 * 
 */
package kim.cyc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alfonso Kim
 *
 */
public class LongestCommonSubsequence {

	/**
	 * 
	 */
	public LongestCommonSubsequence() { }
	
	public static String forStrings(String a, String b) {
		int[][] lengths = new int[a.length()+1][b.length()+1];

		// row 0 and column 0 are initialized to 0 already

		for (int i = 0; i < a.length(); i++)
			for (int j = 0; j < b.length(); j++)
				if (a.charAt(i) == b.charAt(j))
					lengths[i+1][j+1] = lengths[i][j] + 1;
				else
					lengths[i+1][j+1] =
					Math.max(lengths[i+1][j], lengths[i][j+1]);

		// read the substring out from the matrix
		StringBuffer sb = new StringBuffer();
		for (int x = a.length(), y = b.length();
				x != 0 && y != 0; ) {
			if (lengths[x][y] == lengths[x-1][y])
				x--;
			else if (lengths[x][y] == lengths[x][y-1])
				y--;
			else {
				assert a.charAt(x-1) == b.charAt(y-1);
				sb.append(a.charAt(x-1));
				x--;
				y--;
			}
		}

		return sb.reverse().toString();
	}
	
	
	public static List<Integer> forIntegers(List<Integer> a, List<Integer> b) {
		return forIntegers(a, b, 0);
	}

	/**
	 * Encuentra la subsecuencia maxima no n grados de libertad
	 * 
	 * Si la diferencia absoluta maxima es mejor a igual a los grados de libertad, 
	 * entonces los 2 numeros se consideran iguales.
	 * 
	 * @param a			Entero a comparar
	 * @param b			Entero a comparar
	 * @param freedom	Grados de libertad
	 * @return			La subsecuencia maxim de las 2 listas
	 */
	public static List<Integer> forIntegers(List<Integer> a, List<Integer> b, int freedom) {
		int[][] lengths = new int[a.size()+1][b.size()+1];

		// row 0 and column 0 are initialized to 0 already
		
		for (int i = 0; i < a.size(); i++){
			for (int j = 0; j < b.size(); j++) {
				if(checkFreedom(a.get(i), b.get(i), freedom)){
				//if (a.get(i) == b.get(j)){
					lengths[i+1][j+1] = lengths[i][j] + 1;
				} else {
					lengths[i+1][j+1] = Math.max(lengths[i+1][j], lengths[i][j+1]);
				}
			}
		}
					
		// read the substring out from the matrix
		List<Integer> sequences = new ArrayList<Integer>();
		for (int x = a.size(), y = b.size(); x != 0 && y != 0; ) {
			if (lengths[x][y] == lengths[x-1][y]){
				x--;
			} else if (lengths[x][y] == lengths[x][y-1]) {
				y--;
			} else {
				assert a.get(x-1) == b.get(y-1);
				sequences.add(a.get(x - 1));
				x--;
				y--;
			}
		}
		Collections.reverse(sequences);
		return sequences;
	}
	
    /**
     * @return  verdadero si la diferencia absoluta entre a y b es menor o igual a 
     *          los grados de libertad. 
     */
    private static boolean checkFreedom(int a, int b, int freedom){
        return Math.abs(a - b) <= freedom;
    }
	
}
