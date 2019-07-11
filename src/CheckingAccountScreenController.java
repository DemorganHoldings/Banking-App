import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;

public class CheckingAccountScreenController {

    @FXML
    private Button buttonNewTransaction;

    @FXML
    private Button buttonLogoutCheckingAccount;

    @FXML
    private Label labelTransactionDescription;

    @FXML
    private Button buttonCreditCardCheckingAccount;

    @FXML
    private Label labelAccountBalance;

    @FXML
    private Button buttonCheckingAccountCheckingAccount;

    @FXML
    private Button buttonReportsCheckingAccount;

    @FXML
    private Label labelTransactionDateTime;

    @FXML
    private Label labelTransactionType;

    @FXML
    private Button buttonUserAccountCheckingAccount;

    @FXML
    private Label labelAccountNumber;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Label labelTransactionAmount;

    private String customerID, accountNum;

    private final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    private final String USERNAME = "root";
    private final String PASSWORD = "password123";

    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");


    // method for initializing the window
    public void initialize(String id) {
        customerID = id;

        DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT Name from Customer WHERE CustomerID='" + customerID + "'";

            ResultSet rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                labelCustomerName.setText(rs.getString("Name"));
            }

            sql = "SELECT * from CheckingAccount WHERE CustomerID='" + customerID + "'";

            rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                labelAccountNumber.setText(rs.getString("AccountNumber"));
                accountNum = rs.getString("AccountNumber");
                double bal = Double.parseDouble(rs.getString("Balance"));
                labelAccountBalance.setText(moneyFormat.format(bal));
            }
            conn.close();
            CheckingAccount account = new CheckingAccount(customerID, accountNum);

            displayTransactions(account);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void displayTransactions(CheckingAccount account) {
        account.getTransactions();

        for(int i = 0; i < account.transactions.size(); i++){
            labelTransactionDateTime.setText(labelTransactionDateTime.getText() + "\n" + account.transactions.get(i).getTransactionDateTime());
            labelTransactionType.setText(labelTransactionType.getText() + "\n" + account.transactions.get(i).getTransactionType());
            labelTransactionDescription.setText(labelTransactionDescription.getText() + "\n" + account.transactions.get(i).getTransactionDescription());
            labelTransactionAmount.setText(labelTransactionAmount.getText() + "\n" + moneyFormat.format(account.transactions.get(i).getTransactionAmount()));
        }
    }

    public void newTransaction(ActionEvent e) {
        try {
            // the FXML loader object to load the UI design
            FXMLLoader loader = new FXMLLoader();
            // specify the file location
            loader.setLocation(getClass().getResource("NewTransactionScreen.fxml"));

            // the object representing the root node of the scene
            Parent parent;
            // try-catch for possible errors in reading the FXML file.
            try {
                // load the UI
                parent = loader.load();

                // set the scene
                Scene scene = new Scene(parent);

                NewTransactionScreenController controller = loader.getController();
                controller.initialize(customerID, accountNum);

                // get the current window; i.e. the stage
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                // set the scene for the stage
                stage.setScene(scene);
                // show the stage
                stage.show();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }




    /**
     * Menu Button Items
     */

    public void userAccountButton(ActionEvent e) {
        try {
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
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                // set the scene for the stage
                stage.setScene(scene);
                // show the stage
                stage.show();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void checkingAccountButton(ActionEvent e, String id) {
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

            CheckingAccountScreenController controller = loader.getController();
            controller.initialize(id);

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

    public void creditCard(ActionEvent e){
        CreditCardScreenController controller = new CreditCardScreenController();
        controller.creditCardButton(e, customerID);
    }

    public void report(ActionEvent e){
        ReportsScreenController controller = new ReportsScreenController();
        controller.reportsButton(e, customerID);
    }

    public void logOut(ActionEvent e){
        // the FXML loader object to load the UI design
        FXMLLoader loader = new FXMLLoader();
        // specify the file location
        loader.setLocation(getClass().getResource("Login.fxml"));

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

}