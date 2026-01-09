package com.sortingapp.sortingalgorithmevaluator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/sortingapp/sortingalgorithmevaluator/main.fxml")
        );

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Sorting Algorithm Performance Evaluator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
