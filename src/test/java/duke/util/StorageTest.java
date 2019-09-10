package duke.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage = new Storage("", "");

    @Test
    void testDirectoryPath() {
        assertEquals(".", storage.getDirectoryPath("a.txt"));
        assertEquals("f", storage.getDirectoryPath("f/a.txt"));
        assertEquals("f/g", storage.getDirectoryPath("f/g/a.txt"));
    }
}
