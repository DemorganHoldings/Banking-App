import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;

public class ReportsScreenController {

    @FXML
    private Button buttonLogoutCheckingAccount;

    @FXML
    private Label labelStatementMessage;

    @FXML
    private Button buttonCreditCardCheckingAccount;

    @FXML
    private Label labelAccountBalance;

    @FXML
    private Button buttonUserAccountCheckingAccount;

    @FXML
    private TextField textboxFileName;

    @FXML
    private Label labelAccountNumber;

    @FXML
    private Button buttonBrowseForFile;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Button buttonCheckingAccountCheckingAccount;

    @FXML
    private Button buttonReportsCheckingAccount;

    @FXML
    private Button buttonSaveStatement;

    private String filePath;

    private String customerID, accountNum;

    public void initialize(String id) {
        customerID = id;

        System.out.println(customerID);

        final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
        final String USERNAME = "root";
        final String PASSWORD = "password123";

        DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT Name from Customer WHERE CustomerID='" + customerID + "'";

            ResultSet rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                labelCustomerName.setText(rs.getString("Name"));
            }

            sql = "SELECT * from CheckingAccount WHERE CustomerID='" + customerID + "'";

            rs = stmt.executeQuery(sql);

            //Display the content of the result set
            while (rs.next()) {
                labelAccountNumber.setText(rs.getString("AccountNumber"));
                accountNum = rs.getString("AccountNumber");
                double bal = Double.parseDouble(rs.getString("Balance"));
                labelAccountBalance.setText(moneyFormat.format(bal));
            }
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void filePicker(ActionEvent e){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            filePath = selectedFile.getAbsolutePath();
            textboxFileName.setText(filePath);
            buttonSaveStatement.setVisible(true);
        } else {
            labelStatementMessage.setText("File is not Valid.");
        }
    }

    public void generateStatement(ActionEvent e1) {

        try {
            File file = new File(filePath);
            FileWriter fw = new FileWriter(file);
            fw.write(customerID + " Test");
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void userSearch(ActionEvent e){
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.userAccountButton(e);
    }

    public void checkingAccount(ActionEvent e){
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.checkingAccountButton(e, customerID);
    }

    public void creditCard(ActionEvent e){
        CreditCardScreenController controller = new CreditCardScreenController();
        controller.creditCardButton(e, customerID);
    }

    public void reportsButton(ActionEvent e, String id) {
        // the FXML loader object to load the UI design
        FXMLLoader loader = new FXMLLoader();
        // specify the file location
        loader.setLocation(getClass().getResource("ReportsScreen.fxml"));

        // the object representing the root node of the scene
        Parent parent;
        // try-catch for possible errors in reading the FXML file.
        try {
            // load the UI
            parent = loader.load();

            // set the scene
            Scene scene = new Scene(parent);

            ReportsScreenController controller = loader.getController();
            controller.initialize(id);

            // get the current window; i.e. the stage
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            // set the scene for the stage
            stage.setScene(scene);
            // show the stage
            stage.show();
        } catch (IOException e1) {
            System.out.print(e1.getMessage());
        }
    }

    public void logout(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }

}
