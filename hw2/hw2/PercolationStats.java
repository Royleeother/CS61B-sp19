package hw2;

import edu.princeton.cs.introcs.StdRandom;
//import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private double[] data_record;
    private double totalOpen;
    private double ratio;
    private int time;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        time = T;
        data_record = new double[N];
        for (int i = 0; i < T; i++) {
            Percolation filter = pf.make(N);
            while (!filter.percolates()) {
                int r = StdRandom.uniform(0, N); // 0 ~ N - 1
                int c = StdRandom.uniform(0, N); // 0 ~ N - 1
                filter.open(r, c);
            }
            totalOpen = Double.valueOf(filter.numberOfOpenSites());
            ratio  = totalOpen / N * N;
            data_record[i] = ratio;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        int length = data_record.length;
        double total  = 0;
        for (int i = 0; i < length; i++) {
            time += data_record[i];
        }
        return total / time;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        int length = data_record.length;
        double dva = 0;
        double sp = mean();
        for (int i = 0; i < length; i++) {
            double x = data_record[i];
            dva += Math.pow((x - sp), 2);
        }
        return Math.sqrt(dva) / time - 1;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double sp = mean();
        double std = stddev();
        double haha = sp - ((1.96 * std) / Math.sqrt(time));
        return haha;
    }
    public double confidenceHigh() {
        double sp = mean();
        double std = stddev();
        double haha = sp + ((1.96 * std) / Math.sqrt(time));
        return haha;
    }
}
