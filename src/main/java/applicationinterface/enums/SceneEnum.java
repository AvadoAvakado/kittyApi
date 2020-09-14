package applicationinterface.enums;

public enum SceneEnum {
    MAIN_SCREEN("/src/main/java/applicationinterface/screens/mainScreen.fxml"),
    AUTHORIZATION_POPUP("/src/main/java/applicationinterface/screens/authorizationPopup.fxml"),
    RANDOM_KITTY_SCREEN("/src/main/java/applicationinterface/screens/randomKittyScreen.fxml"),
    RANDOM_KITTY_FILTER_SCREEN("/src/main/java/applicationinterface/screens/randomKittyFilterPopup.fxml");

    private String path;

    SceneEnum(String path) {
        this.path = path;
    }

    public String getScenePath() {
        return path;
    }
}
