/**
 * Authors: Prabjot Singh and Satinder Dhaliwal
 * Purpose: A banking application for a bank to use for customer operations
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class BankingApp extends Application {

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage PrimaryStage) throws Exception{
        //load the fxml file
        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));

        //set the scene
        Scene scene = new Scene(parent);

        //set the scene for the stage
        PrimaryStage.setScene(scene);
        //set the title for the stage
        PrimaryStage.setTitle("SP & Demorgan Financial");
        //show the stage
        PrimaryStage.show();
    }
}
