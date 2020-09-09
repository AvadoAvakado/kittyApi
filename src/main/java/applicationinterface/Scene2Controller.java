package applicationinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Scene2Controller {
    SceneManager sceneManager = new SceneManager();
    @FXML
    Button scene2Button;

    @FXML
    public void clickScene2Button(ActionEvent event) throws IOException {
        AppKitty.getStage().setScene(sceneManager.getScene1());
    }
}
