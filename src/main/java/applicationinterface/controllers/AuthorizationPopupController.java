package applicationinterface.controllers;

import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizationPopupController implements Initializable {
    private static Stage authorizationPopupStage;
    static {
        authorizationPopupStage = new Stage();
        authorizationPopupStage.setTitle("Authorization");
        authorizationPopupStage.initOwner(AppKitty.getStage());
        authorizationPopupStage.initModality(Modality.WINDOW_MODAL);
        try {
            authorizationPopupStage.setScene(new SceneManager().getAuthorizationPopup());
        } catch (IOException e) {
            //todo here should be an callin windows with error message
            System.out.println("Error in showing authorization popup");
        }
    }

    @FXML
    public BorderPane authorizationPopup;
    @FXML
    public VBox topContainer;
    @FXML
    public Label title;
    @FXML
    public TextField inputField;
    @FXML
    public Label errorMessage;
    @FXML
    public HBox bottomBar;
    @FXML
    public Button cancelButton;
    @FXML
    public Button confirmButton;

    public void clickCancelButton(ActionEvent event) {
        authorizationPopupStage.close();
    }

    public void clickConfirmButton(ActionEvent event) throws IOException{
        if (inputField.getText().isEmpty()) {
            showErrorMessage("Nickname can't be empty");
        } else if (!isNicknameLatin()) {
            showErrorMessage("Only latin characters are allowed");
        } else {
            AppKitty.userPropertiesUtil.setSubId(inputField.getText());
        }
    }

    private void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

    private boolean isNicknameLatin() {
        boolean isLatin = true;
        for(int i = 0; i < inputField.getText().length(); i++) {
            if(!Character.UnicodeBlock.of(inputField.getText().charAt(i)).equals(Character.UnicodeBlock.BASIC_LATIN)) {
                isLatin = false;
                break;
            }
        }
        return isLatin;
    }

    public void showAuthorizationPopup() {
        authorizationPopupStage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //listener for removing error message if input field value was changed
        inputField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                if (errorMessage.isVisible()) {
                    errorMessage.setVisible(false);
                }
            });
        //clear the input field on popup closing
        authorizationPopupStage.setOnHiding(event -> {
            inputField.setText("");
        });
    }
}
