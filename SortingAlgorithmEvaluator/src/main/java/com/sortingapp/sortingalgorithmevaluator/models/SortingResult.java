package com.sortingapp.sortingalgorithmevaluator.models;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class SortingResult {

    private final SimpleStringProperty algorithmName;
    private final SimpleLongProperty executionTime;
    private final SimpleStringProperty status;

    public SortingResult(String algorithmName, long executionTime, String status) {
        this.algorithmName = new SimpleStringProperty(algorithmName);
        this.executionTime = new SimpleLongProperty(executionTime);
        this.status = new SimpleStringProperty(status);
    }

    // JavaFX property methods (for TableView binding)
    public SimpleStringProperty algorithmNameProperty() {
        return algorithmName;
    }

    public SimpleLongProperty executionTimeProperty() {
        return executionTime;
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    // Standard getters
    public String getAlgorithmName() {
        return algorithmName.get();
    }

    public long getExecutionTime() {
        return executionTime.get();
    }

    public String getStatus() {
        return status.get();
    }

    // ✅ T17: formatted execution time for display
    public String getFormattedExecutionTime() {
        return String.format("%.3f ms", executionTime.get() / 1_000_000.0);
    }
}
