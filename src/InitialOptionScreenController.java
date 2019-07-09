import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class InitialOptionScreenController {

    @FXML
    private TextField textboxNewCustomerSocial;

    @FXML
    private Button buttonCreateNewCustomerAccount;

    @FXML
    private Button buttonSearchExistingCustomer;

    @FXML
    private TextField textboxExistingSocialSecurity;

    @FXML
    private TextField textboxNewCustomerAddress;

    @FXML
    private TextField textboxNewCustomerName;

    @FXML
    private TextField textboxExistingCustomerName;

    @FXML
    private Button buttonLogoutInitialOptionScreen;

    @FXML
    private TextField textboxNewCustomerPhoneNum;

    @FXML
    private TextField textboxNewCustomerEmailAddress;

    @FXML
    void e91e63(ActionEvent event) {

    }

    public void createAccount(){
        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";
        String name, address, phone, email, social;

        try {
            name = textboxNewCustomerName.getText();
            address = textboxNewCustomerAddress.getText();
            phone = textboxNewCustomerPhoneNum.getText();
            email = textboxNewCustomerEmailAddress.getText();
            social = textboxNewCustomerSocial.getText();
            Customer customer = new Customer(name, address, social, phone, email);

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "INSERT INTO Customer " +
                    "(CustomerID, Name, Address, Social, PhoneNumber, Email) " +
                    "VALUES ('" +
                    customer.getCustomerId() + "', '" +
                    customer.getCustomerName() + "', '" +
                    customer.getCustomerAddress() + "', '" +
                    customer.getCustomerSocial() + "', '" +
                    customer.getCustomerPhoneNum() + "', '" +
                    customer.getCustomerEmailAddress() + "')";

            stmt.executeUpdate(sql);
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}