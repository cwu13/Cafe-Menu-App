package persistence;

import model.Menu;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of menu to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened
    // for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of menu to file
    public void write(Menu m) {
        JSONObject json = m.toJson();
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
