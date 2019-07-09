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

    public void createAccount(ActionEvent e){
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

            // the FXML loader object to load the UI design
            FXMLLoader loader = new FXMLLoader();
            // specify the file location
            loader.setLocation(getClass().getResource("CheckingAccountScreen.fxml"));

            // the object representing the root node of the scene
            Parent parent;
            // try-catch for possible errors in reading the FXML file.
            try {
                // load the UI
                parent = loader.load();

                // set the scene
                Scene scene = new Scene(parent);

//                CheckingAccountScreenController controller = loader.getController();
//                controller.initData(account.getCustomerId());

                // get the current window; i.e. the stage
                Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                // set the scene for the stage
                stage.setScene(scene);
                // show the stage
                stage.show();
            } catch (IOException e1) {
                System.out.print(e1.getMessage());
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}