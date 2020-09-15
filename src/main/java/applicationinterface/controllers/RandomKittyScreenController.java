package applicationinterface.controllers;

import api.kittymodels.Kitty;
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
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private DropShadow pictureShadowEffect = new DropShadow(BlurType.GAUSSIAN, Color.web("rgba(154, 18, 179, 1)"), 10, 0.5, 0, 0);
    {
        try {
            RandomKittyFilterPopupController filterPopupController = SceneManager.getInstance().getController(SceneEnum.RANDOM_KITTY_FILTER_SCREEN);
            currentFilter = filterPopupController.getCurrentFilter();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
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
        blurableElements.setEffect(blur);
        String imageUrl = getRandomKittyMatchingFilter(currentFilter).getUrl();
        String kittyImageName = imageUrl.split("/")[imageUrl.split("/").length - 1];
        String pathToKittyImage = String.format("%s%s", RANDOM_KITTY, kittyImageName);
        executor.execute(() -> {
            FileUtils.saveFileFromUrl(imageUrl, pathToKittyImage);
            setCatImage(pathToKittyImage);
            blurableElements.setEffect(pictureShadowEffect);
        });
        executor.execute(() -> deleteImagesExceptNew(pathToKittyImage));
    }

    private Kitty getRandomKittyMatchingFilter(RandomKittyFilterPopupController.Filter filter) {
        Kitty kitty;
        if (filter.equals(RandomKittyFilterPopupController.Filter.getEmptyFilter())) {
            kitty = KittyRequests.getKittyRequests().getRandomKitty();
        } else {
            kitty = KittyRequests.getKittyRequests().getKittiesByCategory(1, filter.getCategory()).get(0);
        }
        return kitty;
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
        blurableElements.setEffect(pictureShadowEffect);
    }

    public void back(ActionEvent event) throws IOException {
        AppKitty.getStage().setScene(SceneManager.getInstance().getScene(SceneEnum.MAIN_SCREEN));
    }

    public void filters(ActionEvent event) {
        RandomKittyFilterPopupController filterPopupController = null;
        try {
            filterPopupController = SceneManager.getInstance().getController(SceneEnum.RANDOM_KITTY_FILTER_SCREEN);
        } catch (IOException e) {

        }
        filterPopupController.showRandomKittyFilterPopup();
        currentFilter = filterPopupController.getCurrentFilter();
        System.out.println(currentFilter.getFilterLabel());
    }
}
