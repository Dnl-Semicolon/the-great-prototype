package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Customer;
import entity.MenuItem;

import java.io.*;

public class WaitlistDAO {
    private String fileName = "waitlist.dat";

    public void saveToFile(ListInterface<Customer> waitList) {
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

    public ListInterface<Customer> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Customer> waitList = new ArrayList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            waitList = (ArrayList<Customer>) (oiStream.readObject());
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
