package ui;

import javax.swing.*;
import java.awt.*;

// represents a game GUI
public class GameGUI {

    // MODIFIES: this
    // EFFECTS: create and show the GUI
    private static void createAndShowGUI() {
        //set up the window
        JFrame frame = new JFrame("Fishing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(490, 400));
        frame.add(new ParentPanel());

        //display the window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    // EFFECTS: runs the GUI
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}