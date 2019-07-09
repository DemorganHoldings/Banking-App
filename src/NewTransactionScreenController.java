import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.text.DecimalFormat;

public class NewTransactionScreenController {

    @FXML
    private Button buttonLogoutCheckingAccount;

    @FXML
    private TextField textboxTransferToAccountNumber;

    @FXML
    private Button buttonCreditCardCheckingAccount;

    @FXML
    private Label labelAccountBalance;

    @FXML
    private RadioButton radiobuttonWithdraw;

    @FXML
    private RadioButton radiobuttonTransfer;

    @FXML
    private Button buttonCheckingAccountCheckingAccount;

    @FXML
    private Button buttonReportsCheckingAccount;

    @FXML
    private Button buttonCancelTransaction;

    @FXML
    private RadioButton radiobuttonDeposit;

    @FXML
    private ToggleGroup groupTransactionType;

    @FXML
    private Button buttonSaveTransaction;

    @FXML
    private Button buttonUserAccountCheckingAccount;

    @FXML
    private Label labelAccountNumber;

    @FXML
    private Label labelCustomerName;

    @FXML
    private TextField textboxTransactionAmount;

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

}
