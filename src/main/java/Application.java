import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This program is a simulation of John Conway's Game of Life.
 * The application class contains the main method,
 * mouse listener and action listener, as well as the
 * implementation for the front end using AWT and Swing.
 *
 * @author Lewis Duncan
 *
 */

public class Application implements MouseListener, ActionListener, Runnable {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;
    public long gameSpeed = 300;

    Grid grid = new Grid();
    boolean isRunning = false;

    JFrame frame = new JFrame("Game of Life");
    JButton clearCellsButton = new JButton("Clear Cells");
    JButton randomizeButton = new JButton("Random Cells");
    JButton nextStepButton = new JButton("Next Step");
    JButton startButton = new JButton("Start");
    JButton stopButton = new JButton("Stop");

    //JSlider speedSlider = new JSlider();

    Container south = new Container();

    public Application() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.add(grid, BorderLayout.CENTER);
        grid.addMouseListener(this);

        frame.setResizable(false);
        south.setLayout(new GridLayout(1,5));
        south.add(clearCellsButton);
        clearCellsButton.addActionListener(this);
        south.add(randomizeButton);
        randomizeButton.addActionListener(this);
        south.add(nextStepButton);
        nextStepButton.addActionListener(this);
        south.add(startButton);
        startButton.addActionListener(this);
        south.add(stopButton);
        stopButton.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        // The width and height of the grid
        double width = (double)grid.getWidth() / grid.cells[0].length;
        double height = (double)grid.getHeight() / grid.cells.length;

        // The column(X) and row(Y) values
        // Math.min() works out the smallest value to preventing it returning
        // 0 if you click slight off of the screen on the bottom or the right
        int column = Math.min(grid.cells[0].length - 1,(int)(e.getX() / width));
        int row = Math.min(grid.cells.length - 1,(int)(e.getY() / height));
        // Prints out the X and Y values to the console for debugging purposes
        System.out.println("X: " + column + ", " + "Y: " + row);
        // Works out the opposite by assigning the value it already has and says NOT (as in opposite)
        grid.cells[row][column] = !grid.cells[row][column];
        // Repaints the graphics
        frame.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nextStepButton)) {
            if (isRunning == false) {
                nextStepButton();
                // Repaints the graphics
                frame.repaint();
            }
        }
        if (e.getSource().equals(startButton)) {
            if (isRunning == false) {
                isRunning = true;
                Thread thread = new Thread(this);
                thread.start();
            }
        }
        if (e.getSource().equals(stopButton)) {
            isRunning = false;
        }
        if (e.getSource().equals(clearCellsButton)) {
            isRunning = false;
            clearCellsButton();
        }
    }

    public void clearCellsButton() {
        grid.clearCells(grid.updateCells());
        // Repaints the graphics
        frame.repaint();
    }

    public void randomizeButton() {

    }

    public void nextStepButton() {
        // Checks neighbours method, passing the updated cells
        grid.checkNeighbours(grid.updateCells());
        // Repaints the graphics
        frame.repaint();
    }

    @Override
    public void run() {
        while(isRunning) {
            nextStepButton();
            // Repaints the graphics
            frame.repaint();
            try{
                Thread.sleep(gameSpeed);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Application();
    }
}