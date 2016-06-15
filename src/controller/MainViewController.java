package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainViewController implements Initializable {

    @FXML //  fx:id="myButton"
    private Label statusLabel; // Value injected by FXMLLoader


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'simple.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }
}
