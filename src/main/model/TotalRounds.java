package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// Represents the total list of fishes caught on multiple rounds
public class TotalRounds {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private double largestCaughtPercentage;
    private double avgNumTriesToWin;
    private double avgTotalWeight;

    private List<Fishes> fishCaughtAllRounds;
    private List<String> allSummaries;
    private List<RoundSummary> roundSummaries;

    //EFFECTS: constructs an empty list of fishes
    public TotalRounds() {
        largestCaughtPercentage = 0.0;
        avgNumTriesToWin = 0.0;
        avgTotalWeight = 0.0;

        fishCaughtAllRounds = new ArrayList<Fishes>();
        allSummaries = new ArrayList<>();
        roundSummaries = new ArrayList<>();
    }

    public void addRoundSummary(RoundSummary roundSummary) {
        if (!roundSummaries.contains(roundSummary)) {
            roundSummaries.add(roundSummary);
            getFishCaughtAllRounds();
        }
    }

    public List<String> getAllSummaries() {
        for (RoundSummary r: roundSummaries) {
            String sum = r.getRoundSummary();
            allSummaries.add(sum);
            if (!allSummaries.contains(sum)) {
                allSummaries.add(sum);
            }
        }
        return allSummaries;
    }

    public void removeRoundSummary(RoundSummary roundSummary) {
        roundSummaries.remove(roundSummary);
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
    // EFFECTS: adds given list of fish to list of fishes, totalCaughtFish
    public void addListOfFishCaught(Fishes fishes) {
        if (!fishCaughtAllRounds.contains(fishes)) {
            fishCaughtAllRounds.add(fishes);
        }
    }

    // EFFECTS: gets avg total weight of fishes caught per round
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

    // EFFECTS: gets number of times largest was caught per round played
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



    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("round summaries", roundSummariesToJson());
        return json;
    }

    private JSONArray roundSummariesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (RoundSummary r: roundSummaries) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}
