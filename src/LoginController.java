import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private Label labelLoginErrorMessage;

    @FXML
    private Button buttonLogIn;

    @FXML
    private TextField textboxUsername;

    @FXML
    private TextField textboxPassword;

    public void loginButtonListener(ActionEvent e){
        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";

        String username, password;
        try {
            labelLoginErrorMessage.setText("");
            username = textboxUsername.getText();
            password = textboxPassword.getText();
            if (username.isEmpty() || password.isEmpty()) {
                labelLoginErrorMessage.setText("Check username or password");
            }
            else {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
                Statement stmt = conn.createStatement(); //Create new statement object
                String sql = "SELECT * from BankStaff WHERE UserName='" + textboxUsername.getText() + "' and Password ='" + textboxPassword.getText() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    labelLoginErrorMessage.setText("Login Successful");


                    // the FXML loader object to load the UI design
                    FXMLLoader loader = new FXMLLoader();
                    // specify the file location
                    loader.setLocation(getClass().getResource("InitialOptionScreen.fxml"));

                    // the object representing the root node of the scene
                    Parent parent;
                    // try-catch for possible errors in reading the FXML file.
                    try {
                        // load the UI
                        parent = loader.load();

                        // set the scene
                        Scene scene = new Scene(parent);

                        // get the current window; i.e. the stage
                        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                        // set the scene for the stage
                        stage.setScene(scene);
                        // show the stage
                        stage.show();
                    } catch (IOException e1) {
                        System.out.print(e1.getMessage());
                    }
                }
                else {
                    labelLoginErrorMessage.setText("Login Error, Check username or password");
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

/*
    public void loginAccountListener(ActionEvent e) throws Exception{
        String username, password;

        username = textboxUsername.getText();
        password = textboxPassword.getText();


        if(username.equals("root") && password.equals("")){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("InitialOptionScreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        }
    } */

}
