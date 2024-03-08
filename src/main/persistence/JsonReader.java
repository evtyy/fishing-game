package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads total rounds from JSON data stored in file
// References code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads total rounds from file and returns it
    public TotalRounds read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTotalRounds(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();

    }

    // EFFECTS: parses TotalRounds from JSON object and returns it
    private TotalRounds parseTotalRounds(JSONObject jsonObject) {
        TotalRounds totalRounds = new TotalRounds();
        addRoundSummaries(totalRounds, jsonObject);
        return totalRounds;

    }

    private void addRoundSummaries(TotalRounds totalRounds, JSONObject jsonObject) {
        JSONArray roundSummariesArray = jsonObject.getJSONArray("round summaries");
        for (Object json : roundSummariesArray) {
            JSONObject nextRoundSummary = (JSONObject) json;
            addRoundSummary(totalRounds, nextRoundSummary);
        }
    }

    private void addRoundSummary(TotalRounds totalRounds, JSONObject jsonObject) {
        Fishes fishes = new Fishes();
        JSONArray fishesArray = jsonObject.getJSONArray("fishes caught");
        for (Object json : fishesArray) {
            JSONObject nextFish = (JSONObject) json;
            addFish(fishes, nextFish);
        }
        totalRounds.addListOfFishCaught(fishes);
        String summary = jsonObject.getString("summary");
        RoundSummary roundSummary = new RoundSummary(fishes, summary);
        totalRounds.addRoundSummary(roundSummary);
    }

//    private void addFishInPond(Game game, JSONObject jsonObject) {
//        Fishes fishes = new Fishes();
//        JSONArray jsonArray = jsonObject.getJSONArray("fishes in pond");
//        for (Object json : jsonArray) {
//            JSONObject nextFish = (JSONObject) json;
//            addFish(fishes, nextFish);
//        }
//        game.setFishesTotal(fishes);
//    }


    // MODIFIES:
    // EFFECTS: parses list of list of fish from JSON object and adds them to totalRounds
//    private void addListOfFishes(TotalRounds totalRounds, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("rounds");
//        for (Object json : jsonArray) {
//            JSONObject nextFishList = (JSONObject) json;
//            addFishes(totalRounds, nextFishList);
//        }
//    }

    // MODIFIES: fishes
    // EFFECTS: parses list of fish from JSON object and adds them to total rounds
//    private void addFishes(TotalRounds totalRounds, JSONObject jsonObject) {
//        Fishes fishes = new Fishes();
//        JSONArray jsonArray = jsonObject.getJSONArray("fishes");
//        for (Object json : jsonArray) {
//            JSONObject nextFish = (JSONObject) json;
//            addFish(fishes, nextFish);
//        }
//        totalRounds.addListOfFishCaught(fishes);
//    }

    // MODIFIES: fishes
    // EFFECTS: parses fish from JSON object and adds it to total rounds
    private void addFish(Fishes fishes, JSONObject jsonObject) {
        String letter = jsonObject.getString("letter");
        char c = letter.charAt(0);
        Integer weight = jsonObject.getInt("weight");
        boolean isLargest = jsonObject.getBoolean("isLargest");
        Fish fish = new Fish(c);
        fish.setWeight(weight);
        if (isLargest) {
            fish.setLargest();
        }
        fishes.addFish(fish);

    }
}
