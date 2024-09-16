package org.example;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DoubleValuesRounder {

    public static final int NUM_OF_QUERIES = 24;

    public static void main(String[] args) {
        ArrayList<String> systems = new ArrayList<>();
        systems.add("Flink");
        systems.add("Spark");
        systems.add("Rsp4J");
        systems.add("ksqlDB");
        systems.add("RisingWave");

        String inputPath = "Files/Input/";

        String outputPath = "Files/Output/";

        int numberOfSystems = systems.size();

        // for system in systems
        for (String s: systems){
            // for queries 13-18
            for (int i = 13; i < 19; i++) {
                // parse values, approximate double, write on file
                String readPath = inputPath + s + "/output" + i + ".csv";
                String writePath = outputPath + s + "/output" + i + ".csv";
                parseCsvFile(readPath,writePath);

            }


        }


    }

    public static void parseCsvFile(String inputFilePath, String outputFilePath) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        try {
            reader = new BufferedReader(new FileReader(inputFilePath));
            writer = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                // Check if the line has 4 columns
                if (columns.length == 4) {
                    // Parse and format the 4th column value
                    try {
                        double value = Double.parseDouble(columns[3]);
                        columns[3] = decimalFormat.format(value);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in line: " + line);
                    }

                    // Join the columns and write to the output file
                    String newLine = String.join(",", columns);
                    writer.write(newLine);
                    writer.newLine();
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
