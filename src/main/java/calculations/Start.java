package calculations;

import static calculations.Order.DESC;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        String filename = args[0];
        CsvFileReaderWriter readerWriter = new CsvFileReaderWriter(filename);

        List<CsvLine> lines = readerWriter.readFromFile();

        List<CsvLine> newLines = new CsvCalculation(lines)
                .fill(0, DESC)
                .groupBy(0, 2)
                .sort(0, DESC)
                .getResult();

        readerWriter.writeToFile(newLines);
    }

}
