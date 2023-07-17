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

