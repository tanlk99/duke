import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DummyTest {
    @Test
    void testArithmetic() {
        int a = 2, b = 5;

        assertEquals(7, a + b);
        assertEquals(10, a * b);
        assertEquals(-3, a - b);
    }
}
