package ui;

import model.Fish;
import model.Fishes;
import model.TotalRounds;

import java.util.*;

// Represents the terminal/ui of the game
public class TerminalGame {
    private Scanner input;
    private List<Integer> randomWeights;
    private TotalRounds totalRounds;
    private Fishes fishesTotal;
    private Fishes fishesCaught;
    private List<String> allSummaries;
    private boolean playAgain;

    private static final int TOTAL_FISH = 10;
    private static final int MAX_TRIES = 3;
    private static final int MIN_WEIGHT = 1;
    private static final int MAX_WEIGHT = 30;

    // EFFECTS: starts the terminal of the game
    public TerminalGame() {
        start();
    }

    // EFFECTS: initializes the fishes with random weights and chars in the pond
    public void start() {
        input = new Scanner(System.in);
        randomWeights = generateRandomIntegers(TOTAL_FISH, MIN_WEIGHT, MAX_WEIGHT);
        fishesTotal = new Fishes();
        fishesCaught = new Fishes();
        totalRounds = new TotalRounds();
        allSummaries = new ArrayList<>();
        playAgain = true;

        initFishesInPond();
        setRandomWeights();
        fishesTotal.getLargest();

        while (playAgain) {
            playRound(MAX_TRIES);
            displayResults();
            releaseFishOption();
            totalRounds.addFishes(fishesCaught);
            printWeights();
            playAgain = playAgainOption(); // if true, play again
            fishesCaught = new Fishes(); // resets list of fishesCaught
        }
        printAllRounds();
    }

    // MODIFIES: this
    // EFFECTS: creates a list of fish of the same size as randomWeights and assigns a random char
    public void initFishesInPond() {
        for (int i = 0; i < randomWeights.size(); i++) {
            fishesTotal.addFish(new Fish(generateRandomChar()));
        }
    }

    // MODIFIES: this, fishesCaught, fishesTotal
    // EFFECTS: conducts a round of the game
    public void playRound(int maxTries) {
        Random random = new Random();
        Date date = new Date();
        fishesCaught.setDateCaught(date.toString());
        for (int i = 0; i < maxTries; i++) {
            int fishAvail = fishesTotal.getFishList().size();
            int randomIndex = random.nextInt(fishAvail);
            Fish currentFish = fishesTotal.getFishList().get(randomIndex);
            System.out.println("Type the character for the fish: " + currentFish.getLetter());
            char playerInput = input.next().charAt(0);

            if (playerInput == currentFish.getLetter()) {
                fishesCaught.addFish(currentFish);
                fishesTotal.getFishList().remove(randomIndex);
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
        for (Fish f: fishesCaught.getFishList()) {
            summary.append("Fish caught with weight: ").append(f.getWeight()).append(" lb\n");
        }
        addRoundSummary(summary.toString());
        return summary.toString();
    }

    // MODIFIES: this
    // EFFECTS: adds round summary to list of summaries
    public void addRoundSummary(String summary) {
        allSummaries.add(summary);
    }

    // EFFECTS: prints the summaries of each round played in one session
    public void printAllRounds() {
        System.out.println("All rounds played this session:");
        for (String summary: allSummaries) {
            System.out.println(summary);
        }
    }


    // EFFECTS: displays the option to play another round
    public boolean playAgainOption() {
        System.out.println("Play another round? y or n");
        char playerInput = input.next().charAt(0);
        if  (playerInput == 'y') {
            return true;
        }
        return false;
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

            for (Fish f: fishesCaughtCopy) {
                if (releaseWeight == f.getWeight()) {
                    fishesCaught.releaseFish(f);
                    fishesTotal.addFish(f); //return fish to pond
                    System.out.println("Fish with weight " + releaseWeight + " released");
                    printRoundSummary();
                    releaseFishOption();
                    return;
                }
            }
            System.out.println("No fish with weight " + releaseWeight + " found");
            releaseFishOption();
        }
    }

    // EFFECTS: prints fish weights of fish left in pond (including released)
    public void printWeights() {
        fishesTotal.sortFishByWeight();
        System.out.println("Fish left in pond: ");
        for (Fish f : fishesTotal.getFishList()) {
            System.out.println("Fish with weight: " + f.getWeight() + " lb");
        }
    }

    // MODIFIES: fishesTotal
    // EFFECTS: randomly assigns a weight to each fish in pond
    public void setRandomWeights() {
        for (int i = 0; i < fishesTotal.getFishList().size(); i++) {
            fishesTotal.getFishList().get(i).setWeight(randomWeights.get(i));
        }
    }

    // MODIFIES: this
    // EFFECTS: returns a distinct list of random int of given size, min <= int <= max
    public List<Integer> generateRandomIntegers(int size, int min, int max) {
        Random random = new Random();
        List<Integer> randomIntegers = new ArrayList<>();
        while (randomIntegers.size() < size) {
            int randomInteger = random.nextInt(max - min + 1) + min;
            if (!randomIntegers.contains(randomInteger)) {
                randomIntegers.add(randomInteger);
            }
        }
        return randomIntegers;
    }

    // EFFECTS: returns a random letter of the lowercase alphabet
    public char generateRandomChar() {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        char letter = abc.charAt(random.nextInt(abc.length()));
        return letter;
    }


}

