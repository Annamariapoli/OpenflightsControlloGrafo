package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Route;

public class FlightDAO {

	public List<Airport> getAllAirports() {		
		String sql = "SELECT * FROM airport" ;
		List<Airport> list = new ArrayList<>() ;	
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;			
			ResultSet res = st.executeQuery() ;	
			while(res.next()) {
				list.add( new Airport(
						res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						new LatLng (res.getDouble("Latitude"), res.getDouble("Longitude")),					
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz"))) ;
			}			
			conn.close();		
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	public List<Airline> getAllCompagnie(){
     String sql = "SELECT * FROM airline" ;
     Connection conn = DBConnect.getConnection();
	 List<Airline> list = new ArrayList<>() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;			
			while(res.next()) {
				Airline a =new Airline(
						res.getInt("Airline_ID"),
						res.getString("name"),
						res.getString("alias"),
						res.getString("IATA"),
						res.getString("ICAO"),
						res.getString("Callsign"),
						res.getString("Country"),
						res.getString("Active")) ;
				list.add(a);
			}			
			conn.close();
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}


	public boolean getEsisteArco(int codiceComp, int aereoId1, int aereoId2) {
		String sql ="select * from airport a1, airport a2, route r "
				+ "where r.Airline_ID=? and a1.Airport_ID=? and a2.Airport_ID=?"
				+ "and a1.Airport_ID<>a2.Airport_ID "
				+ "and (r.Destination_airport_ID=a1.Airport_ID and r.Source_airport_ID=a2.Airport_ID) OR"
				+ "(r.Destination_airport_ID=a2.Airport_ID and r.Source_airport_ID=a1.Airport_ID)" ;
	    Connection conn = DBConnect.getConnection();
	    try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, codiceComp);
			st.setInt(1, aereoId1);
			st.setInt(1, aereoId2);
			ResultSet res = st.executeQuery() ;			
			if(res.next()) {
				return true;
			  }	else {
				return false;
			   }
		} catch (SQLException e) {
			e.printStackTrace();
			return false ;
		}
	}

	
	public List<Route> tutteLeRotteDiQuellaCompagnia(int codiceComp){
		String query = "select  * from route r where r.Airline_ID=?";
		Connection conn = DBConnect.getConnection();
		 List<Route> list = new ArrayList<>() ;
			try {
				PreparedStatement st = conn.prepareStatement(query) ;
				st.setInt(1, codiceComp);
				ResultSet res = st.executeQuery() ;			
				while(res.next()) {
					Route a =new Route(
							res.getString("Airline"),
							res.getInt("Airline_ID"),
							res.getString("Source_airport"),
							res.getInt("Source_airport_ID"),
							res.getString("Destination_airport"),
							res.getInt("Destination_airport_ID"),
							res.getString("Codeshare"),
							res.getInt("stops"),
							res.getString("Equipment"));
					list.add(a);
				}			
				conn.close();
				return list ;
			} catch (SQLException e) {
				e.printStackTrace();
				return null ;
			}
	}
	
}
