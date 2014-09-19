/**
 * 
 */
package cyc.simulacion;

import java.util.ArrayList;
import java.util.List;


/**
 * Representa un cuantil en los calculos de las simulaciones
 * 
 * Esta clase no hace mucho, solo mantener una relacion
 * del identificador de cuantil (i), el valor de x (x), el
 * resultado de la funcion evaluada en x (f(x)), la probabilidad
 * de x (px) y la probabilidad acumulada para el cuantil (pdf)
 * 
 * Tambien se guarda las observaciones que se han visto en el cuantil
 * 
 * @author Alfonso Kim
 */
public class Cuantil implements Comparable<Cuantil>, Cloneable {
	
	private double i;
	private double x;
	private double fx;
	private double px;
	private double pdf;
	private long observacionesAcumuladas;
	private List<Double> observaciones;

	/**
	 * Constructor simple
	 */
	public Cuantil() { 
		observaciones = new ArrayList<Double>();
	}
	
	/**
	 * Clonar para generar la muestra
	 */
	@Override
	public Cuantil clone(){
		Cuantil clon = new Cuantil();
		clon.i = this.i;
		clon.x = this.x;
		clon.fx = this.fx;
		clon.px = this.px;
		clon.pdf = this.pdf;
		return clon;
	}
	
	/**
	 * 
	 * @param probX
	 */
	public void nuevaObservacion(double probX){
		observaciones.add(probX);
	}
	
	/**
	 * @return el numero de observaciones en el cuantil
	 */
	public int getNumObservaciones(){
		return observaciones.size();
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the fx
	 */
	public double getFx() {
		return fx;
	}

	/**
	 * @param fx the fx to set
	 */
	public void setFx(double fx) {
		this.fx = fx;
	}

	/**
	 * @return the px
	 */
	public double getPx() {
		return px;
	}

	/**
	 * @param px the px to set
	 */
	public void setPx(double px) {
		this.px = px;
	}

	/**
	 * @return the pdf
	 */
	public double getPdf() {
		return pdf;
	}

	/**
	 * @param pdf the pdf to set
	 */
	public void setPdf(double pdf) {
		this.pdf = pdf;
	}

	/**
	 * @return the i
	 */
	public double getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 */
	public void setI(double i) {
		this.i = i;
	}
	
	/**
	 * 
	 * @return el numero de observaciones acumuladas
	 */
	public long getObservacionesAcumuladas(){
		return observacionesAcumuladas;
	}
	
	/**
	 * @param observaciones agrega observaciones acumuladas al cuantil
	 */
	public void addObservacionesAcumuladas(long observaciones){
		//TODO: Tal vez validar que observaciones > 0
		observacionesAcumuladas += observaciones;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cuantil [i=").append(i).append(", x=").append(x)
				.append(", fx=").append(fx).append(", px=").append(px)
				.append(", pdf=").append(pdf)
				.append("]");
		return builder.toString();
	}


	@Override
	public int compareTo(Cuantil o) {
		return new Double(fx).compareTo(new Double(o.fx));
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(i);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pdf);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(px);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuantil other = (Cuantil) obj;
		if (Double.doubleToLongBits(fx) != Double.doubleToLongBits(other.fx))
			return false;
		if (Double.doubleToLongBits(i) != Double.doubleToLongBits(other.i))
			return false;
		if (Double.doubleToLongBits(pdf) != Double.doubleToLongBits(other.pdf))
			return false;
		if (Double.doubleToLongBits(px) != Double.doubleToLongBits(other.px))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		return true;
	}

	/**
	 * @return the observaciones
	 */
	public List<Double> getObservaciones() {
		return observaciones;
	}
	

}
