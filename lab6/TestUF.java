import org.junit.Test;
import static org.junit.Assert.*;

public class TestUF {
    //UnionFind UF = new UnionFind(8);
    @Test
    public void testInitialize() {
        //UnionFind UF = new UnionFind(8);
    }
    @Test
    public void testSizeof() {
        UnionFind UF = new UnionFind(8);
        int s1 = UF.sizeOf(1);
        int s2 = UF.sizeOf(2);
        assertEquals(s1, s2);
    }
    @Test
    public void testConnected() {

    }
    @Test
    public void testFind() {

    }
    @Test
    public void testUnion() {

    }
    @Test
    public void testParent() {
        UnionFind UF = new UnionFind(8);

    }
}
