import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

/**
 * Transaction class
 */
public class Transaction {
    private String customerId;
    private String accountNumber;
    private String transactionId;
    private String transactionType;
    private String transactionDescription;
    private double transactionAmount;
    private String transactionDateTime;
    private String transferAccount;

    final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    final String USERNAME = "root";
    final String PASSWORD = "password123";

    /**
     * Transaction Constructor
     * @param custId customer id
     * @param acctNum account number
     * @param tranType transaction type
     * @param tranID transaction id
     * @param tranDescription transaction description
     * @param amount transaction amount
     * @param tranDateTime transaction date and time
     * @param transferAcct transfer account number
     */
    public Transaction (String custId, String acctNum, String tranType, String tranID,  String tranDescription, double amount, String tranDateTime, String transferAcct) {
        this.customerId = custId;
        this.accountNumber = acctNum;
        this.transactionType = tranType;
        this.transactionId = tranID;
        this.transactionDescription = tranDescription;
        this.transactionAmount = amount;
        this.transactionDateTime = tranDateTime;
        this.transferAccount = transferAcct;
    }

    /**
     * New Transaction constructor with no Transfer Account and generate Transaction ID
     * @param custId customer id
     * @param acctNum account number
     * @param tranType transaction type
     * @param tranDescription transaction description
     * @param amount transaction amount
     * @param tranDateTime transaction date and time
     */
    public Transaction (String custId, String acctNum, String tranType, String tranDescription, double amount, String tranDateTime) {
        this.customerId = custId;
        this.accountNumber = acctNum;
        this.transactionType = tranType;
        generateTransactionId();
        this.transactionDescription = tranDescription;
        this.transactionAmount = amount;
        this.transactionDateTime = tranDateTime;
        this.transferAccount = "";
    }

    /**
     * New Transaction constructor with a Transfer Account and generate Transaction ID
     * @param custId customer id
     * @param acctNum account number
     * @param tranType transaction type
     * @param tranDescription transaction description
     * @param amount transaction amount
     * @param tranDateTime transaction date and time
     * @param transferAcct transfer account number
     */
    public Transaction (String custId, String acctNum, String tranType, String tranDescription, double amount, String tranDateTime, String transferAcct) {
        this.customerId = custId;
        this.accountNumber = acctNum;
        this.transactionType = tranType;
        generateTransactionId();
        this.transactionDescription = tranDescription;
        this.transactionAmount = amount;
        this.transactionDateTime = tranDateTime;
        this.transferAccount = transferAcct;
    }

    //Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Generate transaction id
     */
    public void generateTransactionId() {
        LocalDate currentDate = LocalDate.now(); // Create a date object
        Random rand = new Random();
        int randomNumber = rand.nextInt(999999) + 1;
        String randomNumberString = String.format("%06d", randomNumber);
        this.transactionId = this.transactionType.charAt(0) + "-" + currentDate + "-" + randomNumberString;
    }

    //Getters and Setters
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransferAccount() {
        return transferAccount;
    }

    /**
     * Withdraw transaction method
     */
    public void withdraw(){
        double initialBalance = 0, total = 0;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT Balance from CheckingAccount WHERE CustomerID='" + this.customerId + "' && AccountNumber='" + this.accountNumber + "'" ;

            ResultSet rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                initialBalance= rs.getDouble("Balance");
            }

            if (this.transactionAmount > initialBalance){
                this.transactionAmount = initialBalance;
            }

            total = initialBalance - this.transactionAmount;

            sql = "UPDATE CheckingAccount SET Balance='" +  total + "' WHERE CustomerID= '" + this.customerId + "' && AccountNumber='" + this.accountNumber + "'" ;

            stmt.executeUpdate(sql);
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Deposit transaction method
     */
    public void deposit(){
        double initialBalance = 0, total = 0;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT Balance from CheckingAccount WHERE CustomerID='" + this.customerId + "' && AccountNumber='" + this.accountNumber + "'" ;

            ResultSet rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                initialBalance= rs.getDouble("Balance");
            }

            total = initialBalance + this.transactionAmount;

            sql = "UPDATE CheckingAccount SET Balance='" +  total + "' WHERE CustomerID= '" + this.customerId + "' && AccountNumber='" + this.accountNumber + "'" ;

            stmt.executeUpdate(sql);
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Transfer transaction method
     */
    public void transfer(){
        double initialBalance = 0, total = 0;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            //Withdraw from customer account
            String sql = "SELECT Balance from CheckingAccount WHERE AccountNumber='" + this.accountNumber + "'" ;

            ResultSet rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                initialBalance= rs.getDouble("Balance");
            }

            if (this.transactionAmount > initialBalance)
                total = initialBalance - initialBalance;
            else
                total = initialBalance - this.transactionAmount;

            sql = "UPDATE CheckingAccount SET Balance='" +  total + "' WHERE CustomerID= '" + this.customerId + "' && AccountNumber='" + this.accountNumber + "'" ;

            stmt.executeUpdate(sql);



            //Deposit money into other account
            sql = "SELECT Balance from CheckingAccount WHERE AccountNumber='" + this.transferAccount + "'" ;

            rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                initialBalance= rs.getDouble("Balance");
            }

            total = initialBalance + this.transactionAmount;

            sql = "UPDATE CheckingAccount SET Balance='" +  total + "' WHERE AccountNumber='" + this.transferAccount + "'" ;

            stmt.executeUpdate(sql);
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * update database with new transaction
     */
    public void updateDBWithTransaction(){

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

             String sql = "INSERT into Transactions " +
                        "(CustomerID, AccountNumber, TransactionID, TransactionType, TransactionDescription, TransactionAmount," +
                        "DateTime, TransferAccount) VALUES ('" +
                        this.customerId + "', '" +
                        this.accountNumber + "', '" +
                        this.transactionId + "', '" +
                        this.transactionType + "', '" +
                        this.transactionDescription + "', '" +
                        this.transactionAmount + "', '" +
                        this.transactionDateTime + "', '" +
                        this.transferAccount + "')";

            stmt.executeUpdate(sql);

            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
