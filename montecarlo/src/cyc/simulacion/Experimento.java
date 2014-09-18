/**
 * 
 */
package cyc.simulacion;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cyc.simulacion.funcion.Funcion;

/**
 * @author Alfonso Kim
 *
 */
public class Experimento {
	
	private Funcion funcion;
	private double minimo;
	private double maximo;
	private int segmentos;
	private List<Cuantil> cuantiles;
	private Random random;
	
	/**
	 * 
	 * @param funcion
	 * @param minimo
	 * @param maximo
	 * @param segmentos
	 */
	public Experimento(Funcion funcion, double minimo, double maximo, int segmentos) {
		this.funcion = funcion;
		this.minimo = minimo;
		this.maximo = maximo;
		this.segmentos = segmentos;
		this.cuantiles = new ArrayList<Cuantil>();
		this.random = new Random();
		
		double delta = (maximo - minimo) / segmentos;
		
		double x = minimo;
		double sumFx = 0;
		for(int i = 0; i < segmentos; i++){
			double fx = funcion.fX(x);
			Cuantil elemento = new Cuantil();
			elemento.setI(i);
			elemento.setX(x);
			sumFx += fx;
			elemento.setFx(fx);
			cuantiles.add(elemento);
			x += delta;
		}
		
		if(cuantiles.isEmpty()){
			throw new RuntimeException("No se generaron elementos");
		}
		
		double sumPx = 0;
		for(Cuantil elemento: cuantiles){
			double px = elemento.getFx() / sumFx;
			elemento.setPx(px);
			elemento.setPdf(sumPx);
			sumPx += px;
		}
		
		if(new Double(sumPx).intValue() != 1){
			throw new RuntimeException("probabilidades no suman 1:" + sumPx);
		}
	}
	
	/**
	 * 
	 * @param funcion
	 * @param minimo
	 * @param maximo
	 */
	public Experimento(Funcion funcion, double minimo, double maximo){
		this(funcion, minimo, maximo, 100);
	}
	
	/**
	 * 
	 * @param probX
	 */
	protected Muestra generarMuestra(long numObservaciones){
		
		// Se podria resolver con un Set si tuviera el metodo get
		Map<Cuantil, Cuantil> cuantilesMuestreados = new HashMap<Cuantil, Cuantil>();
		
		Collections.sort(cuantiles, new Comparator<Cuantil>(){
			@Override
			public int compare(Cuantil a, Cuantil b) {
				return new Double(a.getPdf()).compareTo(new Double(b.getPdf()));
			}
		});
		
		for(long observacion = 0; observacion < numObservaciones; observacion++){
			
			double probX = random.nextDouble();
		
			if(probX < 0 || probX > 1){
				throw new RuntimeException("observacion fuera de rango [0, 1]");
			}
			
			Cuantil observado = cuantiles.get(0);
			for(Cuantil elemento: cuantiles){
				if(elemento.getPdf() > probX){
					break;	// Esto se puede hacer en un ciclo sin cuerpo
				}
				observado = elemento;
			}
			
			double valorObservado = observado.getPx();
			
			if(cuantilesMuestreados.containsKey(observado)){
				cuantilesMuestreados.get(observado).nuevaObservacion(valorObservado);
			} else {
				Cuantil cuantilMuestreado = observado.clone();
				cuantilMuestreado.nuevaObservacion(valorObservado);
				cuantilesMuestreados.put(cuantilMuestreado, cuantilMuestreado);
			}
		}
		
		return new Muestra(new ArrayList<Cuantil>(cuantilesMuestreados.values()));
		
	}
	

	/**
	 * @return the funcion
	 */
	public Funcion getFuncion() {
		return funcion;
	}

	/**
	 * @return the minimo
	 */
	public double getMinimo() {
		return minimo;
	}

	/**
	 * @return the maximo
	 */
	public double getMaximo() {
		return maximo;
	}

	/**
	 * @return the segmentos
	 */
	public int getSegmentos() {
		return segmentos;
	}

	/**
	 * @return the cuantiles
	 */
	public List<Cuantil> getCuantiles() {
		return cuantiles;
	}

	/**
	 * toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Experimento [funcion=").append(funcion)
				.append(", minimo=").append(minimo)
				.append(", maximo=").append(maximo)
				.append(", segmentos=").append(segmentos)
				.append(", cuantiles=");
		for(Cuantil cuantil : cuantiles){
			builder.append("\n").append(cuantil);
		}
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * 
	 * @param streamSalida
	 */
	public void toCsv(OutputStream streamSalida){
		PrintWriter salida = new PrintWriter(streamSalida);
		salida.println(new StringBuilder("funcion").append(",").append(funcion));
		salida.println(new StringBuilder("minimo").append(",").append(minimo));
		salida.println(new StringBuilder("maximo").append(",").append(maximo));
		salida.println(new StringBuilder("segmentos").append(",").append(segmentos));
		salida.println("i,x,fx,px,pdf");
		for(Cuantil cuantil: cuantiles){
			salida.println(new StringBuilder()
				.append(cuantil.getI()).append(",")
				.append(cuantil.getX()).append(",")
				.append(cuantil.getFx()).append(",")
				.append(cuantil.getPx()).append(",")
				.append(cuantil.getPdf())
				.toString()
			);
		}
		salida.close();
	}


}
