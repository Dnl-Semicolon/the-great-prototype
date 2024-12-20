package dao;

import adt.ArrayList;
import adt.LinkedQueue;
import adt.ListInterface;
import adt.QueueInterface;
import entity.Customer;
import entity.MenuItem;

import java.io.*;

public class WaitlistDAO {
    private String fileName = "waitlist.dat";

    public void saveToFile(QueueInterface<Customer> waitList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(waitList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public QueueInterface<Customer> retrieveFromFile() {
        File file = new File(fileName);
        QueueInterface<Customer> waitList = new LinkedQueue<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            waitList = (LinkedQueue<Customer>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return waitList;
        }
    }
}
