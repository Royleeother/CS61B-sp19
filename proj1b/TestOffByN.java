import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void test_equalChars() {
        CharacterComparator obo = new OffByN(5);
        assertFalse(obo.equalChars('a', 'b'));
        assertTrue(obo.equalChars('a', 'f'));
    }
}
