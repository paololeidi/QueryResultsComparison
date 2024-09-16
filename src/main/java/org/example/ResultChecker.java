package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultChecker {

    public static final int NUM_OF_QUERIES = 24;

    public static void main(String[] args) {

        ArrayList<String> systems = new ArrayList<>();
        systems.add("Flink");
        systems.add("Spark");
        systems.add("Rsp4J");
        systems.add("ksqlDB");
        systems.add("RisingWave");

        String path = "Files/Output/";

        int numberOfSystems = systems.size();

        int[][][] outcome = new int[NUM_OF_QUERIES][numberOfSystems][numberOfSystems];
        // for every query
        for (int i=0; i<24; i++) {
            String filename = "output" + (i + 1) + ".csv";
            // for every system1
            for (String s1: systems) {
                // for every systems2
                for (String s2: systems) {
                    // check if the results are the same, included or neither
                    if(s1.equals(s2)){
                        continue;
                    }
                    boolean equality = RowsEqualityChecker.check(path+s1+"/"+filename,path+s2+"/"+filename);
                    System.out.println(i + 1 +"         "+s1+"         "+s2+"        "+"equality         "+equality);

                    int i1 = systems.indexOf(s1);
                    int i2 = systems.indexOf(s2);

                    outcome[i][i1][i2]=1;

                    if (!equality){
                        boolean inclusion = RowsInclusionChecker.check(path+s1+"/"+filename,path+s2+"/"+filename);
                        System.out.println(i + 1 +"         "+s1+"         "+s2+"        "+"inclusion         "+inclusion);
                        outcome[i][i1][i2]=2;
                        if (!inclusion){
                            outcome[i][i1][i2]=3;
                        }
                    }

                }
            }
        }

        for (int i=0;i<24;i++){
            printMatrixAsTable(outcome[i],i+1);
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

    public static void printMatrixAsTable(int[][] matrix, int q) {
        System.out.println("query "+ (q));
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%-5d", matrix[i][j]); // Adjust the width as needed
            }
            System.out.println();
        }
    }
}
