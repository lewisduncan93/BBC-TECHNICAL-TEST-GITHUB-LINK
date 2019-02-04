import javax.swing.*;
import java.awt.*;
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
    private double width;
    private double height;
    private int generation = 0;

    // New 2D array of boolean called cells containing the number of rows and columns
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

    // Paints graphical components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        width = (double) this.getWidth() / cells.length;
        height = (double) this.getHeight() / cells.length;

        /* Drawing the grid */

        // Draws the vertical lines
        g.setColor(Color.BLACK);
        for (int x = 0; x < cells.length + 1; x++) {
            g.drawLine((int) Math.round(x * width), 0, (int) (Math.round(x * width)), this.getHeight());
        }
        // Draws the horizontal lines
        for (int y = 0; y < cells.length + 1; y++) {
            g.drawLine(0, (int) Math.round(y * height), this.getWidth(), (int) Math.round(y * height));
        }

        // Drawing the rectangles representing the live cells
        g.setColor(Color.decode("#DE6918"));
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                if (cells[x][y] == true) {
                    g.fillRect((int) Math.round(y * width), (int) Math.round(x * height),
                            (int) width + 1, (int) height + 1);
                }
            }
        }
    }

}
