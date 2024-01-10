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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.chatApp.util.DB;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class WelcomeFormController {



    @FXML
    private AnchorPane welcomePane;

    
    @FXML
    void btnLetsGoOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/createAccountForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        welcomePane.getChildren().clear();
        welcomePane.getChildren().add(registerPane);
    }



}

