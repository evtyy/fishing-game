package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FishesTest {
    Fishes fishList;
    Fish f1;
    Fish f2;
    Fish f3;

    @BeforeEach
    void setup() {
        fishList = new Fishes();
        f1 = new Fish('c');
        f2 = new Fish('b');
        f3 = new Fish('a');
        f1.setWeight(12);
        f2.setWeight(2);
        f3.setWeight(20);
    }

    @Test
    void testConstructor() {
        assertTrue(fishList.getFishList().isEmpty());
        assertFalse(fishList.isLargestCaught());
        assertEquals(0, fishList.getTotalWeight());
        assertEquals("", fishList.getDateCaught());
    }

    @Test
    void testAddFish() {
        assertTrue(fishList.getFishList().isEmpty());
        fishList.addFish(f1);
        fishList.addFish(f2);
        fishList.addFish(f3);
        assertEquals(3, fishList.getFishList().size());
        assertTrue(fishList.getFishList().contains(f1));
        assertEquals(f2, fishList.getFishList().get(1));
        assertTrue(fishList.getFishList().contains(f3));
    }

    @Test
    void testCatchFish() {
        assertTrue(fishList.getFishList().isEmpty());
        fishList.catchFish(f1);
        fishList.catchFish(f2);
        fishList.catchFish(f3);
        assertEquals(3, fishList.getFishList().size());
        assertTrue(fishList.getFishList().contains(f1));
        assertEquals(f2, fishList.getFishList().get(1));
        assertTrue(fishList.getFishList().contains(f3));
    }

    @Test
    void testReleaseFish() {
        fishList.addFish(f1);
        fishList.addFish(f2);
        fishList.addFish(f3);
        fishList.releaseFish(f1);
        assertEquals(2, fishList.getFishList().size());
        assertFalse(fishList.getFishList().contains(f1));
        assertEquals(f2, fishList.getFishList().get(0));
        assertTrue(fishList.getFishList().contains(f3));
    }

    @Test
    void testSortFishByWeight() {
        fishList.addFish(f1);
        fishList.addFish(f2);
        fishList.addFish(f3);
        assertEquals(f1, fishList.getFishList().get(0));
        assertEquals(f2, fishList.getFishList().get(1));
        assertEquals(f3, fishList.getFishList().get(2));
        fishList.sortFishByWeight();
        assertEquals(f2, fishList.getFishList().get(0));
        assertEquals(f1, fishList.getFishList().get(1));
        assertEquals(f3, fishList.getFishList().get(2));
    }

    @Test
    void testGetLargest() {
        fishList.addFish(f1);
        fishList.addFish(f2);
        fishList.addFish(f3);
        fishList.setLargest();
        assertEquals(f3, fishList.getLargest());
    }

    @Test
    void testIsLargestCaught() {
        fishList.addFish(f1);
        fishList.addFish(f2);
        fishList.addFish(f3);
        assertFalse(fishList.isLargestCaught());
        fishList.setLargest();
        assertTrue(fishList.isLargestCaught());
    }


    @Test
    void testGetTotalWeight() {
        assertEquals(0, fishList.getTotalWeight());
        fishList.addFish(f1);
        fishList.addFish(f2);
        int sum = f1.getWeight() + f2.getWeight();
        assertEquals(sum, fishList.getTotalWeight());
    }

    @Test
    void testGetDateCaught() {
        Date date = new Date();
        String formattedDate = date.toString();
        fishList.setDateCaught(formattedDate);
        assertEquals(formattedDate, fishList.getDateCaught());
    }
}
