package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CsvDifferenceFinder {

    public static void main(String[] args) {

        String file1 = "Files/Spark/output3.csv";
        String file2 = "Files/Rsp4J/output3.csv";
        String output1 = "Files/Comparison/Join/onlyInSpark.csv";
        String output2 = "Files/Comparison/Join/onlyInRsp4J.csv";

        try {
            createFileIfNotExists(output1);
            createFileIfNotExists(output2);

            Set<List<String>> rowsFile1 = readCsv(file1);
            Set<List<String>> rowsFile2 = readCsv(file2);

            Set<List<String>> onlyInFile1 = new HashSet<>(rowsFile1);
            onlyInFile1.removeAll(rowsFile2);

            Set<List<String>> onlyInFile2 = new HashSet<>(rowsFile2);
            onlyInFile2.removeAll(rowsFile1);

            writeCsv(output1, onlyInFile1);
            writeCsv(output2, onlyInFile2);

            System.out.println("Differences written to " + output1 + " and " + output2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }


    private static void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static Set<List<String>> readCsv(String filePath) throws IOException, CsvValidationException {
        Set<List<String>> rows = new HashSet<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                rows.add(List.of(nextLine));
            }
        }
        return rows;
    }

    private static void writeCsv(String filePath, Set<List<String>> rows) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            for (List<String> row : rows) {
                writer.writeNext(row.toArray(new String[0]));
            }
        }
    }
}
