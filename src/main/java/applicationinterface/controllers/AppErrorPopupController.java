package applicationinterface.controllers;

import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import applicationinterface.enums.SceneEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppErrorPopupController implements Controller {
    private static final String ERROR_MESSAGE_PATTERN = "Oops, an error has occurred on the %s screen. Please save the logs and send them to the developer";
    private Stage appErrorPopupStage;
    @FXML
    Label errorMessage;
    @FXML
    Button saveLogsButton;
    @FXML
    Button cancelButton;

    private void initStageForPopup() {
        appErrorPopupStage = new Stage();
        appErrorPopupStage.setTitle("Critical error");
        appErrorPopupStage.initOwner(AppKitty.getStage());
        appErrorPopupStage.initModality(Modality.WINDOW_MODAL);
        appErrorPopupStage.setScene(SceneManager.getInstance().getScene(SceneEnum.APP_ERROR_POPUP));
    }

    public void showAppErrorPopup(SceneEnum failedScreen) {
        if (appErrorPopupStage == null) {
            initStageForPopup();
        }
        errorMessage.setText(String.format(ERROR_MESSAGE_PATTERN, failedScreen.name()));
        appErrorPopupStage.showAndWait();
    }
}
