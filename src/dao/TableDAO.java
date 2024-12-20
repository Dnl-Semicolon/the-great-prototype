package dao;

import adt.*;
import entity.*;

import java.io.*;

public class TableDAO {

    private static TableDAO singletonInstance;

    private String fileName = "tables.dat";

    private TableDAO() {}

    public static TableDAO getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new TableDAO();
        }
        return singletonInstance;
    }

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
            tableList = initBackup();
        } catch (IOException ex) {
            System.out.println("\nCannot read from file. TABLE");
            tableList = initBackup();
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
            tableList = initBackup();
        } finally {
            return tableList;
        }
    }

    private ListInterface<Table> initBackup() {
        ListInterface<Table> tableList = new ArrayList<>();
        tableList.add(new Table(1));
        tableList.add(new Table(2));
        tableList.add(new Table(3));
        tableList.add(new Table(4));
        tableList.add(new Table(5));
        saveToFile(tableList);
        return tableList;
    }
}
