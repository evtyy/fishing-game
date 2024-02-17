package model;

import java.util.ArrayList;
import java.util.List;

// Represents the total list of fishes caught on multiple rounds
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

}
