package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FishTest {
    Fish f1;
    Fish f2;
    Fish f3;

    @BeforeEach
    void setup() {
        f1 = new Fish('x');
        f2 = new Fish('y');
        f3 = new Fish('z');

    }

    @Test
    void testConstructor() {
        assertEquals('x', f1.getLetter());
        assertEquals('y', f2.getLetter());
        assertEquals('z', f3.getLetter());
        assertEquals(0, f1.getWeight());
        assertEquals(0, f2.getWeight());
        assertEquals(0, f3.getWeight());
        assertFalse(f1.isLargest());
        assertFalse(f2.isLargest());
        assertFalse(f3.isLargest());
    }

    @Test
    void testGetWeight() {
        f1.setWeight(21);
        f2.setWeight(1);
        f3.setWeight(9);
        assertEquals(21, f1.getWeight());
        assertEquals(1, f2.getWeight());
        assertEquals(9, f3.getWeight());
    }
}