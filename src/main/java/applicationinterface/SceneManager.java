package applicationinterface;

import applicationinterface.controllers.AppErrorPopupController;
import applicationinterface.controllers.Controller;
import applicationinterface.enums.SceneEnum;
import functionalinterfaces.ConsumerWithException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import lombok.Data;

import java.io.IOException;
import java.util.*;

/**
 * @author AvadoAvakado
 */
public class SceneManager {
    private final Map<SceneEnum, SceneData> sceneData = new HashMap<>();

    @Data
    private class SceneData {
        private Scene scene;
        private FXMLLoader loader;
        private Controller controller;

        SceneData(Scene scene, FXMLLoader loader) {
            setScene(scene);
            setLoader(loader);
            setController(controller);
        }
    }

    private static SceneManager instance = new SceneManager();

    public static SceneManager getInstance() {
        return instance;
    }

    private SceneManager() {
    }

    public <T extends Controller> T getController(Class<T> controllerClass) {
        SceneEnum sceneEnum = getCorrespondingEnum(controllerClass);
        if (sceneData.get(sceneEnum) == null) {
            initScene(sceneEnum);
        }
        return (T) sceneData.get(sceneEnum).getLoader().getController();
    }

    public Controller getController(SceneEnum sceneEnum) {
        return getController(sceneEnum.getControllerClass());
    }

    private <T extends Controller> SceneEnum getCorrespondingEnum(Class<T> controllerClass) {
        Optional<SceneEnum> desiredSceneEnum = Arrays.stream(SceneEnum.values())
                .filter(sceneEnum -> controllerClass.isAssignableFrom(sceneEnum.getControllerClass()))
                .findFirst();
        return desiredSceneEnum.orElseGet(() -> {
            //todo here should be shown popup with error
            return null;
        });
    }

    public Scene getScene(SceneEnum sceneEnum) {
        if (sceneData.get(sceneEnum) == null) {
            initScene(sceneEnum);
        }
        return sceneData.get(sceneEnum).getScene();
    }

    private void initScene(SceneEnum sceneEnum) {
        ConsumerWithException<SceneEnum, IOException> loadingScene = sceneType -> {
            FXMLLoader loader = getFxmlLoader(sceneEnum);
            Scene scene = new Scene(loader.load());
            sceneData.put(sceneEnum, new SceneData(scene, loader));
        };
        try {
            loadingScene.accept(sceneEnum);
        } catch (IOException initializeScreenException) {
            try {
                //Loading app error popup scene, before calling getController() method for
                //this popup, to avoid recursion if loading app error popup scene producing exception as well
                //(it may be in initScene method inside getController method)
                loadingScene.accept(SceneEnum.APP_ERROR_POPUP);
                getController(AppErrorPopupController.class)
                        .showAppErrorPopup(sceneEnum);
            } catch (IOException initializeErrorPopupException) {
                //todo make a better logging
                initializeErrorPopupException.printStackTrace();
            }
        }
    }

    private FXMLLoader getFxmlLoader(SceneEnum scene){
        return new FXMLLoader(this.getClass().getResource(scene.getScenePath()));
    }
}
