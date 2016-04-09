package calculations;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        String filename = args[0];
        CsvFileReaderWriter readerWriter = new CsvFileReaderWriter(filename);

        List<CsvLine> lines = readerWriter.readFromFile();

        CsvCalculation csvCalculation = new CsvCalculation(lines);
        List<CsvLine> newLines = csvCalculation.groupBy(0, 2).getResult();

        readerWriter.writeToFile(newLines);
    }

}
