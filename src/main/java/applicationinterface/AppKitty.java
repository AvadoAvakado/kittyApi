package applicationinterface;

import api.utils.ExecutorServiceUtil;
import api.utils.UserPropertiesUtil;
import applicationinterface.controllers.RandomKittyScreenController;
import applicationinterface.enums.SceneEnum;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class AppKitty extends Application {
    public static UserPropertiesUtil userPropertiesUtil = UserPropertiesUtil.getUserPropertiesUtil();
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("AppKitty");
        try {
            primaryStage.setScene(SceneManager.getInstance().getScene(SceneEnum.MAIN_SCREEN));
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }
        primaryStage.setOnHiding(event -> {
            RandomKittyScreenController.deleteAllRandomKittiesImages();
            ExecutorServiceUtil.shutdownAllThreadExecutors();
        });
        primaryStage.show();
    }
}
