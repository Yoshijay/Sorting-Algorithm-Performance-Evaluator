package com.sortingapp.sortingalgorithmevaluator.utils;
import com.sortingapp.sortingalgorithmevaluator.models.CSVData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static CSVData readCSV(File file) throws Exception {
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
        }

        return new CSVData(headers, rows, file.getAbsolutePath());
    }

}