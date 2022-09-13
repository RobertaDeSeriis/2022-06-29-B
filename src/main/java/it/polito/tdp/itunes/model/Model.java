package it.polito.tdp.itunes.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	ItunesDAO dao; 
	List<Album> vertici; 
	Graph<Album, DefaultWeightedEdge> grafo; 
	List<Album> migliore; 
	double bilancioSorgente;
	Set<Album> visitati; 
	
	
	public Model() {
		this.dao= new ItunesDAO(); 
		
	}
	
	public String creaGrafo(double d) {
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		vertici= dao.getAllAlbums(d);
		double peso=0;
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(Album a: vertici) {
			for (Album a1: vertici) {
				if(a!=a1 && a.p!=a1.p && (a1.p+a.p)>4*d) {
					peso=a1.p+a.p;
					
					if(a.p>a1.p) {
						Graphs.addEdge(this.grafo, a1, a, peso);
					}
					else if(a.p<a1.p) {
						Graphs.addEdge(this.grafo, a, a1, peso);
					}
				}
					
			}
		}
		
		
		return "Grafo creato!\n# Vertici:"+grafo.vertexSet().size()+ "\n# Archi: "+grafo.edgeSet().size();	
	}

	public List<Album> getVertici() {
		return vertici;
	}
	
	public double calcolaBilancio(Album a1) {
		double p=0; 
		
		for(Album a: Graphs.predecessorListOf(this.grafo, a1)) {
			p+=grafo.getEdgeWeight(this.grafo.getEdge(a, a1));
		}
		for(Album a: Graphs.successorListOf(this.grafo, a1)) {
			p-=grafo.getEdgeWeight(this.grafo.getEdge(a1, a));
		}
		return p; 
	}
	
	public List<AlbumBilancio> getBilancio(Album a1){
		List<Album> vicini= Graphs.successorListOf(this.grafo, a1);
		List<AlbumBilancio> bilancio= new LinkedList<>(); 
		
		for(Album a: vicini) {
			double bil=this.calcolaBilancio(a);
			bilancio.add(new AlbumBilancio(a,(int) bil));
		}
		Collections.sort(bilancio);
		return bilancio;
	}
	
	public boolean esisteGrafo() {
		if(this.grafo!=null)
			return true;
		return false;
	}
	
	

public List<Album> calcolaPercorso(Album sorg, Album dest, double x)
{
	migliore = new LinkedList<Album>();
	List<Album> parziale = new LinkedList<>();
	parziale.add(sorg);
	bilancioSorgente=this.calcolaBilancio(sorg);
	cercaRicorsiva(parziale, dest, x);
	return migliore;
}

private void cercaRicorsiva(List<Album> parziale, Album dest, double x) {
	
			//condizione di terminazione
			if(parziale.get(parziale.size()-1).equals(dest)) //finisce quando in parziale c'è la destinazione
			{
				int verticiMax= calcolaVerticiMax(parziale);
				if(verticiMax > calcolaVerticiMax(migliore))// tocca il maggior numero di vertici
					//con bilancio maggiore del bilancio del vertice di partenza
				{
					migliore = new LinkedList<>(parziale);
				}
				return;
			}
			Album ultimo=parziale.get(parziale.size()-1);
			for(Album v:Graphs.successorListOf(this.grafo, parziale.get(parziale.size()-1))) //scorro sui vicini dell'ultimo nodo sulla lista
			{
				if(!parziale.contains(v))
				{
					if(grafo.getEdgeWeight(grafo.getEdge(ultimo,v))>=x)
					{
						parziale.add(v);
						cercaRicorsiva(parziale, dest, x);
						parziale.remove(parziale.size()-1);
					}
					
				}
				
			}
	
}

private int calcolaVerticiMax(List<Album> parziale) {
	int n=0;
	for(Album a: parziale) {
		if(this.calcolaBilancio(a)>bilancioSorgente) {
			n++; 
		}
	}
	return n;
}
	

public boolean sonoConnessi(Album p, Album a) {
	visitati= new HashSet<>(); 
	DepthFirstIterator<Album, DefaultWeightedEdge> it= new DepthFirstIterator<>(this.grafo, p); 
	while(it.hasNext()) {
		visitati.add(it.next()); 
		}
	
	if(visitati.contains(a)) {
	return true; 
	}
	return false;
}
}
