import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Date;
import java.text.*;

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
    private RadioButton radiobuttonIsCash;

    @FXML
    private RadioButton radiobuttonTransfer;

    @FXML
    private Button buttonCheckingAccountCheckingAccount;

    @FXML
    private Button buttonReportsCheckingAccount;

    @FXML
    private TextArea textboxTransactionDescription;

    @FXML
    private Button buttonCancelTransaction;

    @FXML
    private Button buttonChooseCheckFile;

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
    private TextField textboxTransactionAmount;

    @FXML
    private Label labelCustomerName;

    @FXML
    private RadioButton radiobuttonIsCheck;

    private String customerID, filePath, accountNumber, type;

    private String val, description, transferAccountNumber;

    private double amount;

    final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    final String USERNAME = "root";
    final String PASSWORD = "password123";

    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    /**
     * Initialize the new transaction screen and controller
     * @param id pass in customer id
     * @param accountNum pass in the account number
     */
    public void initialize(String id, String accountNum) {
        customerID = id;
        accountNumber = accountNum;

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

    /**
     * displays options and fields based on the transaction type that is selected by the user
     */
    public void availableOptions(){
        if(radiobuttonWithdraw.isSelected()){
            radiobuttonIsCash.setVisible(false);
            radiobuttonIsCheck.setVisible(false);
            buttonChooseCheckFile.setVisible(false);
            textboxTransferToAccountNumber.setVisible(false);
        }
        else if(radiobuttonDeposit.isSelected()){
            radiobuttonIsCash.setVisible(true);
            radiobuttonIsCash.setSelected(true);
            radiobuttonIsCheck.setVisible(true);
            buttonChooseCheckFile.setVisible(false);
            textboxTransferToAccountNumber.setVisible(false);
        }
        else if(radiobuttonTransfer.isSelected()){
            radiobuttonIsCash.setVisible(false);
            radiobuttonIsCheck.setVisible(false);
            buttonChooseCheckFile.setVisible(false);
            textboxTransferToAccountNumber.setVisible(true);
        }
    }

    /**
     * Checks if deposit transaction is cash or check and displays the choose file button if check is selected
     */
    public void fileBtnOption(){
        if(radiobuttonIsCheck.isSelected())
            buttonChooseCheckFile.setVisible(true);
        else if(radiobuttonIsCash.isSelected())
            buttonChooseCheckFile.setVisible(false);
    }

    /**
     * File picker to get file path for check files
     * @param e action event object
     */
    public void filePicker(ActionEvent e){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            filePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("File is not Valid.");
        }
    }

    /**
     * Reads in the check file that was selected
     * @return check amount which was read from the file
     * @throws IOException throws IOException for reading from file
     */
    public String readCheck() throws IOException{
        String amount = "0";

        // Open the file.
        File file = new File(filePath);
        Scanner inputFile = new Scanner(file);

        //When the files reader finds the next line to read
        while (inputFile.hasNext()){
            // Read the next line.
            amount = inputFile.nextLine();
        }

        // Close the file.
        inputFile.close();

        amount = amount.replaceAll("," ,"");
        amount = amount.replaceAll("\\$", "");

        return amount;
    }

    /**
     * Gets current date and time for transaction log
     * @return returns the date and time for the transaction log
     */
    public String dateTime() {
        String date;
        Date today = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd-yyyy 'at' hh:mm:ss");

        date = ft.format(today);
        return date;
    }

    /**
     * Listener for the save transaction button
     * @param e action event object
     * @throws IOException throws IOException
     */
    public void save(ActionEvent e) throws IOException{
        Transaction transaction;

        if(radiobuttonWithdraw.isSelected()){
            val = textboxTransactionAmount.getText();
            val = val.replaceAll("," ,"");
            val = val.replaceAll("\\$", "");
            amount = Double.parseDouble(val);
            description = textboxTransactionDescription.getText();
            type = "Withdraw";
            transaction = new Transaction(customerID, accountNumber, type, description, amount, dateTime());
            transaction.withdraw();
            transaction.updateDBWithTransaction();
        }
        else if(radiobuttonDeposit.isSelected()){
            if(radiobuttonIsCash.isSelected())
                val = textboxTransactionAmount.getText();
            else if(radiobuttonIsCheck.isSelected())
                val = readCheck();

            val = val.replaceAll("," ,"");
            val = val.replaceAll("\\$", "");

            amount = Double.parseDouble(val);
            description = textboxTransactionDescription.getText();
            type = "Deposit";
            transaction = new Transaction(customerID, accountNumber, type, description, amount, dateTime());
            transaction.deposit();
            transaction.updateDBWithTransaction();
        } else if(radiobuttonTransfer.isSelected()){
            val = textboxTransactionAmount.getText();
            val = val.replaceAll("," ,"");
            val = val.replaceAll("\\$", "");
            amount = Double.parseDouble(val);
            description = textboxTransactionDescription.getText();
            transferAccountNumber = textboxTransferToAccountNumber.getText();
            type = "Transfer";
            transaction = new Transaction(customerID, accountNumber, type, description, amount, dateTime(), transferAccountNumber);
            transaction.transfer();
            transaction.updateDBWithTransaction();
        }

        checkingAccount(e);
    }

    /**
     * Listener for user search button
     * @param e action event object
     */
    public void userSearch(ActionEvent e){
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.userAccountButton(e);
    }

    /**
     * Listener for checking account button
     * @param e action event object
     */
    public void checkingAccount(ActionEvent e){
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.checkingAccountButton(e, customerID);
    }

    /**
     * Listener for credit card button
     * @param e action event object
     */
    public void creditCard(ActionEvent e){
        CreditCardScreenController controller = new CreditCardScreenController();
        controller.creditCardButton(e, customerID);
    }

    /**
     * Listener for report button
     * @param e action event object
     */
    public void report(ActionEvent e){
        ReportsScreenController controller = new ReportsScreenController();
        controller.reportsButton(e, customerID);
    }

    /**
     * Listener for logout button
     * @param e action event object
     */
    public void logout(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }

}
