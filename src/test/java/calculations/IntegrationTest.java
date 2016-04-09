package calculations;

import static calculations.FileUtils.getPathToFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IntegrationTest {

    @Test
    public void integration() throws IOException {
        String path = getPathToFile("/test2.csv");

        Start.run(path, 1, 4);

        String pathToExpectedFile = getPathToFile("/test2-result-expected.csv");
        String pathToActualFile = getPathToFile("/test2-result.csv");
        byte[] expected = Files.readAllBytes(Paths.get(pathToExpectedFile));
        byte[] actual = Files.readAllBytes(Paths.get(pathToActualFile));

        assertThat("files are not identical", actual, is(expected));
    }

}
