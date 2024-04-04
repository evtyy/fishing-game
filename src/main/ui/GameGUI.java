package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// represents a game GUI
public class GameGUI extends JFrame implements WindowListener {

    // EFFECTS: sets up the JFrame for the game
    public GameGUI() {
        setTitle("Fishing Game");
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(490, 400));
        add(new ParentPanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // EFFECTS: runs the GUI
    public static void main(String[] args) {
        new GameGUI();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        printLog(EventLog.getInstance());
    }

    // references AlarmSystem ScreenPrinter's printLog method
    // EFFECTS: prints event log to terminal
    public void printLog(EventLog el) {
        for (Event event : el) {
            System.out.println(event.toString() + "\n");
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}