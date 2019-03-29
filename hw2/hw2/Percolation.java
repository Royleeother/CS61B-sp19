package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// WOCAO
public class Percolation {
    /**
     * RULE:
     *  false stands for block
     *  true stands for open
     */
    private int num; // number of open sites
    public boolean[][] grid; // N * N grid
    WeightedQuickUnionUF WQUF;
    private int NN; // N * N
    private int head;
    private int bottom;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        // initialize all the girds with false value which means block
        grid = new boolean[N][N];
        // plus one virtual top and one virtual bottom
        WQUF = new WeightedQuickUnionUF((N * N) + 2);
        // not a site open yet
        num = 0;
        NN  = N;
        // virtual sites
        head = N * N;
        bottom = (N * N) + 1;
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            num += 1;
        }
        int current_pos = xyTo1D(row, col);

        // logic problem, can not use else if expression
        // top-gird's duty
        if (row == 0) WQUF.union(current_pos, head);
        // bottom-grid's duty
        if (row == (NN - 1)) WQUF.union(current_pos, bottom);
        // upper neighbour
        if ((row + 1) < NN && isOpen(row + 1, col)) {
            int upper = xyTo1D(row + 1, col);
            WQUF.union(current_pos, upper);
        }
        // nether neighbour
        if ((row - 1) >= 0 && isOpen(row - 1, col)) {
            int nether = xyTo1D(row - 1, col);
            WQUF.union(current_pos, nether);
        }
        // left neighbour
        if ((col - 1) >= 0 && isOpen(row, col - 1)) {
            int left = xyTo1D(row, col - 1);
            WQUF.union(current_pos, left);
        }
        // right neighbour
        if ((col + 1) < NN && isOpen(row, col + 1)) {
            int right = xyTo1D(row, col + 1);
            WQUF.union(current_pos, right);
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        // return grid[row][col] == true; bad style
        return grid[row][col];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) return false;

        int current_pos = xyTo1D(row, col);
        // whether the grid connected to head
        return WQUF.connected(head, current_pos);
    }
    // number of open sites
    public int numberOfOpenSites() {
        return num;
    }
    // does the system percolate?
    public boolean percolates() {
        return WQUF.connected(head, bottom);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
        Percolation fillter = new Percolation(10);
        fillter.open(-1, 5);
        fillter.open(2, 2);
        boolean haha = fillter.isFull(0, 2);
        boolean enen = fillter.isFull(2, 2);
    }

    // convert (x,y) to num
    private int xyTo1D(int r, int c) {
        return (r * NN) + c;
    }
    // check whether input the correct row, col
    private void validate(int row, int col) {
        /*if (!(row >= 0 && row <= NN - 1 && col >= 0 && col <= NN - 1)) {
            throw new java.lang.IllegalArgumentException();
        }*/
        if (row < 0 || col < 0 || row >= NN || col >= NN) {
            throw new java.lang.IllegalArgumentException();
        }
    }

}
