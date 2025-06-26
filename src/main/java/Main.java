
import main.java.views.EventoViewer;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EventoViewer viewer = new EventoViewer();
            viewer.setVisible(true);
        });
    }
}
