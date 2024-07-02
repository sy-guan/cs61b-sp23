import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;
// TODO: Add any other necessary imports.

public class Percolation {
    // TODO: Add any necessary instance variables.
    private int[][] square;
    private boolean percolate;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF antiBackWash;
    private int dimension;
    private int numOpen;


    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }

        dimension = N;
        square = new int[N][N];
        percolate = false;
        uf = new WeightedQuickUnionUF(N*N+2);
        antiBackWash = new WeightedQuickUnionUF(N*N+1);
    }

    public void open(int row, int col) {
        validate(row, col);
        numOpen += 1;
        square[row][col] = 1;
        
        unionNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);

        return square[row][col] != 0;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);

        return antiBackWash.connected(xyTo1D(row, col), dimension*dimension);
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return uf.connected(dimension * dimension, dimension * dimension + 1);
    }

    /* Helper function to get the unique number for each element in the int[][] in order to use in union
    for example open(3,4) and open(2,4) ==> union(14,19)
     */
    public int xyTo1D(int x, int y) {
        return x * dimension + y;
    }

    /* Helper function to check if the row and col number entered are valid,
    throw an exception if not valid.
     */
    private void validate(int row, int col) {
        if (row > dimension - 1 || col > dimension - 1){
            throw new java.lang.IndexOutOfBoundsException("Row or column index greater than N");
        }
    }

    /* Helper function to get the neighbors of a site
     */
    private List<int[]> getNeighbors(int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        // Directions: {row offset, col offset}
        int[][] directions = {
                {-1, 0}, // Up
                {1, 0},  // Down
                {0, -1}, // Left
                {0, 1}   // Right
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (newRow >= 0 && newRow < dimension && newCol >= 0 && newCol < dimension) {
                neighbors.add(new int[]{newRow, newCol});
            }
        }
        return neighbors;
    }


    /* Helper function to ensure when opening a site,
    add to the unweighted quick union container if it is connected to its neighbors.
     */
    
    private void unionNeighbors(int row, int col) {
        List<int[]> neighbors = getNeighbors(row, col);
        int num1D = xyTo1D(row, col);

        if (row == 0) {
            uf.union(dimension * dimension, num1D);
            antiBackWash.union(dimension * dimension, num1D);
        } else if (row == dimension - 1) {
            uf.union(dimension * dimension + 1, num1D);
        }

        for (int[] n: neighbors) {
            int nbrow = n[0];
            int nbcol = n[1];
            int neighbor1D = xyTo1D(nbrow,nbcol);

            if (isOpen(nbrow, nbcol)) {
                uf.union(num1D, neighbor1D);
                antiBackWash.union(num1D, neighbor1D);
            }
        }
    }
}
