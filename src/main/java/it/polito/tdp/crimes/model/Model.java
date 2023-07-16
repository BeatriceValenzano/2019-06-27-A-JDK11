package it.polito.tdp.crimes.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	EventsDao dao;
	List<String> soluzioneMigliore = new LinkedList<String>();
	List<String> parziale = new LinkedList<String>();
	
	public Model() {
		dao = new EventsDao();
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	public void creaGrafo(int anno, String categoria) {
		
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
//		AGGIUNTA DEI VERTICI
		Graphs.addAllVertices(grafo, dao.reato(anno, categoria));
		
//		AGGIUNTA ARCHI
		
		for(String s : grafo.vertexSet()) {
			for(String s1 : grafo.vertexSet()) {
				if(!s.equals(s1)) {
					double peso = dao.getPeso(anno, s, s1);
					if(peso>0)
						Graphs.addEdge(grafo, s, s1, peso);
				}
			}
		}
	}
	
	public List<String> getArchiMax() {
		
		double pesoMax = 0.0;
		List<String> archiMax = new LinkedList<String>();
		for(DefaultWeightedEdge edge : grafo.edgeSet()) {
			double peso = grafo.getEdgeWeight(edge);
			if(peso>pesoMax) {
				pesoMax = peso;
			}
		}
		
		for(DefaultWeightedEdge edge : grafo.edgeSet()) {
			double peso = grafo.getEdgeWeight(edge);
			if(peso == pesoMax) {
				archiMax.add(grafo.getEdgeSource(edge) + " - " + grafo.getEdgeTarget(edge) + " - " + peso);
			}
		}
		return archiMax;
	}
	
	public List<String> percorsoMigliore(String partenza, String arrivo) {
		
		soluzioneMigliore = new LinkedList<String>();
		parziale = new LinkedList<String>();
		parziale.add(partenza);
		cercaPercorso(parziale, partenza, arrivo);
		return soluzioneMigliore;
	}
	
	public void cercaPercorso(List<String> parziale, String partenza, String arrivo) {
		
		if(parziale.size() == grafo.vertexSet().size() && partenza.equals(arrivo)) { //devono essere verificate insieme
			if(pesoMaggiore(parziale) > pesoMaggiore(soluzioneMigliore)) {
				soluzioneMigliore.clear();
				soluzioneMigliore.addAll(parziale);
			}
			return;
		}
		
		for(String s : Graphs.neighborListOf(grafo, partenza)) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cercaPercorso(parziale, s, arrivo);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	private double pesoMaggiore(List<String> parziale) {
		
		double peso = 0.0;
		for(int i=0; i<parziale.size()-1; i++ ) {
			peso += grafo.getEdgeWeight(grafo.getEdge(parziale.get(i), parziale.get(i+1)));
		}
		return peso;
	}

	public List<Integer> getAnni(){
		return dao.anni();
	}
	
	public List<String> getCategories() {
		return dao.categorie();
	}
}
