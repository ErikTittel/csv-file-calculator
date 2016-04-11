package calculations;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

class CsvFileReaderWriter {

    private String filename;
    private String encoding;

    CsvFileReaderWriter(String filename, String encoding) {
        this.filename = filename;
        this.encoding = encoding;
    }

    List<CsvLine> readFromFile() {
        Path path = Paths.get(filename);
        boolean exists = Files.exists(path);
        if (!exists) {
            System.out.println("File not found: " + filename);
        }
        try {
            return Files.lines(path, Charset.forName(encoding)).skip(1).map(CsvLine::new).collect(toList());
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    void writeToFile(List<CsvLine> lines) {
        String outputFilename = createOutputFilename();
        Path outPath = Paths.get(outputFilename);
        List<String> textLines = lines.stream().map(CsvLine::toString).collect(toList());
        try {
            Files.write(outPath, textLines, Charset.forName(encoding));
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file: " + outputFilename, e);
        }
    }

    private String createOutputFilename() {
        int posOfLastDot = filename.lastIndexOf('.');
        return filename.substring(0, posOfLastDot) + "-result.csv";
    }
}
