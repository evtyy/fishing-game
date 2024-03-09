package model;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a round's summary with Fishes (details about fishes caught this round), and String summarizing round
public class RoundSummary {
    private Fishes fishCaughtThisRound;
    private Fishes fishLeftInPond;
    private String roundSummary;
    private boolean isLargestCaught;
    private int numFishCaught;
    //private int tryNumToCatchLargest;


    // EFFECTS: constructs a round summary with given Fishes and roundSummary; initializes empty list of fish
    //          left in pond, isLargestCaught as false, and numFishCaught as 0
    public RoundSummary(Fishes fishes, String roundSummary) {
        this.fishCaughtThisRound = fishes;
        this.roundSummary = roundSummary;
        this.fishLeftInPond = new Fishes();
        this.isLargestCaught = false;
        //this.tryNumToCatchLargest = 0;
        this.numFishCaught = 0;
    }

    // getters
    public Fishes getFishCaughtThisRound() {
        return fishCaughtThisRound;
    }

    public int getNumFishCaught() {
        return fishCaughtThisRound.getFishList().size();
    }

//    public int getTryNumToCatchLargest() {
//        return tryNumToCatchLargest;
//    }

    public String getRoundSummary() {
        return roundSummary;
    }

    public Fishes getFishLeftInPond() {
        return fishLeftInPond;
    }

    // setters
    public void setFishCaughtThisRound(Fishes fishes) {
        this.fishCaughtThisRound = fishes;
    }

//    public void setNumFishCaught() {
//        this.numFishCaught = fishCaughtThisRound.getFishList().size();
//    }

    public void setLargestCaught() {
        this.isLargestCaught = true;
    }

//    public void setTryNumToCatchLargest(int tryNum) {
//        this.tryNumToCatchLargest = tryNum;
//    }

//    public void setRoundSummary(String summary) {
//        this.roundSummary = summary;
//    }

    public void setFishLeftInPond(Fishes fishes) {
        this.fishLeftInPond = fishes;
    }


    // EFFECTS: returns true if largest is in list of fish caught; false otherwise
    public boolean isLargestCaught() {
        for (Fish f: fishCaughtThisRound.getFishList()) {
            if (f.isLargest()) {
                setLargestCaught();
                return true;
            }
        }
        return false;
    }


    // MODIFIES: this
    // EFFECTS: adds fish to list of fish caught this round
    public void addFishCaught(Fish fish) {
        fishCaughtThisRound.addFish(fish);
    }

    // EFFECTS: returns roundSummary as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fishes caught", fishCaughtThisRound.fishesCaughtToJson());
        json.put("summary", roundSummary);
        json.put("fish left in pond", fishesToJson());
        return json;
    }

    // EFFECTS: returns fishes in this round summary as a JSON array
    private JSONArray fishesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Fish f : fishLeftInPond.getFishList()) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }



}
