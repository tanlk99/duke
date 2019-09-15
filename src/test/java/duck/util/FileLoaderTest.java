package duck.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class FileLoaderTest {
    @Test
    void testGetDirectoryPath() {
        assertEquals(".", FileLoader.getDirectoryPath("a.txt"));
        assertEquals("f", FileLoader.getDirectoryPath("f/a.txt"));
        assertEquals("f/g", FileLoader.getDirectoryPath("f/g/a.txt"));
    }
}
