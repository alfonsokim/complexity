/**
 * 
 */
package cyc.simulacion;


/**
 * @author Alfonso Kim
 *
 */
public class Cuantil implements Comparable<Cuantil> {
	
	private double i;
	private double x;
	private double fx;
	private double px;
	private double pdf;

	/**
	 * 
	 */
	public Cuantil() { 
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
	

}
