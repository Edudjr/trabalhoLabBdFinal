package application;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Vector;

import controller.CustomerCountryStateViewController;
import controller.CustomerShoppingViewController;
import controller.PersonViewController;
import controller.SalesMonthViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Person;
import model.SalesMonthModel;
import model.CustomerCountryStateModel;
import model.CustomerShoppingModel;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
    private DatabaseManager database;
    
    //Retrieve data
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private ObservableList<CustomerShoppingModel> csmData = FXCollections.observableArrayList();
    private ObservableList<CustomerCountryStateModel> ccsData = FXCollections.observableArrayList();
    private ObservableList<SalesMonthModel> smData = FXCollections.observableArrayList();
    
	
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
		
		database = new DatabaseManager();
		database.openConnection();
		
		//Show each screen individually
		//showMainView();
		//initMainList();
		
		//showCsmView();
		//initCsmList(null, null);
		
		//showCcsView();
		//initCcsList();
		
		showSmView();
		initSmList(null);
	}
	
	//Init lists
	public void initMainList(){
		ArrayList<Person> people = new ArrayList<Person>();
		people = database.getPeopleArray(20);
		
		for (Person person : people){
			personData.add(person);
		}
	}
	
	public void initCsmList(String name, String id){
		ArrayList<CustomerShoppingModel> array = new ArrayList<CustomerShoppingModel>();
		array = database.getCsmArray(name, id);
		
		csmData.clear();
		for (CustomerShoppingModel csm : array){
			csmData.add(csm);
		}
	}
	
	public void initCcsList(){
		ArrayList<CustomerCountryStateModel> array = new ArrayList<CustomerCountryStateModel>();
		array = database.getCcsArray();
		
		ccsData.clear();
		for (CustomerCountryStateModel item : array){
			ccsData.add(item);
		}
	}
	
	public void initSmList(String month){
		ArrayList<SalesMonthModel> array = new ArrayList<SalesMonthModel>();
		array = database.getSmArray(month); //passar mes
		
		smData.clear();
		for (SalesMonthModel item : array){
			smData.add(item);
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
	
	public void showCcsView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/CustomerCountryStateResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
			//Give the controller access to the main app
			CustomerCountryStateViewController controller = loader.getController();
			controller.setMainApp(this);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showSmView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/SalesMonthResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
			//Give the controller access to the main app
			SalesMonthViewController controller = loader.getController();
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
	
	//Return observable lists
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    public ObservableList<CustomerShoppingModel> getCsmData(){
    	return csmData;
    }
    public ObservableList<CustomerCountryStateModel> getCcsData(){
    	return ccsData;
    }
    public ObservableList<SalesMonthModel> getSmData(){
    	return smData;
    }
    
    //Filter
    public void setCsmData(String name, String id){
    	this.initCsmList(name, id);
    }

	public void setSmData(String month) {
		this.initSmList(month);
	}
}
