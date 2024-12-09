package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.MenuItem;

import java.io.*;

public class MenuDAO {
	private String fileName = "menu.dat";

	public void saveToFile(ListInterface<MenuItem> menuItemList) {
		File file = new File(fileName);
		try {
			ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
			ooStream.writeObject(menuItemList);
			ooStream.close();
		} catch (FileNotFoundException ex) {
			System.out.println("\nFile not found");
		} catch (IOException ex) {
			System.out.println("\nCannot save to file");
		}
	}

	public ListInterface<MenuItem> retrieveFromFile() {
		File file = new File(fileName);
		ListInterface<MenuItem> menuItemList = new ArrayList<>();
		try {
			ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
			menuItemList = (ArrayList<MenuItem>) (oiStream.readObject());
			oiStream.close();
		} catch (FileNotFoundException ex) {
			System.out.println("\nNo such file.");
		} catch (IOException ex) {
			System.out.println("\nCannot read from file.");
		} catch (ClassNotFoundException ex) {
			System.out.println("\nClass not found.");
		} finally {
			return menuItemList;
		}
	}
}
