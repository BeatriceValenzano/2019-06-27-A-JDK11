package it.polito.tdp.crimes.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	EventsDao dao;
	
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
	
	public String getArchiMax() {
		
		double pesoMax = 0.0;
		String s = "";
		for(DefaultWeightedEdge edge : grafo.edgeSet()) {
			double peso = grafo.getEdgeWeight(edge);
			if(peso>pesoMax) {
				pesoMax = peso;
			}
		}
		
		for(DefaultWeightedEdge edge : grafo.edgeSet()) {
			double peso = grafo.getEdgeWeight(edge);
			if(peso == pesoMax) {
				s+= grafo.getEdgeSource(edge) + " - " + grafo.getEdgeTarget(edge) + "; Peso: " + peso + "\n";
			}
		}
		return s;
	}
	
	public List<Integer> getAnni(){
		return dao.anni();
	}
	
	public List<String> getCategories() {
		return dao.categorie();
	}
}
