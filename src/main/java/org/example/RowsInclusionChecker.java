package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RowsInclusionChecker {

    public static boolean check(String file1, String file2) {

        try {
            Set<List<String>> rowsFile1 = readCsv(file1);
            Set<List<String>> rowsFile2 = readCsv(file2);

            if (rowsFile2.containsAll(rowsFile1)) {
                System.out.println("All rows of " + file1 + " are contained in " + file2);
                return true;
            } else {
                System.out.println("Not all rows of " + file1 + " are contained in " + file2);
                return false;
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        System.out.println("Something went wrong in the comparison");
        return false;
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
}
