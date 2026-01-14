module com.sortingapp.sortingalgorithmevaluator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.sortingapp.sortingalgorithmevaluator to javafx.fxml;
    exports com.sortingapp.sortingalgorithmevaluator;
    exports com.sortingapp.sortingalgorithmevaluator.controllers;
    opens com.sortingapp.sortingalgorithmevaluator.controllers to javafx.fxml;
}