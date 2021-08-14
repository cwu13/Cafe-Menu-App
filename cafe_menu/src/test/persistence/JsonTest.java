package persistence;

import model.MenuItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMenuItem(String name, double price, MenuItem menuItem) {
        assertEquals(name, menuItem.getMenuItemName());
        assertEquals(price, menuItem.getMenuItemPrice());
    }
}
