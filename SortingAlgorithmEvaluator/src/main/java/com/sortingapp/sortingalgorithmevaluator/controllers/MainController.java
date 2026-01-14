package com.sortingapp.sortingalgorithmevaluator.controllers;

import com.sortingapp.sortingalgorithmevaluator.algorithms.SortingAlgorithm;
import com.sortingapp.sortingalgorithmevaluator.models.CSVData;
import com.sortingapp.sortingalgorithmevaluator.models.SortingResult;
import com.sortingapp.sortingalgorithmevaluator.utils.FileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MainController {

    // ===== T18: Table columns for attractive GUI presentation =====
    @FXML
    private TableColumn<SortingResult, String> algorithmColumn;

    @FXML
    private TableColumn<SortingResult, Number> timeColumn;

    @FXML
    private TableColumn<SortingResult, String> statusColumn;

    // ============================================================

    @FXML
    private ComboBox<String> columnComboBox;

    @FXML
    private TableView<SortingResult> resultsTable;

    @FXML
    private Label bestAlgorithmLabel;

    @FXML
    private TableView<String> dataPreviewTable;

    private CSVData csvData;

    // ===== T18: Initialize table column bindings =====
    @FXML
    private void initialize() {
        algorithmColumn.setCellValueFactory(
                data -> data.getValue().algorithmNameProperty()
        );

        timeColumn.setCellValueFactory(
                data -> data.getValue().executionTimeProperty()
        );

        statusColumn.setCellValueFactory(
                data -> data.getValue().statusProperty()
        );
    }
    // =================================================

    @FXML
    private void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                csvData = FileHandler.readCSV(file);
                columnComboBox.setItems(
                        FXCollections.observableArrayList(csvData.getHeaders())
                );
            } catch (Exception e) {
                showError("File Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleSort(ActionEvent event) {
        if (csvData == null) {
            showError("Error", "Please upload a CSV file first.");
            return;
        }

        int columnIndex = columnComboBox.getSelectionModel().getSelectedIndex();
        if (columnIndex < 0) {
            showError("Error", "Please select a column.");
            return;
        }

        if (!csvData.isNumericColumn(columnIndex)) {
            showError("Error", "Selected column is not numeric.");
            return;
        }

        double[] data = csvData.getNumericColumn(columnIndex);

        Map<String, Long> results = SortingAlgorithm.compareAllAlgorithms(data);
        List<SortingResult> sortingResults = SortingResult.fromResultMap(results);

        ObservableList<SortingResult> observableResults =
                FXCollections.observableArrayList(sortingResults);

        resultsTable.setItems(observableResults);

        bestAlgorithmLabel.setText(
                "Best Algorithm: " + SortingAlgorithm.getBestAlgorithm(results)
        );
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
