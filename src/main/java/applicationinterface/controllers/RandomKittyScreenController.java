package applicationinterface.controllers;

import api.kittymodels.Kitty;
import api.requests.managers.KittyRequests;
import api.utils.ExecutorServiceUtil;
import api.utils.FileUtils;
import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import applicationinterface.enums.SceneEnum;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import static applicationinterface.enums.SavingPath.RANDOM_KITTY;

public class RandomKittyScreenController implements Initializable, PostInitializable, Controller {
    private GaussianBlur blur = new GaussianBlur();
    private static ExecutorService executor = ExecutorServiceUtil.getNewExecutor(2);
    private RandomKittyFilterPopupController.Filter currentFilter;
    private DropShadow pictureShadowEffect = new DropShadow(BlurType.GAUSSIAN, Color.web("rgba(154, 18, 179, 1)"), 10, 0.5, 0, 0);
    {
        RandomKittyFilterPopupController filterPopupController = SceneManager.getInstance().getController(RandomKittyFilterPopupController.class);
        currentFilter = filterPopupController.getCurrentFilter();
    }
    @FXML
    Button nextButton;
    @FXML
    ImageView catImage;
    @FXML
    StackPane parentNode;
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

    private DoubleBinding bindToParentWithMargin(ReadOnlyDoubleProperty dimensionProperty, int dimensionValueToConstantMargin) {
        return new DoubleBinding() {
            {
                super.bind(dimensionProperty);
            }
            @Override
            protected double computeValue() {
                if (parentNode.getHeight() > dimensionValueToConstantMargin) {
                    return dimensionProperty.getValue() - 30;
                } else {
                    return dimensionProperty.getValue() * 0.9;
                }
            }
        };
    }

    private void bindImageToScreenSize(ImageView image) {
        image.fitWidthProperty().bind(bindToParentWithMargin(blurableElements.widthProperty(),
                600));
        image.fitWidthProperty().addListener((a, b, c) -> {});
        image.fitHeightProperty().bind(bindToParentWithMargin(blurableElements.heightProperty(),
                400));
        image.fitHeightProperty().addListener((a, b, c) -> {});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindImageToScreenSize(catImage);
        blurableElements.setEffect(pictureShadowEffect);
    }

    @Override
    public void postInitialize() {
        parentNode.setMargin(blurableElements,
                new Insets(0, 0, nextButton.getHeight() + 5, 0));
    }

    public void back(ActionEvent event){
        AppKitty.setScene(SceneEnum.MAIN_SCREEN);
    }

    public void filters(ActionEvent event) {
        RandomKittyFilterPopupController filterPopupController;
        filterPopupController = SceneManager.getInstance().getController(RandomKittyFilterPopupController.class);
        filterPopupController.showRandomKittyFilterPopup();
        currentFilter = filterPopupController.getCurrentFilter();
    }
}
