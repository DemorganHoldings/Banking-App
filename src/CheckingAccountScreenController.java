import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import java.sql.*;

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

    public void initData(String id){
        customerID = id;
    }
    // method for initializing the window
    public void initialize() {
        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT Name from Customer WHERE CustomerID='" + customerID + "'";

            ResultSet rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()){
                labelCustomerName.setText(rs.getString("Name"));
            }

            conn.close();

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
