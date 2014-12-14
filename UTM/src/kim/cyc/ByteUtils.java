/**
 * 
 */
package kim.cyc;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

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
	
	public static String getZeroString(int strSize){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < strSize; i++){
			builder.append("0");
		}
		return builder.toString();
	}
	
	public static String getRandomString(int strSize){
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < strSize; i++){
			builder.append(random.nextBoolean() ? '1' : '0');
		}
		return builder.toString();
	}
	
	public static byte toByte(String bits){
		if(bits.length() != 8){
			throw new RuntimeException("There must be 8 bits");
		}
		byte aByte = (byte)0;
		for(int pos = 0; pos < bits.length(); pos++){
			int bitIdx = bits.length() - 1 - pos;
			if(bits.charAt(bitIdx) == '1'){
				aByte |= 1 << pos;
			} else if(bits.charAt(bitIdx) == '0'){
				aByte &= ~(1 << pos);
			} else {
				throw new RuntimeException("Invalid bit: " + bits.charAt(pos));
			}
			
		}
		return aByte;
	}
	
	public static String readFileAsBitString(File in){
		StringBuilder bitStringBuilder = new StringBuilder();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(in, "r");
			byte read;
			while((read = raf.readByte()) != -1){
				bitStringBuilder.append(toBinaryString(read));
			}
		} catch (EOFException e) {
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(raf != null)
				try { raf.close(); } 
				catch (IOException e) { throw new RuntimeException(e); }
		}
		return bitStringBuilder.toString();
	}
	
	public static void writeBitStringAsFile(File out, String data){
		if(data.length() % 8 != 0){
			throw new RuntimeException("Lenght must be multiple of 8");
		}
		RandomAccessFile raf = null;
		byte[] buffer = new byte[data.length() / 8];
		for(int idx = 0; idx < buffer.length; idx++){
			int start = idx * 8;
			int end = start + 8;
			buffer[idx] = toByte(data.substring(start, end));
		}
		try {
			raf = new RandomAccessFile(out, "rw");
			raf.write(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(raf != null)
				try { raf.close(); }
				catch (IOException e) { throw new RuntimeException(e); }
		}
	}

	public static void main(String[] args){
		String strFile = readFileAsBitString(new File("test.txt"));
		System.out.println("probando con: " + strFile);
		writeBitStringAsFile(new File("testout.txt"), strFile);
		int test = 93;
		String strTest = toBinaryString((byte) test);
		System.out.println(test + "=" + strTest);
		System.out.println("toByte: " + (byte)toByte(strTest));
		System.out.println("x=" + toBinaryString("x"));
		System.out.println("intSeq=" + binarySequence("x"));
		System.out.println("lcs4str: " + LongestCommonSubsequence.forStrings("thisisatest", "testing123testing"));
		List<Integer> a = Arrays.asList(new Integer[]{9, 4, 0, 5, 8});
		List<Integer> b = Arrays.asList(new Integer[]{9, 4, 3, 5, 8});
		System.out.println("lcs4int: " + LongestCommonSubsequence.forIntegers(a, b));
		System.out.println("lcs4intFree: " + LongestCommonSubsequence.forIntegers(a, b, 5));
		
	}

}
