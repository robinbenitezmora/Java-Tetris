import java.util.Random;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.scene.paint.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;

class Shape {
  Tetrominoe pieceShape;
  int coords[][];
  int[][][] coordsTable;

  public Shape() {
    initShape();
  }

  void initShape() {
    coords = new int[4][2];
    setShape(Tetrominoe.NoShape);
  }

  protected void setShape(Tetrominoe shape) {
    coordsTable = new int[][][] {
        { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
        { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } },
        { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
        { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } },
        { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
        { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } },
        { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
        { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }
    };

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 2; ++j) {
        coords[i][j] = coordsTable[shape.getValue()][i][j];
      }
    }
    pieceShape = shape;
  }

  void setX(int index, int x) {
    coords[index][0] = x;
  }

  void setY(int index, int y) {
    coords[index][1] = y;
  }

  public int x(int index) {
    return coords[index][0];
  }

  public int y(int index) {
    return coords[index][1];
  }

  public Tetrominoe getShape() {
    return pieceShape;
  }

  public void setRandomShape() {
    Random r = new Random();
    int x = Math.abs(r.nextInt()) % 7 + 1;
    Tetrominoe[] values = Tetrominoe.values();
    setShape(values[x]);
  }

  public int minX() {
    int m = coords[0][0];
    for (int i = 0; i < 4; i++) {
      m = Math.min(m, coords[i][0]);
    }
    return m;
  }

  public int minY() {
    int m = coords[0][1];
    for (int i = 0; i < 4; i++) {
      m = Math.min(m, coords[i][1]);
    }
    return m;
  }

  public Shape rotateLeft() {
    if (pieceShape == Tetrominoe.SQUARE_SHAPE)
      return this;

    Shape result = new Shape();
    result.pieceShape = pieceShape;

    for (int i = 0; i < 4; ++i) {
      result.setX(i, y(i));
      result.setY(i, -x(i));
    }
    return result;
  }

  public Shape rotateRight() {
    if (pieceShape == Tetrominoe.SQUARE_SHAPE)
      return this;

    Shape result = new Shape();
    result.pieceShape = pieceShape;

    for (int i = 0; i < 4; ++i) {
      result.setX(i, -y(i));
      result.setY(i, x(i));
    }
    return result;
  }
}

enum Tetrominoe {
  NO_SHAPE, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape, SQUARE_SHAPE, NoShape;

  int getValue() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getValue'");
  }

  int getRed() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRed'");
  }

  int getGreen() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getGreen'");
  }

  float getSaturation() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getSaturation'");
  }

  float getBrightness() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getBrightness'");
  }
};

class Board extends JPanel {
  static final long serialVersionUID = 1L;
  final int BOARD_WIDTH = 10;
  final int BOARD_HEIGHT = 22;
  final int INITIAL_DELAY = 100;
  final int PERIOD_INTERVAL = 300;

  Timer timer;
  boolean isFallingFinished = false;
  boolean isStarted = false;
  boolean isPaused = false;
  int numLinesRemoved = 0;
  int curX = 0;
  int curY = 0;
  JLabel statusbar;
  Shape curPiece;
  // ...

  Tetrominoe[] board; // Declare the 'board' variable

  public Board(Tetris parent) {
    initBoard(parent);
  }

  class ScheduleTask extends TimerTask {
    public void run() {
      // Add the necessary logic here
    }
  }

  void initBoard(Tetris parent) {
    setFocusable(true);
    setBorder(BorderFactory.createLineBorder(Color.pink, 4));
    timer = new Timer();
    timer.scheduleAtFixedRate(new ScheduleTask(), (long) INITIAL_DELAY, (long) PERIOD_INTERVAL);
    curPiece = new Shape();

    statusbar = parent.getStatusBar();
    board = new Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT];
    addKeyListener(new TAdapter());
    clearBoard();
  }

  void clearBoard() {
    for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; ++i) {
      board[i] = Tetrominoe.NoShape;
    }
  }

  int squareWidth() {
    return (int) getSize().getWidth() / BOARD_WIDTH;
  }

  int squareHeight() {
    return (int) getSize().getHeight() / BOARD_HEIGHT;
  }

  Tetrominoe shapeAt(int x, int y) {
    return board[(y * BOARD_WIDTH) + x];
  }

  public void start() {
    isStarted = true;
    clearBoard();
    newPiece();
  }

  private void newPiece() {
    curPiece.setRandomShape(); // set the shape of the piece to a random shape
    curX = BOARD_WIDTH / 2 + 1; // set the x coordinate of the piece to the middle of the board
    curY = BOARD_HEIGHT - 1 + curPiece.minY();

    if (!tryMove(curPiece, curX, curY)) { // if the piece cannot move to the new coordinates
      curPiece.setShape(Tetrominoe.NoShape); // set the shape of the piece to a no shape
      timer.cancel();
      isStarted = false;
      statusbar.setText("GAME OVER!");
    }
  }

  void pause() {
    if (!isStarted)
      return;

    isPaused = !isPaused;
    if (isPaused) {
      statusbar.setText("paused");
    } else {
      statusbar.setText(String.valueOf(numLinesRemoved));
    }
  }

  void doDrawing(Graphics g) {
    Dimension size = getSize();
    int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    doDrawing(g);
  }

  void dropDown() {
    int newY = curY;
    while (newY > 0) {
      if (!tryMove(curPiece, curX, newY - 1))
        break;
      --newY;
    }
    pieceDropped();
  }

  void oneLineDown() {
    if (!tryMove(curPiece, curX, curY - 1))
      pieceDropped();
  }

  boolean tryMove(Shape shape, int newX, int newY) {
    for (int i = 0; i < 4; ++i) {
      int x = newX + shape.x(i);
      int y = newY - shape.y(i);
      if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT)
        return false;
      if (shapeAt(x, y) != Tetrominoe.NoShape)
        return false;
    }

    curPiece = shape;
    curX = newX;
    curY = newY;
    repaint();
    return true;
  }

  void cleanBoard() {
    for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; ++i) {
      board[i] = Tetrominoe.NoShape;
    }
  }

  void pieceDropped() {
    for (int i = 0; i < 4; ++i) {
      int x = curX + curPiece.x(i);
      int y = curY - curPiece.y(i);
      board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
    }

    removeFullLines();

    if (!isFallingFinished)
      newPiece();
  }

  void removeFullLines() {

    int numFullLines = 0; // set the number of full lines to 0

    for (int i = BOARD_HEIGHT - 1; i >= 0; --i) { // for the no of rows in the board
      boolean lineIsFull = true; // set the line is full to true

      for (int j = 0; j < BOARD_WIDTH; ++j) { // for the no of columns in the board

        if (shapeAt(j, i) == Tetrominoe.NoShape) { // if the shape at the coordinates is a no shape

          lineIsFull = false; // set the line is full to false
          break; // break the loop
        }
      }

      if (lineIsFull) { // if the line is full

        ++numFullLines; // increment the number of full lines

        for (int k = i; k < BOARD_HEIGHT - 1; ++k) { // for the no of rows in the board
          for (int j = 0; j < BOARD_WIDTH; ++j) { // for the no of columns in the board

            board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1); // set the shape at the coordinates to the shape at the
                                                              // coordinates below
          }
        }
      }
    }

    if (numFullLines > 0) { // if the number of full lines is greater than 0

      numLinesRemoved += numFullLines; // increment the number of lines removed by the number of full lines
      statusbar.setText("Score: " + String.valueOf(numLinesRemoved)); // set the text of the score to the number of
                                                                      // lines removed
      isFallingFinished = true; // set the piece has finished falling to true
      curPiece.setShape(Tetrominoe.NoShape); // set the shape of the piece to a no shape
      repaint();
    }
  }

  // draw the square on the board
  void drawSquare(Graphics g, int x, int y,
      Tetrominoe shape) {

    Color colors[] = {
        new Color(0, 0, 0), new Color(204, 102, 102),
        new Color(102, 204, 102), new Color(102, 102, 204),
        new Color(204, 204, 102), new Color(204, 102, 204),
        new Color(102, 204, 204), new Color(218, 170, 0),

    };

    Color color = colors[shape.ordinal()];

    g.setColor(color);
    g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

    g.setColor(color.brighter());
    g.drawLine(x, y + squareHeight() - 1, x, y);
    g.drawLine(x, y, x + squareWidth() - 1, y);

    g.setColor(color.darker());
    g.drawLine(x + 1, y + squareHeight() - 1,
        x + squareWidth() - 1, y + squareHeight() - 1);
    g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
        x + squareWidth() - 1, y + 1);

  }

  void doGameCycle() {

    update();
    repaint();
  }

  void update() {

    if (isPaused) { // if the game is paused
      return; // return
    }

    if (isFallingFinished) { // if the piece has finished falling

      isFallingFinished = false; // set the piece has finished falling to false
      newPiece(); // create a new piece
    } else {

      oneLineDown(); // move the piece down one line
    }
  }

  // used to check the pressed keys
  class TAdapter extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

      if (!isStarted || curPiece.getShape() == Tetrominoe.NoShape) { // if the game has not started or the shape of the
                                                                     // piece is a no shape
        return; // return
      }

      int keycode = e.getKeyCode(); // get the key code of the pressed key

      if (keycode == KeyEvent.VK_ENTER) { // if the pressed key is the enter key
        pause(); // pause the game
        return; // return
      }

      if (isPaused) {
        return;
      }

      switch (keycode) { // switch the key code of the pressed key

        case KeyEvent.VK_LEFT: // if the pressed key is the left arrow
          tryMove(curPiece, curX - 1, curY); // try to move the piece to the left
          break;

        case KeyEvent.VK_RIGHT: // if the pressed key is the right arrow
          tryMove(curPiece, curX + 1, curY); // try to move the piece to the right
          break;

        case KeyEvent.VK_DOWN: // if the pressed key is the down arrow
          tryMove(curPiece.rotateRight(), curX, curY); // try to rotate the piece to the right
          break;

        case KeyEvent.VK_UP: // if the pressed key is the up arrow
          tryMove(curPiece.rotateLeft(), curX, curY); // try to rotate the piece to the left
          break;

        case KeyEvent.VK_SPACE: // if the pressed key is the space bar
          dropDown(); // drop the piece down
          break;

        case KeyEvent.VK_D: // if the pressed key is the d key
          oneLineDown(); // move the piece down one line
          break;
      }
    }
  }

}

// main class of the game
class Tetris extends JFrame {

  static final long serialVersionUID = 1L;
  JLabel statusbar;

  public Tetris() {

    initUI();
  }

  void initUI() {

    JPanel panel = new JPanel();
    panel.setForeground(new Color(0XF5EBE0));

    statusbar = new JLabel("Score: 0");
    statusbar.setFont(new Font("MV Boli", Font.BOLD, 30));
    panel.add(statusbar, BorderLayout.NORTH);

    Board board = new Board(this);
    add(panel, BorderLayout.NORTH);
    add(board);
    double securityWarningPointX;
    board.setBackground(new Color(0Xf0e2d3, securityWarningPointX, securityWarningPointX, securityWarningPointX));
    board.start();

    setTitle("Tetris");
    setSize(400, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
  }

  public JLabel getStatusBar() {

    return statusbar;
  }

  // run the game from here
  public static void main(String[] args) {

    EventQueue.invokeLater(() -> {

      Tetris game = new Tetris();
      game.setVisible(true);
    });
  }
}