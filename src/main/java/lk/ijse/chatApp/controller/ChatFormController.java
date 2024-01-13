package lk.ijse.chatApp.controller;

import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lk.ijse.chatApp.util.DB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatFormController {


    @FXML
    private ScrollPane scrollPane;


    @FXML
    private TextField txtMassage;


    @FXML
    private Text txtMemberCount;


    @FXML
    private Circle circleImg;


    @FXML
    private Text txtName;


    @FXML
    private VBox vBox;

    private String name;


    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    String message;



    public void initialize() {

        setScrollPaneTransparent();
        setChatNameAndProfilePic();

        setUserCount();

        //This line is to auto scroll down when new Message is received
        vBox.heightProperty().addListener((observableValue, oldValue, newValue) -> scrollPane.setVvalue((Double) newValue));

        //Set Listener to Observable Map to update the userount
        DB.users.addListener((MapChangeListener<String, Image>) change -> setUserCount());


        //idk wtf is this but apparently its a another lambda expression
        //Original code was
        //Runnable runnable = ()->{ socketInitialize())};

        Runnable runnable = this::socketInitialize;

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(runnable);

    }

    private void socketInitialize() {

        try {
            socket=new Socket("localhost",3030);

            do {
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
                message=inputStream.readUTF();

                Platform.runLater(()->{
                    receiveMassage("Server",message);
                });

                System.out.println(message);
            }while (!message.equals("end-chat"));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void setUserCount() {
        Platform.runLater(() -> {
            String count = String.valueOf(DB.users.size());
            txtMemberCount.setText(count);
        });
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setChatNameAndProfilePic() {
        Platform.runLater(() -> {

            //Set Name
            txtName.setText(this.name);


            //Set Image
            Image profileImage = DB.users.get(name);
            circleImg.setFill(new ImagePattern(profileImage));

        });
    }

    private void setScrollPaneTransparent() {
        Platform.runLater(() -> {
            scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
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

    private void notification(String massage) {

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

    private void receiveMassage(String sender, String massage) {


        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);

        Text senderText = new Text(sender + "\n");
        senderText.setFont(Font.font("Arial", FontWeight.BLACK, 12));


        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text(massage);
        TextFlow textFlow = new TextFlow(senderText, text);
        textFlow.setStyle(
                "-fx-color: rgb(239, 242, 255);" +
                        "-fx-background-color: rgb(255,255,255);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 20, 5, 10));
        textFlow.setLineSpacing(5);
        text.setFill(Color.rgb(0, 0, 0));

        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);

    }


    @FXML
    void btnAddUserOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/createAccountForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {

        //Remove User
        DB.users.remove(this.name);


        loadCreateAccountForm();
        closeWindow();
    }

    private void loadCreateAccountForm() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/createAccountForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtMassage.getScene().getWindow();
        stage.close();
    }


}

