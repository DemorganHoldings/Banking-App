import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.*;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class ReportsScreenController {

    @FXML
    private Button buttonLogoutCheckingAccount;

    @FXML
    private Label labelStatementMessage;

    @FXML
    private Button buttonCreditCardCheckingAccount;

    @FXML
    private Label labelAccountBalance;

    @FXML
    private Button buttonUserAccountCheckingAccount;

    @FXML
    private TextField textboxFileName;

    @FXML
    private Label labelAccountNumber;

    @FXML
    private Button buttonBrowseForFile;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Button buttonCheckingAccountCheckingAccount;

    @FXML
    private Button buttonReportsCheckingAccount;

    @FXML
    private Button buttonSaveStatement;

    private String filePath;

    private String customerID, accountNum;

    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    public void initialize(String id) {
        customerID = id;

        System.out.println(customerID);

        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";

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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void pathPicker(){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if(selectedDirectory == null){
            textboxFileName.setText("No Directory selected");

        }else{
            filePath = selectedDirectory.getAbsolutePath();
            textboxFileName.setText(selectedDirectory.getAbsolutePath());
            buttonSaveStatement.setVisible(true);
        }
    }

    public void generateStatement(ActionEvent e) {

        CheckingAccount account = new CheckingAccount(customerID, accountNum);

        account.getTransactions();

        try {
            LocalDate currentDate = LocalDate.now(); // Create a date object
            File file = new File(filePath + "/Report.txt");
            FileWriter fw = new FileWriter(file, false);
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println("SP & Demorgan Financial");
            outputFile.println(" ");
            outputFile.println("Checking Account Statement");
            outputFile.println("Generated on " + currentDate + "\n");
            outputFile.println(" ");
            outputFile.println("Account Number\t\tTransaction ID\t\t\tTransaction Type\tTransaction Amount\t" +
                    "Date & Time\t\t\tDescription");
            outputFile.println("----------------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------");
            for (int i = 0; i < account.transactions.size(); i++){
                outputFile.println(account.transactions.get(i).getAccountNumber() + "\t\t" +
                        account.transactions.get(i).getTransactionId() + "\t\t" +
                        account.transactions.get(i).getTransactionType() + "\t\t" +
                        moneyFormat.format(account.transactions.get(i).getTransactionAmount()) + "\t\t\t" +
                        account.transactions.get(i).getTransactionDateTime() + "\t\t" +
                        account.transactions.get(i).getTransactionDescription());
            }

            labelStatementMessage.setText("Report has been generated and saved to " + filePath + "/Report.txt");

            outputFile.close();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void userSearch(ActionEvent e){
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.userAccountButton(e);
    }

    public void checkingAccount(ActionEvent e){
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.checkingAccountButton(e, customerID);
    }

    public void creditCard(ActionEvent e){
        CreditCardScreenController controller = new CreditCardScreenController();
        controller.creditCardButton(e, customerID);
    }

    public void reportsButton(ActionEvent e, String id) {
        // the FXML loader object to load the UI design
        FXMLLoader loader = new FXMLLoader();
        // specify the file location
        loader.setLocation(getClass().getResource("ReportsScreen.fxml"));

        // the object representing the root node of the scene
        Parent parent;
        // try-catch for possible errors in reading the FXML file.
        try {
            // load the UI
            parent = loader.load();

            // set the scene
            Scene scene = new Scene(parent);

            ReportsScreenController controller = loader.getController();
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

    public void logout(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }

}
