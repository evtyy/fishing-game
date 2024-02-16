package model;

import java.util.*;

// Represents the list of fishes caught on one day
public class Fishes {
    private List<Fish> fishList;
    private int totalWeight;
    private String dateCaught;
    private boolean largestCaught;

    public Fishes() {
        fishList = new ArrayList<>();
        this.totalWeight = 0;
        this.dateCaught = "";
        this.largestCaught = false;
    }

    //getters
    public List<Fish> getFishList() {
        return fishList;
    }

    public int getTotalWeight() {
        for (Fish f : fishList) {
            totalWeight += f.getWeight();
        }
        return totalWeight;
    }


    public String getDateCaught() {
        return dateCaught;
    }

    public void setDateCaught(String dateCaught) {
        this.dateCaught = dateCaught;
    }


    // EFFECTS: sorts fish by weight in ascending order
    public void sortFishByWeight() {
        Comparator<Fish> comparator = Comparator.comparingInt(Fish::getWeight);
        Collections.sort(fishList, comparator);
    }

//    public Fish getFishByWeight(int weight) {
//        for (Fish f: fishList) {
//            if (weight == f.getWeight()) {
//                return f;
//            }
//    }

    // EFFECTS: finds and sets the largest fish
    public Fish getLargest() {
        sortFishByWeight();
        int lastIndex = fishList.size() - 1;
        fishList.get(lastIndex).setLargest();
        return fishList.get(lastIndex);
    }

    // EFFECTS: returns true if largest is in fishes caught list; false otherwise
    public boolean isLargestCaught() {
        for (Fish f: fishList) {
            if (f.isLargest()) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds given fish to collection of fishes
    public void addFish(Fish fish) {
        fishList.add(fish);
    }

    // REQUIRES: list of fish contains given fish
    // MODIFIES: this
    // EFFECTS: removes given fish from collection of fishes
    public void releaseFish(Fish fish) {
        fishList.remove(fish);
    }

}
