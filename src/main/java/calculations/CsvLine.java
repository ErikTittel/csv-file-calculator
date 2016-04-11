package calculations;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class CsvLine {

    private static final String FIELD_SEPARATOR = ";";
    private static final String DEFAULT_FILLING_STRING = "0";

    private List<Field> fields;

    static CsvLine emptyLine(int size) {
        CsvLine newLine = new CsvLine();
        for (int i = 0; i < size; i++) {
            newLine.fields.add(Field.fromString(DEFAULT_FILLING_STRING));
        }
        return newLine;
    }

    CsvLine(String line) {
        fields = Arrays.asList(line.split(FIELD_SEPARATOR)).stream().map(Field::fromString).collect(toList());
    }

    CsvLine() {
        fields = new ArrayList<>();
    }

    Field get(int index) {
        return fields.get(index);
    }

    CsvLine field(Field field) {
        fields.add(field);
        return this;
    }

    CsvLine update(int updateFieldIndex, Field newField) {
        fields.remove(updateFieldIndex);
        fields.add(updateFieldIndex, newField);
        return this;
    }

    int size() {
        return fields.size();
    }

    @Override
    public String toString() {
        return String.join(FIELD_SEPARATOR, fields.stream().map(Field::toString).collect(toList()));
    }

    static Comparator<CsvLine> comparator(int sortColumn, Order order) {
        return (line1, line2) -> {
            switch (order) {
                case ASC:
                    return line1.get(sortColumn).compareTo(line2.get(sortColumn));
                case DESC:
                    return line2.get(sortColumn).compareTo(line1.get(sortColumn));
                default:
                    throw new RuntimeException("Unknown sort order");
            }
        };
    }
}
