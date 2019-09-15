package duck.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BufferTest {
    private Buffer buffer = new Buffer();

    @Test
    void testFormatLine() {
        buffer.formatLine("a");
        buffer.formatLine("b");
        buffer.formatLine("c");
        assertEquals("a\nb\nc\n", buffer.getOutput());
        buffer.formatLine("d");
        buffer.formatLine("e");
        buffer.formatLine("f");
        assertEquals("d\ne\nf\n", buffer.getOutput());
    }
}
