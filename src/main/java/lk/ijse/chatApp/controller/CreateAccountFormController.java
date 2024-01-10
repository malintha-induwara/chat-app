package lk.ijse.chatApp.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.chatApp.util.DB;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class CreateAccountFormController {

    @FXML
    private Circle circleImg;

    @FXML
    private MFXTextField txtFieldName;



    public void initialize(){
        loadDefaultImage();
        txtFieldName.requestFocus();
    }


    private void loadDefaultImage() {
        Image image = new Image("assets/images/user.png");
        circleImg.setFill(new ImagePattern(image));
    }

    @FXML
    void btnChatOnAction(ActionEvent event) throws IOException {
        boolean isValidated=validateFields();
        if (!isValidated){
            return;
        }
        ImagePattern imagePattern = (ImagePattern) circleImg.getFill();
        Image userImage = imagePattern.getImage();

        //Save on the array list
        DB.users.put(txtFieldName.getText(),userImage);

        loadChatForm();
        closeWindow();

    }

    private void loadChatForm() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/chatForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle(txtFieldName.getText()+"'s Chat");
        stage.show();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtFieldName.getScene().getWindow();
        stage.close();
    }

    private boolean validateFields() {
        String name = txtFieldName.getText();
        boolean isNameValidate= Pattern.matches("[A-Za-z]{3,}",name);
        if (!isNameValidate){
            txtFieldName.requestFocus();
            txtFieldName.getStyleClass().add("mfx-text-field-error");
            return false;
        }
        txtFieldName.getStyleClass().removeAll("mfx-text-field-error");
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
    void txtFieldNameOnAction(ActionEvent event) throws IOException {
        btnChatOnAction(event);
    }



}

