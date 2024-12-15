package boundary;

import java.util.Scanner;

public class MenuMaintenanceUI {
	private Scanner scanner;

	public MenuMaintenanceUI() {
		scanner = new Scanner(System.in);
	}

	public void displayEmptyMenuMessage() {
		System.out.println("No Menu Items To Display");
		System.out.println();
	}

	public int getMainMenuChoice() {
		int choice = 0;
		boolean isExceptionFound = false;
		do {
			try {
				System.out.println("--- Menu Maintenance ---");
				System.out.println("1. Add Menu Item to Menu");
				System.out.println("2. Display Menu Items");
				System.out.println("3. Search Menu Item");
				System.out.println("4. Delete Menu Item");
				System.out.println("5. Exit");
				System.out.print("Enter Your Choice >> ");
				choice = scanner.nextInt();
				isExceptionFound = false;
			} catch (Exception ex) {
				scanner.nextLine();
				choice = 0;
				isExceptionFound = true;
				System.out.println();
				System.out.println("Error: Not A Valid Choice. Enter A Whole Number As Your Choice.");
				System.out.println();
			}
		} while (isExceptionFound);
		scanner.nextLine();
		System.out.println();
		return choice;
	}

	public String inputMenuItemName() {
		String name = "";
		System.out.println("Name:");
		System.out.print(">> ");
		name = scanner.nextLine();
		System.out.println();
		return name;
	}

	public double inputMenuItemPrice() {
		double price = 0.00;
		boolean isExceptionFound = false;
		do {
			System.out.println("Price:");
			System.out.print(">> ");
			try {
				price = scanner.nextDouble();
				isExceptionFound = false;
			} catch (Exception ex) {
				scanner.nextLine();
				price = 0.00;
				isExceptionFound = true;
				System.out.println();
			}
		} while (isExceptionFound);
		scanner.nextLine();
		System.out.println();
		return price;
	}

	public void displayMenuItems(String menuItems) {
		System.out.println("Entry  Name             Price");
		System.out.println(menuItems);
	}
}
