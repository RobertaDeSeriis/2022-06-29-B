package it.polito.tdp.itunes.model;


public class AlbumBilancio implements Comparable<AlbumBilancio>{
	
	Album a; 
	double peso;

	
	public AlbumBilancio(Album a, double peso) {
		super();
		this.a = a;
		this.peso = peso;
	}
	
	
	public Album getA() {
		return a;
	}
	public void setA(Album a) {
		this.a = a;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}


	@Override
	public String toString() {
		return  a + ", bilancio=" + peso;
	}


	public int compareTo(AlbumBilancio o) {
		// compare to con double ordine decrescente 
		if(this.getPeso()>o.getPeso())
		{
			return -1;
		}
		if(this.getPeso()<o.getPeso())
		{
			return 1;
		}
		return 0;
	}
	
	

}
