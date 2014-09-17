/**
 * 
 */
package cyc.montecarlo;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

import cyc.montecarlo.funcion.Elemento;
import cyc.montecarlo.funcion.Funcion;
import cyc.montecarlo.funcion.FuncionBase;

/**
 * @author Alfonso Kim
 *
 */
public class Experimento {
	
	private Funcion funcion;
	private Random random;

	/**
	 * 
	 * @param funcion
	 */
	public Experimento(Funcion funcion) {
		this.funcion = funcion;
		this.random = new Random();
	}
	
	
	/**
	 * 
	 * @param numObservaciones
	 */
	public void generarObservaciones(int numObservaciones){
		for(int i = 0; i < numObservaciones; i++){
			funcion.observacion(random.nextDouble());
		}
		funcion.setValoresObservados(numObservaciones);
	}
	
	public Gausiana calculaGausiana(int numCuantiles){
		return funcion.calculaGausiana(numCuantiles);
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMedia(){
		return funcion.getMedia();
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDesviacionStd(){
		return funcion.getDesviacionStd();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Experimento [funcion=").append(funcion)
		.append(", media=").append(getMedia())
		.append(", desviacionStd=").append(getDesviacionStd())
		.append("]");
		return builder.toString();
	}
	
	public void toCsv(OutputStream streamSalida){
		PrintWriter salida = new PrintWriter(streamSalida);
		salida.println("i,x,fx,px,pdf,pxObservado,numObservaciones,deltaPx");
		//Lo siguiente es una aberracion de lo mal estructurado del codigo
		FuncionBase fBase = (FuncionBase)funcion;
		for(Elemento elemento: fBase.getElementos()){
			salida.println(new StringBuilder()
				.append(elemento.getI()).append(",")
				.append(elemento.getX()).append(",")
				.append(elemento.getFx()).append(",")
				.append(elemento.getPx()).append(",")
				.append(elemento.getPdf()).append(",")
				.append(elemento.getPxObservado()).append(",")
				.append(elemento.getNumObservaciones()).append(",")
				.append(elemento.getDeltaPx())
				.toString()
			);
		}
		salida.close();
	}
	

}
