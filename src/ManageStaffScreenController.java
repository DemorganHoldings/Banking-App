import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ManageStaffScreenController {

    @FXML
    private Button buttonUserSearch;

    @FXML
    private Button buttonLogout;

    @FXML
    private Label labelStaffName;

    @FXML
    private TextField textboxRemoveStaffId;

    @FXML
    private Label labelStaffPassword;

    @FXML
    private Label labelStaffUsername;

    @FXML
    private Button buttonRemoveStaffAccount;

    @FXML
    private CheckBox checkboxStaffIsManager;

    @FXML
    private TextField textboxNewStaffUsername;

    @FXML
    private Label labelStaffId;

    @FXML
    private TextField textboxNewStaffName;

    @FXML
    private TextField textboxNewStaffPassword;

    @FXML
    private Button buttonCreateNewStaffAccount;

    @FXML
    private Label labelStaffManager;

    private final String DB_URL = "jdbc:mysql://142.93.91.169:3306/spDemorganDB";
    private final String USERNAME = "root";
    private final String PASSWORD = "password123";

    public ArrayList<BankStaff> allStaff;

    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT 'StaffID', 'Name', 'Username', 'Password', 'isManager' FROM 'BankStaff'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                BankStaff newStaff = new BankStaff(rs.getString("StaffID") , rs.getString("Name"), rs.getString("Username"), rs.getString("Password"), rs.getBoolean("isManager"));
                allStaff.add(newStaff);
            }

            for(int i = 0; i < allStaff.size(); i++){
                String isManager;
                if (allStaff.get(i).isManager()) {
                    isManager = "Yes";
                }
                else {
                    isManager = "No";
                }
                labelStaffId.setText(labelStaffId.getText() + "\n" + allStaff.get(i).getStaffId());
                labelStaffName.setText(labelStaffId.getText() + "\n" + allStaff.get(i).getStaffName());
                labelStaffUsername.setText(labelStaffUsername.getText() + "\n" + allStaff.get(i).getStaffUsername());
                labelStaffPassword.setText(labelStaffPassword.getText() + "\n" + allStaff.get(i).getStaffPassword());
                labelStaffManager.setText(labelStaffManager.getText() + "\n" + isManager);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
