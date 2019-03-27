public class UnionFind {

    // TODO - Add instance variables?
    //private int size[]; // size of one set
    //private int id[]; // the component that store sets
    private int[] id;
    private int[] size;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        id = new int[n];
        size = new int[n];
        // initialize a component with sets separately
        for (int i = 0; i < n; i += 1) {
            //id[i] = i;
            id[i] = -1;
            size[i] = 1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return size[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        int node = id[v1];
        if (node < 0) {
            node = -1 * sizeOf(v1);
        }
        return node;
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        int p1 = parent(v1);
        int p2 = parent(v2);
        if (p1 == p2 && sizeOf(v1) != 1 && sizeOf(v2) != 1) {
            return true;
        } else if (p1 < 0 || p2 < 0) {
            return false;
        }
        return connected(p1, p2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        if (connected(v1, v2)) {
            return;

        } else if (sizeOf(v1) > sizeOf(v2)) {
            int big_parent = find(v1);
            int small_dd   = find(v2);
            id[small_dd]   = big_parent;
            size[v1] += size[v2];

        } else if (sizeOf(v1) <= sizeOf(v2)) {
            int big_parent = find(v2);
            int small_dd   = find(v1);
            id[small_dd]   = big_parent;
            size[v2] += size[v1];
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (id[vertex] < 0) {
            return vertex;
        }
        return find(id[vertex]);
    }

}
