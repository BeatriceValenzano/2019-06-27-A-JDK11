try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
			}
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
SimpleWeightedGraph --> grafo pesato semplice non orientato = new SimpleWeightedGraph<Vertice, DefaultWeightedEdge>(DefaultWeightedEdge.class)
SimpleDirectedWeightedGraph --> grafo pesato orientato semplice


public void creaGrafo() {

}

//COMPONENTE CONNESSA

public boolean esistePercorso(Airport origin, Airport destination) {
		
//		PERMETTE DI OTTENERE VARIE CONNESSIONI DI UN GRAFO
		ConnectivityInspector<Airport, DefaultWeightedEdge> inspect = new ConnectivityInspector<Airport, DefaultWeightedEdge>(this.grafo);

//		RITORNA UN INSIEME DI AEROPORTI CHE SONO CONNESSI ALL'ORIGINI		
		Set<Airport> componenteConnessaOrigine = inspect.connectedSetOf(origin);
		
//		VEDE SE TRA GLI AEROPORTI CONNESSI C'E' QUELLO DI DESTINAZIONE
		return componenteConnessaOrigine.contains(destination);
	}

//percorso tra origine e destinazione

public List<Airport> trovaPercorso(Airport origin, Airport destination) {
		
		BreadthFirstIterator<Airport, DefaultWeightedEdge> visita = new BreadthFirstIterator<>(this.grafo, origin);
		
		List<Airport> raggiungibili = new LinkedList<Airport>();
		List<Airport> percorso = new LinkedList<Airport>();
		
//		MI TROVA LA COMPONENTE CONNESSA
//		while(visita.hasNext()) {
//			Airport a = visita.next();
//			percorso.add(a);
//		}
//		return percorso;
		while(visita.hasNext()) {
			Airport a = visita.next();
			raggiungibili.add(a);
		}
		Airport corrente = destination;
		percorso.add(corrente);
		DefaultWeightedEdge e = visita.getSpanningTreeEdge(corrente);
		while (e != null) {
			Airport precedenteAlCorrente = Graphs.getOppositeVertex(this.grafo, e, corrente);
			percorso.add(0, precedenteAlCorrente);
			corrente = precedenteAlCorrente;
			e = visita.getSpanningTreeEdge(corrente);
		}
		
		return percorso;
	}
	
public List<Airport> trovaPercorso2(Airport origin, Airport destination){
		List<Airport> percorso = new ArrayList<>();
	 	BreadthFirstIterator<Airport,DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo, origin);
	 	Boolean trovato = false;  //per vedere se ha trovato l'aeroporto di destinazione
	 	
//visito il grafo fino alla fine o fino a che non trovo la destinazione
	 	while(it.hasNext() & !trovato) { 
	 	//serve per visitare il grafo in quanto non posso usare il get se prima non ho visitato tutti i vertici
	 	Airport visitato = it.next();
	 	if(visitato.equals(destination))  //se quello che visito è uguale alla destinazione allora lo metto true
	 	trovato = true;
	 	}
	 
	 
/* se ho trovato la destinazione, costruisco il percorso risalendo l'albero di visita in senso
	 	 * opposto, ovvero partendo dalla destinazione fino ad arrivare all'origine, ed aggiungo gli aeroporti
	 	 * ad ogni step IN TESTA alla lista
	 	 * se non ho trovato la destinazione, restituisco null.
	 	 */
	 	if(trovato) {
	 		percorso.add(destination);  //aggiunge la destinazione
	 		Airport step = it.getParent(destination);  //trova il vertice opposto alla destinazione
	 		while (!step.equals(origin)) {  //finchè il vertice precendente non è uguale all'origine 
	 			percorso.add(0,step);  //aggiunge in testa alla lista percorso il vertice step
	 			step = it.getParent(step);  //prende il precedente di step
	 		}
	 		
		 percorso.add(0,origin); //aggiunge l'origine
		 return percorso;
	 	} else {
	 		return null;
	 	}
	}
