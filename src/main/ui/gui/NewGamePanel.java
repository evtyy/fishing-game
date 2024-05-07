package ui.gui;

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
    private FishDrawing fishDrawing;
    private JTextField userInputField;
    private JButton catchButton;
    private JButton recastButton;
    JButton confirmButton;
    JButton cancelButton;

    private Fishes fishesCaught;
    private Fish currentFish;
    private static TotalRounds totalRounds = new TotalRounds();

    private JPanel fishPanel;
    private JFrame releaseFishFrame;
    private static final int MAX_TRIES = 3;
    private int recastButtonCounter;
    private int catchButtonCounter;
    private int randomIndex;
    private boolean gameLoaded;

    // MODIFIES: this
    // EFFECTS: constructs a JPanel with a user input panel and initializes a new game
    public NewGamePanel() {
        setLayout(new BorderLayout());
        initUserInputPanel();
        newGame();
    }


    // EFFECTS: adds an ActionListener to catchButton, determines what happens when catchButton pressed
    private void catchButtonActionListener() {
        catchButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this
            // EFFECTS: can only catch fish MAX_TRIES (3) times:
            //          when pressed, if user input is correct, do fishesCaught();
            //          if not, do fishSwamAway()
            public void actionPerformed(ActionEvent e) {
                String userInputText = userInputField.getText();
                if (!userInputText.isEmpty()) {
                    char userInput = userInputText.charAt(0);
                    if (userInput == currentFish.getLetter()) {
                        fishCaught();
                    } else {
                        fishSwamAway();
                    }
                }
                userInputField.setText("");
                catchButtonCounter++;
                if (catchButtonCounter == MAX_TRIES) {
                    gameOverWindow();
                }
            }
        });
    }

    // MODIFIES: fishDrawing, fishesCaught, game
    // EFFECTS: updates fishDrawing for correct input
    private void fishCaught() {
        fishesCaught.catchFish(currentFish);
        game.getFishesTotal().getFishList().remove(randomIndex);
        fishDrawing.setCaught(true);
        fishDrawing.setSwamAway(false);
        fishDrawing.repaint();
    }

    // MODIFIES: fishDrawing
    // EFFECTS: updates fishDrawing for wrong input
    private void fishSwamAway() {
        fishDrawing.setCaught(false);
        fishDrawing.setSwamAway(true);
        fishDrawing.repaint();
        showPopup("Wrong letter! Fish swam away", 2000);
    }

    // MODIFIES: this
    // EFFECTS: constructs a pop-up message
    private void showPopup(String message, int delayTime) {
        JOptionPane pane = new JOptionPane(message);
        JDialog dialog = pane.createDialog("");
        Timer timer = new Timer(delayTime, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    // EFFECTS: adds an ActionListener to recastButton, determines what happens when recastButton pressed
    private void recastButtonActionListener() {
        recastButton.addActionListener(new ActionListener() {
            @Override
            // MODIFIES: this, fishDrawing, recastButton
            // EFFECTS: when pressed, recast line; disable recastButton when MAX_TRIES reached
            public void actionPerformed(ActionEvent e) {
                fishDrawing.setCaught(false);
                fishDrawing.setSwamAway(false);
                playRound();

                recastButtonCounter++;
                System.out.println("times reset button pressed: " + recastButtonCounter);
                if (recastButtonCounter >= 2) {
                    recastButton.setEnabled(false);
                    showPopup("Last try!", 1200);

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
        recastButton.setEnabled(true);

        inputPanel.add(new JLabel("Type the character for the fish: "));
        inputPanel.add(userInputField);
        inputPanel.add(catchButton);
        inputPanel.add(recastButton);
        catchButtonActionListener();
        recastButtonActionListener();
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
        for (Fish f : fishesCaught) {
            summary.append("Fish caught with weight: ").append(f.getWeight()).append(" lb\n");
        }
        RoundSummary roundSummary = new RoundSummary(fishesCaught, summary.toString());
        roundSummary.setFishLeftInPond(game.getFishesTotal());
        return roundSummary;
    }

    // MODIFIES: this
    // EFFECTS: generates a game over window with list of fish caught and option to release fish
    private void gameOverWindow() {
        totalRounds.addRoundSummary(getRoundSummary());
        JPanel messagePanel = new JPanel(new BorderLayout());
        //JLabel gameOverLabel = new JLabel("game over!");
        //messagePanel.add(gameOverLabel, BorderLayout.NORTH);

        // summary of the fish caught
        JTextArea summaryTextArea = initSummary();
        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        messagePanel.add(scrollPane, BorderLayout.CENTER);

        JLabel releaseFishLabel = new JLabel("Release fish?");
        messagePanel.add(releaseFishLabel, BorderLayout.SOUTH);

        ImageIcon originalIcon = new ImageIcon("./data/gameover.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);


        int option = JOptionPane.showConfirmDialog(null, messagePanel, "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, resizedIcon);

        if (option == JOptionPane.YES_OPTION) {
            showReleaseFishOption();
        } else {
            showPlayAgainOption();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays option to save game
    private void showSaveGameOption() {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());

        JLabel saveGameLabel = new JLabel("Save game?");
        messagePanel.add(saveGameLabel, BorderLayout.NORTH);

        ImageIcon originalIcon = new ImageIcon("./data/savegame.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        int option = JOptionPane.showConfirmDialog(null, messagePanel, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, resizedIcon);

        if (option == JOptionPane.YES_OPTION) {
            saveGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves game to file
    private void saveGame() {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(totalRounds);
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
        showSummary();
        newGame();
    }

    // MODIFIES: this, releaseFishFrame, fishPanel
    // EFFECTS: generates and displays a JFrame with checkboxes for releasing fish
    private void showReleaseFishOption() {
        releaseFishFrame = new JFrame("Release fish");
        releaseFishFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fishPanel = new JPanel(new GridLayout(0, 1));
        for (Fish f : fishesCaught) {
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
                showSaveGameOption();
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
                showPlayAgainOption();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: shows fishes caught
    private void showSummary() {
        ImageIcon originalIcon = new ImageIcon("./data/summary.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(85, 85, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JOptionPane.showMessageDialog(null, initSummary(), "Summary",
                JOptionPane.INFORMATION_MESSAGE, resizedIcon);

    }

    // MODIFIES: this
    // EFFECTS: initializes summary of fishes caught in a JTextArea
    private JTextArea initSummary() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        if (!gameLoaded) {
            for (Fish f : fishesCaught.getFishList()) {
                textArea.append("Fish letter: " + f.getLetter() + ", Weight: " + f.getWeight() + "\n");
            }
            textArea.append("Largest caught?: " + fishesCaught.isLargestCaught() + "\n\n");
        } else {
            for (Fishes fishes : totalRounds.getFishCaughtAllRounds()) {
                textArea.append(fishes.getDateCaught() + "\n");
                for (Fish f : fishes.getFishList()) {
                    textArea.append("Fish letter: " + f.getLetter() + ", Weight: " + f.getWeight() + "\n");
                }
                textArea.append("Largest caught?: " + fishes.isLargestCaught() + "\n\n");
            }
        }
        return textArea;
    }

    // EFFECTS: starts a new game
    public void newGame() {
        fishesCaught = new Fishes();
        recastButtonCounter = 0;
        catchButtonCounter = 0;
        fishDrawing = new FishDrawing();
        add(fishDrawing, BorderLayout.CENTER);

        if (!gameLoaded) {
            game = new Game();
        }
        playRound();
    }

    // MODIFIES: this
    // EFFECTS: displays option to play again
    private void showPlayAgainOption() {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());

        JLabel playAgainLabel = new JLabel("Play again?");
        messagePanel.add(playAgainLabel, BorderLayout.NORTH);

        ImageIcon originalIcon = new ImageIcon("./data/playagain.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        int option = JOptionPane.showConfirmDialog(null, messagePanel,
                "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, resizedIcon);
        if (option == JOptionPane.YES_OPTION) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.add(new NewGamePanel());
            topFrame.pack();
            topFrame.setLocationRelativeTo(null);
        } else {
            showSaveGameOption();
        }
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







