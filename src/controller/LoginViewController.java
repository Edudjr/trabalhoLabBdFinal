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

		String query = "select * from pessoa "
				+ "where email='"+email+"'";

		ResultSet res = database.select(query);

		try {
			if(res.next()) {

				if (res.getString("password_hash").equals(senha)){
					MainViewController.logged = true;
					mainApp.showMenu();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void setMainApp(MainApp app){
		this.mainApp = app;
	}

}
