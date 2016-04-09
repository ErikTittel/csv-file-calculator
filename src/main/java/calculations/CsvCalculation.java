package calculations;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CsvCalculation {

    private List<CsvLine> lines;

    CsvCalculation(List<CsvLine> lines) {
        this.lines = lines;
    }

    CsvCalculation groupBy(int groupByColumn, int aggregateColumn) {
        Map<String, List<CsvLine>> groups =
                lines.stream().collect(groupingBy(csvLine -> csvLine.get(groupByColumn).toString()));

        List<CsvLine> newLines = new ArrayList<>();
        groups.forEach((property, group) -> {
            BigDecimal sum = group.stream().map(csvLine -> csvLine.get(aggregateColumn)).map(Field::getAsNumber)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            CsvLine csvLine = new CsvLine().field(Field.fromString(property)).field(Field.fromNumber(sum));
            newLines.add(csvLine);
        });
        return new CsvCalculation(newLines);
    }

    List<CsvLine> getResult() {
        return lines;
    }
}