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
import lk.ijse.chatApp.model.UserModel;
import lk.ijse.chatApp.util.UserCountUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
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


    UserModel userModel = new UserModel();

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

    private boolean validateFields() throws SQLException {

        String name = txtUserName.getText();

        boolean isNameValidate = Pattern.matches("[A-Za-z]{3,}", name);
        if (!isNameValidate) {
            txtUserName.requestFocus();
            txtUserName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        //Check User Already exists
        boolean isUserExists = userModel.isExistsUser(name);
        if (isUserExists) {
            txtUserName.requestFocus();
            txtUserName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        //CheckPassword are Match
        if (!(txtPassword.getText().equals(txtPasswordReEnter.getText()))){
            txtPassword.requestFocus();
            txtPasswordReEnter.requestFocus();

            txtPassword.getStyleClass().add("mfx-text-field-error");
            txtPasswordReEnter.getStyleClass().add("mfx-text-field-error");
            return false;
        }



        txtUserName.getStyleClass().removeAll("mfx-text-field-error");
        txtPassword.getStyleClass().removeAll("mfx-text-field-error");
        txtPasswordReEnter.getStyleClass().removeAll("mfx-text-field-error");
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
    void btnCreateAccountOnAction(ActionEvent event) throws IOException {
        try {
            boolean isValidated = validateFields();
            if (!isValidated) {
                return;
            }

            String url = imageSave();

            //Save User
            boolean isSaved = userModel.saveUser(txtUserName.getText(),txtPassword.getText(),url);

           if (!isSaved){
               new Alert(Alert.AlertType.ERROR,"Something went wrong user didnt saved").show();
               return;
           }

           loadLoginForm();

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void loadLoginForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Pane registerPane = fxmlLoader.load();
        createAccountPane.getChildren().clear();
        createAccountPane.getChildren().add(registerPane);
    }

    private String imageSave() {
        try {
            ImagePattern imagePattern = (ImagePattern) circleImg.getFill();
            Image userImage = imagePattern.getImage();
            URI uri = new URI(userImage.getUrl());

            File file = new File(uri);
            String sourceLocation = file.getAbsolutePath();

            //Copy File to resources user folder

            if (!(sourceLocation.equals("/home/syrex/Desktop/chat-app/src/main/resources/assets/images/users/user.png"))) {
                Path sourcePath = file.toPath();
                Path destinationPath = Paths.get("/home/syrex/Desktop/chat-app/src/main/resources/assets/images/users/" + file.getName());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

                return "assets/images/users/"+file.getName();
            }

            return "assets/images/users/user.png";

        } catch (URISyntaxException | IOException e) {
            new Alert(Alert.AlertType.ERROR,"Check The File Path").show();
            throw new RuntimeException(e);
        }
    }
}

