package calculations;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CsvLine {

    private static final String FIELD_SEPARATOR = ";";

    private List<Field> fields;

    private static List<Field> fromArray(String[] fieldTexts) {
        return Arrays.asList(fieldTexts).stream().map(Field::fromString).collect(toList());
    }

    CsvLine(String line) {
        fields = fromArray(line.split(FIELD_SEPARATOR));
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

    @Override
    public String toString() {
        return String.join(FIELD_SEPARATOR, fields.stream().map(Field::toString).collect(toList()));
    }
}
