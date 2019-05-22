package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.db.PortoDAO;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	private Model model; 
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	this.txtResult.clear();
    for (Author a: this.model.trovaVicini(this.boxPrimo.getValue())) {
    	this.txtResult.appendText(a.toString()+"\n");
    }
    this.boxSecondo.getItems().addAll(this.model.nonVicini(this.boxPrimo.getValue()));
    }

    @FXML
    void handleSequenza(ActionEvent event) {
   Author partenza = this.boxPrimo.getValue();
   Author arrivo = this.boxSecondo.getValue();
   
   for (Paper p: this.model.listPaper(partenza, arrivo)){
	   this.txtResult.appendText(p.toString()+"\n");
   }
   
    }
    
    public void setModel(Model model) {
    	this.model=model; 
    	this.model.creaGrafo();
       this.boxPrimo.getItems().addAll(this.model.getIdMapAutori().values());
       
       
      
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
}
