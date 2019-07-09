import java.util.UUID;
import java.time.LocalDate;
import java.util.Random;

public class Customer {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerSocial;
    private String customerPhoneNum;
    private String customerEmailAddress;

    public Customer(String name, String address, String social, String phone, String email) {

    }

    public void generateCustomerId() {
        LocalDate currentDate = LocalDate.now(); // Create a date object
        Random rand = new Random();
        int randomNumber = rand.nextInt(999999) + 1;
        String randomNumberString = String.format("%06d", randomNumber);
        this.customerId = "SP-" + currentDate + "-" + randomNumberString;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerSocial() {
        return customerSocial;
    }

    public void setCustomerSocial(String customerSocial) {
        this.customerSocial = customerSocial;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }

    public void setCustomerPhoneNum(String customerPhoneNum) {
        this.customerPhoneNum = customerPhoneNum;
    }

    public String getCustomerEmailAddress() {
        return customerEmailAddress;
    }

    public void setCustomerEmailAddress(String customerEmailAddress) {
        this.customerEmailAddress = customerEmailAddress;
    }
}
