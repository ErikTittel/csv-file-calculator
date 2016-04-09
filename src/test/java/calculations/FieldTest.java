package calculations;

import static calculations.Order.ASC;
import static calculations.Order.DESC;
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

    @Test
    public void smallerThan() {
        Field field1 = Field.fromString("01.04.2016");
        Field field1_1 = Field.fromString("01.04.2016");
        Field field2 = Field.fromString("02.04.2016");

        assertThat(field1.before(field1_1, ASC), is(false));
        assertThat(field1.before(field2, ASC), is(true));
        assertThat(field2.before(field1, ASC), is(false));
    }

    @Test
    public void greaterThan() {
        Field field1 = Field.fromString("01.04.2016");
        Field field1_1 = Field.fromString("01.04.2016");
        Field field2 = Field.fromString("02.04.2016");

        assertThat(field1.before(field1_1, DESC), is(false));
        assertThat(field1.before(field2, DESC), is(false));
        assertThat(field2.before(field1, DESC), is(true));
    }

    @Test
    public void next() {
        Field field = Field.fromString("01.04.2016");

        assertThat(field.next(ASC).toString(), is("02.04.2016"));
        assertThat(field.next(DESC).toString(), is("31.03.2016"));
    }
}