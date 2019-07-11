import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    private Button buttonModifyStaffAccount;

    @FXML
    private Label labelStaffPassword;

    @FXML
    private Label labelStaffUsername;

    @FXML
    private TextField textboxModifyStaffId;

    @FXML
    private Label labelModifyStaffStatus;

    @FXML
    private Button buttonRemoveStaffAccount;

    @FXML
    private CheckBox checkboxModifyStaffIsManager;

    @FXML
    private TextField textboxNewStaffUsername;

    @FXML
    private Label labelAddStaffStatus;

    @FXML
    private Label labelStaffId;

    @FXML
    private CheckBox checkboxAddStaffIsManager;

    @FXML
    private TextField textboxNewStaffName;

    @FXML
    private Label labelRemoveStaffStatus;

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
        getStaffFromDb();
    }

    public void addStaffButton() {
        String name, username, password;
        int manager;

        if (textboxNewStaffName.getText().isEmpty() || textboxNewStaffUsername.getText().isEmpty() || textboxNewStaffPassword.getText().isEmpty()) {
            labelAddStaffStatus.setText("Please enter all three fields.");
        }
        else {
            name = textboxNewStaffName.getText();
            username = textboxNewStaffUsername.getText();
            password = textboxNewStaffPassword.getText();

            if (checkboxAddStaffIsManager.isSelected()) {
                manager = 1;
            } else {
                manager = 0;
            }

            BankStaff newStaff = new BankStaff(name, username, password, manager);

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
                Statement stmt = conn.createStatement(); //Create new statement object

                String sql = "INSERT INTO BankStaff " +
                        "(StaffID, Name, Username, Password, isManager) " +
                        "VALUES ('" +
                        newStaff.getStaffId() + "', '" +
                        newStaff.getStaffName() + "', '" +
                        newStaff.getStaffUsername() + "', '" +
                        newStaff.getStaffPassword() + "', '" +
                        newStaff.getIsManager() + "')";

                stmt.executeUpdate(sql);

                labelAddStaffStatus.setText("New Staff Account Has Been Created.");

                conn.close();
                getStaffFromDb();

                textboxNewStaffName.setText("");
                textboxNewStaffUsername.setText("");
                textboxNewStaffPassword.setText("");
                checkboxAddStaffIsManager.setSelected(false);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void removeStaffButton() {
        String staffId = textboxRemoveStaffId.getText();

        if (textboxRemoveStaffId.getText().isEmpty()) {
            labelRemoveStaffStatus.setText("Please enter Staff ID");
        }
        else {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
                Statement stmt = conn.createStatement(); //Create new statement object
                String sql = "DELETE FROM BankStaff WHERE StaffId = '" + staffId + "'";

                stmt.executeUpdate(sql);

                labelRemoveStaffStatus.setText("Staff Account Has Been Removed.");

                conn.close();
                getStaffFromDb();
                textboxRemoveStaffId.setText("");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void modifyStaffButton() {
        if (textboxModifyStaffId.getText().isEmpty()) {
            labelModifyStaffStatus.setText("Please enter Staff ID");
        }
        else {

            String staffId = textboxModifyStaffId.getText();
            int isManager = 0;

            if (checkboxModifyStaffIsManager.isSelected()) {
                isManager = 1;
            } else {
                isManager = 0;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
                Statement stmt = conn.createStatement(); //Create new statement object
                String sql = "UPDATE BankStaff SET isManager = '" + isManager + "' WHERE StaffID = '" + staffId + "'";

                stmt.executeUpdate(sql);

                labelModifyStaffStatus.setText("Staff Account Has Been Modified.");

                conn.close();
                getStaffFromDb();
                textboxModifyStaffId.setText("");
                checkboxModifyStaffIsManager.setSelected(false);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

        public void getStaffFromDb(){
        try {
            ArrayList<BankStaff> bankStaffList = new ArrayList<BankStaff>();
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD); //Establish database connection
            Statement stmt = conn.createStatement(); //Create new statement object

            String sql = "SELECT StaffID, Name, Username, Password, isManager FROM BankStaff";
            ResultSet rs = stmt.executeQuery(sql);

            labelStaffId.setText("");
            labelStaffName.setText("");
            labelStaffUsername.setText("");
            labelStaffPassword.setText("");
            labelStaffManager.setText("");

            while (rs.next()) {
                BankStaff newStaff = new BankStaff(rs.getString("StaffID") , rs.getString("Name"), rs.getString("Username"), rs.getString("Password"), rs.getInt("isManager"));
                bankStaffList.add(newStaff);
            }
            this.allStaff = bankStaffList;

            for(int i = 0; i < allStaff.size(); i++){
                String isManager;
                if (allStaff.get(i).getIsManager() == 1) {
                    isManager = "Yes";
                }
                else {
                    isManager = "No";
                }
                labelStaffId.setText(labelStaffId.getText() + "\n" + allStaff.get(i).getStaffId());
                labelStaffName.setText(labelStaffName.getText() + "\n" + allStaff.get(i).getStaffName());
                labelStaffUsername.setText(labelStaffUsername.getText() + "\n" + allStaff.get(i).getStaffUsername());
                labelStaffPassword.setText(labelStaffPassword.getText() + "\n" + allStaff.get(i).getStaffPassword());
                labelStaffManager.setText(labelStaffManager.getText() + "\n" + isManager);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void userSearch(ActionEvent e){
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.userAccountButton(e);
    }

    public void logout(ActionEvent e) {
        CheckingAccountScreenController controller = new CheckingAccountScreenController();
        controller.logOut(e);
    }
}
