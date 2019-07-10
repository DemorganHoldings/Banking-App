import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.sql.*;


public class CreditCard {
    final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    final String USERNAME = "root";
    final String PASSWORD = "password123";

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

    public boolean checkDBForExistingCreditCard() {
        String custId = "";
        boolean hasCreditCard = false;
        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object
            String sql = "SELECT CustomerID from CreditCard";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                custId = rs.getString("CustomerID");
            }

            if (this.customerId.equals(custId)) {
                hasCreditCard = true;
            }

            else {
                hasCreditCard = false;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return hasCreditCard;
    }

    public void updateDBWithCreditCard(){

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "INSERT into CreditCard " +
                    "(CustomerID, CreditCardNum, ExpirationDate, CVV, CreditLimit) VALUES ('" +
                    this.customerId + "', '" +
                    this.creditCardNumber + "', '" +
                    this.expirationDate + "', '" +
                    this.cvvCode + "', '" +
                    this.creditLimit + "')";

            stmt.executeUpdate(sql);

            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void displayCreditCard(){

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT 'CreditCardNum', 'ExpirationDate', 'CVV` FROM 'CreditCard' WHERE 'CustomerID' = " + "'" + customerId + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                this.creditCardNumber = rs.getString("CreditCardNum");
                this.expirationDate = rs.getString("ExpirationDate");
                this.cvvCode = rs.getString("CVV");
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
