import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double T;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        double[] ratio = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int randRow = StdRandom.uniform(N);
                int randCol = StdRandom.uniform(N);
                p.open(randRow, randCol);
            }
            ratio[i] = ((double) p.numberOfOpenSites()) / (N * N);
        }

        this.mean = StdStats.mean(ratio);
        this.stddev = StdStats.stddev(ratio);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLow() {
        return mean - 1.96 * stddev / Math.sqrt(T);
    }

    public double confidenceHigh() {
        return mean + 1.96 * stddev / Math.sqrt(T);
    }

    public static void timer(int[] n, int[] t, String s) {
        PercolationFactory pf = new PercolationFactory();
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < t.length; j++){
                int gridSize = n[i], trial = t[j];
                Stopwatch timer = new Stopwatch();
                PercolationStats ps = new PercolationStats(gridSize, trial, pf);
                double time = timer.elapsedTime();
                StdOut.printf("%.2f seconds with %s, gridsize: %d, trial: %d\n", time, s, gridSize, trial);
            }
        }
    }

    public static void main(String[] args) {
        int trials = 100, gridSize = 50;
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(gridSize, trials, pf);

        System.out.printf("Grid Size: %d x %d | Number of Trials: %d%n", gridSize, gridSize, trials);
        System.out.printf("The mean percolation threshold is %.2f%n", ps.mean());
        System.out.printf("The standard deviation of the percolation threshold is %.2f.%n", ps.stddev());
        System.out.printf("The 95%% confidence interval is [%.3f, %.3f].%n", ps.confidenceLow(), ps.confidenceHigh());

        int[] n = new int[]{20, 40, 80};
        int[] t = new int[]{50, 100, 200};
        timer(n, t, "WeightedQuickUnion");

        /*  0.01 seconds with WeightedQuickUnion, gridsize: 20, trial: 50
            0.01 seconds with WeightedQuickUnion, gridsize: 20, trial: 100
            0.08 seconds with WeightedQuickUnion, gridsize: 20, trial: 200
            0.11 seconds with WeightedQuickUnion, gridsize: 40, trial: 50
            0.12 seconds with WeightedQuickUnion, gridsize: 40, trial: 100
            0.50 seconds with WeightedQuickUnion, gridsize: 40, trial: 200
            0.21 seconds with WeightedQuickUnion, gridsize: 80, trial: 50
            1.23 seconds with WeightedQuickUnion, gridsize: 80, trial: 100
            1.06 seconds with WeightedQuickUnion, gridsize: 80, trial: 200
         */

        /*
            0.03 seconds with QuickFindUnion, gridsize: 20, trial: 50
            0.04 seconds with QuickFindUnion, gridsize: 20, trial: 100
            0.08 seconds with QuickFindUnion, gridsize: 20, trial: 200
            0.16 seconds with QuickFindUnion, gridsize: 40, trial: 50
            0.31 seconds with QuickFindUnion, gridsize: 40, trial: 100
            0.62 seconds with QuickFindUnion, gridsize: 40, trial: 200
            1.69 seconds with QuickFindUnion, gridsize: 80, trial: 50
            2.73 seconds with QuickFindUnion, gridsize: 80, trial: 100
            5.67 seconds with QuickFindUnion, gridsize: 80, trial: 200
         */
    }
}
