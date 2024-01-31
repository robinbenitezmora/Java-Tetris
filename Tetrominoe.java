
public class Tetrominoe {

 public static final Tetrominoe SQUARE_SHAPE = null;
 public static Tetrominoe NoShape;

 public int getValue() {
  if (this == SQUARE_SHAPE) {
   return 0;
  } else if (this == NoShape) {
   return 0;
  } else {
   return 0;
  }
 }

 public static Tetrominoe[] values() {
  return new Tetrominoe[] { SQUARE_SHAPE, NoShape };
 }
}
