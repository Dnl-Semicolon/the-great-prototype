package entity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PersistentCounter {

    private static final String FILE_NAME = "codeCounter.txt";
    private int currentNumber;

    public PersistentCounter() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // File does not exist, create it with initial value 100
            currentNumber = 1000;
            writeToFile(currentNumber);
        } else {
            // File exists, read the number from it
            currentNumber = readFromFile();
            if (currentNumber < 1000) {
                currentNumber = 1000;
                writeToFile(currentNumber);
            }
        }
    }

    /**
     * Increments the stored number by 1 and updates the file.
     */
    public void increment() {
        currentNumber++;
        writeToFile(currentNumber);
    }

    public void update(int newCurrentNumber) {
        writeToFile(newCurrentNumber);
    }

    /**
     * Returns the currently stored number.
     */
    public int getCurrentNumber() {
        return currentNumber;
    }

    /**
     * Writes the given number to the file.
     */
    private void writeToFile(int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(Integer.toString(number));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the number from the file.
     */
    private int readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            // If there's an error reading the file or parsing the number,
            // default to 100 and rewrite the file.
            writeToFile(1000);
            return 1000;
        }
    }

    public static void main(String[] args) {
        PersistentCounter persistentCounter = new PersistentCounter();
    }
}
