import javax.swing.*;
import java.util.Random;

/**
 * The Grid class carries out the back-end processes of the program.
 *
 * Graphical components are drawn, this is done using a paintComponent
 * method from Swing. This class also creates a 2D array of booleans called cells
 * and contains a method called checkNeighbour which applies Conway's Game of Life's
 * rules to determine the outcome of the cells. Depending on the outcome it will
 * draw the relevant rectangles. There are several other methods such as updateCells,
 * setNewCells, clearCells, radomizeCells, getGeneration and setGeneration.
 *
 * @author Lewis Duncan
 *
 */

public class Grid extends JPanel {

    private static int numberOfRows = 30;
    private static int numberOfColumns = 30;
    private int generation = 0;

    // A new 2D array of type boolean called cells
    boolean[][] cells = new boolean[numberOfRows][numberOfColumns];

    Random random = new Random();

    // Returns cells
    public boolean[][] updateCells() {
        return cells;
    }

    // Sets new cells in the newCells 2D array
    public void setNewCells(boolean[][] newCells) {
        cells = newCells;
    }

    // Clears cells from the cells 2D array
    public void clearCells(boolean[][] cells) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                cells[x][y] = false;
            }
        }
    }

    // Randomizes cells in the cells 2D array
    public void randomizeCells(boolean[][] cells) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                cells[x][y] = random.nextBoolean();
            }
        }
    }

    // Sets generation
    public void setGeneration(int generation) {
        generation++;
        this.generation = generation;
    }

    // Resets generation
    public void resetGeneration() {
        generation = 0;
    }

    // Gets current generation
    public int getGeneration() {
        return generation;
    }

    // Checks for any other live cells and adds to an integer called neighbours
    public void checkNeighbours(boolean[][] cells) {
        // New 2D boolean array for new cells
        boolean[][] newCells = new boolean[cells.length][cells.length];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {

                int neighbours = 0;

                // Checks left
                if (x > 0 && cells[x - 1][y] == true) {
                    neighbours++;
                }
                // Checks right
                if (x < cells.length - 1 && cells[x + 1][y] == true) {
                    neighbours++;
                }
                // Checks top
                if (y > 0 && cells[x][y - 1] == true) {
                    neighbours++;
                }
                // Checks bottom
                if (y < cells.length - 1 && cells[x][y + 1] == true) {
                    neighbours++;
                }
                // Checks top-left
                if (x > 0 && y > 0 && cells[x - 1][y - 1] == true) {
                    neighbours++;
                }
                // Checks top-right
                if (x < cells.length - 1 && y > 0 && cells[x + 1][y - 1] == true) {
                    neighbours++;
                }
                // Checks bottom-left
                if (x > 0 && y < cells.length - 1 && cells[x - 1][y + 1] == true){
                    neighbours++;
                }
                // Checks bottom-right
                if (x < cells.length - 1 && y < cells.length - 1 && cells[x + 1][y + 1] == true) {
                    neighbours++;
                }

                /* Conway's Game of Life */
                // If this cell is alive (true)
                if (cells[x][y] == true) {
                    // If alive cell has 2 or 3 neighbours then it stays alive, else dead
                    if (neighbours == 2 || neighbours == 3) {
                        // Set this new cell to true
                        newCells[x][y] = true;
                    }
                    else {
                        // Else set this new cell to false
                        newCells[x][y] = false;
                    }
                }
                else {
                    // If dead cell has 3 neighbours then create new cell, else dead
                    if (neighbours == 3) {
                        // Set this new cell to true
                        newCells[x][y] = true;
                    } else {
                        // Else set this new cell to false
                        newCells[x][y] = false;
                    }
                }
            }
        }
        // Update cells with newCells
        this.cells = newCells;
        // Update grid passing the newCells
        setNewCells(newCells);
    }

}
