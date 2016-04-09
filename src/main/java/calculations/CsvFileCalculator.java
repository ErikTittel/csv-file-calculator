package calculations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvFileCalculator {

    public static void main(String[] args) {
        new CsvFileCalculator().startCalculation(args[0]);
    }

    private void startCalculation(String filename) {
        CsvFileReaderWriter readerWriter = new CsvFileReaderWriter(filename);
        List<CsvLine> lines = readerWriter.readFromFile();

        Map<LocalDate, List<CsvLine>> dateGroups = lines.stream().collect(Collectors.groupingBy(line -> line.get(0)
                .getAsDate()));


        List<CsvLine> newLines = new ArrayList<>();
        dateGroups.forEach((date, group) -> {
            BigDecimal sum = group.stream().map(csvLine -> csvLine.get(2)).map(Field::getAsNumber).reduce
                    (BigDecimal.ZERO, BigDecimal::add);

            CsvLine csvLine = new CsvLine()
                    .field(Field.fromDate(date))
                    .field(Field.fromNumber(sum));
            newLines.add(csvLine);
        });

        readerWriter.writeToFile(newLines);
    }

}
