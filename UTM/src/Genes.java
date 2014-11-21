import java.util.Arrays;
import java.util.Random;


/**
 * @author Alfonso Kim
 *
 */
public class Genes {
	
	private static Random random = new Random();
	
	private int numGenes;
	private int numIntegerPositions;
	private int numDecimalPositions;
	private char[][] genes;
	
	public Genes(int numGenes, int numIntegerPositions, int numDecimalPositions) {
		this.numGenes = numGenes;
		this.numIntegerPositions = numIntegerPositions;
		this.numDecimalPositions = numDecimalPositions;
		this.genes = getRandomGenes();
	}
	
	private char[][] getRandomGenes(){
		int length = numIntegerPositions + numDecimalPositions + 1;
		char[][] genes = new char[numGenes][length];
		for(int i = 0; i < numGenes; i++){
			genes[i] = getRandomGene();
		}
		return genes;
	}
	
	private char[] getRandomGene(){
		int length = numIntegerPositions + numDecimalPositions + 1;
		char[] gene = new char[length];
		for(int i = 0; i < length; i++){
			gene[i] = random.nextBoolean() ? '1' : '0';
		}
		return gene;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Integer Positions: ").append(numIntegerPositions).append("\n");
		builder.append("Decimal Positions: ").append(numDecimalPositions).append("\n");
		for(int i = 0; i < numGenes; i++){
			builder.append(new String(genes[i]))
				.append(": ")
				.append(asDouble(numIntegerPositions, genes[i]));
			if(i < numGenes - 1) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	public Genes clone(){
		char[][] copy = new char[genes.length][genes[0].length];
		for(int i = 0; i < genes.length; i++){
			char[] from = genes[i];
			char[] to = new char[from.length];
			System.arraycopy(from, 0, to, 0, from.length);
			copy[i] = to;
		}
		Genes clon = new Genes(numGenes, numIntegerPositions, numDecimalPositions);
		clon.genes = copy;
		return clon;
	}
	
	public void mutate(){
		char[] gene = genes[random.nextInt(genes.length)]; 	// Seleccionar gen aleatorio
		int chromosomeIdx = random.nextInt(gene.length);	// Seleccionar cromosoma aleatorio
		char chromosome = gene[chromosomeIdx];
		gene[chromosomeIdx] = chromosome == '0' ? '1' : '0';	// Invertir
	}
	
	public double[] asDoubles(){
		double[] values = new double[genes.length];
		for(int i = 0; i < genes.length; i++){
			values[i] = genAsDouble(genes[i]);
		}
		return values;
	}
	
	private double genAsDouble(char[] gene){
		if(null == gene || gene.length == 0){
			throw new RuntimeException("Gene must not be empty");
		}
		if(gene.length < numIntegerPositions + 2){
			throw new RuntimeException("Invalid number of integer positions: " + numIntegerPositions);
		}
		int sign = gene[0] == '0' ? 1 : -1;
		int numDecimalDigits = gene.length - numIntegerPositions - 1;
		double decimal = Math.pow(2, numDecimalDigits);
		char[] number = Arrays.copyOfRange(gene, 1, gene.length);
		return (Long.parseLong(new String(number), 2) / decimal * sign);
	}
	
	// ================

	public static double[] asDoubles(int numIntegerPositions, char[]... genes){
		if(null == genes){
			return null;
		}
		double[] values = new double[genes.length];
		for(int i = 0; i < genes.length; i++){
			values[i] = asDouble(numIntegerPositions, genes[i]);
		}
		return values;
	}
	
	public static double asDouble(int numIntegerPositions, char[] gene){
		if(null == gene || gene.length == 0){
			throw new RuntimeException("Gene must not be empty");
		}
		if(gene.length < numIntegerPositions + 2){
			throw new RuntimeException("Invalid number of integer positions: " + numIntegerPositions);
		}
		int sign = gene[0] == '0' ? 1 : -1;
		int numDecimalDigits = gene.length - numIntegerPositions - 1;
		double decimal = Math.pow(2, numDecimalDigits);
		char[] number = Arrays.copyOfRange(gene, 1, gene.length);
		return (Long.parseLong(new String(number), 2) / decimal * sign);
	}
	
	public static char[][] copyGenes(char[]... genes){
		if(null == genes){
			return null;
		}
		if(genes.length == 0){
			return new char[genes.length][0];
		}
		char[][] copy = new char[genes.length][genes[0].length];
		for(int i = 0; i < genes.length; i++){
			char[] from = genes[i];
			char[] to = new char[from.length];
			System.arraycopy(from, 0, to, 0, from.length);
			copy[i] = to;
		}
		return copy;
	}
	
	
	public static void mutate(char[]... genes){
		char[] gene = genes[random.nextInt(genes.length)]; 	// Seleccionar gen aleatorio
		int chromosomeIdx = random.nextInt(gene.length);	// Seleccionar cromosoma aleatorio
		char chromosome = gene[chromosomeIdx];
		gene[chromosomeIdx] = chromosome == '0' ? '1' : '0';	// Invertir
	}
	
	public static char[][] getRandomGenes(int numGenes, int length){
		char[][] genes = new char[numGenes][length];
		for(int i = 0; i < numGenes; i++){
			genes[i] = getRandomGene(length);
		}
		return genes;
	}
	
	public static char[] getRandomGene(int length){
		char[] gene = new char[length];
		for(int i = 0; i < length; i++){
			gene[i] = random.nextBoolean() ? '1' : '0';
		}
		return gene;
	}

}
