package applicationinterface.controllers;

import api.enums.Categories;
import applicationinterface.AppKitty;
import applicationinterface.SceneManager;
import applicationinterface.enums.SceneEnum;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RandomKittyFilterPopupController implements Initializable {
    private static final String NONE_FILTER_LABEL = "None";
    private static final int SPACING_BETWEEN_COLUMNS = 10;
    private Stage randomKittyFilterPopupStage;
    private ToggleGroup filtersGroup;
    private Filter currentFilter = Filter.getEmptyFilter();
    private List<Filter> filters;
    private List<RadioButton> filtersButton;

    {
        filters = Arrays.stream(Categories.values()).map(Filter::new).collect(Collectors.toList());
        filters.add(new Filter());
        filtersButton = filters.stream().map(filter -> {
            RadioButton filterButton = new RadioButton(filter.getFilterLabel());
            return configureFilterButton(filterButton);
        }).collect(Collectors.toList());
    }

    @Getter
    static class Filter {
        @Getter(AccessLevel.NONE)
        private static Filter emptyFilter;
        private Categories category;
        private String filterLabel;

        static Filter getEmptyFilter() {
            return emptyFilter == null ? emptyFilter = new Filter() : emptyFilter;
        }

        private Filter(Categories category) {
            this.category = category;
            this.filterLabel = this.category.name();
        }

        private Filter() {
            this.category = null;
            this.filterLabel = NONE_FILTER_LABEL;
        }

        @Override
        public boolean equals(Object object) {
            boolean result = false;
            boolean isCategoriesEqual = this.getCategory() == null ? ((Filter) object).getCategory() == null
                    : this.getCategory().equals(((Filter) object).getCategory());
            if (object instanceof Filter && isCategoriesEqual
                    && ((Filter) object).getFilterLabel().equals(this.getFilterLabel())) {
                result = true;
            }
            return result;
        }
    }

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

    Filter getCurrentFilter() {
        return currentFilter;
    }

    private RadioButton configureFilterButton(RadioButton buttonToConfigure) {
        buttonToConfigure.setToggleGroup(filtersGroup);
        VBox.setVgrow(buttonToConfigure, Priority.ALWAYS);
        buttonToConfigure.setMaxHeight(Double.MAX_VALUE);
        buttonToConfigure.setMaxWidth(Double.MAX_VALUE);
        return buttonToConfigure;
    }

    public void cancelButtonClick(ActionEvent event) {
        ((Stage) applyButton.getScene().getWindow()).close();
    }

    public void applyButtonClick(ActionEvent event) {
        String currentToggleLabel = ((RadioButton) filtersGroup.getSelectedToggle()).getText();
        currentFilter = filters.stream().filter(filter -> filter.getFilterLabel().equals(currentToggleLabel)).findFirst().orElseGet(() -> {
            //todo do better
            System.out.println("Selected toggle's lable does not match with existing filters");
            return Filter.getEmptyFilter();
        });
        System.out.println(currentFilter.getFilterLabel());
        ((Stage) applyButton.getScene().getWindow()).close();
    }

    private void initStageForPopup() {
        randomKittyFilterPopupStage = new Stage();
        randomKittyFilterPopupStage.setTitle("Filters");
        randomKittyFilterPopupStage.initOwner(AppKitty.getStage());
        randomKittyFilterPopupStage.initModality(Modality.WINDOW_MODAL);
        randomKittyFilterPopupStage.setScene(SceneManager.getInstance().getScene(SceneEnum.RANDOM_KITTY_FILTER_SCREEN));
    }

    public void showRandomKittyFilterPopup() {
        if (randomKittyFilterPopupStage == null) {
            initStageForPopup();
        }
        randomKittyFilterPopupStage.showAndWait();
    }

    private void fillVBoxWithRadioButtons(VBox vBoxToFill, List<RadioButton> filtersButton) {
        filtersButton.forEach(this::configureFilterButton);
        vBoxToFill.getChildren().addAll(filtersButton);
    }

    private RadioButton getFilterButton(Filter filter) {
        return filtersButton.stream().filter(filterButton -> filterButton.getText().equals(filter.getFilterLabel()))
                .findFirst().orElseGet(() -> {
                    //todo do better
                    System.out.println("Selected toggle's lable does not match with existing filters");
                    return filtersButton.stream().filter(filterButton -> filterButton.getText().equals(NONE_FILTER_LABEL)).findFirst().get();
                });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox.setMargin(leftColumn, new Insets(0, 0, 0, SPACING_BETWEEN_COLUMNS));
        columnContainer.setSpacing(SPACING_BETWEEN_COLUMNS);
        filtersGroup = new ToggleGroup();
        int separateNumber = filtersButton.size() % 2 == 1 ? filtersButton.size() / 2 + 1 : filtersButton.size() / 2;
        List<RadioButton> buttonsForLeftColumn = filtersButton.subList(0, separateNumber);
        List<RadioButton> buttonsForRightColumn = filtersButton.subList(separateNumber, filtersButton.size());
        fillVBoxWithRadioButtons(leftColumn, buttonsForLeftColumn);
        fillVBoxWithRadioButtons(rightColumn, buttonsForRightColumn);
        filtersGroup.selectToggle(getFilterButton(currentFilter));
    }
}
