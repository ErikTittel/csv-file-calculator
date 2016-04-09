package calculations;

import java.util.List;

public class CsvFileCalculator {

    public static void main(String[] args) {
        new CsvFileCalculator().startCalculation(args[0]);
    }

    private void startCalculation(String filename) {
        FileLineReaderWriter readerWriter = new FileLineReaderWriter(filename);
        List<String> lines = readerWriter.readFromFile();


        readerWriter.writeToFile(lines);
    }

}
