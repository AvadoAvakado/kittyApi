package applicationinterface.controllers;

import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import applicationinterface.controllers.test.ChooseBreedScreenController;
import applicationinterface.enums.SceneEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;


public class MainScreenController implements Controller {
    private GaussianBlur blur = new GaussianBlur();
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
        blurOn();
        SceneManager.getInstance().getController(ChooseBreedScreenController.class)
                .showChooseBreedPopup();
    }

    public void blurOn() {
        mainScreen.setEffect(blur);
    }

    public void blurOff() {
        mainScreen.setEffect(null);
    }
}
