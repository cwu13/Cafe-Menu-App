package ui;

import exceptions.DuplicateItemException;
import model.Menu;
import model.MenuItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// cafeGUI for cafe
public class CafeGUI {
    JFrame frame = new JFrame("Cafe");
    private static final String JSON_STORE = "./data/menu.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    JLabel searchLabel = new JLabel("Search:");
    JLabel filterLabel = new JLabel("Filter Prices:");
    JLabel addItemLabel = new JLabel("Add Item:");
    JLabel nameLabel = new JLabel("Name");
    JLabel priceLabel = new JLabel("Price");

    JButton filterPricesButton = new JButton("Filter");
    JButton searchItemButton = new JButton("Search Item");
    JButton addItemButton = new JButton("Add Item");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");

    JPanel panelCont = new JPanel();
    JPanel menuPanel = new JPanel();
    JPanel rowPanel1 = new JPanel();
    JPanel rowPanel2 = new JPanel();
    JPanel rowPanel3 = new JPanel();

    Menu menu;
    MenuItem m1;
    MenuItem m2;
    MenuItem m3;

    CardLayout c1 = new CardLayout();

    JTable table;
    TableRowSorter<DefaultTableModel> sorter;

    private JTextField filterPrice;
    private JTextField searchItem;
    private JTextField addItemName;
    private JTextField addItemPrice;

    private static DefaultTableModel myModel;
    private JScrollPane scrollPane;

    public CafeGUI() {
        setUpPanelControl();
        getMenuPanel();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        frame.add(panelCont);
        frame.setSize(550,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: sets up all panels and panel controls
    public void setUpPanelControl() {
        play("bellsoundeffect.wav");
        panelCont.setLayout(c1);
        panelCont.add(menuPanel, "menu");
        c1.show(panelCont, "menu");
    }

    // EFFECTS: sets up menu panel
    public void getMenuPanel() {
        getMenuTable();
        setUpFilterAndSearch();
        addItem();
        setUpPanels();

        menuPanel.add(rowPanel1);

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); //newly added
        menuPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "MENU", TitledBorder.CENTER, TitledBorder.TOP));
        scrollPane = new JScrollPane(table);
        menuPanel.add(scrollPane, BorderLayout.CENTER);
        menuPanel.setPreferredSize(new Dimension(550,300));

        menuPanel.add(rowPanel2);
        menuPanel.add(rowPanel3);

        SaveButtonListener saveButtonListener = new SaveButtonListener();
        saveButton.addActionListener(saveButtonListener);

        LoadButtonListener loadButtonListener = new LoadButtonListener();
        loadButton.addActionListener(loadButtonListener);
    }

    // EFFECTS: sets up menu
    public void setUpMenu() {
        m1 = new MenuItem("Americano", 5.0);
        m2 = new MenuItem("Vanilla Latte",6.0);
        m3 = new MenuItem("Grapefruit Ade", 6.5);

        menu = new Menu("menu");
        try {
            menu.addMenuItem(m1);
            menu.addMenuItem(m2);
            menu.addMenuItem(m3);
        } catch (DuplicateItemException e) {
            System.out.println("Item already in menu!");
        }
    }

    // EFFECTS: creates table from menu
    public void getMenuTable() {
        setUpMenu();

        String[] columnNames = { "Item", "Price"};
        myModel = new DefaultTableModel(new Object[0][0],columnNames);

        for (MenuItem menuItem : menu.getMenuItems()) {
            Object[] tableData = new Object[2];
            tableData[0] = menuItem.getMenuItemName();
            tableData[1] = menuItem.getMenuItemPrice();
            myModel.addRow(tableData);
        }

        table = new JTable();
        table.setModel(myModel);
        table.setFillsViewportHeight(true);
    }

    // EFFECTS: adds button and text field elements to panels
    public void setUpPanels() {
        rowPanel1.add(saveButton);
        rowPanel1.add(loadButton);

        rowPanel2.add(searchLabel);
        rowPanel2.add(searchItem);
        rowPanel2.add(searchItemButton);
        rowPanel2.add(filterLabel);
        rowPanel2.add(filterPrice);
        rowPanel2.add(filterPricesButton);

        rowPanel3.add(addItemLabel);
        rowPanel3.add(nameLabel);
        rowPanel3.add(addItemName);
        rowPanel3.add(priceLabel);
        rowPanel3.add(addItemPrice);
        rowPanel3.add(addItemButton);
    }

    // EFFECTS: gets strings from JTextFields and adds items to menu
    public void addItem() {
        addItemName = new JTextField(12);
        addItemPrice = new JTextField(4);
        addItemName.setMaximumSize(addItemName.getPreferredSize());
        addItemPrice.setMaximumSize(addItemPrice.getPreferredSize());

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = addItemName.getText();
                String itemPrice = addItemPrice.getText();

                addItemActionListener(itemName,itemPrice);
            }
        });
    }

    // EFFECTS: creates pop up panes if there are errors in input, else adds menu item
    public void addItemActionListener(String itemName, String itemPrice) {
        try {
            sorter.setRowFilter(null);
            MenuItem menuItem = new MenuItem(addItemName.getText(), new Double(addItemPrice.getText()));

            try {
                menu.addMenuItem(menuItem);
                myModel.addRow(new Object[]{itemName, itemPrice});
                play("moneysoundeffect.wav");
            } catch (DuplicateItemException e) {
                JOptionPane.showMessageDialog(null,
                        "There's already an item with this name.",
                        "Duplicate MenuItem",
                        JOptionPane.WARNING_MESSAGE);
                Toolkit.getDefaultToolkit().beep();
            }

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null,
                    itemPrice + " is not a valid price.",
                    "Invalid Price",
                    JOptionPane.WARNING_MESSAGE);
            Toolkit.getDefaultToolkit().beep();
        }
    }

    // EFFECTS: sets up the filter and search functions
    public void setUpFilterAndSearch() {
        filterPrice = new JTextField(4);
        filterPrice.setMaximumSize(filterPrice.getPreferredSize());
        sorter = new TableRowSorter<>(myModel);

        table.setRowSorter(sorter);
        filter(sorter);

        searchItem = new JTextField(8);
        searchItem.setMaximumSize(searchItem.getPreferredSize());
        search(sorter);
    }

    // EFFECTS: filters table using sorter
    public void filter(TableRowSorter sorter) {
        RowFilter<DefaultTableModel, Integer> filter = new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                myModel = entry.getModel();
                // changed table to myModel down here vv, not sure if it's right but it works.
                Double d = new Double(myModel.getValueAt(entry.getIdentifier(),1).toString());

                if (d <= Double.parseDouble(filterPrice.getText())) {
                    return true;
                }
                return false;
            }
        };

        filterPricesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setRowFilter(filter);
                filterPrice.requestFocusInWindow();
                filterPrice.setText("");
            }
        });
    }

    // EFFECTS: searches table using sorter
    public void search(TableRowSorter sorter) {
        searchItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = searchItem.getText();

                if (item.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + item));
                }
            }
        });
    }

    // EFFECTS: saves the menu to file
    private void saveMenu() {
        try {
            jsonWriter.open();
            jsonWriter.write(menu);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Your menu has been saved to " + JSON_STORE + ".",
                    "Menu Saved",
                    JOptionPane.WARNING_MESSAGE);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads menu from file
    private void loadMenu() {
        try {
            menu = jsonReader.read();
            myModel.setRowCount(0);

            for (MenuItem menuItem : menu.getMenuItems()) {
                myModel.addRow(new Object[]{menuItem.getMenuItemName(),menuItem.getMenuItemPrice()});
            }

            JOptionPane.showMessageDialog(null,
                    "Your menu has been loaded from " + JSON_STORE + ".",
                    "Menu Loaded",
                    JOptionPane.WARNING_MESSAGE);

        } catch (IOException e) {
            System.out.println("Cannot read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: ActionListener for save button; saves menu when clicked
    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveMenu();
        }
    }

    // EFFECTS: ActionListener for load button; loads menu from JSON
    class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadMenu();
        }
    }

    // EFFECTS: plays audio clip with given filename
    public static void play(String filename) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    // EFFECTS: runs main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CafeGUI();
            }
        });
    }
}
