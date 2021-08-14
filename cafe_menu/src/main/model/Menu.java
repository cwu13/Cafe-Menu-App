package model;

import exceptions.DuplicateItemException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a menu that is used by baristas and customers
public class Menu implements Writable {
    private String name;
    public List<MenuItem> cafeItems;

    // constructor
    // initializes an empty menu;
    public Menu(String name) {
        this.name = name;
        cafeItems = new ArrayList<>();
    }

    // EFFECTS: returns name of menu
    public String getName() {
        return name;
    }

    // EFFECTS: returns a list of menu items
    public List<MenuItem> getMenuItems() {
        return cafeItems;
    }

    // EFFECTS: returns a list of all menu item names
    public List<String> getMenuNames() {
        List<String> menuItemNames = new ArrayList<>();

        for (MenuItem m : cafeItems) {
            menuItemNames.add(m.getMenuItemName());
        }
        return menuItemNames;
    }

    // EFFECTS: return the price of given menu item, otherwise return 0.0
    public double getMenuPrice(MenuItem menuItem) {
        if (cafeItems.contains(menuItem)) {
            return menuItem.getMenuItemPrice();
        }
        return 0.0;
    }

    // EFFECT: returns the size of the menu
    public int getMenuSize() {
        return cafeItems.size();
    }

    // MODIFIES : this
    // EFFECTS: if menu item is in the menu already, throw DuplicateItemException
    // otherwise add the given item into menu and return true.
    public boolean addMenuItem(MenuItem m) throws DuplicateItemException {
        for (MenuItem menuItem : cafeItems) {
            if (menuItem.getMenuItemName().equalsIgnoreCase(m.getMenuItemName())) {
                throw new DuplicateItemException();
            }
        }
        cafeItems.add(m);
        return true;
    }

    // EFFECTS: return all the items on the menu that have a price that is less than or equal to the given priceLimit
    public List<String> getFilteredPrices(double priceLimit) {
        List<String> filteredMenu = new ArrayList<>();
        for (MenuItem m : cafeItems) {
            if (priceLimit >= m.getMenuItemPrice()) {
                filteredMenu.add(m.getMenuItemName());
            }
        }
        return filteredMenu;
    }

    // EFFECTS: returns new jsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("menu items", cafeItemsToJson());
        return json;
    }

    // EFFECTS: returns menuItems in this menu as JSON array
    private JSONArray cafeItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MenuItem c : cafeItems) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

}
