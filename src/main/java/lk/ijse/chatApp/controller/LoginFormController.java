package lk.ijse.chatApp.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.chatApp.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginFormController {



    @FXML
    private AnchorPane welcomePane;

    @FXML
    private MFXPasswordField txtPassword;

    @FXML
    private MFXTextField txtUserName;

    private final UserModel userModel = new UserModel();


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        try {


            boolean isValidated = validateFields();
            if (!isValidated) {
                return;
            }

            boolean isUserExists = validateUser();

            if (!isUserExists) {
                return;
            }


            System.out.println("GG");


        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    private boolean validateUser() throws SQLException {
        return userModel.validateUser(txtUserName.getText(),txtPassword.getText());
    }

    private boolean validateFields() {

        String name = txtUserName.getText();

        boolean isNameValidate = Pattern.matches("[A-Za-z]{3,}", name);
        if (!isNameValidate) {
            txtUserName.requestFocus();
            txtUserName.getStyleClass().add("mfx-text-field-error");
            return false;
        }


        txtUserName.getStyleClass().removeAll("mfx-text-field-error");
        return true;
    }


    @FXML
    void btnCreateAccountOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/createAccountForm.fxml"));
        Pane registerPane = fxmlLoader.load();
        welcomePane.getChildren().clear();
        welcomePane.getChildren().add(registerPane);
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}

