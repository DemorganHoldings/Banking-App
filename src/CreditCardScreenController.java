import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Label labelNewCreditCard;

    @FXML
    private Label labelCreditCardCvvCode;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Label labelCreditCardApplicationStatus;

    @FXML
    private ImageView imageviewCreditCard;

    @FXML
    private Label labelExistingCreditCard;

    @FXML
    void e91e63(ActionEvent event) {

    }

    private String customerID, accountNum;
    DecimalFormat dollarFormatter = new DecimalFormat("#,##0.00");

    public void initialize(String id) {
        customerID = id;

        System.out.println(customerID);

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

            CreditCard newCard = new CreditCard(customerID, 10000);
            if(newCard.checkDBForExistingCreditCard()){
                labelCreditCardApplicationStatus.setText("Customer already has a credit card with limit $" + dollarFormatter.format(newCard.getCreditLimit()));
                labelCreditCardNumber.setVisible(true);
                labelCreditCardExpiration.setVisible(true);
                labelCreditCardCvvCode.setVisible(true);
                imageviewCreditCard.setVisible(true);
                labelExistingCreditCard.setVisible(true);
                newCard.displayCreditCard();
                labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                labelCreditCardExpiration.setText(newCard.getExpirationDate());
                labelCreditCardCvvCode.setText(newCard.getCvvCode());

                labelNewCreditCard.setVisible(false);
                textboxCreditScore.setVisible(false);
                buttonApplyForCreditCard.setVisible(false);
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

                labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + dollarFormatter.format(creditLimit));
                newCard.updateDBWithCreditCard();
                labelCreditCardNumber.setVisible(true);
                labelCreditCardExpiration.setVisible(true);
                labelCreditCardCvvCode.setVisible(true);
                imageviewCreditCard.setVisible(true);
                labelExistingCreditCard.setVisible(true);
                newCard.displayCreditCard();
                labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                labelCreditCardExpiration.setText(newCard.getExpirationDate());
                labelCreditCardCvvCode.setText(newCard.getCvvCode());

            }
            else if (creditScore > 740 && accountBalance > 8000) {
                creditLimit = accountBalance * 0.15;
                CreditCard newCard = new CreditCard(customerID, creditLimit);
                hasCreditCard = newCard.checkDBForExistingCreditCard();

                if (hasCreditCard) {
                    labelCreditCardApplicationStatus.setText("Customer already has a credit card with limit $" + dollarFormatter.format(newCard.getCreditLimit()));
                    newCard.displayCreditCard();
                    labelCreditCardNumber.setVisible(true);
                    labelCreditCardExpiration.setVisible(true);
                    labelCreditCardCvvCode.setVisible(true);
                    imageviewCreditCard.setVisible(true);
                    labelExistingCreditCard.setVisible(true);
                    labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                    labelCreditCardExpiration.setText(newCard.getExpirationDate());
                    labelCreditCardCvvCode.setText(newCard.getCvvCode());                }
                else {
                    labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + dollarFormatter.format(creditLimit));
                    newCard.updateDBWithCreditCard();
                    labelCreditCardNumber.setVisible(true);
                    labelCreditCardExpiration.setVisible(true);
                    labelCreditCardCvvCode.setVisible(true);
                    imageviewCreditCard.setVisible(true);
                    labelExistingCreditCard.setVisible(true);
                    newCard.displayCreditCard();
                    labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                    labelCreditCardExpiration.setText(newCard.getExpirationDate());
                    labelCreditCardCvvCode.setText(newCard.getCvvCode());
                }
            }
            else if (creditScore > 670 && accountBalance > 6000) {
                creditLimit = accountBalance * 0.10;
                CreditCard newCard = new CreditCard(customerID, creditLimit);
                hasCreditCard = newCard.checkDBForExistingCreditCard();

                if (hasCreditCard) {
                    labelCreditCardApplicationStatus.setText("Customer already has a credit card with limit $" + dollarFormatter.format(newCard.getCreditLimit()));
                    newCard.displayCreditCard();
                    labelCreditCardNumber.setVisible(true);
                    labelCreditCardExpiration.setVisible(true);
                    labelCreditCardCvvCode.setVisible(true);
                    imageviewCreditCard.setVisible(true);
                    labelExistingCreditCard.setVisible(true);
                    labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                    labelCreditCardExpiration.setText(newCard.getExpirationDate());
                    labelCreditCardCvvCode.setText(newCard.getCvvCode());                }
                else {
                    labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + dollarFormatter.format(creditLimit));
                    newCard.updateDBWithCreditCard();
                    labelCreditCardNumber.setVisible(true);
                    labelCreditCardExpiration.setVisible(true);
                    labelCreditCardCvvCode.setVisible(true);
                    imageviewCreditCard.setVisible(true);
                    labelExistingCreditCard.setVisible(true);
                    newCard.displayCreditCard();
                    labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                    labelCreditCardExpiration.setText(newCard.getExpirationDate());
                    labelCreditCardCvvCode.setText(newCard.getCvvCode());
                }
            }
            else if (creditScore > 580 && accountBalance > 4000) {
                creditLimit = accountBalance * 0.05;
                CreditCard newCard = new CreditCard(customerID, creditLimit);
                hasCreditCard = newCard.checkDBForExistingCreditCard();

                if (hasCreditCard) {
                    labelCreditCardApplicationStatus.setText("Customer already has a credit card with limit $" + dollarFormatter.format(newCard.getCreditLimit()));
                    newCard.displayCreditCard();
                    labelCreditCardNumber.setVisible(true);
                    labelCreditCardExpiration.setVisible(true);
                    labelCreditCardCvvCode.setVisible(true);
                    imageviewCreditCard.setVisible(true);
                    labelExistingCreditCard.setVisible(true);
                    labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                    labelCreditCardExpiration.setText(newCard.getExpirationDate());
                    labelCreditCardCvvCode.setText(newCard.getCvvCode());                }
                else {
                    labelCreditCardApplicationStatus.setText("Approved, Your credit limit is $" + dollarFormatter.format(creditLimit));
                    newCard.updateDBWithCreditCard();
                    labelCreditCardNumber.setVisible(true);
                    labelCreditCardExpiration.setVisible(true);
                    labelCreditCardCvvCode.setVisible(true);
                    imageviewCreditCard.setVisible(true);
                    labelExistingCreditCard.setVisible(true);
                    newCard.displayCreditCard();
                    labelCreditCardNumber.setText(newCard.getCreditCardNumber());
                    labelCreditCardExpiration.setText(newCard.getExpirationDate());
                    labelCreditCardCvvCode.setText(newCard.getCvvCode());
                }
            }
            else if (creditScore > 0) {
                labelCreditCardApplicationStatus.setText("Denied, Your credit score or checking account balance is too low.");
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

    public void creditCardButton(ActionEvent e, String id) {
        // the FXML loader object to load the UI design
        FXMLLoader loader = new FXMLLoader();
        // specify the file location
        loader.setLocation(getClass().getResource("CreditCardScreen.fxml"));

        // the object representing the root node of the scene
        Parent parent;
        // try-catch for possible errors in reading the FXML file.
        try {
            // load the UI
            parent = loader.load();

            // set the scene
            Scene scene = new Scene(parent);

            CreditCardScreenController controller = loader.getController();
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

    public void report(ActionEvent e){
        ReportsScreenController controller = new ReportsScreenController();
        controller.reportsButton(e, customerID);
    }

    public void logout(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }
}
