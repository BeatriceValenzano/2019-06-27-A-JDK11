/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<String> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	this.boxArco.getItems().clear();
    	String categoria = this.boxCategoria.getValue();
    	Integer anno = this.boxAnno.getValue();
    	if(categoria == null || anno == null) {
    		txtResult.appendText("Selezionare una categoria e un anno!");
    		return;
    	}
    	model.creaGrafo(anno, categoria);
    	for(String s : model.getArchiMax()) {
    		txtResult.appendText(s + "\n");
    		String[] parts = s.split(" - ");
    		this.boxArco.getItems().add(parts[0] + " <-> " + parts[1]);
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	String arco = this.boxArco.getValue();
    	if(arco == null) {
    		txtResult.appendText("Selezionare un arco!\n");
    	} else {
    		String[] parts = arco.split(" <-> ");
    		txtResult.appendText("Il percorso migliore tra " + parts[0] + " e " + parts[1] + " è:\n");
    		for(String s : model.percorsoMigliore(parts[0], parts[1])) {
    			txtResult.appendText(s + "\n");
    		}
    		
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    
    	this.boxCategoria.getItems().addAll(model.getCategories());
    	this.boxAnno.getItems().addAll(model.getAnni());
    }
}
