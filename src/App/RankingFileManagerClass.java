package App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Scanner;

public class RankingFileManagerClass {
    public static ObservableList<RankingRecord> getObservableListOfRecords() {
        ObservableList<RankingRecord> records = FXCollections.observableArrayList();
        try {
            File rankingDir = new File("src\\App\\ranking");
            File rankingFile = new File("src\\App\\ranking\\currentRanking.txt");

            if (!rankingDir.exists()) {
                Files.createDirectory(rankingDir.toPath());
            }

            if (!rankingFile.exists()) {
                Files.createFile(rankingFile.toPath());
            } else {
                Scanner fileScanner = new Scanner(rankingFile);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] separatedLine = line.split("~");
                    records.add(new RankingRecord(separatedLine[0], Integer.parseInt(separatedLine[1]), separatedLine[2], separatedLine[3], separatedLine[4]));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return records;
    }

    public static void addDataToRankingFile(String inputData) {
        try {
            File rankingDir = new File("src\\App\\ranking");
            File rankingFile = new File("src\\App\\ranking\\currentRanking.txt");

            if (!rankingDir.exists()) {
                Files.createDirectory(rankingDir.toPath());
            }

            if (!rankingFile.exists()) {
                Files.createFile(rankingFile.toPath());
            }

            FileWriter fileWriter = new FileWriter(rankingFile, true);
            fileWriter.write(inputData);
            fileWriter.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
