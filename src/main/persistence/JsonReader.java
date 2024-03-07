package persistence;

import model.Fish;
import model.Fishes;
import model.TotalRounds;
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
    public Fishes read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFishes(jsonObject);
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
    private TotalRounds parseFishes(JSONObject jsonObject) {
        TotalRounds totalRounds = new TotalRounds();
        addListOfFishes(totalRounds, jsonObject);
        return totalRounds;
//        Fishes fishes = new Fishes();
//        addFishes(fishes, jsonObject);
//        return fishes;
    }

    // MODIFIES:
    // EFFECTS: parses list of list of fish from JSON object and adds them to totalRounds
    private void addListOfFishes(TotalRounds totalRounds, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("fishes");
        for (Object json : jsonArray) {
            JSONObject nextFishList = (JSONObject) json;
            addFishes(totalRounds, nextFishList);
        }
    }

    // MODIFIES: fishes
    // EFFECTS: parses list of fish from JSON object and adds them to total rounds
    private void addFishes(TotalRounds totalRounds, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("fishes");
        for (Object json : jsonArray) {
            JSONObject nextFish = (JSONObject) json;
            addFish(totalRounds, nextFish);
        }
    }

    // MODIFIES: fishes
    // EFFECTS: parses fish from JSON object and adds it to total rounds
    private void addFish(TotalRounds totalRounds, JSONObject jsonObject) {
        String letter = jsonObject.getString("letter");
        char c = letter.charAt(0);
        Fish fish = new Fish(c);
        totalRounds.addFish(fish);

    }
}
