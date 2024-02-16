package model;

import java.util.ArrayList;
import java.util.List;

// Represents the total collection of fishes caught on various days
// A list of dates (type String) -- what should be seen
public class TotalRounds {
    private List<Fishes> collection;

    //EFFECTS: constructs an empty list of fish
    public TotalRounds() {
        collection = new ArrayList<>();
    }

    //getter
    public List<Fishes> getCollection() {
        return collection;
    }

    public void addFishes(Fishes fishes) {
        collection.add(fishes);
    }

    //
    // EFFECTS: get list of fishes given dateCaught; null if not found
    public List<Fish> getFishesFromDate(String date) {
        for (Fishes f: collection) {
            if (f.getDateCaught().equals(date)) {
                return f.getFishList();
            }
        }
        return null;
    }


}
