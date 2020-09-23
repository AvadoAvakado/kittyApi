package applicationinterface.controllers;

import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import applicationinterface.enums.SceneEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;

public class MainScreenController {
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

    public void randomKittyClick(ActionEvent event) {
        AppKitty.setScene(SceneEnum.RANDOM_KITTY_SCREEN);
    }

    public void makeTestClick(ActionEvent event) {
        //todo find a better way
        new AuthorizationPopupController().showAuthorizationPopup();
    }
}
