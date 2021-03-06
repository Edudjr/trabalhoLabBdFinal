package controller;

import application.DatabaseManager;
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
    	DatabaseManager.getInstance().closeConnection();
    	MainViewController.logged = false;
        System.exit(0);
    }
    
    //Menu Items click
    @FXML
    private void handleSmClick() {
    	mainApp.showSmView();
    }
    
    @FXML
    private void handleCcsClick() {
    	mainApp.showCcsView();
    }
    
    @FXML
    private void handleCsmClick() {
    	mainApp.showCsmView();
    }
    
    @FXML
    private void handlePscClick() {
    	mainApp.showPscView();
    }
    
    @FXML
    private void handleTpClick() {
    	mainApp.showTpView();
    }
    
    @FXML
    private void handleSysClick() {
    	mainApp.showSysView();
    }

    @FXML
    private void handleTrClick() {
    	mainApp.showTrView();
    }

    @FXML
    private void handleSyClick() {
    	mainApp.showSyView();
    }
    
    @FXML
    private void handleDashboardClick(){
    	mainApp.showDashboardView();
    }
    
}