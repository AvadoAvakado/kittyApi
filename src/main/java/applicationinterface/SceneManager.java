package applicationinterface;

import applicationinterface.enums.SceneEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import lombok.Data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private final Map<SceneEnum, SceneData> sceneData = new HashMap<>();

    @Data
    private class SceneData {
        private Scene scene;
        private FXMLLoader loader;

        SceneData(Scene scene, FXMLLoader loader) {
            setScene(scene);
            setLoader(loader);
        }
    }

    private static SceneManager instance = new SceneManager();

    public static SceneManager getInstance() {
        return instance;
    }

    private SceneManager() {
    }

    public <T> T getController(SceneEnum sceneEnum) throws IOException{
            if (sceneData.get(sceneEnum) == null) {
                initScene(sceneEnum);
            }

        return sceneData.get(sceneEnum).getLoader().getController();
    }

    public Scene getScene(SceneEnum sceneEnum) throws IOException{
            if (sceneData.get(sceneEnum) == null) {
                initScene(sceneEnum);
            }

        return sceneData.get(sceneEnum).getScene();
    }

    private void initScene(SceneEnum sceneEnum) throws IOException {
        FXMLLoader loader = getFxmlLoader(sceneEnum);
        Scene scene = new Scene(loader.load());
        sceneData.put(sceneEnum, new SceneData(scene, loader));
    }

    private FXMLLoader getFxmlLoader(SceneEnum scene){
        return new FXMLLoader(this.getClass().getResource(scene.getScenePath()));
    }
}
