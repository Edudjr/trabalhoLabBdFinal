package controller;

import application.MainApp;
import javafx.fxml.FXML;

public class RootViewController {

    // Reference to the main application
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
    }

    @FXML
    private void handleAbout() {
        //Dialogs.showInformationDialog(mainApp.getPrimaryStage(), "Author: Marco Jakob\nWebsite: http://code.makery.ch", "AddressApp", "About");
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    //Menu Items click
    @FXML
    private void handleSmClick() {
    	System.out.println("click");
    	mainApp.showSmView();
    }
    
    @FXML
    private void handleCcsClick() {
    	System.out.println("click");
    	mainApp.showCcsView();
    }
    
    @FXML
    private void handleCsmClick() {
    	System.out.println("click");
    	mainApp.showCsmView();
    }
    
}