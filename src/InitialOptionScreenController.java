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

    private final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    private final String USERNAME = "root";
    private final String PASSWORD = "password123";

    public void createAccount(ActionEvent e){
        String name, address, phone, email, social;

        try {
            name = textboxNewCustomerName.getText();
            address = textboxNewCustomerAddress.getText();
            phone = textboxNewCustomerPhoneNum.getText();
            email = textboxNewCustomerEmailAddress.getText();
            social = textboxNewCustomerSocial.getText();
            Customer customer = new Customer(name, address, social, phone, email);
            customer.generateCustomerId();

            CheckingAccount account = new CheckingAccount(customer.getCustomerId());

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

            sql = "INSERT INTO CheckingAccount " +
                    "(CustomerID, AccountNumber, Balance) " +
                    "VALUES ('" +
                    account.getCustomerId() + "', '" +
                    account.getAccountNumber() + "', '" +
                    account.getAccountBalance() + "')";

            stmt.executeUpdate(sql);
            conn.close();

            CheckingAccountScreenController controller = new CheckingAccountScreenController();
            controller.checkingAccountButton(e, account.getCustomerId());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void searchAccount(ActionEvent e) {
        String name, social;

        Customer customer;
        try {
            name = textboxExistingCustomerName.getText();
            social = textboxExistingSocialSecurity.getText();

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT CustomerID, Name, Address, Social, PhoneNumber, Email FROM Customer WHERE Name = '" + name + "' && Social = '" + social + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();

            customer = new Customer(rs.getString("Name"), rs.getString("Address"), rs.getString("Social"), rs.getString("PhoneNumber"), rs.getString("Email"));
            customer.setCustomerId(rs.getString("CustomerID"));

            CheckingAccountScreenController controller = new CheckingAccountScreenController();
            controller.checkingAccountButton(e, customer.getCustomerId());

            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void logOut(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }
}