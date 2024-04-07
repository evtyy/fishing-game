package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// Represents the total list of fishes caught on multiple rounds, all round summaries, and stats for
// all rounds: largest caught percentage, avg number of tries to catch largest, avg total weight
public class TotalRounds {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private double largestCaughtPercentage;
    private double avgNumTriesToWin;
    private double avgTotalWeight;

    private List<Fishes> fishCaughtAllRounds;
    private List<String> allSummaries;
    private List<RoundSummary> roundSummaries;

    //EFFECTS: constructs an empty list of fishes, empty list of summaries, empty list of
    //         round summaries, and initializes largestCaughtPercentage, avgNumTriesToWin, avgTotalWeight
    public TotalRounds() {
        largestCaughtPercentage = 0.0;
        avgNumTriesToWin = 0.0;
        avgTotalWeight = 0.0;

        fishCaughtAllRounds = new ArrayList<Fishes>();
        allSummaries = new ArrayList<>();
        roundSummaries = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if list of round summaries doesn't contain given round summary,
    //          adds it to list of round summaries; nothing otherwise
    public void addRoundSummary(RoundSummary roundSummary) {
        if (!roundSummaries.contains(roundSummary)) {
            roundSummaries.add(roundSummary);
            getFishCaughtAllRounds();
        }
    }

    // REQUIRES: list of round summaries contains given roundSummary
    // MODIFIES: this
    // EFFECTS: removes given round summary from list of round summaries
    public void removeRoundSummary(RoundSummary roundSummary) {
        roundSummaries.remove(roundSummary);
    }

    // MODIFIES: this
    // EFFECTS: adds all summaries in roundSummaries to a list and returns it
    public List<String> getAllSummaries() {
        for (RoundSummary r: roundSummaries) {
            String sum = r.getRoundSummary();
            addSummary(sum);
        }
        return allSummaries;
    }

    // MODIFIES: this
    // EFFECTS: if list of summaries doesn't contain given summary,
    //          adds summary to list of summaries; otherwise nothing
    public void addSummary(String summary) {
        if (!allSummaries.contains(summary)) {
            allSummaries.add(summary);
        }
    }



    //getters
    public List<Fishes> getFishCaughtAllRounds() {
        for (RoundSummary r: roundSummaries) {
            Fishes fishesCaught = r.getFishCaughtThisRound();
            if (!fishCaughtAllRounds.contains(fishesCaught)) {
                fishCaughtAllRounds.add(fishesCaught);
            }
        }
        return fishCaughtAllRounds;
    }

    public int getNumFishCaughtAllRounds() {
        int sum = 0;
        for (RoundSummary r: roundSummaries) {
            sum += r.getNumFishCaught();
        }
        return sum;
    }

    public List<RoundSummary> getRoundSummaries() {
        return roundSummaries;
    }

    public double getLargestCaughtPercentage() {
        return largestCaughtPercentage;
    }

    public double getAvgNumTriesToWin() {
        return avgNumTriesToWin;
    }

    public double getAvgTotalWeight() {
        return avgTotalWeight;
    }

    //setters
    public void setLargestCaughtPercentage(double percentage) {
        this.largestCaughtPercentage = percentage;
    }

    public void setAvgTotalWeight(double avgWeight) {
        this.avgTotalWeight = avgWeight;
    }

    public void setAvgNumTriesToWin(double avgTries) {
        this.avgNumTriesToWin = avgTries;
    }

    // MODIFIES: this
    // EFFECTS: if list of fishes caught on all rounds doesn't contain given list of fish, add;
    //          otherwise nothing
    public void addListOfFishCaught(Fishes fishes) {
        if (!fishCaughtAllRounds.contains(fishes)) {
            fishCaughtAllRounds.add(fishes);
        }
    }

    // EFFECTS: returns avg total weight of fishes caught per round
    public double avgTotalWeight() {
        int sum = 0;
        for (Fishes f: fishCaughtAllRounds) {
            sum += f.getTotalWeight();
        }
        double numRounds = roundSummaries.size();
        double avg = sum / numRounds;

        double formattedWeight = Math.floor(avg * 100) / 100;
        setAvgTotalWeight(formattedWeight);
        return formattedWeight;
    }

    // EFFECTS: returns number of times largest was caught per round played
    public double largestCaughtPercentage() {
        int timesLargestCaught = 0;
        for (RoundSummary r: roundSummaries) {
            if (r.isLargestCaught()) {
                timesLargestCaught++;
            }
        }
        double numRounds = roundSummaries.size();
        double percentage = (timesLargestCaught / numRounds) * 100;
        double formattedPercentage = Math.floor(percentage * 100) / 100;
        setLargestCaughtPercentage(formattedPercentage);
        return formattedPercentage;
    }


    // EFFECTS: returns totalRounds as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("round summaries", roundSummariesToJson());
        return json;
    }


    // EFFECTS: returns round summaries in totalRounds as a JSON array
    private JSONArray roundSummariesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (RoundSummary r: roundSummaries) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}
