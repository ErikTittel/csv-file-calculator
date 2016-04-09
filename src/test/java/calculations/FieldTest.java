package calculations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FieldTest {

    @Test
    public void text() {
        Field field = Field.fromString("-1.8");

        assertThat(field.getAsNumber(), is(new BigDecimal("-1.8")));
    }

    @Test
    public void number() {
        Field field = Field.fromNumber(new BigDecimal("3.5"));

        assertThat(field.getAsNumber(), is(new BigDecimal("3.5")));
        assertThat(field.toString(), is("3.5"));
    }

    @Test
    public void date() {
        Field field = Field.fromDate(LocalDate.of(2016, 4, 9));

        assertThat(field.getAsDate(), is(LocalDate.of(2016, 4, 9)));
        assertThat(field.toString(), is("09.04.2016"));
    }
}