package controller;

import java.sql.*;

import application.DatabaseManager;
import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginViewController {
	@FXML
	private TextField emailTextfield;
	@FXML
	private TextField passwordTextfield;
	
	DatabaseManager database = DatabaseManager.getInstance();
	
	private MainApp mainApp;
	
	@FXML
	private void login() {
		String email = emailTextfield.getText();
		String senha = passwordTextfield.getText();
		
		String query = "SELECT login(" + email + ", " + senha + ") FROM dual";

		ResultSet res = database.select(query);
		
		if(res != null) {
			MainViewController.logged = true;
			mainApp.showDashboardView();
		}
		
		System.out.println(MainViewController.logged);
	}	

}
