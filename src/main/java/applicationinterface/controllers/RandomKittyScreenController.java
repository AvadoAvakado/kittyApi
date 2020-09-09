package applicationinterface.controllers;

import applicationinterface.enums.SavingPath;
import api.kittymodels.Kitty;
import api.requests.managers.KittyRequests;
import api.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RandomKittyScreenController implements Initializable {
    private static final String pathToDefaultCatImage = "/kittyPictures/appimages/noRandomCatImage.png";

    @FXML
    Button nextButton;

    @FXML
    public ImageView catImage;

    @FXML
    StackPane parentNode;

    @FXML
    public void showNewKitty(ActionEvent event) throws IOException {
        Kitty randomKitty = KittyRequests.getKittyRequests().getRandomKitty();
        String imageUrl = randomKitty.getUrl();
        String kittyImageName = imageUrl.split("/")[imageUrl.split("/").length - 1];
        String pathToKittyImage = String.format("%s%s", SavingPath.RANDOM_KITTY, kittyImageName);
        FileUtils.saveFileFromUrl(randomKitty.getUrl(), pathToKittyImage);
        setCatImage(pathToKittyImage);
    }

    private void setCatImage(String pathToImage) {
        File imageAsFile = new File(pathToImage);
        Image image = new Image(imageAsFile.toURI().toString());
        catImage.setImage(image);
    }

    private void bindImageToScreenSize() {
        catImage.fitWidthProperty().bind(parentNode.widthProperty());
        catImage.fitHeightProperty().bind(parentNode.heightProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindImageToScreenSize();
    }
}
