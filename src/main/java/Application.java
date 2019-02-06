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
 * Graphical components are also drawn. This is done using a
 * method from Swing called paintComponent.
 *
 * @author Lewis Duncan
 *
 */

public class Application extends JPanel implements MouseListener, ActionListener, Runnable {

    private boolean isRunning;
    private long gameSpeed = 300;
    private static int windowWidth = 800;
    private static int windowHeight = 800;
    private double width;
    private double height;

    // Declaration of objects
    private JFrame window;
    private Container north, south;
    private Grid grid;
    private JButton clearCellsButton, randomizeButton, nextStepButton, startButton, stopButton;
    private JLabel generationLabel;

    public Application() {

        // Initialization of objects
        window = new JFrame("Game of Life");
        north = new Container();
        south = new Container();
        grid = new Grid();

        generationLabel = new JLabel();

        clearCellsButton = new JButton("Clear Cells");
        randomizeButton = new JButton("Random Cells");
        nextStepButton = new JButton("Next Step");
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        // Set window size
        window.setSize(windowWidth, windowHeight);
        // Set window layout
        window.setLayout(new BorderLayout());
        // Set window border layout
        window.add(this, BorderLayout.CENTER);
        // Register for mouse events on grid
        addMouseListener(this);
        // Disable window resizable
        window.setResizable(false);

        // Set north container layout
        north.setLayout(new GridLayout(1,1));

        // Add generation label to north container
        north.add(generationLabel, SwingConstants.CENTER);
        generationLabel.setText("Generation: " + grid.getGeneration());
        generationLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        // Set south container layout
        south.setLayout(new GridLayout(1,5));

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

        // Add clear cells button to south container
        south.add(clearCellsButton);
        clearCellsButton.addActionListener(this);
        clearCellsButton.setFocusPainted(false);

        // Add randomize button to south container
        south.add(randomizeButton);
        randomizeButton.addActionListener(this);
        randomizeButton.setFocusPainted(false);

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
        double width = (double)this.getWidth() / grid.cells.length;
        double height = (double)this.getHeight() / grid.cells.length;

        // The column(X) and row(Y) values
        // Math.min() works out the smallest value to preventing it returning
        // 0 if you click slight off of the screen on the bottom or the right
        int column = Math.min(grid.cells.length - 1,(int)(e.getX() / width));
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

    // Paints graphical components
    public void paintComponent(Graphics g) {

        width = (double) this.getWidth() / grid.cells.length;
        height = (double) this.getHeight() / grid.cells.length;

        /* Drawing the grid */

        // Draws the vertical lines
        g.setColor(Color.BLACK);
        for (int x = 0; x < grid.cells.length + 1; x++) {
            g.drawLine((int) Math.round(x * width), 0, (int) (Math.round(x * width)), this.getHeight());
        }
        // Draws the horizontal lines
        for (int y = 0; y < grid.cells.length + 1; y++) {
            g.drawLine(0, (int) Math.round(y * height), this.getWidth(), (int) Math.round(y * height));
        }

        // Drawing the rectangles representing the live cells
        g.setColor(Color.decode("#DE6918"));
        for (int x = 0; x < grid.cells.length; x++) {
            for (int y = 0; y < grid.cells.length; y++) {
                if (grid.cells[x][y]) {
                    g.fillRect((int) Math.round(y * width), (int) Math.round(x * height),
                            (int) width + 1, (int) height + 1);
                }
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // Instantiate Application()
        new Application();
    }
}
