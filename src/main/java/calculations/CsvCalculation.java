package calculations;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class CsvCalculation {

    private List<CsvLine> lines;
    private int groupByIndex;
    private int aggregateindex;
    private Order order;

    CsvCalculation(List<CsvLine> lines, int groupByIndex, int aggregateIndex, Order order) {
        this.lines = lines;
        this.groupByIndex = groupByIndex;
        this.aggregateindex = aggregateIndex;
        this.order = order;
    }

    CsvCalculation fill() {
        List<CsvLine> newLines = new ArrayList<>();
        for (int i = 0; i < lines.size() - 1; i++) {
            CsvLine currentLine = lines.get(i);
            CsvLine nextLine = lines.get(i + 1);
            newLines.addAll(duplicateLine(currentLine, nextLine.get(groupByIndex)));
        }
        newLines.add(lines.get(lines.size() - 1));
        lines = newLines;
        return this;
    }

    private List<CsvLine> duplicateLine(CsvLine line, Field terminator) {
        List<CsvLine> lines = new ArrayList<>();
        lines.add(line);
        Field field = line.get(groupByIndex);
        while ((field = field.next(order)).before(terminator, order)) {
            CsvLine duplicate = CsvLine.emptyLine(line.size()).update(groupByIndex, field);
            lines.add(duplicate);
        }
        return lines;
    }

    CsvCalculation groupBy() {
        Map<String, List<CsvLine>> groups =
                lines.stream().collect(groupingBy(csvLine -> csvLine.get(groupByIndex).toString()));

        List<CsvLine> newLines = new ArrayList<>();
        groups.forEach((property, group) -> {
            BigDecimal sum = group.stream().map(csvLine -> csvLine.get(aggregateindex)).map(Field::getAsNumber)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            CsvLine csvLine = new CsvLine().field(Field.fromString(property)).field(Field.fromNumber(sum));
            newLines.add(csvLine);
        });
        lines = newLines;
        return this;
    }

    CsvCalculation sort() {
        Collections.sort(lines, CsvLine.comparator(0, order));
        return this;
    }

    List<CsvLine> getResult() {
        return lines;
    }
}