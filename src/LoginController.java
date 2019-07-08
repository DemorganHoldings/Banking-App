
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class LoginController {

    @FXML
    private Button buttonLogIn;

    @FXML
    private TextField textboxUsername;

    @FXML
    private TextField textboxPassword;

    /**
    public void loginAccount(ActionEvent e) throws Exception{
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
    }
     */

}
