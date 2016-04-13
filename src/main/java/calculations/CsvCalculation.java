package calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

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

    CsvCalculation continuousSubstract(BigDecimal startValue) {
        BigDecimal prevValue = startValue;
        BigDecimal diff = BigDecimal.ZERO;
        for(CsvLine line : lines) {
            BigDecimal continuousValue = prevValue.subtract(diff);
            line.field(Field.fromNumber(continuousValue));
            prevValue = continuousValue;
            diff = line.get(1).getAsNumber();
        }
        return this;
    }

    CsvCalculation average(int maxCount) {
        int count = Math.min(lines.size(), maxCount);
        BigDecimal sum = lines.stream()
                .limit(count)
                .map(line -> line.get(2).getAsNumber())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        lines.add(new CsvLine()
                .field(Field.fromString(""))
                .field(Field.fromString("Avg first " + count))
                .field(Field.fromNumber(avg)));
        return this;
    }
}