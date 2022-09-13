/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.AlbumBilancio;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	txtResult.clear();
    	//int n=this.txtN.getText();  
    	if(!model.esisteGrafo()) {
    		txtResult.clear();
    		txtResult.appendText("CREA PRIMA GRAFO\n");
    		return;
    	}
    	if(cmbA1.getValue()==null) {
    		txtResult.clear();
    		txtResult.appendText("SCEGLI PRIMA ALBUM\n");
    		return;
    	}
    	
    		for(AlbumBilancio ab: model.getBilancio(cmbA1.getValue())) {
    			txtResult.appendText(ab+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	double x; 
     	if(!model.esisteGrafo()) {
    		txtResult.clear();
    		txtResult.appendText("CREA PRIMA GRAFO\n");
    		return;
    	}
    	if(cmbA1.getValue()==null) {
    		txtResult.clear();
    		txtResult.appendText("SCEGLI PRIMO ALBUM\n");
    		return;
    	}
    	if(cmbA2.getValue()==null) {
    		txtResult.clear();
    		txtResult.appendText("SCEGLI SECONDO ALBUM\n");
    		return;
    	}
    	if(cmbA1.getValue().equals(cmbA2.getValue())) {
    		txtResult.clear();
    		txtResult.appendText("SLEZIONARE DUE ALBUM DIVERSI\n");
    		return;
    	}
    	if(txtX.getText()=="") {
    		txtResult.clear();
    		txtResult.appendText("SCEGLI PRIMA SOGLIA\n");
    		return;
    	}
    	
    	
    	Album a1= this.cmbA1.getValue();
    	Album a2= this.cmbA2.getValue();
    	if(a1!= null && a2!= null && a1!=a2) {
    		try {
    	    	x=Double.parseDouble(this.txtX.getText());
    	    		if(model.sonoConnessi(a1, a2)) {
    	    				for(Album a:model.calcolaPercorso(a1, a2, x)) {
    	    						txtResult.appendText(a+"\n");
    	    				}
    	    		}
		else {
			txtResult.appendText("VERTICI NON COLLEGATI");
		}
    
    	}catch(NumberFormatException e) {
			txtResult.clear();
    		txtResult.appendText("INSERISCI SOGLIA VALIDA\n");
    		return; 
    	}
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	if(txtN.getText()=="") {
    		txtResult.clear();
    		txtResult.appendText("SCEGLI PRIMA NUMERO\n");
    		return;
    	}
    	double n;
    	try {
    		n= Double.parseDouble(this.txtN.getText());
    		txtResult.appendText(model.creaGrafo(n));
    		this.cmbA1.getItems().clear();
    		this.cmbA2.getItems().clear();
    		this.cmbA1.getItems().addAll(model.getVertici());
    		this.cmbA2.getItems().addAll(model.getVertici());
    	}catch(NumberFormatException e) {
    		txtResult.clear();
    		txtResult.appendText("INSERISCI NUMERO VALIDO\n");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
