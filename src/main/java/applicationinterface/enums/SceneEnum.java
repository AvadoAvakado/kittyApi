package applicationinterface.enums;

public enum SceneEnum {
    MAIN_SCREEN("/src/main/java/applicationinterface/screens/mainScreen.fxml"),
    AUTHORIZATION_POPUP("/src/main/java/applicationinterface/screens/authorizationPopup.fxml"),
    RANDOM_KITTY_SCREEN("/src/main/java/applicationinterface/screens/randomKittyScreen.fxml");

    private String path;

    SceneEnum(String path) {
        this.path = path;
    }

    public String getScenePath() {
        return path;
    }
}
