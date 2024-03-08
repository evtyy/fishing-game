package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RoundSummaryTest {
    RoundSummary roundSummary;
    Fishes list1;
    Fish f1;
    Fish f2;
    Fish f3;
    String roundOneSum;

    @BeforeEach
    void setup() {
        list1 = new Fishes();
        f1 = new Fish('a');
        f2 = new Fish('b');
        f3 = new Fish('c');
        f1.setWeight(20);
        f1.setLargest();
        f2.setWeight(10);
        f3.setWeight(4);
        roundOneSum = "round 1 summary";
        roundSummary = new RoundSummary(list1, roundOneSum);
    }

    @Test
    void testConstructor() {
        assertEquals(0, list1.getFishList().size());
        assertFalse(roundSummary.isLargestCaught());
    }

    @Test
    void testAddFishCaught() {
        assertEquals(0, roundSummary.getNumFishCaught());
        roundSummary.addFishCaught(f1);
        assertEquals(1, roundSummary.getNumFishCaught());
        assertEquals(1,roundSummary.getFishCaughtThisRound().getFishList().size());

        roundSummary.addFishCaught(f2);
        roundSummary.addFishCaught(f3);
        assertEquals(3, roundSummary.getNumFishCaught());
        assertEquals(3,roundSummary.getFishCaughtThisRound().getFishList().size());
    }

    @Test
    void testGetFishLeftInPond() {
        roundSummary.setFishLeftInPond(list1);
        assertEquals(0, roundSummary.getFishLeftInPond().getFishList().size());
        list1.addFish(f1);
        list1.addFish(f2);
        list1.addFish(f3);
        assertEquals(3, roundSummary.getFishLeftInPond().getFishList().size());

    }

    @Test
    void testSetFishCaughtThisRound() {
        roundSummary.setFishCaughtThisRound(list1);
        assertEquals(0, roundSummary.getFishCaughtThisRound().getFishList().size());
        list1.addFish(f1);
        list1.addFish(f2);
        list1.addFish(f3);
        assertEquals(3, roundSummary.getFishCaughtThisRound().getFishList().size());
    }

    @Test
    void testIsLargestCaughtTrue() {
        roundSummary.addFishCaught(f1);
        assertTrue(roundSummary.isLargestCaught());
    }

    @Test
    void testIsLargestCaughtFalse() {
        roundSummary.addFishCaught(f2);
        roundSummary.addFishCaught(f3);
        assertFalse(roundSummary.isLargestCaught());
    }

}
