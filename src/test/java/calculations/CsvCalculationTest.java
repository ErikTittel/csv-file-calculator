package calculations;

import static calculations.Order.ASC;
import static calculations.Order.DESC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erik
 */
public class CsvCalculationTest {

    @Test
    public void result() {
        List<CsvLine> lines = new ArrayList<>();

        CsvCalculation calculation = new CsvCalculation(lines, 0, 0, DESC);

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

        CsvCalculation calculation = new CsvCalculation(lines, 0, 1, ASC).groupBy();

        List<String> result = calculation.getResult().stream()
                .map(CsvLine::toString)
                .sorted()
                .collect(Collectors.toList());
        assertThat(result, iterableWithSize(3));
        assertThat(result.get(0), is("a;4"));
        assertThat(result.get(1), is("b;2"));
        assertThat(result.get(2), is("c;9"));
    }

    @Test
    public void fill_asc() {
        List<CsvLine> lines = Arrays.asList(
                new CsvLine("01.04.2016;a;x"),
                new CsvLine("03.04.2016;b;y"),
                new CsvLine("03.04.2016;c;z"),
                new CsvLine("06.04.2016;d;q")
        );

        CsvCalculation calculation = new CsvCalculation(lines, 0, 1, ASC).fill();

        List<CsvLine> result = calculation.getResult();
        assertThat(result, iterableWithSize(7));
        assertThat(result.get(0).toString(), is("01.04.2016;a;x"));
        assertThat(result.get(1).toString(), is("02.04.2016;0;0"));
        assertThat(result.get(2).toString(), is("03.04.2016;b;y"));
        assertThat(result.get(3).toString(), is("03.04.2016;c;z"));
        assertThat(result.get(4).toString(), is("04.04.2016;0;0"));
        assertThat(result.get(5).toString(), is("05.04.2016;0;0"));
        assertThat(result.get(6).toString(), is("06.04.2016;d;q"));
    }

    @Test
    public void fill_desc() {
        List<CsvLine> lines = Arrays.asList(
                new CsvLine("06.04.2016;d;q"),
                new CsvLine("03.04.2016;c;z"),
                new CsvLine("03.04.2016;b;y"),
                new CsvLine("01.04.2016;a;x")
        );

        CsvCalculation calculation = new CsvCalculation(lines, 0, 1, DESC).fill();

        List<CsvLine> result = calculation.getResult();
        assertThat(result, iterableWithSize(7));
        assertThat(result.get(0).toString(), is("06.04.2016;d;q"));
        assertThat(result.get(1).toString(), is("05.04.2016;0;0"));
        assertThat(result.get(2).toString(), is("04.04.2016;0;0"));
        assertThat(result.get(3).toString(), is("03.04.2016;c;z"));
        assertThat(result.get(4).toString(), is("03.04.2016;b;y"));
        assertThat(result.get(5).toString(), is("02.04.2016;0;0"));
        assertThat(result.get(6).toString(), is("01.04.2016;a;x"));
    }

    @Test
    public void sort() {
        List<CsvLine> lines = Arrays.asList(
                new CsvLine("09.04.2015;b;y"),
                new CsvLine("01.04.2016;c;z"),
                new CsvLine("06.04.2016;d;q")
        );

        CsvCalculation calculation = new CsvCalculation(lines, 0, 1, DESC).sort();

        List<CsvLine> result = calculation.getResult();
        assertThat(result, iterableWithSize(3));
        assertThat(result.get(0).toString(), is("06.04.2016;d;q"));
        assertThat(result.get(1).toString(), is("01.04.2016;c;z"));
        assertThat(result.get(2).toString(), is("09.04.2015;b;y"));
    }
}