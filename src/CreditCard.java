import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class CreditCard extends Customer{

    //fields
    private String customerId;
    private String creditCardNumber;
    private String expirationDate;
    private String cvvCode;
    private double creditLimit;

    //Initialize variable for database connection
    final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    final String USERNAME = "root";
    final String PASSWORD = "password123";

    /**
     * Constructor for new CreditCard Object
     * @param id Customer ID
     * @param limit Credit Limit
     */
    public CreditCard(String id, double limit) {
        this.customerId = id;
        this.creditLimit = limit;
        generateCreditCardNumber();
        generateExpirationDate();
        generateCvvCode();
    }

    /**
     * Constructor for existing CreditCard Object
     * @param id
     */
    public CreditCard(String id) {
        this.customerId = id;
    }


    //Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Generate random Credit Card Number
     */
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

    /**
     * Generate a Expiration Date
     */
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

    /**
     * Generate a random CCV Code
     */
    public void generateCvvCode() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(999) + 100;
        String cvvCode = String.format("%03d", randomNumber);
        this.cvvCode = cvvCode;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * Checks database to see if user already has a credit card
     * @return true or false based on if Credit Card Exists
     */
    public boolean checkDBForExistingCreditCard() {
        String custId = "";
        boolean hasCreditCard = false;
        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement OBJECT
            String sql = "SELECT * from CreditCard WHERE CustomerID = " + "'" + this.getCustomerId() + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                custId = rs.getString("CustomerID");
                if (this.customerId.equals(custId)) {
                    this.creditCardNumber = rs.getString("CreditCardNum");
                    this.expirationDate = rs.getString("ExpirationDate");
                    this.cvvCode = Integer.toString(rs.getInt("CVV"));
                    this.creditLimit = rs.getDouble("CreditLimit");
                    hasCreditCard = true;
                }
                else {
                    hasCreditCard = false;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return hasCreditCard;
    }

    /**
     * Updates the Database with the new Credit card information
     */
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

    /**
     * Pulls the Credit Card information from Database
     */
    public void displayCreditCard(){

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT * FROM CreditCard WHERE CustomerID = '" + customerId + "'";
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
