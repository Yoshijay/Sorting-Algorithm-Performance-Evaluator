package com.sortingapp.sortingalgorithmevaluator.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.sortingapp.sortingalgorithmevaluator.algorithms.*;
import com.sortingapp.sortingalgorithmevaluator.models.CSVData;
import com.sortingapp.sortingalgorithmevaluator.models.SortingResult;
import com.sortingapp.sortingalgorithmevaluator.utils.FileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.util.*;

public class MainController {

    @FXML private TextField filePathField;
    @FXML private ComboBox<String> columnComboBox;
    @FXML private Button loadColumnsButton;
    @FXML private Button sortButton;

    @FXML private CheckBox insertionSortCheck;
    @FXML private CheckBox shellSortCheck;
    @FXML private CheckBox mergeSortCheck;
    @FXML private CheckBox quickSortCheck;
    @FXML private CheckBox heapSortCheck;
    @FXML private CheckBox selectAllCheck;

    @FXML private TableView<SortingResult> resultsTable;
    @FXML private TableView<Map<String, String>> dataPreviewTable;
    @FXML private Label bestAlgorithmLabel;

    private CSVData csvData;
    private ObservableList<SortingResult> results = FXCollections.observableArrayList();
    private ObservableList<Map<String, String>> previewData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize results table
        setupResultsTable();
        setupDataPreviewTable();

        // Disable sort button initially
        sortButton.setDisable(true);
        loadColumnsButton.setDisable(true);
    }

    private void setupResultsTable() {
        // Configure columns for results table
        TableColumn<SortingResult, String> algorithmCol = (TableColumn<SortingResult, String>) resultsTable.getColumns().get(0);
        TableColumn<SortingResult, Number> timeCol = (TableColumn<SortingResult, Number>) resultsTable.getColumns().get(1);
        TableColumn<SortingResult, String> statusCol = (TableColumn<SortingResult, String>) resultsTable.getColumns().get(2);

        algorithmCol.setCellValueFactory(cellData -> cellData.getValue().algorithmNameProperty());
        timeCol.setCellValueFactory(cellData -> cellData.getValue().executionTimeProperty());
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        resultsTable.setItems(results);
    }

    private void setupDataPreviewTable() {
        dataPreviewTable.setItems(previewData);
    }

    @FXML
    private void handleBrowseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePathField.setText(file.getAbsolutePath());
            loadColumnsButton.setDisable(false);

            // Clear previous data
            columnComboBox.getItems().clear();
            previewData.clear();
            results.clear();
            bestAlgorithmLabel.setText("Best Algorithm: ");
        }
    }

    @FXML
    private void handleLoadColumns() {
        try {
            File file = new File(filePathField.getText());
            csvData = FileHandler.readCSV(file);

            // Clear previous items
            columnComboBox.getItems().clear();

            // Add headers to combo box and check for numeric columns
            for (int i = 0; i < csvData.getHeaders().size(); i++) {
                String header = csvData.getHeaders().get(i);
                List<String> columnData = FileHandler.getColumnData(csvData.getRows(), i);

                if (FileHandler.isNumericColumn(columnData)) {
                    columnComboBox.getItems().add(header);
                }
            }

            // Load data preview (first 10 rows)
            loadDataPreview();

            // Enable sort button if we have numeric columns
            sortButton.setDisable(columnComboBox.getItems().isEmpty());

            if (columnComboBox.getItems().isEmpty()) {
                showAlert("No Numeric Columns", "The CSV file doesn't contain any numeric columns suitable for sorting.");
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to load CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadDataPreview() {
        previewData.clear();

        // Show first 10 rows in preview
        int previewRowCount = Math.min(10, csvData.getRows().size());
        for (int i = 0; i < previewRowCount; i++) {
            Map<String, String> rowData = new HashMap<>();
            List<String> row = csvData.getRows().get(i);

            for (int j = 0; j < csvData.getHeaders().size(); j++) {
                String header = csvData.getHeaders().get(j);
                String value = j < row.size() ? row.get(j) : "";
                rowData.put(header, value);
            }
            previewData.add(rowData);
        }

        // Setup columns for preview table
        dataPreviewTable.getColumns().clear();
        for (String header : csvData.getHeaders()) {
            TableColumn<Map<String, String>, String> column = new TableColumn<>(header);
            column.setCellValueFactory(param -> {
                Map<String, String> row = param.getValue();
                return new javafx.beans.property.SimpleStringProperty(row.get(header));
            });
            column.setPrefWidth(100);
            dataPreviewTable.getColumns().add(column);
        }
    }

    @FXML
    private void handleSelectAll() {
        boolean selected = selectAllCheck.isSelected();
        insertionSortCheck.setSelected(selected);
        shellSortCheck.setSelected(selected);
        mergeSortCheck.setSelected(selected);
        quickSortCheck.setSelected(selected);
        heapSortCheck.setSelected(selected);
    }

    @FXML
    private void handleSort() {
        if (csvData == null || columnComboBox.getValue() == null) {
            showAlert("Error", "Please select a CSV file and column first.");
            return;
        }

        // Clear previous results
        results.clear();
        bestAlgorithmLabel.setText("Best Algorithm: ");

        // Get selected column data
        String selectedColumn = columnComboBox.getValue();
        int columnIndex = csvData.getHeaders().indexOf(selectedColumn);

        // Extract and convert column data to integers
        List<String> columnData = FileHandler.getColumnData(csvData.getRows(), columnIndex);
        int[] dataToSort = FileHandler.convertToIntArray(columnData);

        if (dataToSort.length == 0) {
            showAlert("Error", "Selected column doesn't contain valid numeric data.");
            return;
        }

        // Create list of selected algorithms
        List<SortingAlgorithm> selectedAlgorithms = new ArrayList<>();
        if (insertionSortCheck.isSelected()) selectedAlgorithms.add(new InsertionSort());
        if (shellSortCheck.isSelected()) selectedAlgorithms.add(new ShellSort());
        if (mergeSortCheck.isSelected()) selectedAlgorithms.add(new MergeSort());
        if (quickSortCheck.isSelected()) selectedAlgorithms.add(new QuickSort());
        if (heapSortCheck.isSelected()) selectedAlgorithms.add(new HeapSort());

        if (selectedAlgorithms.isEmpty()) {
            showAlert("Error", "Please select at least one sorting algorithm.");
            return;
        }

        // Run sorting algorithms and measure performance
        SortingResult bestResult = null;

        for (SortingAlgorithm algorithm : selectedAlgorithms) {
            // Create a copy of the data for each algorithm
            int[] dataCopy = Arrays.copyOf(dataToSort, dataToSort.length);

            long startTime = System.nanoTime();
            try {
                algorithm.sort(dataCopy);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

                // Verify sorting is correct
                boolean isSorted = isSorted(dataCopy);
                String status = isSorted ? "Success" : "Failed";

                SortingResult result = new SortingResult(algorithm.getName(), duration, status);
                results.add(result);

                // Update best algorithm
                if (isSorted && (bestResult == null || duration < bestResult.getExecutionTime())) {
                    bestResult = result;
                }

            } catch (Exception e) {
                results.add(new SortingResult(algorithm.getName(), 0, "Error: " + e.getMessage()));
            }
        }

        // Display best algorithm
        if (bestResult != null) {
            bestAlgorithmLabel.setText("Best Algorithm: " + bestResult.getAlgorithmName() +
                    " (" + bestResult.getExecutionTime() + " ms)");
        }
    }

    @FXML
    private void handleReset() {
        // Clear all fields and results
        filePathField.clear();
        columnComboBox.getItems().clear();
        columnComboBox.setValue(null);
        previewData.clear();
        results.clear();
        bestAlgorithmLabel.setText("Best Algorithm: ");

        // Reset checkboxes
        insertionSortCheck.setSelected(false);
        shellSortCheck.setSelected(false);
        mergeSortCheck.setSelected(false);
        quickSortCheck.setSelected(false);
        heapSortCheck.setSelected(false);
        selectAllCheck.setSelected(false);

        // Reset buttons
        sortButton.setDisable(true);
        loadColumnsButton.setDisable(true);

        csvData = null;
    }

    private boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}