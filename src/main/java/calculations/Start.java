package calculations;

import static calculations.Order.DESC;

import java.util.List;

class Start {

    public static void main(String[] args) {
        String filename = args[0];
        int fillAndGroupColumnIndex = Integer.valueOf(args[1]);
        int aggragationColumnIndex = Integer.valueOf(args[2]);

        run(filename, fillAndGroupColumnIndex, aggragationColumnIndex);
    }

    static void run(String filename, int fillAndGroupColumnIndex, int aggragationColumnIndex) {
        CsvFileReaderWriter readerWriter = new CsvFileReaderWriter(filename);

        List<CsvLine> lines = readerWriter.readFromFile();

        List<CsvLine> newLines = new CsvCalculation(lines, fillAndGroupColumnIndex, aggragationColumnIndex, DESC)
                .fill()
                .groupBy()
                .sort()
                .getResult();

        readerWriter.writeToFile(newLines);
    }

}
