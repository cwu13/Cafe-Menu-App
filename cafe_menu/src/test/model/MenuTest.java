package model;

import exceptions.DuplicateItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
        private Menu menu;
        private MenuItem m1;
        private MenuItem m2;
        private MenuItem m3;
        private MenuItem m4;

    @BeforeEach
    public void setUp() {
        menu = new Menu("Caitlin's cafe");
        m1 = new MenuItem("Americano",4.0);
        m2 = new MenuItem("Vanilla Latte",5.0);
        m3 = new MenuItem("Milk Tea",5.5);
        m4 = new MenuItem("Grapefruit Ade",6.0);
        try {
            menu.addMenuItem(m1);
            menu.addMenuItem(m2);
            menu.addMenuItem(m3);
        } catch (DuplicateItemException e) {
            System.out.println("Item is already in the menu!");
        }
    }

    // return list of String all the names in the menu
    @Test
    public void testGetMenuNames() {
        List<String> menuNames = new ArrayList<>();
        menuNames.add("Americano");
        menuNames.add("Vanilla Latte");
        menuNames.add("Milk Tea");
        assertEquals(menuNames, menu.getMenuNames());

    }

    // return price of given menu item, if menu item is not in menu, return 0.0
    @Test
    public void testGetMenuItemPrice() {
        assertEquals(menu.getMenuPrice(m1), 4.0);
        assertEquals(menu.getMenuPrice(m4), 0.0);
    }

    // add menu item no exception thrown
    @Test
    public void testAddMenuItemNoDuplicateException() {
        assertFalse(menu.getMenuItems().contains(m4));
        try {
            menu.addMenuItem(m4);
        } catch (DuplicateItemException e) {
            fail("Exception not expected.");
        }
        assertTrue(menu.getMenuItems().contains(m4));
    }

    // add duplicate menu item exception thrown
    @Test
    public void testAddMenuItemDuplicateException() {
        assertTrue(menu.getMenuItems().contains(m1));
        assertEquals(3, menu.getMenuSize());
        try {
            menu.addMenuItem(m1);
            fail("Exception expected.");
        } catch (DuplicateItemException e) {
            //expected
        }
        assertTrue(menu.getMenuItems().contains(m1));
        assertEquals(3,menu.getMenuSize());
    }

    // test for filtering prices
    @Test
    public void testGetFilteredPrices() {
        List<String> filteredMenu = new ArrayList<>();
        filteredMenu.add(m1.getMenuItemName());
        filteredMenu.add(m2.getMenuItemName());
        assertEquals(filteredMenu, menu.getFilteredPrices(5.0));
    }


}
