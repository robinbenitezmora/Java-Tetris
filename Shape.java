import java.util.Random;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.scene.paint.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    setFocusable(true);
    setBorder(BorderFactory.createDashedBorder(Color.BLACK));
    timer = new Timer();
    timer.scheduleAtFixedRate(new ScheduleTask(), (long) INITIAL_DELAY, (long) PERIOD_INTERVAL);

    curPiece = new Shape();

    statusbar = parent.getStatusBar();
    board = new Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT]; // Initialize the 'board' variable
    addKeyListener((KeyListener) new TAdapter());
    clearBoard();
  }

  private void clearBoard() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'clearBoard'");
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'newPiece'");
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
    int numFullLines = 0;

    for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {
      boolean lineIsFull = true;

      for (int j = 0; j < BOARD_WIDTH; ++j) {
        if (shapeAt(j, i) == Tetrominoe.NoShape) {
          lineIsFull = false;
          break;
        }
      }

      if (lineIsFull) {
        ++numFullLines;
        for (int k = i; k < BOARD_HEIGHT - 1; ++k) {
          for (int j = 0; j < BOARD_WIDTH; ++j) {
            board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
          }
        }
      }
    }

    if (numFullLines > 0)

    {
      numLinesRemoved += numFullLines;
      statusbar.setText(String.valueOf(numLinesRemoved));
      isFallingFinished = true;
      curPiece.setShape(Tetrominoe.NoShape);
      repaint();
    }
  }

  void drawSquare(Graphics g, int x, int y, Tetrominoe shape) {
    // ...

    float[] hsb = new float[] { shape.getValue(), shape.getSaturation(), shape.getBrightness() };
    javafx.scene.paint.Color color;
    javafx.scene.paint.Color awtColor = new Color((int) (color.getRed() * 255), (int) (color.getGreen() * 255),
        (int) (color.getBlue() * 255), (int) (getAlignmentX() * 255));
    g.setColor(awtColor);
    g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
    g.setColor(awtColor.brighter());
    g.drawLine(x, y + squareHeight() - 1, x, y);
    g.drawLine(x, y, x + squareWidth() - 1, y);
    g.setColor(awtColor.darker());
    g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
    g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
  }

  void drawBoard(Graphics g) {
    for (int i = 0; i < BOARD_HEIGHT; ++i) {
      for (int j = 0; j < BOARD_WIDTH; ++j) {
        Tetrominoe shape = shapeAt(j, BOARD_HEIGHT - i - 1);
        if (shape != Tetrominoe.NoShape)
          drawSquare(g, j * squareWidth(), (BOARD_HEIGHT - i - 1) * squareHeight(), shape);
      }
    }
  }

  class ScheduleTask extends java.util.TimerTask {
    public void run() {
      if (isFallingFinished) {
        isFallingFinished = false;
        newPiece();
      } else {
        oneLineDown();
      }
    }
  }

  class TAdapter extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      if (!isStarted || curPiece.getShape() == Tetrominoe.NoShape) {
        return;
      }

      int keycode = e.getKeyCode();

      if (keycode == 'p' || keycode == 'P') {
        pause();
        return;
      }

      if (isPaused)
        return;

      switch (keycode) {
        case KeyEvent.VK_LEFT:
          tryMove(curPiece, curX - 1, curY);
          break;
        case KeyEvent.VK_RIGHT:
          tryMove(curPiece, curX + 1, curY);
          break;
        case KeyEvent.VK_DOWN:
          tryMove(curPiece.rotateRight(), curX, curY);
          break;
        case KeyEvent.VK_UP:
          tryMove(curPiece.rotateLeft(), curX, curY);
          break;
        case KeyEvent.VK_SPACE:
          dropDown();
          break;
        case 'd':
          oneLineDown();
          break;
        case 'D':
          oneLineDown();
          break;
      }

    }
  }
}