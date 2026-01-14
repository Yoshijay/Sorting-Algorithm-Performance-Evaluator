package com.sortingapp.sortingalgorithmevaluator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Starting Sorting Algorithm Evaluator...");

            // Load FXML - using getResource with correct path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sortingapp/sortingalgorithmevaluator/main.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 900, 700);

            primaryStage.setTitle("Sorting Algorithm Performance Evaluator");
            primaryStage.setScene(scene);

            // Prevent window resizing during initialization to avoid tracking rect issues
            primaryStage.setResizable(false);

            System.out.println("Application started successfully!");
            primaryStage.show();

            // Re-enable resizing after window is fully shown
            Platform.runLater(() -> primaryStage.setResizable(true));

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Failed to load application", e.getMessage());
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Application Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.out.println("Launching JavaFX Application...");

        // Set properties to handle macOS issues
        System.setProperty("glass.disableGrab", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("javafx.animation.fullspeed", "true");

        try {
            launch(args);
        } catch (Exception e) {
            System.err.println("Failed to launch application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

//done all