package applicationinterface;

import api.utils.ExecutorServiceUtil;
import api.utils.UserPropertiesUtil;
import applicationinterface.controllers.AuthorizationPopupController;
import applicationinterface.controllers.PostInitializable;
import applicationinterface.controllers.RandomKittyScreenController;
import applicationinterface.enums.SceneEnum;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppKitty extends Application {
    public static UserPropertiesUtil userPropertiesUtil = UserPropertiesUtil.getUserPropertiesUtil();
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setScene(SceneEnum sceneEnum) {
        getStage().setScene(SceneManager.getInstance()
                        .getScene(sceneEnum));
        if (SceneManager.getInstance().getController(sceneEnum) instanceof PostInitializable) {
            ((PostInitializable) SceneManager.getInstance().getController(sceneEnum)).postInitialize();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("AppKitty");
        primaryStage.setScene(SceneManager.getInstance().getScene(SceneEnum.MAIN_SCREEN));
        primaryStage.setOnHiding(event -> {
            RandomKittyScreenController.deleteAllRandomKittiesImages();
            ExecutorServiceUtil.shutdownAllThreadExecutors();
        });
        primaryStage.show();
        if (!UserPropertiesUtil.getUserPropertiesUtil().isSubIdInitialized()) {
            ((AuthorizationPopupController)SceneManager.getInstance().getController(SceneEnum.AUTHORIZATION_POPUP))
                    .showAuthorizationPopup();
        }
    }
}
