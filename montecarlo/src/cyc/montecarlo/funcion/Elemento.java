/**
 * 
 */
package cyc.montecarlo.funcion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alfonso Kim
 *
 */
public class Elemento implements Comparable<Elemento> {
	
	private double i;
	private double x;
	private double fx;
	private double px;
	private double pxObservado;
	private double pdf;
	private List<Double> observaciones;

	/**
	 * 
	 */
	public Elemento() { 
		observaciones = new ArrayList<Double>();
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
	 * @return the pxObservado to set
	 */
	public void setPxObservado(double pxObservado) {
		this.pxObservado = pxObservado;
	}
	
	/**
	 * @return the pxObservado
	 */
	public double getPxObservado() {
		return pxObservado;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDeltaPx(){
		return Math.abs(pxObservado - px);
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
		builder.append("Elemento [i=").append(i).append(", x=").append(x)
				.append(", fx=").append(fx).append(", px=").append(px)
				.append(", pdf=").append(pdf)
				.append(", pxObservado=").append(pxObservado)
				.append(", numObservaciones=").append(getNumObservaciones())
				.append(", deltaPx=").append(getDeltaPx())
				.append("]\n");
		return builder.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public String todasObservacionesStr(){
		return observaciones.toString();
	}

	@Override
	public int compareTo(Elemento o) {
		return new Double(fx).compareTo(new Double(o.fx));
	}

	/**
	 * @return the numObservaciones
	 */
	public long getNumObservaciones() {
		return observaciones.size();
	}

	/**
	 * 
	 */
	public void nuevaObservacion(double observacion) {
		this.observaciones.add(observacion);
	}

}
