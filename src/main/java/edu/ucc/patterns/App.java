package edu.ucc.patterns;

import edu.ucc.patterns.ui.ShippingDashboard;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class App {
    private App() { }

    public static void main(String[] args) {
        setSystemLookAndFeel();
        SwingUtilities.invokeLater(() -> new ShippingDashboard().setVisible(true));
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // The default Swing theme remains available if the system theme cannot be loaded.
        }
    }
}
