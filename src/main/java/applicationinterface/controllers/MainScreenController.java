package applicationinterface.controllers;

import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    public StackPane mainScreen;
    @FXML
    public Label title;
    @FXML
    public HBox bottomBar;
    @FXML
    public Button randomKittyButton;
    @FXML
    public Button makeTestButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(bottomBar, Pos.BOTTOM_CENTER);
        title.getStyleClass().add("outline");
        System.out.println(mainScreen.getWidth());
        System.out.println(mainScreen.getPrefWidth());
        bottomBar.setSpacing(20);
    }

    public void randomKittyClick(ActionEvent event) throws IOException {
        AppKitty.getStage().setScene(new SceneManager().getFirstScreen());
    }

    public void makeTestClick(ActionEvent event) throws IOException {
        new AuthorizationPopupController().showAuthorizationPopup();
        /*Stage newStage = new Stage();
        newStage.setTitle("Authorization");
        BorderPane mainPane = new BorderPane();
        VBox comp = new VBox();
        Label nameField = new Label("Enter your nickname to get access to all features");
        nameField.setAlignment(Pos.TOP_CENTER);
        TextField phoneNumber = new TextField("Nickname");
        phoneNumber.setAlignment(Pos.TOP_CENTER);
        comp.getChildren().add(nameField);
        comp.getChildren().add(phoneNumber);
        comp.setSpacing(10);
        BorderPane.setAlignment(comp, Pos.TOP_CENTER);
        HBox buttonsBar = new HBox();
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelButtonEvent -> {
            newStage.close();
        });
        buttonsBar.getChildren().addAll(confirmButton, cancelButton);
        BorderPane.setAlignment(buttonsBar, Pos.BOTTOM_CENTER);
        buttonsBar.setSpacing(50);
        comp.getChildren().add(buttonsBar);
        mainPane.getChildren().addAll(comp, buttonsBar);
        Scene stageScene = new Scene(mainPane, 300, 200);
        newStage.setMaxHeight(150);
        newStage.setMinHeight(150);
        newStage.setMaxWidth(300);
        newStage.setMinWidth(300);
        newStage.setScene(stageScene);
        newStage.initOwner(HelloWorld.getStage());
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.showAndWait();*/
    }
}
