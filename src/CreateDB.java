import java.sql.*;   // Needed for JDBC classes

public class CreateDB {
    public static void main(String[] args) {
        // Create a named constant for the URL.
        // NOTE: This value is specific for MySQL.

        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/";
        final String DB_COFFEE_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "F{G8B-7ThWxM?ut5";

        try {
            // Create a connection to the database.
            Connection conn =
                    DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Create the database. If the database already exists, drop it.
            createDataBase(conn);
            conn.close();

            //Create a connection to the database and to the coffee database
            Connection conn2 =
                    DriverManager.getConnection(DB_COFFEE_URL, USERNAME, PASSWORD);

            // Build the Customer table.
            buildCustomerTable(conn2);

            // Build the BankStaff table.
            buildBankStaffTable(conn2);

            // Build the CheckingAccount table.
            buildCheckingAccountTable(conn2);

            // Build the CreditCard table.
            buildCreditCardTable(conn2);

            // Build the Transactions table.
            buildTransactionsTable(conn2);

            // Close the connection.
            conn2.close();
        }
        catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The createDatabase method creates the DB. If the DB is already existed, drop the DB first.
     */

    public static void createDataBase(Connection conn) {
        System.out.println("Checking for existing database.");

        try{
            Statement stmt = conn.createStatement();

            //Drop the existing database
            try {
                stmt.executeUpdate("Drop DATABASE spDemorganDB");
            }
            catch(SQLException ex) {
                // No need to report an error.
                // The database simply did not exist.
            }
            //Create a new database
            try {
                stmt.execute("Create DATABASE spDemorganDB");
                //stmt.execute("USE coffee");
                System.out.println("Database spDemorgan created.");
            }
            catch(SQLException ex) {
                // No need to report an error.
                // The database simply did not exist.
            }
        }
        catch(SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * The buildCustomerTable method creates the
     * Customer table and adds some rows to it.
     */
    public static void buildCustomerTable(Connection conn){
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Customer" +
                    "( CustomerID CHAR(10) NOT NULL PRIMARY KEY, " +
                    "  Name CHAR(25),"    +
                    "  Address CHAR(25)," +
                    "  Social CHAR(15),"    +
                    "  PhoneNumber CHAR(15),"    +
                    "  Email CHAR(25) )");

            // Add some rows to the new table.
            stmt.executeUpdate("INSERT INTO Customer VALUES" +
                    "('101', 'Person1', '17 N. Main Street'," +
                    " '123-45-6789', '(702) 123-4567', 'fake@gmail.com')");

            stmt.executeUpdate("INSERT INTO Customer VALUES" +
                    "('102', 'Person2'," +
                    " '110 E. Main Street'," +
                    " '123-45-6789', '(702) 123-4567', 'fake@gmail.com')");

            stmt.executeUpdate("INSERT INTO Customer VALUES" +
                    "('103', 'Person3', '101 Center Plaza'," +
                    " '123-45-6789', '(702) 123-4567', 'fake@gmail.com')");

            System.out.println("Customer table created.");
        }
        catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The buildBankStaffTable method creates
     * the BankStaff table.
     */

    public static void buildBankStaffTable(Connection conn){
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE BankStaff" +
                    "( StaffID CHAR(10), " +
                    "  Name CHAR(25)," +
                    "  Username CHAR(25)," +
                    "  Password CHAR(25)," +
                    "  isManager BOOLEAN )");

            System.out.println("BankStaff table created.");
        }
        catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The buildCheckingAccountTable method creates
     * the CheckingAccount table.
     */

    public static void buildCheckingAccountTable(Connection conn){
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE CheckingAccount " +
                    "( CustomerID CHAR(10) NOT NULL REFERENCES Customer(CustomerID), "+
                    "  AccountNumber CHAR(10) NOT NULL PRIMARY KEY,"+
                    "  Balance DOUBLE )");

            System.out.println("CheckingAccount table created.");
        }
        catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The buildCreditCardTable method creates
     * the CreditCard table.
     */

    public static void buildCreditCardTable(Connection conn){
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE CreditCard " +
                    "( CustomerID CHAR(10) NOT NULL REFERENCES Customer(CustomerID), "+
                    "  CreditCardNum CHAR(25),"+
                    "  ExpirationDate CHAR(15),"+
                    "  CVV INTEGER,"+
                    "  CreditLimit DOUBLE )");

            System.out.println("CreditCard table created.");
        }
        catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The buildTransactionsTable method creates
     * the Transactions table.
     */

    public static void buildTransactionsTable(Connection conn){
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Transactions " +
                    "( CustomerID CHAR(10) NOT NULL REFERENCES Customer(CustomerID), "+
                    "  AccountNumber CHAR(10) NOT NULL REFERENCES CheckingAccount(AccountNumber),"+
                    "  TransactionID CHAR(15),"+
                    "  TransactionType CHAR(15),"+
                    "  TransactionDescription CHAR(100),"+
                    "  DateTime CHAR(25) )");

            System.out.println("Transactions table created.");
        }
        catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}