package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;

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
    // EFFECTS: adds given fish to list of fish and its weight to totalWeight
    public void addFish(Fish fish) {
        fishList.add(fish);
        totalWeight += fish.getWeight();
    }

    // REQUIRES: list of fish contains given fish
    // MODIFIES: this
    // EFFECTS: removes given fish from list of fish and its weight from totalWeight
    public void releaseFish(Fish fish) {
        fishList.remove(fish);
        totalWeight -= fish.getWeight();
    }

    // EFFECTS: returns the total weight of all fish in list
    public int getTotalWeight() {
        return totalWeight;
    }

    // MODIFIES: this
    // EFFECTS: calculates the total weight of all fish in list
    public void calcTotalWeight() {
        totalWeight = 0;
        for (Fish f : fishList) {
            totalWeight += f.getWeight();
        }
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

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("totalWeight", totalWeight);
        json.put("dateCaught", dateCaught);
        json.put("fishes", fishesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray fishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Fish f : fishList) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }



}
