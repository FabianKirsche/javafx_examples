package examples.javafx.fxml.basics;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	public Button btn_demo;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }
    
    public void btn_demo_Click() {
    	System.out.println("Button pressed");
    	Random rand = new Random();
    	Integer randInt = 0;
    	randInt = rand.nextInt(100);
    	btn_demo.setText(randInt.toString());
    }

}