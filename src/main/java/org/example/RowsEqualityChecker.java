package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RowsEqualityChecker {

    public static boolean check(String file1, String file2) {

        try {
            Set<String> file1Rows = readCSV(file1);
            Set<String> file2Rows = readCSV(file2);

            if (file1Rows.equals(file2Rows) /*&& file1Rows.equals(file3Rows) && file1Rows.equals(file4Rows)*/) {
                System.out.println("All CSV files contain the same rows.");
                return true;
            } else {
                System.out.println("The CSV files do not contain the same rows.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Something went wrong in the comparison");
        return false;
    }

    private static Set<String> readCSV(String filePath) throws IOException {
        Set<String> rows = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line);
            }
        }
        return rows;
    }
}

