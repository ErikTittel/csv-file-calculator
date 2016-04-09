package calculations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Erik
 */
public class CsvCalculationTest {

    @Test
    public void result() {
        List<CsvLine> lines = new ArrayList<>();

        CsvCalculation calculation = new CsvCalculation(lines);

        assertThat(calculation.getResult(), is(lines));
    }

    @Test
    public void groupBy() {
        List<CsvLine> lines = Arrays.asList(
                new CsvLine("a;1"),
                new CsvLine("b;2"),
                new CsvLine("a;3"),
                new CsvLine("c;4"),
                new CsvLine("c;5")
        );

        CsvCalculation calculation = new CsvCalculation(lines).groupBy(0, 1);

        assertThat(calculation.getResult(), iterableWithSize(3));
        assertThat(calculation.getResult().get(0).toString(), is("a;4"));
        assertThat(calculation.getResult().get(1).toString(), is("b;2"));
        assertThat(calculation.getResult().get(2).toString(), is("c;9"));
    }
}