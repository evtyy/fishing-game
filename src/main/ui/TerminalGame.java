package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// Represents the terminal/ui of the game
public class TerminalGame {
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Scanner input;
    private Game game;
    private static TotalRounds totalRounds = new TotalRounds();
    private RoundSummary roundSummary;
    private Fishes fishesCaught;
    private boolean fishReleased;

    private static final int MAX_TRIES = 3;


    // EFFECTS: starts the terminal of the game
    public TerminalGame() {
        input = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        fishesCaught = new Fishes();
        run();
    }

    // EFFECTS: initializes the fishes with random weights and chars in the pond
    public void newGame() {
        boolean playAgain = true;

        while (playAgain) {
            game = new Game();
            playRound(MAX_TRIES);
            displayResults();
            releaseFishOption();
            totalRounds.addListOfFishCaught(fishesCaught);
            printWeights();
            playAgain = playAgainOption(); // if true, play again
            fishesCaught = new Fishes(); // resets list of fishesCaught
        }
        printAllRounds();
    }

    // EFFECTS: runs the game
    public void run() {
        boolean quitGame = false;
        String command = null;
        input = new Scanner(System.in);

        while (!quitGame) {
            startingMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                quitGame = true;
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: determines what each letter does when user interacts
    private void processCommand(String command) {
        if (command.equals("l")) {
            loadGame();
        } else if (command.equals("c")) {
            newGame();
        } else if (command.equals("n")) {
            newGame();
        } else if (command.equals("s")) {
            saveGame();
        } else if (command.equals("p")) {
            printAllRounds();
        } else if (command.equals("d")) {
            printRounds();
        } else if (command.equals("z")) {
            printStats();
        }
    }

    // EFFECTS: displays a menu of options for user
    private void startingMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tn -> new game");
        System.out.println("\tl -> load game");
        System.out.println("\tc -> continue game");
        System.out.println("\ts -> save game");
        System.out.println("\tp -> see collection");
        System.out.println("\td -> see collection");
        System.out.println("\tz -> print game stats");
        System.out.println("\tq -> quit game");
    }

    // MODIFIES: this, fishesCaught, fishesTotal
    // EFFECTS: conducts a round of the game
    public void playRound(int maxTries) {
        Random random = new Random();
        Date date = new Date();
        fishesCaught.setDateCaught(date.toString());
        for (int i = 0; i < maxTries; i++) {
            int fishAvail = game.getFishesTotal().getFishList().size();
            int randomIndex = random.nextInt(fishAvail);
            Fish currentFish = game.getFishesTotal().getFishList().get(randomIndex);
            System.out.println("Type the character for the fish: " + currentFish.getLetter());
            char playerInput = input.next().charAt(0);

            if (playerInput == currentFish.getLetter()) {
                fishesCaught.catchFish(currentFish);
                game.getFishesTotal().getFishList().remove(randomIndex);
                System.out.println("You caught the fish with weight " + currentFish.getWeight() + " lb!");
                if (currentFish.isLargest()) {
                    System.out.println("Largest fish caught!");
                }
            } else {
                System.out.println("The fish swam away!");
            }
        }
    }

    // EFFECTS: displays the results of a round
    public void displayResults() {
        System.out.println("Game over!");
        System.out.println(printRoundSummary());
    }

    // MODIFIES: this
    // EFFECTS: prints the summary of a round
    public String printRoundSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Round Summary: ")
                .append("Date caught: ").append(fishesCaught.getDateCaught())
                .append(" Largest fish was caught: ").append(fishesCaught.isLargestCaught())
                .append(" Total weight: ").append(fishesCaught.getTotalWeight()).append(" lb\n");
        for (Fish f : fishesCaught.getFishList()) {
            summary.append("Fish caught with weight: ").append(f.getWeight()).append(" lb\n");
        }
        roundSummary = new RoundSummary(fishesCaught, summary.toString());
        return summary.toString();
    }


    // EFFECTS: prints the summaries of each round played in one session
    public void printAllRounds() {
        System.out.println("All rounds played this session:");
        for (String summary : totalRounds.getAllSummaries()) {
            System.out.println(summary);
        }
    }


    // EFFECTS: displays the option to play another round
    public boolean playAgainOption() {
        System.out.println("Play another round? y or n");
        char playerInput = input.next().charAt(0);
        return playerInput == 'y';
    }

    // REQUIRES: fishesCaught is non-empty
    // MODIFIES: this, fishesCaught, fishesTotal
    // EFFECTS: displays the option to release fish that were just caught; if no fish with corresponding
    //          weight found, notify the player
    public void releaseFishOption() {
        System.out.println("Would you like to release fish? y or n");
        char playerInput = input.next().charAt(0);
        if (playerInput == 'y') {
            System.out.println("Release fish with weight: ");
            int releaseWeight = input.nextInt();
            //a copy of fishesCaught to iterate with
            List<Fish> fishesCaughtCopy = new ArrayList<>(fishesCaught.getFishList());
            fishReleased = false;
            for (Fish f : fishesCaughtCopy) {
                if (releaseWeight == f.getWeight()) {
                    fishesCaught.releaseFish(f);
                    game.getFishesTotal().addFish(f); //return fish to pond
                    System.out.println("Fish with weight " + releaseWeight + " released");
                    fishReleased = true;
                    releaseFishOption();
                    break;
                }
            }
            if (!fishReleased) {
                System.out.println("No fish with weight " + releaseWeight + " found");
                releaseFishOption();
            }
        }
        updateRoundSummary();
    }

    public void updateRoundSummary() {
        if (fishReleased) {
            totalRounds.removeRoundSummary(roundSummary);
            roundSummary = new RoundSummary(fishesCaught, printRoundSummary());
        }
        roundSummary.setFishCaughtThisRound(fishesCaught);
        roundSummary.setFishLeftInPond(game.getFishesTotal());
        totalRounds.addRoundSummary(roundSummary);

    }

    // EFFECTS: prints fish weights of fish left in pond (including released)
    public void printWeights() {
        System.out.println("Fish left in pond: ");
        for (Fish f : game.getFishesTotal().getFishList()) {
            System.out.println("Fish with weight: " + f.getWeight() + " lb");
        }
    }

    // EFFECTS: prints fish caught for all rounds
    private void printRounds() {
        System.out.println("All rounds played:");
        List<Fishes> fishes = totalRounds.getFishCaughtAllRounds();
        System.out.println("Fish caught per round: ");
        for (Fishes f : fishes) {
            System.out.println(f.getFishList());
        }
    }

    // EFFECTS: prints game stats
    private void printStats() {
        System.out.println("Game stats: ");
        System.out.println("Largest caught: " + totalRounds.largestCaughtPercentage() + "%");
        System.out.println("Avg total weight: " + totalRounds.avgTotalWeight() + "lb");
        System.out.println("Avg number of tries to win: " + totalRounds.getAvgNumTriesToWin());

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
    private void loadGame() {
        try {
            totalRounds = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load from file: " + JSON_STORE);
        }
    }

}

