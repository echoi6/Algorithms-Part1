/* *****************************************************************************
 *  Name:              Eunwoo Choi
 *  Last modified:     07/21/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int trials;
    private Percolation percolation;
    private double[] nums;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        percolation = new Percolation(n);
        this.n = n;
        this.trials = trials;
        nums = new double[trials];
    }

    public void reset() {
        percolation = new Percolation(n);
    }

    public void randomGenerator() {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);

        if (!percolation.isOpen(row, col)) {
            percolation.open(row, col);
        }
    }

    public void simulation() {
        while (!percolation.percolates()) {
            randomGenerator();
        }
    }

    public void iteration(int trials) {
        int i = 0;
        while (i < trials) {
            simulation();
            nums[i] = (double) percolation.numberOfOpenSites() / (double) (n * n);
            reset();
            i++;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(nums);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(nums);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * Math.sqrt(stddev() / trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * Math.sqrt(stddev() / trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        // 20x20 grid, 5 trial
        PercolationStats simulation1 = new PercolationStats(200, 100);
        simulation1.iteration(simulation1.trials);
        System.out.println("mean: " + simulation1.mean());
        System.out.println("stddev: " + simulation1.stddev());
        System.out.println(
                "95% confidence intervel: " + "[" + simulation1.confidenceLo() + ", " + simulation1
                        .confidenceHi() + "]");

        PercolationStats simulation2 = new PercolationStats(200, 100);
        simulation2.iteration(simulation2.trials);
        System.out.println("mean: " + simulation2.mean());
        System.out.println("stddev: " + simulation2.stddev());
        System.out.println(
                "95% confidence intervel: " + "[" + simulation2.confidenceLo() + ", " + simulation2
                        .confidenceHi() + "]");

        PercolationStats simulation3 = new PercolationStats(2, 10000);
        simulation3.iteration(simulation3.trials);
        System.out.println("mean: " + simulation3.mean());
        System.out.println("stddev: " + simulation3.stddev());
        System.out.println(
                "95% confidence intervel: " + "[" + simulation3.confidenceLo() + ", " + simulation3
                        .confidenceHi() + "]");

        PercolationStats simulation4 = new PercolationStats(2, 100000);
        simulation4.iteration(simulation4.trials);
        System.out.println("mean: " + simulation4.mean());
        System.out.println("stddev: " + simulation4.stddev());
        System.out.println(
                "95% confidence intervel: " + "[" + simulation4.confidenceLo() + ", " + simulation4
                        .confidenceHi() + "]");
    }
}
