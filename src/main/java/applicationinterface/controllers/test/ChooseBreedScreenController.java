package applicationinterface.controllers.test;

import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import applicationinterface.controllers.Controller;
import applicationinterface.controllers.MainScreenController;
import applicationinterface.enums.SceneEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChooseBreedScreenController implements Controller {
    private static Stage chooseBreedStage;
    @FXML
    Button saveLogsButton;

    static {
        chooseBreedStage = new Stage();
        chooseBreedStage.setTitle("Choose breed");
        chooseBreedStage.initOwner(AppKitty.getStage());
        chooseBreedStage.initModality(Modality.APPLICATION_MODAL);
        chooseBreedStage.setScene(SceneManager.getInstance().getScene(SceneEnum.CHOOSE_BREED_SCREEN));
        chooseBreedStage.setMinHeight(150);
        chooseBreedStage.setMinWidth(300);
        chooseBreedStage.setMaxHeight(150);
        chooseBreedStage.setMaxWidth(300);
        chooseBreedStage.addEventHandler(WindowEvent.WINDOW_HIDDEN, event -> {
            SceneManager.getInstance().getController(MainScreenController.class).blurOff();
        });
    }

    public void showChooseBreedPopup() {
        chooseBreedStage.showAndWait();
    }

    public void clickBreedInfoButton(ActionEvent event) {

    }
}
