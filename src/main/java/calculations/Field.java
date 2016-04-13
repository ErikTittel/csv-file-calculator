package calculations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Field implements Comparable<Field> {

    private static final String DATE_PATTERN = "dd.MM.yyyy";

    private String fieldText;

    private Field(String fieldText) {
        this.fieldText = fieldText;
    }

    LocalDate getAsDate() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return LocalDate.parse(fieldText, df);
    }

    static Field fromDate(LocalDate date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return new Field(date.format(df));
    }

    BigDecimal getAsNumber() {
        return new BigDecimal(fieldText.replaceAll(",", "."));
    }

    static Field fromNumber(BigDecimal number) {
        return new Field(number.toString());
    }

    static Field fromString(String text) {
        return new Field(text);
    }

    @Override
    public String toString() {
        if (isNumber()) {
            return fieldText.replace('.', ',');
        }
        return fieldText;
    }

    boolean isNumber() {
        return fieldText.matches("(\\+|-)?\\d+((\\.|,)\\d+)?");
    }

    Field next(Order order) {
        switch (order) {
            case ASC:
                return increment();
            case DESC:
                return decrement();
            default:
                throw new RuntimeException("unknown order");
        }
    }

    private Field increment() {
        return fromDate(getAsDate().plusDays(1));
    }

    private Field decrement() {
        return fromDate(getAsDate().minusDays(1));
    }

    boolean before(Field terminator, Order order) {
        switch (order) {
            case ASC:
                return smallerThan(terminator);
            case DESC:
                return biggerThan(terminator);
            default:
                throw new RuntimeException("unknown order");
        }
    }

    private boolean smallerThan(Field other) {
        return getAsDate().isBefore(other.getAsDate());
    }

    private boolean biggerThan(Field other) {
        return getAsDate().isAfter(other.getAsDate());
    }

    @Override
    public int compareTo(Field other) {
        if (isDate()) {
            return getAsDate().compareTo(other.getAsDate());
        }
        return fieldText.compareTo(other.fieldText);
    }

    boolean isDate() {
        return fieldText.matches("\\d{2}\\.\\d{2}\\.\\d{4}");
    }
}
