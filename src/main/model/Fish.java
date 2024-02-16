package model;

// Represents a fish with a unique char, an int weight, and whether it is the largest
public class Fish {
    private int weight;
    private boolean isLargest;
    private char letter;

    // EFFECTS: constructs a fish with a random char
    public Fish(char letter) {
        this.letter = letter;
        this.weight = 0;
        this.isLargest = false;
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

}
