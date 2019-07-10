import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class CreditCard {
    private String customerId;
    private String creditCardNumber;
    private String expirationDate;
    private String cvvCode;
    private double creditLimit;

    public CreditCard(String id, double limit) {
        this.customerId = id;
        this.creditLimit = limit;
        generateCreditCardNumber();
        generateExpirationDate();
        generateCvvCode();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void generateCreditCardNumber() {
        String creditCardNum ="4100 "; //Visa Card Identifier
        Random rand = new Random();
        while (creditCardNum.trim().length() < 16) {
            int randomNumber = rand.nextInt(9999) + 1;
            String randomNumberString = String.format("%04d", randomNumber);
            creditCardNum += randomNumberString + " ";
        }
        this.creditCardNumber = creditCardNum;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void generateExpirationDate() {
        String formattedExpirationDate;
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, 2);
        Date expDate = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/yy");
        formattedExpirationDate = dateFormat.format(expDate);
        this.expirationDate = formattedExpirationDate;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void generateCvvCode() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(999) + 1;
        String cvvCode = String.format("%03d", randomNumber);
        this.cvvCode = cvvCode;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

}
