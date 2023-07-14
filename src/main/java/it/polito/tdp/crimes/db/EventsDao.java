package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> categorie() {
		
		String sql = "SELECT distinct offense_category_id "
				+ "FROM events";
		List<String> categories = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				categories.add(rs.getString("offense_category_id"));
			}
			conn.close();
			return categories;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> anni() {
		
		String sql = "SELECT DISTINCT YEAR(e.reported_date) AS y "
				+ "FROM EVENTS e "
				+ "ORDER BY y";
		List<Integer> anni = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				anni.add(rs.getInt("y"));
			}
			conn.close();
			return anni;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> reato(int anno, String categoria) {
		
		String sql = "SELECT distinct offense_type_id "
				+ "FROM EVENTS "
				+ "WHERE offense_category_id = ? AND YEAR(reported_date) = ?";
		List<String> reati = new LinkedList<String>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, categoria);
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				reati.add(rs.getString("offense_type_id"));
			}
			conn.close();
			return reati;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public double getPeso(int anno, String s, String s1) {
		 
		String sql = "SELECT COUNT(DISTINCT e1.district_id) as n "
				+ "FROM EVENTS e1, EVENTS e2 "
				+ "WHERE e1.offense_type_id = ? AND e2.offense_type_id = ? "
				+ "AND e1.district_id = e2.district_id AND YEAR(e1.reported_date) = ? AND YEAR(e2.reported_date) = ?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s);
			st.setString(2, s1);
			st.setInt(3, anno);
			st.setInt(4, anno);
			ResultSet rs = st.executeQuery();
			rs.first();
			conn.close();
			return rs.getInt("n");
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

}
