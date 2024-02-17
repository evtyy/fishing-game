package model;

import java.util.*;

// Represents the list of fishes caught in one round, with total weight and date caught
public class Fishes {
    private List<Fish> fishList;
    private int totalWeight;
    private String dateCaught;

    public Fishes() {
        fishList = new ArrayList<>();
        this.totalWeight = 0;
        this.dateCaught = "";
    }

    //getters
    public List<Fish> getFishList() {
        return fishList;
    }

    public String getDateCaught() {
        return dateCaught;
    }

    //setters
    public void setDateCaught(String dateCaught) {
        this.dateCaught = dateCaught;
    }

    // MODIFIES: this
    // EFFECTS: adds given fish to list of fish
    public void addFish(Fish fish) {
        fishList.add(fish);
    }

    // REQUIRES: list of fish contains given fish
    // MODIFIES: this
    // EFFECTS: removes given fish from list of fish
    public void releaseFish(Fish fish) {
        fishList.remove(fish);
    }

    // MODIFIES: this
    // EFFECTS: calculates and returns the total weight of all fish in list
    public int getTotalWeight() {
        for (Fish f : fishList) {
            totalWeight += f.getWeight();
        }
        return totalWeight;
    }

    // MODIFIES: this
    // EFFECTS: sorts fish by weight in ascending order
    public void sortFishByWeight() {
        Comparator<Fish> comparator = Comparator.comparingInt(Fish::getWeight);
        Collections.sort(fishList, comparator);
    }


    // EFFECTS: sets and returns the largest fish
    public Fish getLargest() {
        sortFishByWeight();
        int lastIndex = fishList.size() - 1;
        fishList.get(lastIndex).setLargest();
        return fishList.get(lastIndex);
    }

    // EFFECTS: returns true if largest is list of fish; false otherwise
    public boolean isLargestCaught() {
        for (Fish f: fishList) {
            if (f.isLargest()) {
                return true;
            }
        }
        return false;
    }

}
