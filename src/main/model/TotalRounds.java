package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents the total list of fishes caught on multiple rounds
public class TotalRounds {
    private List<Fishes> totalCaughtFish;
    private List<String> allSummaries;

    //EFFECTS: constructs an empty list of fishes
    public TotalRounds() {
        totalCaughtFish = new ArrayList<>();
        allSummaries = new ArrayList<>();
    }

    //getter
    public List<Fishes> getTotalCaughtFish() {
        return totalCaughtFish;
    }

    public List<String> getAllSummaries() {
        return allSummaries;
    }

    // MODIFIES: this
    // EFFECTS: adds given list of fish to list of fishes, totalCaughtFish
    public void addListOfFishes(Fishes fishes) {
        totalCaughtFish.add(fishes);
    }

    // MODIFIES: this
    // EFFECTS: if list of summaries doesn't contain summary, adds summary to list; if contains, do nothing
    public void addRoundSummary(String summary) {
        if (!allSummaries.contains(summary)) {
            allSummaries.add(summary);
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fishes", fishesToJson());
        json.put("summaries", summariesToJson());
        return json;
    }

    private JSONArray summariesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String s: allSummaries) {
            jsonArray.put(s);
        }
        return jsonArray;
    }

    private JSONArray fishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Fishes f: totalCaughtFish) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}
