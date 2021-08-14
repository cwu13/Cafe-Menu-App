package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a menu item with its name and price
public class MenuItem implements Writable {
    private final String name;
    private final double price;

    // EFFECTS: MenuItem has given name and price.
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // EFFECTS: returns name of menu item
    public String getMenuItemName() {
        return name;
    }

    // EFFECTS: returns price of menu item
    public double getMenuItemPrice() {
        return price;
    }

    // EFFECTS: returns new jsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        return json;
    }
}
