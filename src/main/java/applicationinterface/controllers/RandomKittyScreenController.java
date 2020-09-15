package applicationinterface.controllers;

import api.requests.managers.KittyRequests;
import api.utils.ExecutorServiceUtil;
import api.utils.FileUtils;
import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import applicationinterface.enums.SceneEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import static applicationinterface.enums.SavingPath.RANDOM_KITTY;

public class RandomKittyScreenController implements Initializable {
    private GaussianBlur blur = new GaussianBlur();
    private static ExecutorService executor = ExecutorServiceUtil.getNewExecutor(2);
    private RandomKittyFilterPopupController.Filter currentFilter;
    @FXML
    Button nextButton;
    @FXML
    ImageView catImage;
    @FXML
    BorderPane parentNode;
    @FXML
    StackPane blurableElements;

    @FXML
    public void showNewKitty(ActionEvent event) {
        Effect oldEffect = blurableElements.getEffect();
        blurableElements.setEffect(blur);
        String imageUrl = KittyRequests.getKittyRequests().getRandomKitty().getUrl();
        String kittyImageName = imageUrl.split("/")[imageUrl.split("/").length - 1];
        String pathToKittyImage = String.format("%s%s", RANDOM_KITTY, kittyImageName);
        executor.execute(() -> {
            FileUtils.saveFileFromUrl(imageUrl, pathToKittyImage);
            setCatImage(pathToKittyImage);
            blurableElements.setEffect(oldEffect);
        });
        executor.execute(() -> deleteImagesExceptNew(pathToKittyImage));
    }

    public static void deleteAllRandomKittiesImages() {
        deleteImagesExceptNew(null);
    }

    private static void deleteImagesExceptNew(String newImagePath) {
        executor.execute(() -> getListOfAvailableImages().forEach(image -> {
                    if (!image.getPath().equals(newImagePath)) {
                        image.delete();
                    }
                }));
    }

    private static List<File> getListOfAvailableImages() {
        return Arrays.asList(Objects.requireNonNull(new File(RANDOM_KITTY.toString())
                .listFiles()));
    }

    private void setCatImage(String pathToImage) {
        File imageAsFile = new File(pathToImage);
        Image image = new Image(imageAsFile.toURI().toString());
        catImage.setImage(image);
    }

    private void bindImageToScreenSize(ImageView image) {
        image.fitWidthProperty().bind(blurableElements.widthProperty());
        image.fitHeightProperty().bind(blurableElements.heightProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindImageToScreenSize(catImage);
    }

    public void back(ActionEvent event) throws IOException {
        AppKitty.getStage().setScene(SceneManager.getInstance().getScene(SceneEnum.MAIN_SCREEN));
    }

    public void filters(ActionEvent event) {
        RandomKittyFilterPopupController filterPopupController = null;
                try {
                    filterPopupController= SceneManager.getInstance().getController(SceneEnum.RANDOM_KITTY_FILTER_SCREEN);
                }catch (IOException e) {

                }

        filterPopupController.showRandomKittyFilterPopup();
        currentFilter = filterPopupController.getCurrentFilter();
        System.out.println(currentFilter.getFilterLabel());
    }
}
