package calculations;

import static calculations.FileUtils.getPathToFile;

import org.junit.Test;

public class IntegrationTest {

    @Test
    public void integration() {
        String path = getPathToFile("/test2.csv");

        Start.run(path, 1, 4);
    }

}
