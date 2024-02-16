package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TotalRoundsTest {
    TotalRounds totalRounds;
    Fishes list1;
    Fishes list2;
    Fish f1;
    Fish f2;
    Fish f3;

    @BeforeEach
    void setup() {
        totalRounds = new TotalRounds();
        list1 = new Fishes();
        f1 = new Fish('a');


        list2 = new Fishes();
        f2 = new Fish('b');
        f3 = new Fish('c');
    }

    @Test
    void testConstructor() {
        assertTrue(totalRounds.getCollection().isEmpty());
    }

    @Test
    void testAddFishes() {
        assertTrue(totalRounds.getCollection().isEmpty());
        totalRounds.addFishes(list1);
        assertEquals(1, totalRounds.getCollection().size());
        assertTrue(totalRounds.getCollection().contains(list1));
        assertEquals(list1, totalRounds.getCollection().get(0));

        totalRounds.addFishes(list2);
        assertEquals(2, totalRounds.getCollection().size());
        assertTrue(totalRounds.getCollection().contains(list1));
        assertEquals(list1, totalRounds.getCollection().get(0));
        assertTrue(totalRounds.getCollection().contains(list2));
        assertEquals(list2, totalRounds.getCollection().get(1));

    }
}
