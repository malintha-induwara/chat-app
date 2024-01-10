package lk.ijse.chatApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MainFormController {


    @FXML
    private ScrollPane scrollPane;


    @FXML
    private TextField txtMassage;



    @FXML
    private VBox vBox;



    public void initialize(){

        setScrollPaneTransparent();
        notification("Connected");
        receiveMassage("Hello How are you");




    }

    private void setScrollPaneTransparent() {
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        Platform.runLater(() -> {
            scrollPane.lookup(".viewport").setStyle("-fx-background-color: transparent;");
            scrollPane.lookup(".scroll-bar").setStyle("-fx-background-color: transparent;");
            scrollPane.lookup(".scroll-bar:vertical").setStyle("-fx-background-color: transparent;");

        });
    }


    @FXML
    void btnSendOnAction(MouseEvent event) {
        sendMassage();

    }


    @FXML
    void txtFieldOnAction(ActionEvent event) {
        sendMassage();
    }

    private void sendMassage() {
        String massage = txtMassage.getText();

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_RIGHT);

        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text(massage);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-color: rgb(239, 242, 255);" +
                        "-fx-background-color: rgb(15, 125, 242);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.925, 0.996));

        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);

        txtMassage.clear();
    }

    private void notification(String massage){

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text(massage);
        text.setFont(Font.font("Arial", FontWeight.BLACK, 13));
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-color: rgb(239, 242, 255);" +
                        "-fx-background-color: rgb(255,255,255);" +
                        "-fx-background-radius: 5px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.rgb(84, 83, 83));

        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);
    }

    private void receiveMassage(String massage){


        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);

        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text(massage);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-color: rgb(239, 242, 255);" +
                        "-fx-background-color: rgb(255,255,255);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.rgb(0,0,0));

        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);

    }





}

