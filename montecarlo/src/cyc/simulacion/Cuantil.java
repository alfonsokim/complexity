/**
 * 
 */
package cyc.simulacion;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Alfonso Kim
 *
 */
public class Cuantil implements Comparable<Cuantil>, Cloneable {
	
	private double i;
	private double x;
	private double fx;
	private double px;
	private double pdf;
	private List<Double> observaciones;

	/**
	 * 
	 */
	public Cuantil() { 
		observaciones = new ArrayList<Double>();
	}
	
	/**
	 * 
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
	 * 
	 * @return
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
	
	/**
	 * 
	 * @return
	 */
	public String elementoToString(){
		return toString();
	}


	@Override
	public int compareTo(Cuantil o) {
		return new Double(fx).compareTo(new Double(o.fx));
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
