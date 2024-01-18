package lk.ijse.chatApp.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.chatApp.util.UserCountUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class CreateAccountFormController {

    @FXML
    private Circle circleImg;

    @FXML
    private AnchorPane createAccountPane;


    @FXML
    private MFXTextField txtUserName;


    @FXML
    private MFXPasswordField txtPassword;

    @FXML
    private MFXPasswordField txtPasswordReEnter;



    public void initialize() {
        loadDefaultImage();
        txtUserName.requestFocus();
    }


    private void loadDefaultImage() {
        Image image = new Image("assets/images/users/user.png");
        circleImg.setFill(new ImagePattern(image));
    }

    @FXML
    void btnChatOnAction(ActionEvent event) throws IOException {


        //Save on the array list
//        UserCountUtil.users.put(txtUserName.getText(), userImage);
//
//        loadChatForm();
//        closeWindow();

    }

    private void loadChatForm() throws IOException {


    }

    private void closeWindow() {
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.close();
    }

    private boolean validateFields() {

        String name = txtUserName.getText();

        boolean isNameValidate = Pattern.matches("[A-Za-z]{3,}", name);
        if (!isNameValidate) {
            txtUserName.requestFocus();
            txtUserName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        //Check User Already exists
        boolean isUserExists = UserCountUtil.users.containsKey(name);
        if (isUserExists) {
            txtUserName.requestFocus();
            txtUserName.getStyleClass().add("mfx-text-field-error");
            return false;
        }


        txtUserName.getStyleClass().removeAll("mfx-text-field-error");
        return true;
    }


    @FXML
    void circleImgOnAction(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        Window window = ((Node) event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            Image selectedImage = new Image(file.toURI().toString());
            circleImg.setFill(new ImagePattern(selectedImage));
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Pane registerPane = fxmlLoader.load();
        createAccountPane.getChildren().clear();
        createAccountPane.getChildren().add(registerPane);
    }

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) {
        boolean isValidated = validateFields();
        if (!isValidated) {
            return;
        }

        String url = imageSave();


    }

    private String imageSave() {
        try {
            ImagePattern imagePattern = (ImagePattern) circleImg.getFill();
            Image userImage = imagePattern.getImage();
            URI uri = new URI(userImage.getUrl());

            File file = new File(uri);

            return file.getAbsolutePath();
        } catch (URISyntaxException e) {
            new Alert(Alert.AlertType.ERROR,"Check The File Path").show();
            throw new RuntimeException(e);
        }
    }
}

