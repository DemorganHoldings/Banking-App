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
                    goToInitialOptionScreen(e);
                }
                else {
                    labelLoginErrorMessage.setText("Login Error, Check username or password");
                }

                conn.close();
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void goToInitialOptionScreen(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.userAccountButton(e);
    }
}
