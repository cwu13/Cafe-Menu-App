package ui;

import exceptions.DuplicateItemException;
import model.Menu;
import model.MenuItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Cafe application
public class Cafe {
    private static final String JSON_STORE = "./data/menu.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    public Menu menu;
    private MenuItem m1;
    private MenuItem m2;
    private MenuItem m3;
    private MenuItem m4;
    private Scanner input;
    public static final String AMERICANO_NAME = "americano";
    public static final String VANILLA_LATTE_NAME = "vanilla latte";
    public static final String GRAPEFRUIT_ADE_NAME = "grapefruit ade";
    public static final String DALGONA_COFFEE_NAME = "dalgona coffee";

    // EFFECTS: runs the cafe
    public Cafe() throws FileNotFoundException {
        input = new Scanner(System.in);
        menu = new Menu("Caitlin's menu");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCafe();
    }

    // MODIFIES: this
    // EFFECTS: executes user inputs
    private void runCafe() {
        boolean running = true;
        String command = null; //
        input = new Scanner(System.in); //

        initializeMenu();

        displayWelcome();

        while (running) {
            displayOptions();

            input.useDelimiter("\n");
            command = input.next();

            if (command.equals("q")) {
                running = false;
            } else {
                executeCommand(command);
            }
        }

        System.out.println("Thank you for visiting my cafe!");
    }

    // EFFECTS: executes user commands
    private void executeCommand(String command) {
        String customerCommand;
        String baristaCommand;

        if (command.equals("c")) {
            displayCustomerOptions();
            customerCommand = input.next();
            executeCustomerCommand(customerCommand);

        } else if (command.equals("b")) {
            displayBaristaOptions();
            baristaCommand = input.next();
            executeBaristaCommand(baristaCommand);

        } else {
            System.out.println("Invalid option.");
        }
    }

    // EFFECTS: executes customer user commands
    private void executeCustomerCommand(String customerCommand) {

        if (customerCommand.equals("1")) {
            viewMenu();
        } else if (customerCommand.equals("2")) {
            viewFilteredMenu();
        } else if (customerCommand.equals("3")) {
            findDrinkPrice();
        } else {
            System.out.println("Invalid option.");
        }

    }

    // EFFECTS: executes barista user commands
    private void executeBaristaCommand(String baristaCommand) {
        if (baristaCommand.equals("1")) {
            addItem();
        } else if (baristaCommand.equals("2")) {
            viewMenu();
        } else if (baristaCommand.equals("3")) {
            findDrinkPrice();
        } else if (baristaCommand.equals("4")) {
            loadMenu();
        } else if (baristaCommand.equals("5")) {
            saveMenu();
        } else {
            System.out.println("Invalid option.");
        }

    }

    // EFFECTS: initializes menu
    private void initializeMenu() {
        menu = new Menu("Caitlin's menu");
        m1 = new MenuItem(AMERICANO_NAME, 5.0);
        m2 = new MenuItem(VANILLA_LATTE_NAME, 6.0);
        m3 = new MenuItem(GRAPEFRUIT_ADE_NAME, 6.5);
        m4 = new MenuItem(DALGONA_COFFEE_NAME, 5.5);
        try {
            menu.addMenuItem(m1);
            menu.addMenuItem(m2);
            menu.addMenuItem(m3);
            menu.addMenuItem(m4);
        } catch (DuplicateItemException e) {
            System.out.println("Item is already in the menu!");
        }
        input = new Scanner(System.in);
    }

    // EFFECT: display welcome message
    private void displayWelcome() {
        System.out.println("Welcome to my cafe!");
    }

    // EFFECTS: display options for user
    private void displayOptions() {
        System.out.println("\tFor customers, enter c");
        System.out.println("\tFor baristas, enter b");
        System.out.println("\tEnter q to quit");
    }

    // EFFECTS: display customer options for user
    private void displayCustomerOptions() {
        System.out.println("\tEnter 1 to view menu");
        System.out.println("\tEnter 2 to filter prices on menu");
        System.out.println("\tEnter 3 to find price of a drink");
    }

    // EFFECTS: display barista options for user
    private void displayBaristaOptions() {
        System.out.println("\tEnter 1 to add menu item");
        System.out.println("\tEnter 2 to view menu");
        System.out.println("\tEnter 3 to find price of a drink");
        System.out.println("\tEnter 4 to load menu from file");
        System.out.println("\tEnter 5 to save menu to file");
    }

    // EFFECTS: display names of menu items
    private void viewMenu() {
        System.out.println(menu.getMenuNames());
    }

    // EFFECTS: display names of menu items that are less than or equal to the input amount
    private void viewFilteredMenu() {
        System.out.println("Enter your price limit:");
        double amount = input.nextDouble();

        if (menu.getFilteredPrices(amount).size() == 0) {
            System.out.println("There are no menu items in your price limit.");
        } else if (amount >= 0.0) {
            System.out.println(menu.getFilteredPrices(amount));
        }
    }

    // EFFECTS: prompts user for a menu item and displays the price
    private void findDrinkPrice() {
        System.out.println("Find price of:");

        for (MenuItem m : menu.cafeItems) {
            System.out.println("\t" + m.getMenuItemName());
        }

        String drink = input.next();

        if (!menu.getMenuNames().contains(drink)) {
            System.out.println("This drink is not on the menu.");
        } else {
            for (MenuItem m : menu.cafeItems) {
                if (drink.equalsIgnoreCase(m.getMenuItemName())) {
                    System.out.println("The " + m.getMenuItemName() + " is " + "$" + m.getMenuItemPrice());
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for item name and price and adds it to the menu if not in menu already
    private void addItem() {
        System.out.println("Enter name of new drink");
        String drinkName = input.next();

        System.out.println("Enter price of new drink");
        double drinkPrice = input.nextDouble();

        MenuItem m = new MenuItem(drinkName, drinkPrice);

        try {
            menu.addMenuItem(m);
            System.out.println("New drink " + m.getMenuItemName() + " added!");
        } catch (DuplicateItemException e) {
            System.out.println("This drink is already in the menu!");
        }
    }

    // EFFECTS: saves the menu to file
    private void saveMenu() {
        try {
            jsonWriter.open();
            jsonWriter.write(menu);
            jsonWriter.close();
            System.out.println("Saved " + menu.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads menu from file
    private void loadMenu() {
        try {
            menu = jsonReader.read();
            System.out.println("Loaded " + menu.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Cannot read from file: " + JSON_STORE);
        }
    }

}
