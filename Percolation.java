/* *****************************************************************************
 *  Name:              Eunwoo Choi
 *  Last modified:     07/21/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int count = 0;
    private int size;
    private WeightedQuickUnionUF qf;
    private int virtualTop = 0;
    private int virtualBottom;
    private boolean[][] open;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        open = new boolean[size][size];
        qf = new WeightedQuickUnionUF(size * size + 2);
        virtualBottom = n * n + 1;
    }

    private int index(int row, int col) {
        return size * (row - 1) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        open[row - 1][col - 1] = true;

        if (row == 1) {
            qf.union(index(row, col), virtualTop);
        }

        if (row == size) {
            qf.union(index(row, col), virtualBottom);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            qf.union(index(row, col), index(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            qf.union(index(row, col), index(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            qf.union(index(row, col), index(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            qf.union(index(row, col), index(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > size || row < 1 || col > size || col < 1) {
            throw new IllegalArgumentException();
        }
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= size && col <= size && row > 0 && col > 0 && isOpen(row, col)) {
            return qf.find(virtualTop) == qf.find(index(row, col));
        } else {
            throw new IllegalArgumentException();
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        for (int row = 1; row < size + 1; row++) {
            for (int col = 1; col < size + 1; col++) {
                if (isOpen(row, col)) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.find(virtualTop) == qf.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);

        System.out.println("is 1,2 opened? " + percolation.isOpen(1, 2));
        System.out.println("the number of open sites is: " + percolation.numberOfOpenSites());
        percolation.open(1, 2);

        System.out.println("is 1,2 opened? " + percolation.isOpen(1, 2));
        System.out.println("the number of open sites is: " + percolation.numberOfOpenSites());

        System.out.println("is 1,2, full?: " + percolation.isFull(1, 2));
    }
}
