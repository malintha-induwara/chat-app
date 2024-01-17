package lk.ijse.chatApp.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class WelcomeFormController {



    @FXML
    private AnchorPane welcomePane;

    @FXML
    private MFXPasswordField txtPassword;

    @FXML
    private MFXTextField txtUsername;


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/createAccountForm.fxml"));
        Pane registerPane = fxmlLoader.load();
        welcomePane.getChildren().clear();
        welcomePane.getChildren().add(registerPane);
    }


    @FXML
    void btnCreateAccountOnAction(ActionEvent event) {

    }


}

