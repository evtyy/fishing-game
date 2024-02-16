package model;

import java.util.ArrayList;
import java.util.List;

// Represents the total collection of fishes caught on various rounds
// A list of dates (type String) -- what should be seen
public class TotalRounds {
    private List<Fishes> totalFish;

    //EFFECTS: constructs an empty list of fish
    public TotalRounds() {
        totalFish = new ArrayList<>();
    }

    //getter
    public List<Fishes> getTotalFish() {
        return totalFish;
    }

    // MODIFIES: this
    // EFFECTS: adds given list of fish to list of fishes, totalFish
    public void addFishes(Fishes fishes) {
        totalFish.add(fishes);
    }

    //
    // EFFECTS: get list of fishes given dateCaught; null if not found
    public List<Fish> getFishesFromDate(String date) {
        for (Fishes f: totalFish) {
            if (f.getDateCaught().equals(date)) {
                return f.getFishList();
            }
        }
        return null;
    }


}
