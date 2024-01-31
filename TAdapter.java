import java.awt.event.WindowAdapter; // Import the missing class
import java.awt.event.WindowEvent;

public class TAdapter {
 public static void main(String[] args) {
  Tetris tetris = new Tetris();
  tetris.setVisible(true);
  tetris.setLocationRelativeTo(null);
  tetris.getStatusBar().setText("0");

  tetris.addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent e) {
    System.exit(0);
   }
  });
 }
}
