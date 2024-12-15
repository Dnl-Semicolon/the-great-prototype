package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Reservation;

import java.io.*;

public class ReservationDAO {
    private String fileName = "reservations.dat";

    public void saveToFile(ListInterface<Reservation> reservationList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(reservationList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<Reservation> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Reservation> reservationList = new ArrayList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            reservationList = (ArrayList<Reservation>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return reservationList;
        }
    }
}
