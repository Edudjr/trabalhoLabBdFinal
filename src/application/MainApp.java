package application;

import java.io.IOException;

import java.util.ArrayList;

import controller.PersonViewController;
import controller.RootViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Person;


public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
    private DatabaseManager database;
    
    //Retrieve data
    private ObservableList<Person> personData = FXCollections.observableArrayList();
	
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
			
			// Give the controller access to the main app
		    RootViewController controller = loader.getController();
		    controller.setMainApp(this);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		database = DatabaseManager.getInstance();
		database.openConnection();
		
		//Show each screen individually
		//showMainView();
		
		//showCsmView();
		
		//showCcsView();
		
		//showSmView();
		
		//showPscView();
		
		//showTpView();
		
		//showSysView();
		
	}
	
	//Init lists
	public void initMainList(){
		ArrayList<Person> people = new ArrayList<Person>();
		people = database.getPeopleArray(20);
		
		for (Person person : people){
			personData.add(person);
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
			initMainList();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showDashboardView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/DashboardView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
		
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

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showPscView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/ProductSalesCategoryResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showTpView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/TaxesPaidResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showSysView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/SalesYearStateResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void showTrView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/TransportResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showSyView(){
		try{
			FXMLLoader loader = new FXMLLoader
					(MainApp.class.getResource("/view/SalesYearResumeView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
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

}
