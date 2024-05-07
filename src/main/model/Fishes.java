package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

// Represents the list of fishes caught in one round, with total weight and date caught
public class Fishes implements Iterable<Fish> {
    private List<Fish> fishList;
    private Fish largestFish;
    private int totalWeight;
    private String dateCaught;
    private boolean isLargestCaught;

    // EFFECTS: constructs an empty list of fishes, initializes totalWeight, dateCaught and isLargestCaught
    public Fishes() {
        fishList = new ArrayList<>();
        this.totalWeight = 0;
        this.dateCaught = "";
        this.isLargestCaught = false;
    }

    //getters
    public List<Fish> getFishList() {
        return fishList;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public String getDateCaught() {
        return dateCaught;
    }

    public Fish getLargest() {
        return largestFish;
    }


    //setters
    public void setDateCaught(String dateCaught) {
        this.dateCaught = dateCaught;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    // EFFECTS: sets the largest fish
    public void setLargest() {
        sortFishByWeight();
        int lastIndex = fishList.size() - 1;
        this.largestFish = fishList.get(lastIndex);
        largestFish.setLargest();
    }

    // MODIFIES: this
    // EFFECTS: sorts fish by weight in ascending order
    public void sortFishByWeight() {
        Comparator<Fish> comparator = Comparator.comparingInt(Fish::getWeight);
        fishList.sort(comparator);
    }

    // EFFECTS: returns true if largest is in list of fish; false otherwise
    public boolean isLargestCaught() {
        for (Fish f : fishList) {
            if (f.isLargest()) {
                isLargestCaught = true;
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds given fish to list of fish and its weight to totalWeight
    public void addFish(Fish fish) {
        fishList.add(fish);
        totalWeight += fish.getWeight();
    }

    // MODIFIES: this
    // EFFECTS: adds given fish to list of fish and its weight to totalWeight,
    //          and logs the event of catching fish
    public void catchFish(Fish fish) {
        fishList.add(fish);
        totalWeight += fish.getWeight();
        EventLog.getInstance().logEvent(new Event("Caught fish with weight: " + fish.getWeight() + " lb"));
    }

    // REQUIRES: list of fish contains given fish
    // MODIFIES: this
    // EFFECTS: removes given fish from list of fish and its weight from totalWeight,
    //          and logs the event of releasing fish
    public void releaseFish(Fish fish) {
        fishList.remove(fish);
        totalWeight -= fish.getWeight();
        EventLog.getInstance().logEvent(new Event("Released fish with weight: " + fish.getWeight() + " lb"));
    }

    // EFFECTS: returns fishes as a JSON object
    public JSONObject fishesCaughtToJson() {
        JSONObject json = new JSONObject();
        json.put("totalWeight", totalWeight);
        json.put("dateCaught", dateCaught);
        json.put("fishes", fishesToJson());
        return json;
    }

    // EFFECTS: returns fishesCaught as a JSON array
    private JSONArray fishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Fish f : fishList) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }


    @Override
    public Iterator<Fish> iterator() {
        return fishList.iterator();
    }
}
