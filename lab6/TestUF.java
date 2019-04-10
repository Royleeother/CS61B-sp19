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
        UnionFind UF = new UnionFind(8);
        boolean f =  UF.connected(1, 2);
        assertFalse(f);
    }
    @Test
    public void testFind() {

    }
    @Test
    public void testUnion() {
        UnionFind UF = new UnionFind(8);
        UF.union(1, 2);
        UF.union(2, 6);
        //int t = UF.find(6);
        UnionFind UF1 = new UnionFind(8);
        UF1.union(2,1);
        int t = UF1.find(1);
    }
    @Test
    public void testParent() {
        UnionFind UF = new UnionFind(8);

    }
}
