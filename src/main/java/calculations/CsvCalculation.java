package calculations;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CsvCalculation {

    private List<CsvLine> lines;
    private Order order;

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

    CsvCalculation fill(int fillColumn, Order order) {
        this.order = order;
        List<CsvLine> newLines = new ArrayList<>();
        for (int i = 0; i < lines.size() - 1; i++) {
            CsvLine currentLine = lines.get(i);
            CsvLine nextLine = lines.get(i + 1);
            newLines.addAll(duplicateLine(currentLine, fillColumn, nextLine.get(fillColumn)));
        }
        newLines.add(lines.get(lines.size() - 1));
        return new CsvCalculation(newLines);
    }

    private List<CsvLine> duplicateLine(CsvLine line, int fillColumn, Field terminator) {
        List<CsvLine> lines = new ArrayList<>();
        lines.add(line);
        Field field = line.get(fillColumn);
        while ((field = field.next(order)).before(terminator, order)) {
            CsvLine duplicate = CsvLine.from(line).update(fillColumn, field);
            lines.add(duplicate);
        }
        return lines;
    }

    CsvCalculation sort(int sortColumn, Order order) {
        List<CsvLine> newLines = lines.stream().sorted(CsvLine.comparator(sortColumn, order)).collect(toList());
        return new CsvCalculation(newLines);
    }

    List<CsvLine> getResult() {
        return lines;
    }
}