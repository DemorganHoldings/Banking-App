import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;
import java.text.DecimalFormat;

public class CheckingAccountScreenController {

    @FXML
    private Button buttonNewTransaction;

    @FXML
    private Button buttonLogoutCheckingAccount;

    @FXML
    private TableColumn<?, ?> columnTransactionDescription;

    @FXML
    private TableColumn<?, ?> columnTransactionDate;

    @FXML
    private Button buttonCreditCardCheckingAccount;

    @FXML
    private Label labelAccountBalance;

    @FXML
    private Button buttonUserAccountCheckingAccount;

    @FXML
    private Label labelAccountNumber;

    @FXML
    private TableColumn<?, ?> columnTransactionAmount;

    @FXML
    private Button buttonCheckingAccountCheckingAccount;

    @FXML
    private Button buttonReportsCheckingAccount;

    @FXML
    private Label labelCustomerName;

    @FXML
    private TableColumn<?, ?> columnTransactionType;

    private String customerID;

    // method for initializing the window
    public void initialize(String id) {
        customerID = id;

        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";

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
                double bal = Double.parseDouble(rs.getString("Balance"));
                labelAccountBalance.setText(moneyFormat.format(bal));
            }

            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void displayTransactions(ActionEvent e) {
        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";

        try {

        } catch (Exception ex) {

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
}