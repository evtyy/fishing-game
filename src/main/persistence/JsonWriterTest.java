package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TotalRounds totalRounds = new TotalRounds();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.openWriter();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyFishes() {
        try {
            TotalRounds totalRounds = new TotalRounds();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFishes.json");
            writer.openWriter();
            writer.write(totalRounds);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFishes.json");
            totalRounds = reader.read();
            assertEquals(0, totalRounds.getFishCaughtAllRounds().size());
            assertEquals(0, totalRounds.getAllSummaries().size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterTypicalFishes() {
        try {
            TotalRounds totalRounds = totalRoundsTest();
            JsonWriter writer = new JsonWriter("./data/testWriterTypicalFishes.json");
            writer.openWriter();
            writer.write(totalRounds);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterTypicalFishes.json");
            totalRounds = reader.read();
            assertEquals(3, totalRounds.getNumFishCaughtAllRounds());
            assertEquals(2, totalRounds.getAllSummaries().size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    TotalRounds totalRoundsTest() {
        TotalRounds totalRounds = new TotalRounds();
        //List<RoundSummary> roundSummaries = new ArrayList<>();

        Fish fish1 = new Fish('a');
        Fish fish2 = new Fish('b');
        Fish fish3 = new Fish('c');

        Fishes fishes1 = new Fishes();
        fishes1.addFish(fish1);
        fishes1.addFish(fish2);
        Fishes fishes2 = new Fishes();
        fishes2.addFish(fish3);

        String roundOneSum = "round 1 summary";
        String roundTwoSum = "round 2 summary";

        RoundSummary roundSummary1 = new RoundSummary(fishes1, roundOneSum);
        RoundSummary roundSummary2 = new RoundSummary(fishes2, roundTwoSum);;

        totalRounds.addRoundSummary(roundSummary1);
        totalRounds.addRoundSummary(roundSummary2);

        return totalRounds;
    }
}
