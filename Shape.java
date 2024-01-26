import java.util.Random;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Tetrominoe;
import Tetrominoe;
import Tetrominoe;

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
  NO_SHAPE, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape
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
  Tetrominoe[] board;

  public Board(Tetris parent) {
    initBoard(parent);
  }

  private void initBoard(Tetris parent) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'initBoard'");
  }
}