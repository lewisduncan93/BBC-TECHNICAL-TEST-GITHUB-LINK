import javax.swing.*;
import java.awt.*;

/**
 * Grid creates a grid onto the application
 * and also adds the relevant cells
 *
 * @author Lewis Duncan
 *
 */

public class Grid extends JPanel {


    private final int NUMBER_OF_ROWS = 20;
    private final int NUMBER_OF_COLUMNS = 20;
    private double width;
    private double height;
    private int generation = 0;

    boolean[][] cells = new boolean[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

    public boolean[][] updateCells() {
        return cells;
    }

    public void setNewCells(boolean[][] newCells) {
        cells = newCells;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        width = (double) this.getWidth() / cells[0].length;
        height = (double) this.getHeight() / cells.length;

        // Drawing the cells onto the grid
        g.setColor(Color.ORANGE);
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                if (cells[row][column] == true) {
                    g.fillRect((int) Math.round(column * width), (int) Math.round(row * height),
                            (int) width + 1, (int) height + 1);
                }
            }
        }

        // Drawing the grid
        g.setColor(Color.BLACK);
        for (int x = 0; x < cells[0].length + 1; x++) {
            g.drawLine((int) Math.round(x * width), 0, (int) (Math.round(x * width)), this.getHeight());
        }
        for (int y = 0; y < cells[0].length + 1; y++) {
            g.drawLine(0, (int) Math.round(y * height), this.getWidth(), (int) Math.round(y * height));
        }
    }

    public void clearCells(boolean[][] cells) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                cells[x][y] = false;
            }
        }
    }

    public void checkNeighbours(boolean[][] cells) {
        // New 2D boolean array for new cells
        boolean[][] newCells = new boolean[cells.length][cells[0].length];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                int neighbours = 0;
                // Checks top-left
                if (x > 0 && y > 0 && cells[x - 1][y - 1] == true) {
                    neighbours++;
                }
                // Checks left
                if (x > 0 && cells[x - 1][y] == true) {
                    neighbours++;
                }
                // Checks bottom-left
                if (x > 0 && y < cells[0].length - 1 && cells[x - 1][y + 1] == true){
                    neighbours++;
                }
                // Checks top
                if (y > 0 && cells[x][y - 1] == true) {
                    neighbours++;
                }

                if (y < cells[0].length - 1 && cells[x][y + 1] == true) {
                    neighbours++;
                }
                if (x < cells.length - 1 && y > 0 && cells[x + 1][y - 1] == true) {
                    neighbours++;
                }
                if (x < cells.length - 1 && cells[x + 1][y] == true) {
                    neighbours++;
                }
                if (x < cells.length - 1 && y < cells[0].length - 1 && cells[x + 1][y + 1] == true) {
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
}