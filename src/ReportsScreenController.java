import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.*;

import java.io.FileWriter;
import java.io.PrintWriter;

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

    private String customerID;

    public void initialize(String id) {
        customerID = id;
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

}
