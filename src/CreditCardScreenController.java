import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.function.DoubleBinaryOperator;

public class CreditCardScreenController {

    @FXML
    private Label labelCreditCardExpiration;

    @FXML
    private Button buttonLogoutCheckingAccount;

    @FXML
    private Button buttonCreditCardCheckingAccount;

    @FXML
    private Label labelAccountBalance;

    @FXML
    private Button buttonCheckingAccountCheckingAccount;

    @FXML
    private Button buttonReportsCheckingAccount;

    @FXML
    private Button buttonApplyForCreditCard;

    @FXML
    private Label labelCreditCardNumber;

    @FXML
    private Button buttonUserAccountCheckingAccount;

    @FXML
    private TextField textboxCreditScore;

    @FXML
    private Label labelAccountNumber;

    @FXML
    private Label labelCreditCardCvvCode;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Label labelCreditCardApplicationStatus;

    @FXML
    void e91e63(ActionEvent event) {

    }

    private String customerID, accountNum;

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
                accountNum = rs.getString("AccountNumber");
                double bal = Double.parseDouble(rs.getString("Balance"));
                labelAccountBalance.setText(moneyFormat.format(bal));
            }
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void applyForCreditCard() {
            String creditScoreInput;
            String balanceInput;
            double accountBalance = 10000;
            double creditScore = 0;
            double creditLimit = 0;
            boolean hasCreditCard = false;

            creditScoreInput = textboxCreditScore.getText();
            creditScore = Double.parseDouble(creditScoreInput);

            if (creditScore > 800 && accountBalance > 10000) {
                creditLimit = accountBalance * 0.20;
                CreditCard newCard = new CreditCard(customerID, creditLimit);
                hasCreditCard = newCard.checkDBForExistingCreditCard();

                if (hasCreditCard) {
                    labelCreditCardApplicationStatus.setText("Customer already has a credit card.");
                }
                else {
                    labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + creditLimit + ".");
                    newCard.updateDBWithCreditCard();
                }
            }
            else if (creditScore > 740 && accountBalance > 8000) {
                creditLimit = accountBalance * 0.15;
                CreditCard newCard = new CreditCard(customerID, creditLimit);
                hasCreditCard = newCard.checkDBForExistingCreditCard();

                if (hasCreditCard) {
                    labelCreditCardApplicationStatus.setText("Customer already has a credit card.");
                }
                else {
                    labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + creditLimit + ".");
                    newCard.updateDBWithCreditCard();
                }
            }
            else if (creditScore > 670 && accountBalance > 6000) {
                creditLimit = accountBalance * 0.10;
                CreditCard newCard = new CreditCard(customerID, creditLimit);
                hasCreditCard = newCard.checkDBForExistingCreditCard();

                if (hasCreditCard) {
                    labelCreditCardApplicationStatus.setText("Customer already has a credit card.");
                }
                else {
                    labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + creditLimit + ".");
                    newCard.updateDBWithCreditCard();
                }
            }
            else if (creditScore > 580 && accountBalance > 4000) {
                creditLimit = accountBalance * 0.05;
                CreditCard newCard = new CreditCard(customerID, creditLimit);
                hasCreditCard = newCard.checkDBForExistingCreditCard();

                if (hasCreditCard) {
                    labelCreditCardApplicationStatus.setText("Customer already has a credit card.");
                }
                else {
                    labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + creditLimit + ".");
                    newCard.updateDBWithCreditCard();
                }
            }
            else if (creditScore > 0) {
                labelCreditCardApplicationStatus.setText("Denied, Your credit score is too low.");
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
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.creditCardButton(e);
    }

    public void logout(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.creditCardButton(e);
    }
}
