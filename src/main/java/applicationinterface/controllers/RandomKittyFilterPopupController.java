package applicationinterface.controllers;

import api.enums.Categories;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RandomKittyFilterPopupController implements Initializable {
    private static final String noneFilterLabel = "None";
    private static final int SPACING_BETWEEN_COLUMNS = 10;

    private ToggleGroup filtersGroup;
    private String currentFilter = noneFilterLabel;


    @FXML
    public HBox columnContainer;
    @FXML
    public VBox leftColumn;
    @FXML
    public VBox rightColumn;
    @FXML
    public Button applyButton;
    @FXML
    public Button cancelButton;

    String getCurrentFilter() {
        return currentFilter;
    }

    private RadioButton configureFilterButton(RadioButton buttonToConfigure) {
        buttonToConfigure.setToggleGroup(filtersGroup);
        VBox.setVgrow(buttonToConfigure, Priority.ALWAYS);
        buttonToConfigure.setMaxHeight(Double.MAX_VALUE);
        buttonToConfigure.setMaxWidth(Double.MAX_VALUE);
        return buttonToConfigure;
    }

    private void fillVBoxWithRadioButtons(VBox vBoxToFill, Categories... category) {
        List<RadioButton> radioButtons = Arrays.stream(category).map(filterValue -> {
            RadioButton filterButton = new RadioButton(filterValue.name());
            return configureFilterButton(filterButton);
        }).collect(Collectors.toList());
        vBoxToFill.getChildren().addAll(radioButtons);
    }

    public void cancelButtonClick(ActionEvent event) {
        ((Stage)applyButton.getScene().getWindow()).close();
    }

    public void applyButtonClick(ActionEvent event) {
        currentFilter = ((RadioButton)filtersGroup.getSelectedToggle()).getText();
        System.out.println(currentFilter);
        ((Stage)applyButton.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setMargin(leftColumn, new Insets(0,0,0,SPACING_BETWEEN_COLUMNS));
        columnContainer.setSpacing(SPACING_BETWEEN_COLUMNS);
        filtersGroup = new ToggleGroup();
        Categories[] categories = Categories.values();
        int separateNumber = categories.length % 2 == 1 ? categories.length / 2 + 1 : categories.length / 2;
        Categories[] buttonsForLeftColumn = Arrays.copyOfRange(categories, 0, separateNumber);
        Categories[] buttonsForRightColumn = Arrays.copyOfRange(categories, separateNumber, categories.length);
        fillVBoxWithRadioButtons(leftColumn, buttonsForLeftColumn);
        fillVBoxWithRadioButtons(rightColumn, buttonsForRightColumn);
        //in case number of categories is odd, adding invisible additional button to make columns symmetric
        if (categories.length % 2 == 1) {
            RadioButton regionToMakeColumnSymmetric = new RadioButton(noneFilterLabel);
            configureFilterButton(regionToMakeColumnSymmetric);
            rightColumn.getChildren().add(regionToMakeColumnSymmetric);
        }
    }
}
