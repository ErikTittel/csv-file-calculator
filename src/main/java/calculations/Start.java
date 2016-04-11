package calculations;

import java.util.List;

import static calculations.Order.DESC;

class Start {

    public static void main(String[] args) {
        if (!checkInput(args)) {
            return;
        }
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

    private static boolean checkInput(String[] args) {
        if (args.length < 3) {
            System.out.println("Arguments missing.");
            printUsage();
            return false;
        }
        if (!isNumeric(args[1]) || !isNumeric(args[2])) {
            System.out.println("Second and thrid argument need to be column indices, hence numeric.");
            printUsage();
            return false;
        }
        return true;
    }

    private static void printUsage() {
        System.out.println("Usage:\n\tjava -jar ./csvfilecalculator.jar [file] [date column] [number column] [encoding " + "" +
                "(optional)]");
        System.out.println("Example:\n\tjava -jar ./csvfilecalculator.jar C:/path/to/your/file.csv 1 4 UTF-8");
    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    static void run(String filename, int fillAndGroupColumnIndex, int aggragationColumnIndex, String encoding) {
        CsvFileReaderWriter readerWriter = new CsvFileReaderWriter(filename, encoding);

        List<CsvLine> lines = readerWriter.readFromFile();
        checkFormat(lines, fillAndGroupColumnIndex, aggragationColumnIndex);

        List<CsvLine> newLines = new CsvCalculation(lines, fillAndGroupColumnIndex, aggragationColumnIndex, DESC).fill()
                .groupBy()
                .sort()
                .getResult();

        readerWriter.writeToFile(newLines);
    }

    private static void checkFormat(List<CsvLine> lines, int fillAndGroupColumnIndex, int aggragationColumnIndex) {
        int rowIndex = 0;
        for (CsvLine line : lines) {
            Field groupField = line.get(fillAndGroupColumnIndex);
            Field aggregationField = line.get(aggragationColumnIndex);
            rowIndex++;
            if (!groupField.isDate()) {
                throw new RuntimeException("The " + (fillAndGroupColumnIndex + 1) + ". column needs to be all dates. However the " +
                        "content of  row " + rowIndex + " could not be parsed to a date: " + groupField);
            }
            if (!aggregationField.isNumber()) {
                throw new RuntimeException("The " + (aggragationColumnIndex + 1) + ". column needs to be all numbers. However the" +
                        " content of  row " + rowIndex + " could not be parsed to a number: " + aggregationField);
            }
        }
    }

}
