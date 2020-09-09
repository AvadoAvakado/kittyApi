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
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    /*@Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Text text1 = new Text("This is scene 1");
        text1.setBoundsType(TextBoundsType.VISUAL);

        Text text2 = new Text("This is scene 2");
        text1.setBoundsType(TextBoundsType.LOGICAL);


        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setPrefHeight(40);
        btn.setPrefWidth(200);
        final int[] i = {0};
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                btn.setText(String.format("Hello World! #%s", ++i[0]));
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        StackPane root2 = new StackPane();

        Scene scene1 = new Scene(root, 300, 250, Color.GREEN);
        Scene scene2 = new Scene(root2, 300, 250, Color.GREEN);
        primaryStage.setScene(scene1);
        VBox group1 = new VBox();
        group1.setAlignment(Pos.BASELINE_CENTER);
        VBox group2 = new VBox();
        Button btn1 = new Button();
        btn1.setText("Go to scene 2");
        btn1.setPrefHeight(40);
        btn1.setPrefWidth(200);
        //btn1.setPadding(new Insets(10, 10, 10, 10));
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene2);
            }
        });

        Button btn2 = new Button();
        btn2.setText("Go to scene 1");
        btn2.setPrefHeight(40);
        btn2.setPrefWidth(200);
        btn2.setPadding(new Insets(10, 10, 10, 10));
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene1);
            }
        });

        group1.getChildren().addAll(btn1, text1);
        group2.getChildren().addAll(btn2, text2);


        root.getChildren().add(group1);
        root2.getChildren().add(group2);
        //Scene ss = FXMLLoader.load(getClass().getResource("/src/main/java/applicationinterface/sample.fxml"));
        //primaryStage.setScene(ss);
        primaryStage.show();
    }*/

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Hello World!");
        try {
            Scene firstScreen = new SceneManager().getFirstScreen();
            //System.out.println("AAAA " + firstScreen == null);
            primaryStage.setScene(new SceneManager().getMainScreen());
        } catch (IOException e) {
            System.out.println("AAAAAAAAAAAAAAAAAAA\n" + Arrays.toString(e.getStackTrace()));
            throw new NullPointerException(e.getMessage());
        }
        primaryStage.show();
    }

    @FXML
    private void kek(ActionEvent event) {
        System.out.println("KEK");
    }
}
