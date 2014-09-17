/**
 * 
 */
package cyc.montecarlo;

/**
 * @author Alfonso Kim
 *
 */
public class ResultadoMuestra {
	
	private double media;
	private double desviacionStd;

	/**
	 * 
	 */
	public ResultadoMuestra() { }

	/**
	 * @return the media
	 */
	public double getMedia() {
		return media;
	}

	/**
	 * @param media the media to set
	 */
	public void setMedia(double media) {
		this.media = media;
	}

	/**
	 * @return the desviacionStd
	 */
	public double getDesviacionStd() {
		return desviacionStd;
	}

	/**
	 * @param desviacionStd the desviacionStd to set
	 */
	public void setDesviacionStd(double desviacionStd) {
		this.desviacionStd = desviacionStd;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultadoMuestra [media=").append(media)
				.append(", desviacionStd=").append(desviacionStd).append("]");
		return builder.toString();
	}

}
