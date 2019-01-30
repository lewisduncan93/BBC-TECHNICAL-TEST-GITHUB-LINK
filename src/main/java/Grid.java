import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Grid creates a grid onto the application
 * and also adds the relevant cells
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

    boolean[][] cells = new boolean[numberOfRows][numberOfColumns];

    Random random = new Random();

    public boolean[][] updateCells() {
        return cells;
    }

    public void setNewCells(boolean[][] newCells) {
        cells = newCells;
    }

    public void clearCells(boolean[][] cells) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                cells[x][y] = false;
            }
        }
    }

    public void randomizeCells(boolean[][] cells) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                cells[x][y] = random.nextBoolean();
            }
        }
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void nextGeneration(int generation) {
        generation++;
    }

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

                // If alive cell has 2 or 3 neighbours then it stays alive, else dead
                if (cells[x][y] == true) {
                    if (neighbours == 2 || neighbours == 3) {
                        newCells[x][y] = true;
                    }
                    else {
                        newCells[x][y] = false;
                    }
                }
                // If dead cell has 3 neighbours then create new cell, else dead
                else {
                    if (neighbours == 3) {
                        newCells[x][y] = true;
                    } else {
                        newCells[x][y] = false;
                    }
                }
            }
        }
        // Update cells with newCells
        cells = newCells;
        // Update grid passing the newCells
        setNewCells(newCells);
    }

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