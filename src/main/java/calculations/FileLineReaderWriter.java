package calculations;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class FileLineReaderWriter {

    private String filename;

    FileLineReaderWriter(String filename) {
        this.filename = filename;
    }

    List<String> readFromFile() {
        Path path = Paths.get(filename);
        boolean exists = Files.exists(path);
        if (!exists) {
            System.out.println("Datei existiert nicht: " + filename);
        }
        try {
            return Files.readAllLines(path, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    void writeToFile(List<String> lines) {
        String outputFilename = createOutputFilename();
        Path outPath = Paths.get(outputFilename);
        try {
            Files.write(outPath, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file: " + outputFilename, e);
        }
    }

    private String createOutputFilename() {
        int posOfLastDot = filename.lastIndexOf('.');
        return filename.substring(0, posOfLastDot) + "-result.csv";
    }
}
