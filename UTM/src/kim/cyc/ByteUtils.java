/**
 * 
 */
package kim.cyc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alfonso Kim
 *
 */
public class ByteUtils {

	/**
	 * 
	 */
	private ByteUtils() {}

	public static List<Integer> binarySequence(String sequence){
		List<Integer> intSeq = new ArrayList<Integer>();
		int count = 0;
		char current = 0;
		for(char c: toBinaryString(sequence).toCharArray()){
			if(current == 0){
				current = c; 
				count = 1; 
				continue;
			}
			if(c != current){
				intSeq.add(count);
				current = c;
				count = 0;
			}
			count++;
		}
		intSeq.add(count);
		return intSeq;
	}

	public static String toBinaryString(String data){
		StringBuilder builder = new StringBuilder();
		char[] buffer = data.toCharArray();
		for(int i = 0; i < buffer.length; i++) {
			builder.append(toBinaryString((byte) ((buffer[i] & 0xFF00) >> 8)));
			builder.append(toBinaryString((byte) (buffer[i] & 0x00FF)));
		}
		return builder.toString();
	}

	public static String toBinaryString(byte[] baits){
		StringBuilder builder = new StringBuilder();
		for(Byte bait: baits){
			builder.append(toBinaryString(bait));
		}
		return builder.toString();
	}

	public static String toBinaryString(byte bait){
		return String.format("%8s", Integer.toBinaryString(bait & 0xFF)).replace(' ', '0');
	}

	public static void main(String[] args){
		System.out.println("8=" + toBinaryString((byte) 9));
		System.out.println("x=" + toBinaryString("x"));
		System.out.println("intSeq=" + binarySequence("x"));
		System.out.println("lcs4str: " + LongestCommonSubsequence.forStrings("thisisatest", "testing123testing"));
		List<Integer> a = Arrays.asList(new Integer[]{9, 4, 0, 5, 8});
		List<Integer> b = Arrays.asList(new Integer[]{9, 4, 3, 5, 8});
		System.out.println("lcs4int: " + LongestCommonSubsequence.forIntegers(a, b));
		System.out.println("lcs4intFree: " + LongestCommonSubsequence.forIntegersFreedom(a, b, 5));
	}

}
