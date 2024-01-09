package lk.ijse.chatApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class MainFormController {


    @FXML
    private ScrollPane scrollPane;



    public void initialize(){
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");

    }



}

