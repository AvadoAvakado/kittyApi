package applicationinterface.enums;

public enum SceneEnum {
    MAIN_SCREEN("mainScreen.fxml"),
    AUTHORIZATION_POPUP("authorizationPopup.fxml"),
    RANDOM_KITTY_SCREEN("randomKittyScreen.fxml"),
    RANDOM_KITTY_FILTER_SCREEN("randomKittyFilterPopup.fxml"),
    APP_ERROR_POPUP("appErrorPopup.fxml");

    private static final String screensFolderPath = "/src/main/java/applicationinterface/screens/";
    private String path;

    SceneEnum(String fileName) {
        this.path = screensFolderPath + fileName;
    }

    public String getScenePath() {
        return path;
    }
}
