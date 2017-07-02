package org.krlib.dashboard;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));	//load fxml
		primaryStage.setScene(new Scene(root,1280,720));					//set fxml to scene
		primaryStage.setTitle("LifeBoard");
		primaryStage.show();
	}
}