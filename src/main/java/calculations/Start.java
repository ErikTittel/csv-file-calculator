package calculations;

import static calculations.Order.DESC;

import java.util.List;

class Start {

    public static void main(String[] args) {
        String filename = args[0];
        int fillAndGroupColumnIndex = Integer.valueOf(args[1]) - 1;
        int aggragationColumnIndex = Integer.valueOf(args[2]) - 1;
        String encoding;
        if (args.length > 3) {
            encoding = args[3];
        } else {
            encoding = "ISO-8859-1";
        }

        run(filename, fillAndGroupColumnIndex, aggragationColumnIndex, encoding);
    }

    static void run(String filename, int fillAndGroupColumnIndex, int aggragationColumnIndex, String encoding) {
        CsvFileReaderWriter readerWriter = new CsvFileReaderWriter(filename, encoding);

        List<CsvLine> lines = readerWriter.readFromFile();

        List<CsvLine> newLines = new CsvCalculation(lines, fillAndGroupColumnIndex, aggragationColumnIndex, DESC)
                .fill()
                .groupBy()
                .sort()
                .getResult();

        readerWriter.writeToFile(newLines);
    }

}
