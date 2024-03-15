package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// represents a game GUI
// references CardLayoutDemo
public class GameGUI implements ActionListener {
    JPanel cards;
    CardLayout cardLayout;
    private JButton newButton;
    private JButton loadButton;
    private JButton instructionsButton;
    private JButton backButton;

    public GameGUI() {
        newButton = new JButton("new game");
        loadButton = new JButton("load game");
        instructionsButton = new JButton("how to play");
    }

    public void addComponentToPane(Container pane) {
        // panel to hold cards
        cards = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cards.getLayout();

        //create the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(instructionsButton);

        // add action listeners for each button
        newButton.addActionListener(this);
        loadButton.addActionListener(this);
        instructionsButton.addActionListener(this);


        // new game panel
        JPanel newGamePanel = createCard("new game card");

        // load game panel
        JPanel loadGamePanel = createCard("load game card");

        // instructions panel
        JPanel instructionsPanel = createCard("instructions card");

        JTextArea textArea = new JTextArea("This is a fishing game with some challenges. To catch fish, "
                + "press the correct key (letter) that shows up. \n"
                + "There are 2 challenges for each round:\n"
                + "1. Try to catch the largest fish in the pond within a certain number of tries\n"
                + "2. Try to score the highest total weight in comparison to other rounds");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        instructionsPanel.add(textArea);

        // the panel that contains the cards
        cards.add(buttonPanel);
        cards.add(newGamePanel, "new game card");
        cards.add(loadGamePanel, "load game card");
        cards.add(instructionsPanel, "instructions card");

        pane.add(cards, BorderLayout.CENTER);
    }

    private JPanel createCard(String cardName) {
        JPanel card = new JPanel(new BorderLayout());
        JLabel label = new JLabel(cardName);
        card.add(label, BorderLayout.CENTER);
        backButton = new JButton("back");
        backButton.addActionListener(e -> cardLayout.first(cards));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        card.add(buttonPanel, BorderLayout.SOUTH);
        return card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newButton) {
            showCard("new game card");
        } else if (e.getSource() == loadButton) {
            showCard("load game card");
        } else if (e.getSource() == instructionsButton) {
            showCard("instructions card");
        }

    }

    private void showCard(String cardName) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, cardName);
    }

    private static void createAndShowGUI() {
        //set up the window
        JFrame frame = new JFrame("Fishing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(450, 350));

        //set up the content pane
        GameGUI demo = new GameGUI();
        demo.addComponentToPane(frame.getContentPane());

        //display the window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}