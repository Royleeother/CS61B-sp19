package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /**
     * RULE:
     *  -1 stands for block
     *  0 or greater numbers stand for open
     */
    private int num; // number of open sites
    public UnionFind grid; // N * N grid
    private int NN; // N * N

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        grid  = new UnionFind(N * N);
        NN = N;
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validInput(row, col)) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!isOpen(row, col)) {
            int pos = xyTo1D(row, col);
            grid.setParent(pos);
            num += 1;
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validInput(row, col)) {
            throw new java.lang.IllegalArgumentException();
        }
        int pos = xyTo1D(row, col);
        return (grid.parent(pos) >= 0);
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validInput(row, col)) {
            throw new java.lang.IllegalArgumentException();
        }
        if (row == 0) {
            return true;
        }
        int present_pos = xyTo1D(row, col);
        for (int i = 0; i < NN; i += 1) {
            int top_pos = xyTo1D(0, i);
            while (grid.connected(present_pos, top_pos)) {
                return true;
            }
        }
        return false;
    }
    // number of open sites
    public int numberOfOpenSites() {
        return num;
    }
    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < NN; i += 1) {
            while (isFull(NN, i)) {
                return true;
            }
        }
        return false;
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
        Percolation fillter = new Percolation(5);
        fillter.open(3, 4);
        //boolean haha =  fillter.isOpen(3, 4);
        fillter.open(0, 4);
        boolean haha = fillter.isFull(0, 4);
    }

    // convert (x,y) to num
    private int xyTo1D(int r, int c) {
        return (r * NN) + c;
    }
    // check whether input the correct row, col
    private boolean validInput(int row, int col) {
        return (row >= 0 && row <= NN - 1 && col >= 0 && col <= NN - 1);
    }
}
