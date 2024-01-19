package lk.ijse.chatApp.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.chatApp.model.UserModel;
import lk.ijse.chatApp.util.UserCountUtil;

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
                new Alert(Alert.AlertType.ERROR,"User Doesnt Exists").show();
                return;
            }

            if (UserCountUtil.users.containsKey(txtUserName.getText())){
                new Alert(Alert.AlertType.ERROR,"User Already Logged in ").show();
                return;
            }

            setUserCount(txtUserName.getText());
            loadChatForm();
            closeWindow();

        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.close();
    }


    private void setUserCount(String userName) throws SQLException {
        String path = userModel.getLocation(userName);
        try {
            Image image = new Image(path);
            //Save on the array list
            UserCountUtil.users.put(userName, image);
        }catch (IllegalArgumentException e){
            Image image = new Image("assets/images/users/user.png");
            //Save on the array list
            UserCountUtil.users.put(userName, image);
        }
    }


    private void loadChatForm() throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/chatForm.fxml"));
        Parent rootNode = loader.load();


        //Getting reference to the ChatController
        ChatFormController chatFormController = loader.getController();
        chatFormController.setName(txtUserName.getText());

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle(txtUserName.getText() + "'s Chat");
        stage.show();
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

