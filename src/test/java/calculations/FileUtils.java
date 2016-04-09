package calculations;

import java.net.URISyntaxException;
import java.nio.file.Paths;

class FileUtils {

    static String getPathToFile(String filename) {
        try {
            return Paths.get(CsvFileReaderWriterTest.class.getResource(filename).toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
