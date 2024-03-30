package model;

import org.json.JSONObject;

// Represents a fish with a unique char, an int weight, and whether it is the largest
public class Fish {
    private int weight;
    private boolean isLargest;
    private char letter;

    // constructor for testing
    // EFFECTS: constructs a fish with given letter
    public Fish(char letter) {
        this.letter = letter;
        this.weight = 0;
        this.isLargest = false;
    }

    public String toString() {
        return "Fish weighing: " + getWeight() + " is largest: " + isLargest();
    }

    //getters
    public int getWeight() {
        return weight;
    }

    public char getLetter() {
        return letter;
    }

    //setters
    public void setWeight(int weight) {
        this.weight = weight;
    }


    // EFFECTS: sets fish as largest
    public void setLargest() {
        isLargest = true;
    }

    // EFFECTS: returns true if fish is largest; false otherwise
    public boolean isLargest() {
        return isLargest;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("letter", Character.toString(letter));
        json.put("weight", weight);
        json.put("isLargest", isLargest);
        return json;
    }
}
