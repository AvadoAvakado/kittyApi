package applicationinterface;

import applicationinterface.enums.SceneEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneManager {

    static Scene randomKittyScreen;
    static Scene mainScreen;
    static Scene authorizationPopup;

    public Scene getFirstScreen() throws IOException{
        return randomKittyScreen == null ? randomKittyScreen = loadScene(SceneEnum.RANDOM_KITTY_SCREEN) : randomKittyScreen;
    }

    public Scene getMainScreen() throws IOException {
        return mainScreen == null ? mainScreen = loadScene(SceneEnum.MAIN_SCREEN) : mainScreen;
    }

    public Scene getAuthorizationPopup() throws IOException {
        return authorizationPopup == null ? authorizationPopup = loadScene(SceneEnum.AUTHORIZATION_POPUP) : authorizationPopup;
    }

    private Scene loadScene(SceneEnum sceneEnum) throws IOException{
        return new Scene(FXMLLoader.load(this.getClass().getResource(sceneEnum.getScenePath())));
    }
}
