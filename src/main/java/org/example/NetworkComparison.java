package org.example;

import java.io.File;

public class NetworkComparison {
    public static void main(String[] args) {
        String directoryPath = "Files/Output/Network/";

        File fastDirectory = new File(directoryPath+"fast/");
        File slowDirectory = new File(directoryPath+"slow/");

        File[] fastFilesList = fastDirectory.listFiles();
        File[] slowFilesList = fastDirectory.listFiles();

        compareFiles(fastFilesList);
        compareFiles(slowFilesList);

    }

    private static void compareFiles(File[] filesList) {
        for (File file1: filesList){
            String fileName1 = file1.getName();
            for (File file2: filesList){
                String fileName2 = file2.getName();

                if(fileName1.equals(fileName2)){
                    continue;
                }

                boolean equality = RowsEqualityChecker.check(file1.getAbsolutePath(),file2.getAbsolutePath());
                if (equality)
                    System.out.println(file1.getName() + "         " + file2.getName() + "        "+"equality         "+ true);

                else {
                    boolean inclusion = RowsInclusionChecker.check(file1.getAbsolutePath(),file2.getAbsolutePath());
                    if (inclusion)
                        System.out.println(file1.getName() + "         " + file2.getName() + "        "+"inclusion         "+ true);
                }
            }
        }
    }
}
