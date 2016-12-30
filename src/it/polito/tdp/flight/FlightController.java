package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	
	private Model m = new Model();
	DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo=null;
	
	public void setModel(Model m ){
		this.m=m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;
    
    @FXML
    private Button btnRagg;

   

    @FXML
    void doRaggiungibili(ActionEvent event) {
    	Airport raggiungibil = boxAirport.getValue();   //uno degli aereoporti raggiunti da quella compagnia
    	if(raggiungibil==null){
    		txtResult.appendText("seleziona un aereoporto raggiungibile\n");
    		return;
    	}
    	

    }

    @FXML
    void doServiti(ActionEvent event) {
    	txtResult.clear();
    	Airline a = boxAirline.getValue();
    	if(a==null){
    		txtResult.appendText("Seleziona una compagnia aerea!\n");
    		return;
    	}
        grafo = m.buildGraph(a.getAirlineId());                         //costruisco  Grafo
    	List<Airport > raggiungibili = m.getRaggiungibili(grafo);      //aereoporti raggiungibili da quella compagnia (Punto 1)
    	for(Airport a1 : raggiungibili){
    	     txtResult.appendText(a1+" \n");
    	}

    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";
        
        btnRagg.setDisable(true);

        boxAirline.getItems().clear();
        boxAirline.getItems().addAll(m.getAllComp());
        boxAirport.getItems().addAll(m.getRaggiungibili(grafo));
    }
}
