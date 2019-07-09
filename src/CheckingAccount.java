import java.util.ArrayList;
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

    //public CheckingAccount(double balance, String id) { }

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

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
