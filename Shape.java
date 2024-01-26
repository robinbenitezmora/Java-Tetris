import java.util.Random;

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
}