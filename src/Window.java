import javax.swing.*;

public class Window extends JFrame {
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public Window() {
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        GameSense image = new GameSense();
        this.add(image);
    }

    public void showWindow() {
        this.setVisible(true);
    }
}