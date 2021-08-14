package ui;

import java.io.FileNotFoundException;

// runs the cafe
public class Main {

    public static void main(String[] args) {
        try {
            new Cafe();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot run application: menu file not found");
        }
    }
}
