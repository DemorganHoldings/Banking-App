import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class CheckingAccount {
    private String customerId;
    private String accountNumber;
    private double accountBalance;
    public ArrayList<Transaction> transactions;

    public CheckingAccount(String id){
        this.accountBalance = 0;
        generateAccountNumber();
        this.customerId = id;
    }

    public CheckingAccount(String id, String acctNum) {
        this.customerId = id;
        this.accountNumber = acctNum;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void generateAccountNumber() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(99999999) + 1;
        String randomNumberString = String.format("%08d", randomNumber);

        this.accountNumber = "CH-" + randomNumberString;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void getTransactions() {
        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";

        try {
            ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT * FROM Transactions WHERE CustomerID = '" + this.customerId + "' && " +
                    "AccountNumber = '" + this.accountNumber+ "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Transaction newTransaction = new Transaction(this.customerId, this.accountNumber, rs.getString("TransactionType"),
                        rs.getString("TransactionID"), rs.getString("TransactionDescription"),
                        rs.getDouble("TransactionAmount"), rs.getString("DateTime"), rs.getString("TransferAccount"));

                transactionList.add(newTransaction);
            }
            this.transactions = transactionList;
        }
        catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}
