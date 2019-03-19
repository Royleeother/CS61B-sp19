import static org.junit.Assert.*;
import org.junit.Test;

// determine whether two Integers are the same or not.
public class FlikTest {
    @Test
    public void testFlik() {
        assertTrue(Flik.isSameNumber(128, 128));
        if (!Flik.isSameNumber(128, 128)) {
            System.out.println("Expected: true, but got: " + Flik.isSameNumber(128, 128));
        } else { System.out.println("PASSED !!!");
        }
    }
}
