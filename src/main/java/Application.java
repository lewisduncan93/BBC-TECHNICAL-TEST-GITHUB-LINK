import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This program is a simulation of John Conway's Game of Life.
 *
 * The Application class contains the main method,
 * mouse listener and action listener, as well as the
 * implementation for the front-end GUI using AWT and Swing.
 *
 * @author Lewis Duncan
 *
 */

public class Application implements MouseListener, ActionListener, Runnable {

    private boolean isRunning = false;
    private long gameSpeed = 300;
    private static int windowWidth = 800;
    private static int windowHeight = 800;

    private JFrame window;
    private Container north, south;
    private Grid grid;
    private JButton clearCellsButton, randomizeButton, nextStepButton, startButton, stopButton;
    private JSlider speedSlider;
    private JLabel generationLabel;

    public Application() {

        window = new JFrame("Game of Life");
        north = new Container();
        south = new Container();
        grid = new Grid();

        speedSlider = new JSlider();
        generationLabel = new JLabel();

        clearCellsButton = new JButton("Clear Cells");
        randomizeButton = new JButton("Random Cells");
        nextStepButton = new JButton("Next Step");
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        window.setSize(windowWidth, windowHeight);
        window.setLayout(new BorderLayout());
        window.add(grid, BorderLayout.CENTER);
        grid.addMouseListener(this);
        window.setResizable(false);

        // Set north container layout
        north.setLayout(new GridLayout(1,1));

        // Add generation label to north container
        north.add(generationLabel, SwingConstants.CENTER);
        generationLabel.setText("Generation: " + grid.getGeneration());
        generationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        //generationLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        // Set south container layout
        south.setLayout(new GridLayout(1,5));

        // Add clear cells button to south container
        south.add(clearCellsButton);
        clearCellsButton.addActionListener(this);
        clearCellsButton.setFocusPainted(false);

        // Add randomize button to south container
        south.add(randomizeButton);
        randomizeButton.addActionListener(this);
        randomizeButton.setFocusPainted(false);

        // Add next step button to south container
        south.add(nextStepButton);
        nextStepButton.addActionListener(this);
        nextStepButton.setFocusPainted(false);

        // Add start button to south container
        south.add(startButton);
        startButton.addActionListener(this);
        startButton.setFocusPainted(false);

        // Add stop button to south container
        south.add(stopButton);
        stopButton.addActionListener(this);
        stopButton.setFocusPainted(false);

        // Add north container to the window and position it north
        window.add(north, BorderLayout.NORTH);
        // Add south container to the window and position it south
        window.add(south, BorderLayout.SOUTH);

        // Disable relative location so that it appears on the centre of the screen
        window.setLocationRelativeTo(null);
        // Once the JFrame is closed, call System.exit(0)
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set window to visible
        window.setVisible(true);
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
        window.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Perform action when next step button is pressed
        if (e.getSource().equals(nextStepButton)) {
            if (isRunning == false) {
                nextStepButton();
                // Repaints the graphics
                window.repaint();
            }
        }
        // Perform action when start button is pressed
        if (e.getSource().equals(startButton)) {
            if (isRunning == false) {
                isRunning = true;
                // Creates a new thread
                Thread thread = new Thread(this);
                // Starts the thread
                thread.start();
                // Disable these buttons
                nextStepButton.setEnabled(false);
                clearCellsButton.setEnabled(false);
                randomizeButton.setEnabled(false);
            }
        }
        // Perform action when stop button is pressed
        if (e.getSource().equals(stopButton)) {
            if(isRunning) {
                // Enable these buttons
                nextStepButton.setEnabled(true);
                clearCellsButton.setEnabled(true);
                randomizeButton.setEnabled(true);
                isRunning = false;
            }
        }
        // Perform action when clear cells button is pressed
        if (e.getSource().equals(clearCellsButton)) {
            isRunning = false;
            // Invokes clearCellsButton()
            clearCellsButton();
        }
        // Perform action when randomize button is pressed
        if (e.getSource().equals(randomizeButton)) {
            if (isRunning == false) {
                // Invokes randomizeButton()
                randomizeButton();
            }
        }
    }

    // Clears the existing cells
    private void clearCellsButton() {
        // Invokes clearCells() passing the updateCells() values
        grid.clearCells(grid.updateCells());
        // Invokes resetGeneration()
        grid.resetGeneration();
        // Sets text on generationLabel
        generationLabel.setText("Generation: " + grid.getGeneration());
        // Repaints the graphics
        window.repaint();
    }

    // Creates a new random live cells
    private void randomizeButton() {
        grid.randomizeCells(grid.updateCells());
        // Invokes resetGeneration()
        grid.resetGeneration();
        // Sets text on generationLabel
        generationLabel.setText("Generation: " + grid.getGeneration());
        // Repaints the graphics
        window.repaint();
    }

    // Goes to the next generation (next iteration)
    private void nextStepButton() {
        // Invokes checkNeighbours(), passing the updated cells
        grid.checkNeighbours(grid.updateCells());
        // Invokes setGeneration()
        grid.setGeneration(grid.getGeneration());
        // Sets text on generationLabel
        generationLabel.setText("Generation: " + grid.getGeneration());
        // Repaints the graphics
        window.repaint();
    }

    // Runs when thread starts
    @Override
    public void run() {
        // While isRunning is true
        while(isRunning) {
            // Invoke nextStepButton()
            nextStepButton();
            // Repaints the graphics
            window.repaint();
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