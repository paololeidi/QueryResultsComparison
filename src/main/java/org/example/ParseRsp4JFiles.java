package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ParseRsp4JFiles {

    public static final double NUMBER_OF_QUERIES = 24;

    public static void main(String[] args) {

        for (int i = 0; i < NUMBER_OF_QUERIES; i++){
            String inputFile = "Files/Input/Rsp4J/output"+(i+1)+".csv";  // input file name
            String outputFile = "Files/Rsp4J/output"+(i+1)+".csv"; // output file name
            List<String> lines = new ArrayList<>();
            String lastTimestamp = null;

            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
                for (int j = 0; j < lines.size(); j++) {
                    String line = lines.get(j);
                    if (line.startsWith("2024")) {
                        // Write the timestamp line as is and save the timestamp
                        bw.write(line);
                        bw.newLine();
                        lastTimestamp = line.substring(0, line.indexOf(",", line.indexOf(",") + 1));
                    } else if (lastTimestamp != null && (line.matches("\\d,\\d") || line.matches("\\d"))) {
                        // Add the last saved timestamp to lines without timestamps
                        bw.write(lastTimestamp + "," + line);
                        bw.newLine();
                    } else if (line.startsWith("Exception")) {
                        // Stop writing once "Exception" is encountered
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String inputFile = "Files/Input/Rsp4J/output6.csv";  // input file name
        String outputFile = "Files/Rsp4J/output6.csv"; // output file name
        List<String> lines = new ArrayList<>();
        String lastTimestamp = null;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith("2024")) {
                    // Write the timestamp line as is and save the timestamp
                    bw.write(line);
                    bw.newLine();
                    lastTimestamp = line.substring(0, line.indexOf(",", line.indexOf(",") + 1));
                } else if (lastTimestamp != null && (line.matches("\\d,\\d") || line.matches("\\d"))) {
                    // Add the last saved timestamp to lines without timestamps
                    bw.write(lastTimestamp + "," + line);
                    bw.newLine();
                } else if (line.startsWith("Exception")) {
                    // Stop writing once "Exception" is encountered
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}