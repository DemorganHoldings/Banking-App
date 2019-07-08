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
        //show the stage
        PrimaryStage.show();
    }

}
