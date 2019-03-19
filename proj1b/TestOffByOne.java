import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator obo = new OffByOne();
    // Your tests go here.
    @Test
    public void test_equalChars() {
        assertTrue(obo.equalChars('a', 'b'));
        assertTrue(obo.equalChars('b', 'a'));
        assertTrue(obo.equalChars('&', '%'));

        assertFalse(obo.equalChars('a', 'c'));
        assertFalse(obo.equalChars('c', 'a'));
        assertFalse(obo.equalChars('a', 'a'));
        assertFalse(obo.equalChars('A', 'a'));
    }
}
