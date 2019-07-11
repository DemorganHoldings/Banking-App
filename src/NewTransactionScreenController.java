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

    // method for initializing the window
    public void initialize(String id, String accountNum) {
        customerID = id;
        System.out.println(customerID);
        accountNumber = accountNum;

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

    public void fileBtnOption(){
        if(radiobuttonIsCheck.isSelected())
            buttonChooseCheckFile.setVisible(true);
        else if(radiobuttonIsCash.isSelected())
            buttonChooseCheckFile.setVisible(false);
    }

    public void filePicker(ActionEvent e){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            filePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("File is not Valid.");
        }

        System.out.println(filePath);
    }

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

    public String dateTime() {
        String date;
        Date today = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd-yyyy 'at' hh:mm:ss");

        date = ft.format(today);
        return date;
    }

    public void save(ActionEvent e) throws IOException{
        Transaction transaction;

        if(radiobuttonWithdraw.isSelected()){
            val = textboxTransactionAmount.getText();
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

            System.out.println(val);
            amount = Double.parseDouble(val);
            description = textboxTransactionDescription.getText();
            type = "Deposit";
            transaction = new Transaction(customerID, accountNumber, type, description, amount, dateTime());
            transaction.deposit();
            transaction.updateDBWithTransaction();
        } else if(radiobuttonTransfer.isSelected()){
            val = textboxTransactionAmount.getText();
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

    public void report(ActionEvent e){
        ReportsScreenController controller = new ReportsScreenController();
        controller.reportsButton(e, customerID);
    }

    public void logout(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }

}
