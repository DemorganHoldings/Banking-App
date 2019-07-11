import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sun.rmi.runtime.Log;

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
    private Label warningLabel;

    @FXML
    private TextField textboxExistingSocialSecurity;

    @FXML
    private TextField textboxNewCustomerAddress;

    @FXML
    private TextField textboxNewCustomerName;

    @FXML
    private Button buttonManageTellers;

    @FXML
    private TextField textboxExistingCustomerName;

    @FXML
    private Button buttonLogoutInitialOptionScreen;

    @FXML
    private TextField textboxNewCustomerPhoneNum;

    @FXML
    private TextField textboxNewCustomerEmailAddress;

    private final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    private final String USERNAME = "root";
    private final String PASSWORD = "password123";

    /**
     * Create new account method and listener for create new button
     * Create new customer accounts and inserts into database
     * Also creates new checking accounts and inserts into database
     * @param e action event object
     */
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

    /**
     * Search account button listener
     * Searches database for existing customers and pulls up their checking account information
     * @param e action event object
     */
    public void searchAccount(ActionEvent e) {
        String name, social;

        Customer customer;
        try {
            name = textboxExistingCustomerName.getText();
            social = textboxExistingSocialSecurity.getText();

            if (name.isEmpty() || social.isEmpty()) {
                warningLabel.setText("Check Name or Social");
            }
            else {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
                Statement stmt = conn.createStatement(); //Create new statement object

                String sql = "SELECT * FROM Customer WHERE Name = '" + name + "' && Social = '" + social + "'";
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()) {
                    customer = new Customer(rs.getString("Name"), rs.getString("Address"), rs.getString("Social"), rs.getString("PhoneNumber"), rs.getString("Email"));
                    customer.setCustomerId(rs.getString("CustomerID"));

                    CheckingAccountScreenController controller = new CheckingAccountScreenController();
                    controller.checkingAccountButton(e, customer.getCustomerId());
                } else {
                    warningLabel.setText("Login Error, Check Name or Social");
                }

                conn.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Manage bank tellers button listener
     * @param e action event object
     */
    public void manageTellers(ActionEvent e){
        // the FXML loader object to load the UI design
        FXMLLoader loader = new FXMLLoader();
        // specify the file location
        loader.setLocation(getClass().getResource("ManageStaffScreen.fxml"));

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

    /**
     * Logout button listener
     * @param e action event object
     */
    public void logOut(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }
}