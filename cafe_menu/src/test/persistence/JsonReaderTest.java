package persistence;

import model.Menu;
import model.MenuItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReadNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Menu m = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReaderEmptyMenu() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyMenu.json");
        try {
            Menu m = reader.read();
            assertEquals("Cafe Menu", m.getName());
            assertEquals(0, m.getMenuSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralMenu() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
        try {
            Menu m = reader.read();
            assertEquals("Cafe Menu", m.getName());
            List<MenuItem> menuItems = m.getMenuItems();
            assertEquals(2, menuItems.size());
            checkMenuItem("Mango Ade", 5, menuItems.get(0));
            checkMenuItem("Espresso", 4, menuItems.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
