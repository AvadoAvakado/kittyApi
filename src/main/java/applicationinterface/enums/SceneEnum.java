package applicationinterface.enums;

import applicationinterface.controllers.*;
import applicationinterface.controllers.test.ChooseBreedScreenController;

public enum SceneEnum {
    MAIN_SCREEN("mainScreen.fxml", MainScreenController.class),
    AUTHORIZATION_POPUP("authorizationPopup.fxml", AuthorizationPopupController.class),
    RANDOM_KITTY_SCREEN("randomKittyScreen.fxml", RandomKittyScreenController.class),
    RANDOM_KITTY_FILTER_SCREEN("randomKittyFilterPopup.fxml", RandomKittyFilterPopupController.class),
    APP_ERROR_POPUP("appErrorPopup.fxml", AppErrorPopupController.class),
    CHOOSE_BREED_SCREEN("test/chooseBreedScreen.fxml", ChooseBreedScreenController.class);

    private static final String screensFolderPath = "/src/main/java/applicationinterface/screens/";
    private String path;
    private Class<? extends Controller> controllerClass;

    <T extends Controller> SceneEnum(String fileName, Class<T> controllerClass) {
        this.path = screensFolderPath + fileName;
        this.controllerClass = controllerClass;
    }

    public String getScenePath() {
        return path;
    }

    public Class<? extends Controller> getControllerClass() {
        return controllerClass;
    }
}
