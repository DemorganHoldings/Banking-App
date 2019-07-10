import java.time.LocalDate;
import java.util.Random;

public class Transaction {
    private String customerId;
    private String accountNumber;
    private String transactionId;
    private String transactionType;
    private String transactionDescription;
    private double transactionAmount;
    private String transactionDateTime;
    private String transferAccount;

    /*public Transaction (String custId, String acctNum, String tranType, double amount, String tranDateTime) {
        generateTransactionId();
        this.customerId = custId;
        this.accountNumber = acctNum;
        this.transactionType = tranType;
        this.transactionAmount = amount;
        this.transactionDateTime = tranDateTime;
    }*/

    public Transaction (String custId, String acctNum, String tranType, String tranDescription, double amount, String tranDateTime) {
        this.customerId = custId;
        this.accountNumber = acctNum;
        generateTransactionId();
        this.transactionType = tranType;
        this.transactionDescription = tranDescription;
        this.transactionAmount = amount;
        this.transactionDateTime = tranDateTime;
    }

    public Transaction (String custId, String acctNum, String tranType, String tranDescription, double amount, String tranDateTime, String transferAcct) {
        this.customerId = custId;
        this.accountNumber = acctNum;
        generateTransactionId();
        this.transactionType = tranType;
        this.transactionDescription = tranDescription;
        this.transactionAmount = amount;
        this.transactionDateTime = tranDateTime;
        this.transferAccount = transferAcct;
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

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void generateTransactionId() {
        LocalDate currentDate = LocalDate.now(); // Create a date object
        Random rand = new Random();
        int randomNumber = rand.nextInt(999999) + 1;
        String randomNumberString = String.format("%06d", randomNumber);
        this.transactionId = this.transactionType.charAt(0) + "-" + currentDate + "-" + randomNumberString;
    }

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
}
