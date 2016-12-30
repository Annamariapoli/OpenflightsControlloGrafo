package it.polito.tdp.flight.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private FlightDAO dao = new FlightDAO();
	private DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo = null;
	
	public List<Airline> getAllComp(){                     //tutte le airline
		List<Airline> com = dao.getAllCompagnie();
		return com;
	}
	
	public List<Airport> getAllAereo(){                    //tutti gli airports
		List<Airport> aerei = dao.getAllAirports();
		return aerei;
	}
	
	public List<Route> getRotteCompa(int codC){                          //tutte le rotte di quella compagnia
		List<Route> rotte= dao.tutteLeRotteDiQuellaCompagnia(codC);
		return rotte;
	}
	
	public boolean getEsisteRotta(Airport id1, Airport id2,  int codiceComp){
		List<Route> rotte = getRotteCompa(codiceComp);                           //lista di rotte di quella compagnia
		for(Route r : rotte){
			if(r.getDestinationAirportId()==id1.getAirportId() && r.getSourceAirportId()==id2.getAirportId() ||
					r.getDestinationAirportId()==id2.getAirportId() && r.getSourceAirportId()==id1.getAirportId()){
				return true;
			} 
		}
		return false;
	}
	
	public void buildGraph(int codiceComp){
		grafo = new DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Airport> aerei = getAllAereo();
		Graphs.addAllVertices(grafo, aerei);           //tutti i vertici sono gli aereoporti
		for(Airport a1 : grafo.vertexSet()){           //per ogni coppia di aereoporti
			for(Airport a2 : grafo.vertexSet()){
				if(getEsisteRotta(a1, a2, codiceComp)){   //se esiste almeno una rotta
					double distanza =LatLngTool.distance(a1.getCoords(), a2.getCoords(),LengthUnit.KILOMETER);   
					Graphs.addEdge(grafo, a1, a2, distanza);
				}
			}
		}
		System.out.println(grafo.toString());
	}
	
	
	public static void main(String [] args){
		Model m = new Model();
		m.buildGraph(8359);
	}

}
