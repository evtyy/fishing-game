package persistence;

import model.TotalRounds;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testTestReaderFileDoesntExist() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TotalRounds totalRounds = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyTotalRounds() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFishes.json");

        try {
            TotalRounds totalRounds = reader.read();
            assertEquals(0, totalRounds.getNumFishCaughtAllRounds());
            assertEquals(1, totalRounds.getAllSummaries().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTypicalTotalRounds() {
        JsonReader reader = new JsonReader("./data/testReaderTypicalFishes.json");
        try {
            TotalRounds totalRounds = reader.read();
            assertEquals(4, totalRounds.getNumFishCaughtAllRounds());
            assertEquals(2, totalRounds.getAllSummaries().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
