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
        PrimaryStage.setTitle("SP Demorgan Financial - Login");
        //show the stage
        PrimaryStage.show();
    }

    /*public void getDbConnection() {
        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }*/

}
