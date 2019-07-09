import java.util.Date;

public class CreditCard {
    private String customerId;
    private String creditCardNumber;
    private Date expirationDate;
    private double cvvCode;
    private double creditLimit;

    public CreditCard(Date expDate, double cvv, double limit, double id) {

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

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(double cvvCode) {
        this.cvvCode = cvvCode;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
}
