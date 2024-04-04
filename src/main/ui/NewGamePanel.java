package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

// represents a JPanel for the game
public class NewGamePanel extends JPanel {
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    private Game game;
    private RoundSummary roundSummary;
    private FishDrawing fishDrawing;
    private JTextField userInputField;
    private JButton catchButton;
    private JButton recastButton;
    JButton confirmButton;
    JButton cancelButton;

    private Fishes fishesCaught;
    private static final int MAX_TRIES = 3;
    private int recastButtonCounter = 0;
    private int catchButtonCounter = 0;
    private int randomIndex;
    private Fish currentFish;
    private TotalRounds totalRounds;
    private boolean gameLoaded;
    private JPanel fishPanel;
    private JFrame releaseFishFrame;


    // MODIFIES: this
    // EFFECTS: constructs a JPanel, initializes variables
    public NewGamePanel() {
        fishesCaught = new Fishes();
        fishDrawing = new FishDrawing();
        totalRounds = new TotalRounds();

        setLayout(new BorderLayout());
        add(fishDrawing, BorderLayout.CENTER);

        initUserInputPanel();
        catchButtonActionListener();
        recastButtonActionListener();
        newGame();
    }


    // EFFECTS: adds an ActionListener to catchButton, determines what happens when catchButton pressed
    private void catchButtonActionListener() {
        catchButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this, fishesCaught, game
            // EFFECTS: can only catch fish MAX_TRIES (3) times:
            //          if user input is correct, add fish to fishesCaught and remove from drawing;
            //          if not, do nothing
            public void actionPerformed(ActionEvent e) {
                String userInputText = userInputField.getText();
                if (!userInputText.isEmpty()) {
                    char userInput = userInputText.charAt(0);
                    if (userInput == currentFish.getLetter()) {
                        fishesCaught.catchFish(currentFish);
                        game.getFishesTotal().getFishList().remove(randomIndex);
                        System.out.println("You caught the fish with weight " + currentFish.getWeight() + " lb!");

                        fishDrawing.setCaught(true);
                        fishDrawing.repaint();

                        if (currentFish.isLargest()) {
                            System.out.println("Largest fish caught!");
                        }
                    }
                }
                catchButtonCounter++;
                if (catchButtonCounter == MAX_TRIES) {
                    gameOverWindow();
                }
            }
        });
    }

    // EFFECTS: adds an ActionListener to recastButton, determines what happens when recastButton pressed
    private void recastButtonActionListener() {
        recastButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this, fishDrawing, recastButton
            // EFFECTS: disable recastButton when MAX_TRIES reached
            public void actionPerformed(ActionEvent e) {
                fishDrawing.setCaught(false);
                playRound();

                recastButtonCounter++;
                System.out.println("times reset button pressed: " + recastButtonCounter);
                if (recastButtonCounter >= 2) {
                    recastButton.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "last try!");

                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes user input panel and buttons for catch and recast line
    private void initUserInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout());
        userInputField = new JTextField(1);
        catchButton = new JButton("Catch");
        recastButton = new JButton("Recast");

        inputPanel.add(new JLabel("Type the character for the fish: "));
        inputPanel.add(userInputField);
        inputPanel.add(catchButton);
        inputPanel.add(recastButton);
        add(inputPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: returns the summary of a round
    public RoundSummary getRoundSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Round Summary: ")
                .append("Date caught: ").append(fishesCaught.getDateCaught())
                .append(" Largest fish was caught: ").append(fishesCaught.isLargestCaught())
                .append(" Total weight: ").append(fishesCaught.getTotalWeight()).append(" lb\n");
        for (Fish f : fishesCaught.getFishList()) {
            summary.append("Fish caught with weight: ").append(f.getWeight()).append(" lb\n");
        }
        roundSummary = new RoundSummary(fishesCaught, summary.toString());
        roundSummary.setFishLeftInPond(game.getFishesTotal());
        return roundSummary;
    }

    // MODIFIES: this
    // EFFECTS: generates a game over window with list of fish caught and option to release fish
    private void gameOverWindow() {
        totalRounds.addRoundSummary(getRoundSummary());
        JPanel messagePanel = new JPanel(new BorderLayout());
        JLabel gameOverLabel = new JLabel("Game over!");
        messagePanel.add(gameOverLabel, BorderLayout.NORTH);

        // summary of the fish caught
        JTextArea summaryTextArea = initSummary();
        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        messagePanel.add(scrollPane, BorderLayout.CENTER);

        JLabel releaseFishLabel = new JLabel("release fish?");
        messagePanel.add(releaseFishLabel, BorderLayout.SOUTH);

        int option = JOptionPane.showConfirmDialog(null, messagePanel, "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            releaseFishOption();
        } else {
            saveGameOption();
        }

    }

    // MODIFIES: this
    // EFFECTS: displays option to save game
    private void saveGameOption() {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());

        JLabel saveGameLabel = new JLabel("Save game option");
        messagePanel.add(saveGameLabel, BorderLayout.NORTH);

        int option = JOptionPane.showConfirmDialog(null, messagePanel, "save game?", JOptionPane.YES_NO_OPTION);

        System.out.println(fishesCaught.getFishList().size());
        totalRounds.addListOfFishCaught(fishesCaught);
        if (option == JOptionPane.YES_OPTION) {
            saveGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves game to file
    private void saveGame() {
        try {
            jsonWriter.openWriter();
            RoundSummary mostRecentSummary = totalRounds.getRoundSummaries().get(0);
            for (RoundSummary summary : totalRounds.getRoundSummaries()) {
                String date = mostRecentSummary.getFishCaughtThisRound().getDateCaught();
                if (summary.getFishCaughtThisRound().getDateCaught().compareTo(date) > 0) {
                    mostRecentSummary = summary;
                }
            }
            TotalRounds mostRecentTotalRounds = new TotalRounds();
            mostRecentTotalRounds.addRoundSummary(mostRecentSummary);

            jsonWriter.write(mostRecentTotalRounds);
            jsonWriter.closeWriter();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    public void loadGame() {
        setGameLoaded(true);
        try {
            totalRounds = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);

        } catch (IOException e) {
            System.out.println("Unable to load from file: " + JSON_STORE);
        }
        fishesCaught = totalRounds.getFishCaughtAllRounds().get(0);
        showSummary();
        newGame();
    }

    // MODIFIES: this, releaseFishFrame, fishPanel
    // EFFECTS: generates and displays a JFrame with checkboxes for releasing fish
    private void releaseFishOption() {
        releaseFishFrame = new JFrame("release fish");
        releaseFishFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fishPanel = new JPanel(new GridLayout(0, 1));
        for (Fish f : fishesCaught.getFishList()) {
            JCheckBox checkBox = new JCheckBox("Fish letter: " + f.getLetter() + ", Weight: " + f.getWeight());
            fishPanel.add(checkBox);
        }

        confirmButton = new JButton("confirm");
        cancelButton = new JButton("cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        confirmReleaseButtonActionListener();
        cancelReleaseButtonActionListener();

        releaseFishFrame.getContentPane().setLayout(new BorderLayout());
        releaseFishFrame.getContentPane().add(new JScrollPane(fishPanel), BorderLayout.CENTER);
        releaseFishFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        releaseFishFrame.pack();
        releaseFishFrame.setLocationRelativeTo(null);
        releaseFishFrame.setVisible(true);
    }

    // MODIFIES: cancelButton, releaseFishFrame
    // EFFECTS: if cancel release, display save game option
    private void cancelReleaseButtonActionListener() {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                releaseFishFrame.dispose();
                saveGameOption();
            }
        });
    }

    // MODIFIES: confirmButton, releaseFishFrame
    // EFFECTS: if checkbox checked, release fish
    private void confirmReleaseButtonActionListener() {
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (Component component : fishPanel.getComponents()) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        if (checkBox.isSelected()) {
                            List<Fish> fishesCaughtCopy = new ArrayList<>(fishesCaught.getFishList());
                            for (Fish f : fishesCaughtCopy) {
                                if (checkBox.getText().contains(String.valueOf(f.getWeight()))) {
                                    fishesCaught.releaseFish(f);
                                    game.getFishesTotal().addFish(f);
                                }
                            }
                            System.out.println("Released: " + checkBox.getText());
                        }
                    }
                }
                releaseFishFrame.dispose();
                showSummary();
                saveGameOption();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: shows fishes caught
    private void showSummary() {
        JOptionPane.showMessageDialog(null, initSummary(), "Summary",
                JOptionPane.INFORMATION_MESSAGE);

    }

    // MODIFIES: this
    // EFFECTS: initializes summary of fishes caught in a JTextArea
    private JTextArea initSummary() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        textArea.append("Summary: \n");
        for (Fish f : fishesCaught.getFishList()) {
            textArea.append("Fish letter: " + f.getLetter() + ", Weight: " + f.getWeight() + "\n");
        }
        textArea.append("Largest caught?: " + fishesCaught.isLargestCaught());

        return textArea;
    }

    // EFFECTS: starts a new game
    public void newGame() {
        if (!gameLoaded) {
            game = new Game();
        } else {
            fishesCaught = totalRounds.getFishCaughtAllRounds().get(0);
        }
        playRound();
    }

    // EFFECTS: sets whether game was loaded
    public void setGameLoaded(boolean loaded) {
        this.gameLoaded = loaded;
    }

    // MODIFIES: fishesCaught, fishDrawing
    // EFFECTS: displays a random fish from pond and its letter
    public void playRound() {
        Random random = new Random();
        Date date = new Date();
        fishesCaught.setDateCaught(date.toString());

        int fishAvail = game.getFishesTotal().getFishList().size();
        randomIndex = random.nextInt(fishAvail);
        currentFish = game.getFishesTotal().getFishList().get(randomIndex);
        fishDrawing.setLetter(currentFish.getLetter());
        fishDrawing.repaint();
    }
}







