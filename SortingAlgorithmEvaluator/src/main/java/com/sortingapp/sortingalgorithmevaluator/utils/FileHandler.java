package com.sortingapp.sortingalgorithmevaluator.utils;
import com.sortingapp.sortingalgorithmevaluator.models.CSVData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static CSVData readCSV(File file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("No file selected.");
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("Selected file does not exist.");
        }

        if (!file.getName().toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid file type. Please upload a CSV file.");
        }

        List<String> headers = new ArrayList<>();
        List<List<String>> rows = new ArrayList<>();

        

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (isFirstLine) {
                    for (String header : values) {
                        headers.add(header.trim().replace("\"", ""));
                    }
                    isFirstLine = false;
                } else {
                    List<String> row = new ArrayList<>();
                    for (String value : values) {
                        row.add(value.trim().replace("\"", ""));
                    }
                    rows.add(row);
                }
            }

            if (headers.isEmpty()) {
                throw new RuntimeException("CSV file is empty or invalid.");
            }

            if (rows.isEmpty()) {
                throw new RuntimeException("CSV file contains no data.");
            }

        } catch (Exception e) {
            throw new Exception("Error reading CSV file: " + e.getMessage());
        }


        return new CSVData(headers, rows, file.getAbsolutePath());
    }
    
    public static boolean isNumericColumn(List<String> columnData) {
        for (String value : columnData) {
            if (!value.isEmpty()) {
                try {
                    Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<String> getColumnData(List<List<String>> rows, int columnIndex) {
        List<String> columnData = new ArrayList<>();
        for (List<String> row : rows) {
            if (columnIndex < row.size()) {
                columnData.add(row.get(columnIndex));
            }
        }
        return columnData;
    }

    public static int[] convertToIntArray(List<String> stringList) {
        return stringList.stream()
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}