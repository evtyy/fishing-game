package persistence;

import model.Fishes;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testTestReaderFileDoesntExist() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Fishes fishes = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyFishes() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFishes.json");
        try {
            Fishes fishes = reader.read();
            assertEquals(0, fishes.getFishList().size());
            assertEquals(0, fishes.getTotalWeight());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderTypicalFishes() {
        JsonReader reader = new JsonReader("./data/testReaderTypicalFishes.json");
        try {
            Fishes fishes = reader.read();
            assertEquals(2, fishes.getFishList().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
