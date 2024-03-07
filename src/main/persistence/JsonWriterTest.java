//package persistence;
//
//import model.Fish;
//import model.Fishes;
//import org.junit.jupiter.api.Test;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class JsonWriterTest {
//
//    @Test
//    void testWriterInvalidFile() {
//        try {
//            Fishes fishes = new Fishes();
//            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
//            writer.openWriter();
//            fail("IOException was expected");
//        } catch (FileNotFoundException e) {
//            // expected
//        }
//    }
//
//    @Test
//    void testWriterEmptyFishes() {
//        try {
//            Fishes fishes = new Fishes();
//            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFishes.json");
//            writer.openWriter();
//            writer.write(fishes);
//            writer.closeWriter();
//
//            JsonReader reader = new JsonReader("./data/testWriterEmptyFishes.json");
//            fishes = reader.read();
//            assertEquals(0, fishes.getFishList().size());
//            assertEquals(0, fishes.getTotalWeight());
//        } catch (IOException e) {
//            fail("IOException should not have been thrown");
//        }
//    }
//
//    @Test
//    void testWriterTypicalFishes() {
//        try {
//            Fishes fishes = new Fishes();
//            fishes.addFish(new Fish('s'));
//            fishes.addFish(new Fish('e'));
//            JsonWriter writer = new JsonWriter("./data/testWriterTypicalFishes.json");
//            writer.openWriter();
//            writer.write(fishes);
//            writer.closeWriter();
//
//            JsonReader reader = new JsonReader("./data/testWriterTypicalFishes.json");
//            fishes = reader.read();
//            assertEquals(2, fishes.getFishList().size());
//        } catch (IOException e) {
//            fail("IOException should not have been thrown");
//        }
//    }
//}
