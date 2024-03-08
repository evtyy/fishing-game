package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TotalRoundsTest {
    TotalRounds totalRounds;
    List<RoundSummary> roundSummaries;
    RoundSummary roundSummary1;
    RoundSummary roundSummary2;
    RoundSummary roundSummary3;
    Fishes list1;
    Fishes list2;
    Fishes list3;
    Fish f1;
    Fish f2;
    Fish f3;
    Fish f4;
    List<String> allSummaries;
    String roundOneSum;
    String roundTwoSum;
    String roundThreeSum;

    @BeforeEach
    void setup() {
        totalRounds = new TotalRounds();
        roundSummaries = new ArrayList<>();
        allSummaries = new ArrayList<>();
        //round 1
        list1 = new Fishes();
        f1 = new Fish('a');
        f1.setWeight(20);
        f1.setLargest();
        list1.addFish(f1);

        //round 2
        list2 = new Fishes();
        f2 = new Fish('b');
        f3 = new Fish('c');
        f2.setWeight(10);
        f3.setWeight(4);
        list2.addFish(f2);
        list2.addFish(f3);

        //round 3
        list3 = new Fishes();
        f4 = new Fish('d');
        f4.setWeight(7);
        f4.setLargest();
        list3.addFish(f4);

        roundOneSum = "round 1 summary";
        roundTwoSum = "round 2 summary";
        roundThreeSum = "round 3 summary";

        roundSummary1 = new RoundSummary(list1, roundOneSum);
        roundSummary2 = new RoundSummary(list2, roundTwoSum);
        roundSummary3 = new RoundSummary(list3, roundThreeSum);

    }

    @Test
    void testConstructor() {
        assertTrue(totalRounds.getFishCaughtAllRounds().isEmpty());
        assertEquals(0, totalRounds.getAllSummaries().size());
    }

    @Test
    void testAddRoundSummary() {
        totalRounds.addRoundSummary(roundSummary1);
        assertEquals(1, totalRounds.getRoundSummaries().size());
        assertEquals(1, totalRounds.getFishCaughtAllRounds().size());
        assertTrue(totalRounds.getFishCaughtAllRounds().contains(list1));

        totalRounds.addRoundSummary(roundSummary2);
        assertEquals(2, totalRounds.getRoundSummaries().size());
        assertTrue(totalRounds.getRoundSummaries().contains(roundSummary2));
        assertEquals(3, totalRounds.getNumFishCaughtAllRounds());
        assertTrue(totalRounds.getFishCaughtAllRounds().contains(list1));
        assertTrue(totalRounds.getFishCaughtAllRounds().contains(list2));

    }

    @Test
    void testAddRoundSummaryContains() {
        totalRounds.addRoundSummary(roundSummary1);
        assertEquals(1, totalRounds.getRoundSummaries().size());
        totalRounds.addRoundSummary(roundSummary1);
        assertEquals(1, totalRounds.getRoundSummaries().size());
    }


    @Test
    void testRemoveRoundSummary() {
        totalRounds.addRoundSummary(roundSummary1);
        totalRounds.addRoundSummary(roundSummary2);
        assertEquals(2, totalRounds.getRoundSummaries().size());

        totalRounds.removeRoundSummary(roundSummary1);
        assertEquals(1, totalRounds.getRoundSummaries().size());
    }



    @Test
    void testAvgTotalWeight() {
        totalRounds.addRoundSummary(roundSummary1);
        totalRounds.addRoundSummary(roundSummary2);

        assertEquals(17.00, totalRounds.avgTotalWeight());
        assertEquals(17.00, totalRounds.getAvgTotalWeight());
    }

    @Test
    void testAvgTotalWeightFormattedWeight() {
        totalRounds.addRoundSummary(roundSummary1);
        totalRounds.addRoundSummary(roundSummary2);
        totalRounds.addRoundSummary(roundSummary3);

        assertEquals(13.66, totalRounds.avgTotalWeight());
        assertEquals(13.66, totalRounds.getAvgTotalWeight());

    }

    @Test
    void testGetAvgNumTriesToWin() {
        totalRounds.setAvgNumTriesToWin(3.5);
        assertEquals(3.5, totalRounds.getAvgNumTriesToWin());
    }


    @Test
    void testLargestCaughtPercentage() {
        totalRounds.addRoundSummary(roundSummary1);
        totalRounds.addRoundSummary(roundSummary2);

        totalRounds.largestCaughtPercentage();

        assertEquals(50.00, totalRounds.getLargestCaughtPercentage());
    }

    @Test
    void testAvgTotalWeightFormattedPercentage() {
        totalRounds.addRoundSummary(roundSummary1);
        totalRounds.addRoundSummary(roundSummary2);
        totalRounds.addRoundSummary(roundSummary3);

        assertEquals(66.66, totalRounds.largestCaughtPercentage());
        assertEquals(66.66, totalRounds.getLargestCaughtPercentage());

    }



    @Test
    void testAddListOfFishCaught() {
        assertTrue(totalRounds.getFishCaughtAllRounds().isEmpty());
        totalRounds.addListOfFishCaught(list1);
        assertEquals(1, totalRounds.getFishCaughtAllRounds().size());
        assertTrue(totalRounds.getFishCaughtAllRounds().contains(list1));
        assertEquals(list1, totalRounds.getFishCaughtAllRounds().get(0));

        totalRounds.addListOfFishCaught(list2);
        assertEquals(2, totalRounds.getFishCaughtAllRounds().size());
        assertTrue(totalRounds.getFishCaughtAllRounds().contains(list1));
        assertEquals(list1, totalRounds.getFishCaughtAllRounds().get(0));
        assertTrue(totalRounds.getFishCaughtAllRounds().contains(list2));
        assertEquals(list2, totalRounds.getFishCaughtAllRounds().get(1));

    }

    @Test
    void testAddListOfFishCaughtContains() {
        totalRounds.addListOfFishCaught(list1);
        assertEquals(1, totalRounds.getFishCaughtAllRounds().size());
        totalRounds.addListOfFishCaught(list1);
        assertEquals(1, totalRounds.getFishCaughtAllRounds().size());
        assertTrue(totalRounds.getFishCaughtAllRounds().contains(list1));
        assertEquals(list1, totalRounds.getFishCaughtAllRounds().get(0));

    }

}
