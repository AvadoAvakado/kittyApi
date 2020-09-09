package applicationinterface;

import api.utils.UserPropertiesUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import sun.java2d.ScreenUpdateManager;

import java.io.IOException;
import java.util.Arrays;

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
            primaryStage.setScene(new SceneManager().getMainScreen());
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }
        primaryStage.show();
    }
}
