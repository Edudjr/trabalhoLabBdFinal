package application;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Vector;

import controller.CustomerShoppingViewController;
import controller.PersonViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Person;
import model.CustomerShoppingModel;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
    private DatabaseManager database;
    
    //Retrieve data
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private ObservableList<CustomerShoppingModel> csmData = FXCollections.observableArrayList();
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		
		try{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/RootView.fxml"));
			this.rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(this.rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		showCsmView();
		database = new DatabaseManager();
		database.openConnection();
		//initMainList();
		initCsmList();
	}
	
	//Init lists
	public void initMainList(){
		ArrayList<Person> people = new ArrayList<Person>();
		people = database.getPeopleArray(20);
		
		for (Person person : people){
			personData.add(person);
		}
	}
	
	public void initCsmList(){
		ArrayList<CustomerShoppingModel> array = new ArrayList<CustomerShoppingModel>();
		array = database.getCsmArray("", "");
		
		for (CustomerShoppingModel csm : array){
			csmData.add(csm);
		}
	}
	
	//Show Views
	public void showMainView(){
		try{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/MainView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
			//Give the controller access to the main app
			PersonViewController controller = loader.getController();
			controller.setMainApp(this);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showCsmView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/CustomerShoppingResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
			//Give the controller access to the main app
			CustomerShoppingViewController controller = loader.getController();
			controller.setMainApp(this);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
	
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    public ObservableList<CustomerShoppingModel> getCsmData(){
    	return csmData;
    }
}
