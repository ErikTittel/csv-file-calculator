package calculations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class CsvLineTest {

    @Test
    public void fromText() {
        CsvLine line = new CsvLine("Hello;World");

        assertThat(line.get(0).toString(), is("Hello"));
        assertThat(line.get(1).toString(), is("World"));
    }

    @Test
    public void toText() {
        CsvLine line = new CsvLine().field(Field.fromString("Hello")).field(Field.fromString("World"));

        assertThat(line.toString(), is("Hello;World"));
    }
}