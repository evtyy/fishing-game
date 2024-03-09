package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game = new Game();
    private static final int TOTAL_FISH = 10;
    private static final int MIN_WEIGHT = 1;
    private static final int MAX_WEIGHT = 30;

    @Test
    void testInitFishes() {
        assertEquals(10, game.getFishesTotal().getFishList().size());
    }

    @Test
    void testSetRandomWeights() {
        game.setRandomWeights();
        List<Fish> fishes = game.getFishesTotal().getFishList();
        for (Fish f: fishes) {
            assertTrue(f.getWeight() != 0);
        }

    }

    @Test
    void testGenerateRandomIntegers() {
        List<Integer> randomIntegers = game.generateRandomIntegers(TOTAL_FISH, MIN_WEIGHT, MAX_WEIGHT);
        assertEquals(10, randomIntegers.size());
    }

    @Test
    void testGenerateRandomChar() {
        char randomChar = game.generateRandomChar();
        assertTrue(Character.isLowerCase(randomChar));
        assertTrue(Character.isLetter(randomChar));
    }
}
