package applicationinterface.enums;

public enum SceneEnum {
    MAIN_SCREEN("/src/main/java/applicationinterface/screens/mainScreen.fxml"),
    AUTHORIZATION_POPUP("/src/main/java/applicationinterface/screens/authorizationPopup.fxml");

    private String path;

    SceneEnum(String path) {
        this.path = path;
    }

    public String getScenePath() {
        return path;
    }
}
