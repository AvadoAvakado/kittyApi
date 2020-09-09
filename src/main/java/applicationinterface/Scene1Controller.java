package applicationinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import java.io.IOException;

public class Scene1Controller{
    SceneManager sceneManager = new SceneManager();

    @FXML
    Button scene1Button;

    @FXML
    public void clickScene1Button(ActionEvent event) throws IOException {
        System.out.println("AA " +( AppKitty.getStage() == null));
        AppKitty
                .getStage()
                .setScene(sceneManager
                        .getScene2());
    }
}
