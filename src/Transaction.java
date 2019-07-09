import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class Transaction {
    private String customerId;
    private String accountNumber;
    private String transactionId;
    private String transactionType;
    private double transactionAmount;
    private Date transactionDateTime;

    public Transaction (String custId, String tranType, double amount, Date tranDateTime) {

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

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }
}
