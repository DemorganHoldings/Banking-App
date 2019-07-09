import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class InitialOptionScreenController {

    @FXML
    private TextField textboxNewCustomerSocial;

    @FXML
    private Button buttonCreateNewCustomerAccount;

    @FXML
    private Button buttonSearchExistingCustomer;

    @FXML
    private TextField textboxExistingSocialSecurity;

    @FXML
    private TextField textboxNewCustomerAddress;

    @FXML
    private TextField textboxNewCustomerName;

    @FXML
    private TextField textboxExistingCustomerName;

    @FXML
    private Button buttonLogoutInitialOptionScreen;

    @FXML
    private TextField textboxNewCustomerPhoneNum;

    @FXML
    private TextField textboxNewCustomerEmailAddress;

    @FXML
    void e91e63(ActionEvent event) {

    }

    public void createAccount(){
        String name, address, phone, email, social;

        name = textboxNewCustomerName.getText();
        address = textboxNewCustomerAddress.getText();
        phone = textboxNewCustomerPhoneNum.getText();
        email = textboxNewCustomerEmailAddress.getText();
        social = textboxNewCustomerSocial.getText();



    }
}