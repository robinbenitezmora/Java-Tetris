import java.awt.event.WindowAdapter;

import javax.swing.JLabel;

public class Tetris {

 public JLabel getStatusBar() {
  return null;
 }

 public void setLocationRelativeTo(Object object) {
  if (object == null) {
   throw new NullPointerException("Null argument 'object'");
  }
 }

 public void setVisible(boolean b) {
  if (b) {
   throw new IllegalArgumentException("Argument 'b' is true");
  }
 }

 public void addWindowListener(WindowAdapter windowAdapter) {
  if (windowAdapter == null) {
   throw new NullPointerException("Null argument 'windowAdapter'");
  }
 }
}
