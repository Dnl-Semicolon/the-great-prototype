package dao;

import adt.*;
import entity.*;

import java.io.*;

public class TableDAO {
    private String fileName = "tables.dat";

    public void saveToFile(ListInterface<Table> tableList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(tableList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<Table> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Table> tableList = new ArrayList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            tableList = (ArrayList<Table>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return tableList;
        }
    }

    public ListInterface<Table> initTables() {
        ListInterface<Table> tableList = new ArrayList<>();
        tableList.add(new Table(1));
        tableList.add(new Table(2));
        tableList.add(new Table(3));
        return tableList;
    }


}
