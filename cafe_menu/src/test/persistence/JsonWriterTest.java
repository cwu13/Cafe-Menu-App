package persistence;

import exceptions.DuplicateItemException;
import model.Menu;
import model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    Menu menu;

    @BeforeEach
    public void setUp() {
        menu = new Menu("Cafe Menu");
    }


    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyWorkroom() {
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyMenu.json");
        JsonReader reader = new JsonReader("./data/testWriterEmptyMenu.json");
        try {
            writer.open();
            writer.write(menu);
            writer.close();

            menu = reader.read();
            assertEquals("Cafe Menu", menu.getName());
            assertEquals(0, menu.getMenuSize());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testWriterGeneralMenu() {
        try {
            try {
                menu.addMenuItem(new MenuItem("Mango Ade", 5));
                menu.addMenuItem(new MenuItem("Espresso", 4));
            } catch (DuplicateItemException e) {
                System.out.println("Item already in menu!");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMenu.json");
            writer.open();
            writer.write(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
            menu = reader.read();
            assertEquals("Cafe Menu", menu.getName());
            List<MenuItem> menuItems = menu.getMenuItems();
            assertEquals(2, menuItems.size());
            checkMenuItem("Mango Ade",5, menuItems.get(0));
            checkMenuItem("Espresso",4, menuItems.get(1));

        } catch (IOException e) {
            fail("Exception not expected");
        }
    }
}
