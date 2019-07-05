import java.sql.*;   // Needed for JDBC classes

public class CreateDB {
    public static void main(String[] args) {
        // Create a named constant for the URL.
        // NOTE: This value is specific for MySQL.
        final String DB_URL = "jdbc:mysql://localhost:3306/";
        final String DB_COFFEE_URL = "jdbc:mysql://localhost:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "";

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

            // Build the Coffee table.
            buildCoffeeTable(conn2);

            // Build the Customer table.
            buildCustomerTable(conn2);

            // Build the UnpaidInvoice table.
            buildUnpaidOrderTable(conn2);

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
     * The buildCoffeeTable method creates the
     * Coffee table and adds some rows to it.
     */
    public static void buildCoffeeTable(Connection conn){
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Coffee (" +
                    "Description CHAR(25), " +
                    "ProdNum CHAR(10) NOT NULL PRIMARY KEY, " +
                    "Price DOUBLE " +
                    ")");

            // Insert row #1.
            stmt.execute("INSERT INTO Coffee VALUES ( " +
                    "'Bolivian Dark', " +
                    "'14-001', " +
                    "8.95 )" );

            // Insert row #1.
            stmt.execute("INSERT INTO Coffee VALUES ( " +
                    "'Bolivian Medium', " +
                    "'14-002', " +
                    "8.95 )");

            // Insert row #2.
            stmt.execute("INSERT INTO Coffee VALUES ( " +
                    "'Brazilian Dark', " +
                    "'15-001', " +
                    "7.95 )");

            System.out.println("Coffee table created.");
        }
        catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
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
                    "( CustomerNumber CHAR(10) NOT NULL PRIMARY KEY, " +
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
     * The buildUnpaidOrderTable method creates
     * the UnpaidOrder table.
     */

    public static void buildUnpaidOrderTable(Connection conn){
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE UnpaidOrder " +
                    "( CustomerNumber CHAR(10) NOT NULL REFERENCES Customer(CustomerNumber), "+
                    "  ProdNum CHAR(10) NOT NULL REFERENCES Coffee(ProdNum),"+
                    "  OrderDate CHAR(10),"+
                    "  Quantity DOUBLE,"+
                    "  Cost DOUBLE )");

            System.out.println("UnpaidOrder table created.");
        }
        catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
