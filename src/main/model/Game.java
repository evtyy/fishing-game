package model;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents an instance of the game
public class Game {
    private Fishes fishesTotal;
    private List<Integer> randomWeights;
    private static final int TOTAL_FISH = 10;
    private static final int MIN_WEIGHT = 1;
    private static final int MAX_WEIGHT = 30;

    public Game() {
        randomWeights = generateRandomIntegers(TOTAL_FISH, MIN_WEIGHT, MAX_WEIGHT);
        fishesTotal = new Fishes();

        initFishes();
        setRandomWeights();
        fishesTotal.getLargest(); // marks the largest fish
    }

    // MODIFIES: this
    // EFFECTS: creates a list of fish of size TOTAL_FISH and assigns a random char
    public void initFishes() {
        for (int i = 0; i < TOTAL_FISH; i++) {
            fishesTotal.addFish(new Fish(generateRandomChar()));
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

    // getters
    public Fishes getFishesTotal() {
        return fishesTotal;
    }
}
