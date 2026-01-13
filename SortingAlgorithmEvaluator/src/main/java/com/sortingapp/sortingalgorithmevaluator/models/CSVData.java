package com.sortingapp.sortingalgorithmevaluator.models;

import java.util.List;

public class CSVData {
    private List<String> headers;
    private List<List<String>> rows;
    private String filePath;

    public CSVData(List<String> headers, List<List<String>> rows, String filePath) {
        this.headers = headers;
        this.rows = rows;
        this.filePath = filePath;
    }

    public List<String> getHeaders() { return headers; }
    public void setHeaders(List<String> headers) { this.headers = headers; }

    public List<List<String>> getRows() { return rows; }
    public void setRows(List<List<String>> rows) { this.rows = rows; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}