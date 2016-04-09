package calculations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Field {

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
        return fieldText;
    }
}
