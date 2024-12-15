package control;

import adt.ArrayList;
import adt.ListInterface;
import boundary.MenuMaintenanceUI;
import dao.MenuDAO;
import entity.MenuItem;
import java.util.Scanner;

public class MenuMaintenance {
	private MenuMaintenanceUI menuMaintenanceUI;
	private MenuDAO menuDAO;
	private ListInterface<MenuItem> menu;

	public MenuMaintenance() {
		menuMaintenanceUI = new MenuMaintenanceUI();
		menuDAO = new MenuDAO();
		menu = menuDAO.retrieveFromFile();
	}

	public void deleteMenuItem() {
		MenuItem menuItemToDelete = new MenuItem();
		boolean isMenuItemToDeleteFound = false;
		MenuItem removedMenuItem = new MenuItem();
		System.out.println("Delete Menu Item");
		System.out.println("Would you like to search for the item first? (Y/N)");
		System.out.print(">> ");
		Scanner scanner = new Scanner(System.in);
		if (scanner.nextLine().trim().charAt(0) == 'y') {
			System.out.println();
			menuItemToDelete = searchMenuItem();
			if (menuItemToDelete != null) {
				int n = menu.getNumberOfEntries();
				for (int i = 1; i <= n && !isMenuItemToDeleteFound; i++) {
					MenuItem menuItemEntry = menu.getEntry(i);
					if (menuItemEntry.equals(menuItemToDelete)) {
						removedMenuItem = menu.remove(i);
						isMenuItemToDeleteFound = true;
					}
				}
				System.out.println(removedMenuItem.getName() + " has been deleted.");
				System.out.println();
				menuDAO.saveToFile(menu);
			} else {
				System.out.println("No item was selected for deletion.");
				System.out.println();
			}

		} else {
			System.out.println();
			System.out.println("Menu Items:");
			int n = menu.getNumberOfEntries();
			displayMenu();
			System.out.println("Enter the number of the item to delete:");
			System.out.print(">> ");
			String choice = scanner.nextLine().trim();
			System.out.println();
			try {
				int index = Integer.parseInt(choice);
				if (index >= 1 && index <= menu.getNumberOfEntries()) {
					removedMenuItem = menu.remove(index);
					System.out.println(removedMenuItem.getName() + " has been deleted.");
					System.out.println();
					menuDAO.saveToFile(menu);
				} else {
					System.out.println("Invalid selection. Try again.");
					System.out.println();
				}
			} catch (Exception ex) {
				System.out.println("Invalid input. Try again.");
				System.out.println();
			}
		}
	}

	public MenuItem searchMenuItem() {
	/*  Allows the user to search for menu items using keywords or filters.
        Returns the selected item for further actions or null if the user exits.  */
		String keyword = "";
		Scanner scanner = new Scanner(System.in);
		if (!menu.isEmpty()) {
			while (true) {
				System.out.println("Search Menu Items");
				System.out.println("Enter a keyword to filter menu items (or type 'exit' to return to the main menu):");
				keyword = scanner.nextLine().trim();
				System.out.println();

				if (keyword.equalsIgnoreCase("exit")) {
					return null;
				}

				ListInterface<MenuItem> filteredMenu = new ArrayList<>();
				MenuItem menuItem;
				String menuItemName;
				int n = menu.getNumberOfEntries();
				for (int i = 1; i <= n; i++) {
					menuItem = menu.getEntry(i);
					menuItemName = menuItem.getName();
					if (menuItemName.toLowerCase().contains(keyword.toLowerCase())) {
						filteredMenu.add(menuItem);
					}
				}

				if (filteredMenu.isEmpty()) {
					System.out.printf("No menu items found containing '%s'. Try again.%n", keyword);
					continue;
				}

				System.out.println("Filtered Menu Items:");
				n = filteredMenu.getNumberOfEntries();
				for (int i = 1; i <= n; i++) {
					System.out.printf("%d. %s%n", i, filteredMenu.getEntry(i));
				}
				System.out.println();
				System.out.println("Select an item by number to choose it, or type 'back' to search again.");
				System.out.print("> ");
				String choice = scanner.nextLine().trim();
				System.out.println();

				if (choice.toLowerCase().equals("back")) {
					continue;
				}

				MenuItem selectedItem = new MenuItem();

				try {
					int index = Integer.parseInt(choice);
					if (index >= 1 && index <= filteredMenu.getNumberOfEntries()) {
						selectedItem = filteredMenu.getEntry(index);
						System.out.println("You selected: " + selectedItem.getName());
						System.out.println();
						return selectedItem;
					} else {
						System.out.println("Invalid selection. Try again.");
					}
				} catch (Exception ex) {
					System.out.println("Invalid input. Try again.");
				}

			}
		} else {
			menuMaintenanceUI.displayEmptyMenuMessage();
			return null;
		}
	}

	public void displayMenu() {
		if (!menu.isEmpty()) {
			menuMaintenanceUI.displayMenuItems(getMenuItems());
		} else {
			menuMaintenanceUI.displayEmptyMenuMessage();
		}
	}

	public String getMenuItems() {
		StringBuilder inputStr = new StringBuilder();
		MenuItem menuItem;
		int n = menu.getNumberOfEntries();
		for (int i = 1; i <= n; i++) {
			menuItem = menu.getEntry(i);
			inputStr.append(String.format("%3d.   ", i));
			inputStr.append(menuItem.toString());
			inputStr.append("\n");
		}
		return inputStr.toString();
	}

	public void addMenuItem() {
		String name = "";
		double price = 0.00;
		MenuItem menuItem = new MenuItem();

		boolean isValidInput = false;

		while (!isValidInput) {
			name = menuMaintenanceUI.inputMenuItemName();
			if (MenuItem.isValidName(name)) {
				isValidInput = true;
			}
		}

		isValidInput = false;

		while (!isValidInput) {
			price = menuMaintenanceUI.inputMenuItemPrice();
			if (MenuItem.isValidPrice(price)) {
				isValidInput = true;
			}
		}

		menuItem.setName(name);
		menuItem.setPrice(price);

		menu.add(menuItem);
		menuDAO.saveToFile(menu);

		displayMenu();

	}

	public void runMenuMaintenance() {
		int choice = 0;
		do {
			choice = menuMaintenanceUI.getMainMenuChoice();

			switch (choice) {
				case 1:
					addMenuItem();
					break;
				case 2:
					displayMenu();
					break;
				case 3:
					searchMenuItem();
					break;
				case 4:
					deleteMenuItem();
					break;
				default:
					System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-4.");
					System.out.println();
			}
		} while (choice != 0);
	}



	public static void main(String[] args) {
		MenuMaintenance menuMaintenance = new MenuMaintenance();
		menuMaintenance.runMenuMaintenance();
	}
}
