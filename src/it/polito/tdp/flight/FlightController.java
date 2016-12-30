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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	
	private Model m = new Model();
	
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
    void doRaggiungibili(ActionEvent event) {

    }

    @FXML
    void doServiti(ActionEvent event) {
    	txtResult.clear();
    	Airline a = boxAirline.getValue();
    	if(a==null){
    		txtResult.appendText("Seleziona una compagnia aerea!\n");
    		return;
    	}
    	DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo = m.buildGraph(a.getAirlineId());      //costruisco  Grafo
    	List<Airport > raggiungibili = m.getRaggiungibili(grafo);
    	for(Airport a1 : raggiungibili){
    	     txtResult.appendText(a1+" \n");
    	}

    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

        boxAirline.getItems().clear();
        boxAirline.getItems().addAll(m.getAllComp());
    }
}
