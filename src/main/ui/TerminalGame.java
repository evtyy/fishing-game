package ui;

import model.Fish;
import model.Fishes;
import model.TotalRounds;

import java.util.*;

public class TerminalGame {
    private Scanner input;
    private List<Integer> randomWeights;
    private TotalRounds totalRounds;
    private Fishes fishesTotal;
    private Fishes fishesCaught;

    private static final int TOTAL_FISH = 10;
    private static final int MAX_TRIES = 5;
    private static final int MIN_WEIGHT = 1;
    private static final int MAX_WEIGHT = 30;

    public TerminalGame() {
        start();
    }

    public void start() {
        input = new Scanner(System.in);
        randomWeights = generateRandomIntegers(TOTAL_FISH, MIN_WEIGHT, MAX_WEIGHT);
        fishesTotal = new Fishes();
        fishesCaught = new Fishes();
        totalRounds = new TotalRounds();

        initFishesInPond();
        setRandomWeights();
        fishesTotal.getLargest();

        playRound(MAX_TRIES);
        displayResults();
        releaseFishOption();
        totalRounds.addFishes(fishesCaught);
        printWeights();
        playAgainOption();
    }

    // EFFECTS: creates a list of fish of the same size as randomWeights
    public void initFishesInPond() {
        for (int i = 0; i < randomWeights.size(); i++) {
            fishesTotal.addFish(new Fish(generateRandomChar()));
        }
    }


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

    public void displayResults() {
        System.out.println("Game over!");
        printRoundSummary();
    }

    public void printRoundSummary() {
        System.out.println("Round Summary: ");
        System.out.println("Date caught: " + fishesCaught.getDateCaught()
                + " Largest fish was caught: " + fishesCaught.isLargestCaught()
                + " Total weight: " + fishesCaught.getTotalWeight() + " lb");
        for (Fish f: fishesCaught.getFishList()) {
            System.out.println("Fish caught with weight: " + f.getWeight() + " lb");
        }
    }

//    public void printCollection() {
//        for (Fishes f: totalRounds.getCollection()) {
//            printRoundSummary();
//        }
//    }

    public void playAgainOption() {
        System.out.println("Play another round? y or n");
        char playerInput = input.next().charAt(0);
        if (playerInput == 'y') {
            playRound(MAX_TRIES);
        }
    }

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

    public void viewCollection() {

    }

    public void setRandomWeights() {
        for (int i = 0; i < fishesTotal.getFishList().size(); i++) {
            fishesTotal.getFishList().get(i).setWeight(randomWeights.get(i));
        }
    }

    public List<Integer> generateRandomIntegers(int count, int min, int max) {
        Random random = new Random();
        List<Integer> randomIntegers = new ArrayList<>();
        while (randomIntegers.size() < count) {
            int randomInteger = random.nextInt(max - min + 1) + min;
            if (!randomIntegers.contains(randomInteger)) {
                randomIntegers.add(randomInteger);
            }
        }
        return randomIntegers;
    }

    public char generateRandomChar() {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        char letter = abc.charAt(random.nextInt(abc.length()));
        return letter;
    }


}

