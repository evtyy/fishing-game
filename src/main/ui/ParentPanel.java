package ui;

import model.Fishes;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a JPanel of CardLayout that holds JPanels for game, load game, and instructions
// references CardLayoutDemo https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/layout/card.html
public class ParentPanel extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private FishDrawing fishDrawing;
    private Fishes fishesCaught;

    private JPanel cards;
    private NewGamePanel newGamePanel;
    private JPanel loadGamePanel;
    private JPanel instructionsPanel;
    private CardLayout cardLayout;
    private JButton newButton;
    private JButton loadButton;
    private JButton instructionsButton;
    private JButton backButton;


    // EFFECTS: constructs a JPanel and initializes fields
    public ParentPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newButton = new JButton("new game");
        loadButton = new JButton("load game");
        instructionsButton = new JButton("how to play");
        fishesCaught = new Fishes();
        fishDrawing = new FishDrawing();
        newGamePanel = new NewGamePanel();
        addCardsToPanel();
    }

    // MODIFIES: this, instructionsPanel
    // EFFECTS: creates cards and buttons and adds them to JPanel
    public void addCardsToPanel() {
        // panel to hold cards
        cards = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cards.getLayout();

        JPanel buttonPanel = initButtonPanel();

        // load game panel
        loadGamePanel = createCard("load game card");
        loadGameOption();

        // instructions panel
        instructionsPanel = createCard("instructions card");
        instructionsPanel.add(generateInstructions());

        // the panel that contains the cards
        cards.add(buttonPanel);
        cards.add(newGamePanel, "new game card");
        cards.add(loadGamePanel, "load game card");
        cards.add(instructionsPanel, "instructions card");

        add(cards, BorderLayout.CENTER);
    }

    // MODIFIES: this, newButton, loadButton, instructionsButton
    // EFFECTS: creates and returns buttons each with action listeners
    private JPanel initButtonPanel() {
        //create the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(instructionsButton);

        // add action listeners for each button
        newButton.addActionListener(this);
        loadButton.addActionListener(this);
        instructionsButton.addActionListener(this);
        return buttonPanel;
    }

    // MODIFIES: this, loadGamePanel
    // EFFECTS: displays option to load game
    private void loadGameOption() {
        JLabel loadGameLabel = new JLabel("load game?");
        JButton loadGameButton = new JButton("load");
        loadGamePanel.add(loadGameLabel, BorderLayout.NORTH);
        loadGamePanel.add(loadGameButton, BorderLayout.CENTER);

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            // EFFECTS: load game and display newGamePanel
            public void actionPerformed(ActionEvent e) {
                newGamePanel.loadGame();
                showCard("new game card");
            }
        });
    }


    @Override
    // EFFECTS: determines which card to show depending on button pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newButton) {
            showCard("new game card");
        } else if (e.getSource() == loadButton) {
            showCard("load game card");
        } else if (e.getSource() == instructionsButton) {
            showCard("instructions card");
        }
    }

    // EFFECTS: shows the card in CardLayout corresponding to cardName
    private void showCard(String cardName) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, cardName);
    }

    // EFFECTS: generates the instructions for how to play
    private JTextArea generateInstructions() {
        JTextArea textArea = new JTextArea("This is a fishing game with challenges. To catch fish, "
                + "press the correct key (letter) that shows up. \n"
                + "The challenge for each game:\n"
                + "Try to catch the largest fish in the pond within 3 tries");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    // MODIFIES: this, backButton
    // EFFECTS: creates a JPanel with given cardName
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


}
