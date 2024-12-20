package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.MenuItem;

import java.io.*;

public class MenuDAO {

	private static MenuDAO singletonInstance;

	private String fileName = "menu.dat";

	private MenuDAO() {}

	public static MenuDAO getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new MenuDAO();
		}
		return singletonInstance;
	}

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
			menuItemList = initBackup();
		} catch (IOException ex) {
			System.out.println("\nCannot read from file. MENU");
			menuItemList = initBackup();
		} catch (ClassNotFoundException ex) {
			System.out.println("\nClass not found.");
			menuItemList = initBackup();
		} finally {
			return menuItemList;
		}
	}

	public ListInterface<MenuItem> initBackup() {
		ListInterface<MenuItem> menuList = new ArrayList<>();
		menuList.add(new MenuItem("Chicken Burger", 15.00));
		menuList.add(new MenuItem("Beef Burger", 15.00));
		menuList.add(new MenuItem("Spaghetti Bolongnese", 16.00));
		menuList.add(new MenuItem("Fish and Chips", 14.00));
		menuList.add(new MenuItem("Veggie Pizza", 20.00));
		menuList.add(new MenuItem("Peperoni Pizza", 22.00));
		menuList.add(new MenuItem("Nasi Lemak", 10.00));
		menuList.add(new MenuItem("Garlic Bread", 6.00));
		menuList.add(new MenuItem("Baugette", 6.00));
		menuList.add(new MenuItem("Chocolate Lava Cake", 12.00));
		menuList.add(new MenuItem("Cheesecake", 12.00));
		menuList.add(new MenuItem("Chocolate Milk Shake", 9.00));
		menuList.add(new MenuItem("Hot Chocolate", 9.00));
		menuList.add(new MenuItem("Coffee", 9.00));
		saveToFile(menuList);
		return menuList;
	}
}
