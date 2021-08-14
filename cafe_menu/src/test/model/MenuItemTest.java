package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemTest {
    MenuItem m1;
    MenuItem m2;

    @BeforeEach
    public void setUp() {
        m1 = new MenuItem("Americano", 5.0);
        m2 = new MenuItem("Iced Green Tea", 6.5);
    }

    @Test
    public void testGetMenuItemName() {
        assertEquals("Americano",m1.getMenuItemName());
        assertEquals("Iced Green Tea",m2.getMenuItemName());
    }

    @Test
    public void testGetMenuItemPrice() {
        assertEquals(5.0, m1.getMenuItemPrice());
        assertEquals(6.5, m2.getMenuItemPrice());
    }

}