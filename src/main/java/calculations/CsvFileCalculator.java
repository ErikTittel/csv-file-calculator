package calculations;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvFileCalculator {

    public static void main(String[] args) throws IOException {
        new CsvFileCalculator().startCalculation(args[0]);
    }

    private void startCalculation(String filename) throws IOException {
        Path path = Paths.get(filename);
        boolean exists = Files.exists(path);
        if (!exists) {
            System.out.println("Datei existiert nicht: " + filename);
        }

        int posOfLastDot = filename.lastIndexOf('.');
        String outputFilename = filename.substring(0, posOfLastDot) + "-result.csv";
        Path outPath = Paths.get(outputFilename);

        List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

        Files.write(outPath, lines, Charset.forName("UTF-8"));
    }
}
