
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.*;

public class LoginController {

    @FXML
    private Label labelLoginErrorMessage;

    @FXML
    private Button buttonLogIn;

    @FXML
    private TextField textboxUsername;

    @FXML
    private TextField textboxPassword;

    public void loginButtonListener(){
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
