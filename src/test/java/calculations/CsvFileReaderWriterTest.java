package calculations;

import static calculations.FileUtils.getPathToFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author Erik
 */
public class CsvFileReaderWriterTest {

    private String path;

    @Before
    public void setUp() {
        path = getPathToFile("/test.csv");
    }

    @Test
    public void read() {
        List<CsvLine> csvLines = new CsvFileReaderWriter(path, "UTF-8").readFromFile();

        assertThat(csvLines, iterableWithSize(2));
        assertThat(csvLines.get(0).toString(), is("09.04.2016;Some Text;-3,5"));
        assertThat(csvLines.get(1).toString(), is("10.04.2016;More Words;4,93"));
    }

    @Test
    public void write() throws IOException {
        List<CsvLine> csvLines = Arrays.asList(
                new CsvLine().field(Field.fromString("some")).field(Field.fromString("text")),
                new CsvLine().field(Field.fromString("more")).field(Field.fromString("words"))
        );

        new CsvFileReaderWriter(path, "UTF-8").writeToFile(csvLines);

        Path resultPath = Paths.get(getPathToFile("/test-result.csv"));
        List<String> lines = Files.readAllLines(resultPath);

        assertThat(lines, iterableWithSize(2));
        assertThat(lines.get(0), is("some;text"));
        assertThat(lines.get(1), is("more;words"));
    }

}