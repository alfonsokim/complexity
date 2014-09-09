package cyc.montecarlo;

/**
 * 
 * @author Alfonso Kim
 */
public class Configuracion {
	
	private long semilla;
	private long iteraciones;
	private long numPromedios;
	private long numCuantiles;
	private double minimo;
	private double maximo;
	
	/**
	 * 
	 * @return
	 */
	public static Configuracion getConfiguracionPruebas(){
		Configuracion conf = new Configuracion();
		conf.iteraciones = 10000;
		conf.numPromedios = 1000;
		conf.numCuantiles = 10;
		conf.minimo = 0;
		conf.maximo = 1;
		return conf;
	}
	
	/**
	 * 
	 */
	public Configuracion() { }

	/**
	 * @return the semilla
	 */
	public long getSemilla() {
		return semilla;
	}

	/**
	 * @param semilla the semilla to set
	 */
	public void setSemilla(long semilla) {
		this.semilla = semilla;
	}

	/**
	 * @return the numAleatorios
	 */
	public long getIteraciones() {
		return iteraciones;
	}

	/**
	 * @param numAleatorios the numAleatorios to set
	 */
	public void setIteraciones(long iteraciones) {
		this.iteraciones = iteraciones;
	}

	/**
	 * @return the numPromedios
	 */
	public long getNumPromedios() {
		return numPromedios;
	}

	/**
	 * @param numPromedios the numPromedios to set
	 */
	public void setNumPromedios(long numPromedios) {
		this.numPromedios = numPromedios;
	}

	/**
	 * @return the numCuantiles
	 */
	public long getNumCuantiles() {
		return numCuantiles;
	}

	/**
	 * @param numCuantiles the numCuantiles to set
	 */
	public void setNumCuantiles(long numCuantiles) {
		this.numCuantiles = numCuantiles;
	}

	/**
	 * @return the minimo
	 */
	public double getMinimo() {
		return minimo;
	}

	/**
	 * @param minimo the minimo to set
	 */
	public void setMinimo(double minimo) {
		this.minimo = minimo;
	}

	/**
	 * @return the maximo
	 */
	public double getMaximo() {
		return maximo;
	}

	/**
	 * @param maximo the maximo to set
	 */
	public void setMaximo(double maximo) {
		this.maximo = maximo;
	}
	
	public double getDelta(){
		return (maximo - minimo) / numCuantiles;
	}

	
	
}
