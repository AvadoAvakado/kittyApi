package applicationinterface;

import applicationinterface.enums.SceneEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class SceneManager {

    static Scene scene1;
    static Scene scene2;
    static Scene firstScreen;
    static Scene mainScreen;
    static Scene authorizationPopup;

    public Scene getScene1() throws IOException {
        return scene1 == null ? new Scene(FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("/src/main/java/applicationinterface/scene1.fxml")))) : scene1;
    }

    public Scene getScene2() throws IOException {
        Scene aa = new Scene(FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("/src/main/java/applicationinterface/scene2.fxml"))));
        return scene2 == null ? scene2 = new Scene(FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("/src/main/java/applicationinterface/scene2.fxml")))) : scene2;
    }

    public Scene getFirstScreen() throws IOException{
        return firstScreen == null ? firstScreen = new Scene(FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("/src/main/java/applicationinterface/firstScreen.fxml")))) : firstScreen;
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
