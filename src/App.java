import javax.swing.SwingUtilities;

import view.InterfazMapa;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new InterfazMapa().setVisible(true);
        });
    }
}
