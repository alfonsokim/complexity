package cyc.montecarlo.funcion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Alfonso Kim
 */
public abstract class FuncionBase implements Funcion {
	
	protected double minimo;
	protected double maximo;
	protected long cuantiles;
	private List<Elemento> elementos;
	private double delta;
	private long numObservaciones;
	
	
	protected FuncionBase(double minimo, double maximo) {
		this(minimo, maximo, 10);
	}
	
	
	/**
	 * 
	 * @param minimo
	 * @param maximo
	 * @param cuantiles
	 */
	protected FuncionBase(double minimo, double maximo, long cuantiles) {
		this.minimo = minimo;
		this.maximo = maximo;
		this.cuantiles = cuantiles;
		this.elementos = new ArrayList<Elemento>();
		this.delta = (maximo - minimo) / cuantiles;
		
		double x = minimo;
		double sumFx = 0;
		for(int i = 0; i < cuantiles; i++){
			double fx = fX(x);
			Elemento elemento = new Elemento();
			elemento.setI(i);
			elemento.setX(x);
			sumFx += fx;
			elemento.setFx(fx);
			elementos.add(elemento);
			x += delta;
		}
		
		if(elementos.isEmpty()){
			throw new RuntimeException("No se generaron elementos");
		}
		
		double sumPx = 0;
		for(Elemento elemento: elementos){
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
	 * @param probX
	 */
	protected void agregarObservacion(double probX){
		if(probX < 0 || probX > 1){
			throw new RuntimeException("observacion fuera de rango [0, 1]");
		}
		
		Collections.sort(elementos, new Comparator<Elemento>(){
			@Override
			public int compare(Elemento a, Elemento b) {
				return new Double(a.getPdf()).compareTo(new Double(b.getPdf()));
			}
		});
		
		Elemento observado = elementos.get(0);
		for(Elemento elemento: elementos){
			if(elemento.getPdf() > probX){
				break;	// Esto se puede hacer en un ciclo sin cuerpo
			}
			observado = elemento;
		}
		observado.nuevaObservacion(probX);
		
	}
	
	/**
	 * 
	 */
	public void setValoresObservados(long numObservaciones){
		this.numObservaciones = numObservaciones;
		for(Elemento elemento: elementos){
			double pxObservado = (
					new Double(elemento.getNumObservaciones()) / 
					new Double(numObservaciones));
			elemento.setPxObservado(pxObservado);
		}
	}
	
	/**
	 * 
	 */
	public double getMedia(){
		double media = 0;
		for(Elemento elemento: elementos){
			media += elemento.getDeltaPx();
		}
		return media;
	}
	
	/**
	 * 
	 */
	public double getDesviacionStd(){
		double acumulado = 0;
		double media = getMedia();
		for(Elemento elemento : elementos){
			double a = media - elemento.getDeltaPx();
			acumulado += (a * a);
		}
		return Math.sqrt(acumulado / elementos.size());
	}
	
	/**
	 * 
	 */
	public List<Double> calculaLimitesCuantiles(int numCuantiles){
		//TODO: Validar que numCuantiles sea impar
		List<Double> limites = new ArrayList<Double>();
		long observacionesEsperadas = new Double(Math.ceil(numObservaciones / numCuantiles)).longValue();
		long obsAcumuladas = 0;
		for(Elemento elemento : elementos){
			if(obsAcumuladas + elemento.getNumObservaciones() >= observacionesEsperadas){
				limites.add(elemento.getX());
				obsAcumuladas = 0;
			}
			obsAcumuladas =+ elemento.getNumObservaciones();
		}
		return limites;
	}
	
	
	/**
	 * 
	 * @param x
	 */
	protected void validar(double x){
		if(x < minimo || x > maximo){
			throw new RuntimeException("x fuera de los limites");
		}
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FuncionBase [elementos=").append(elementos).append("]");
		return builder.toString();
	}


}
