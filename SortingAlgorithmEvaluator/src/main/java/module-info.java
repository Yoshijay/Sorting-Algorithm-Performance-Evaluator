module com.sortingapp.sortingalgorithmevaluator {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sortingapp.sortingalgorithmevaluator to javafx.fxml;
    exports com.sortingapp.sortingalgorithmevaluator;
}
