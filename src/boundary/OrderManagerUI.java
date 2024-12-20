package boundary;

import entity.*;
import java.util.Scanner;

public class OrderManagerUI {
    private Scanner scanner = new Scanner(System.in);
    public int getMenuChoice() {
        System.out.println("--- Order Management ---");
        System.out.println("1. Create Order");
        System.out.println("2. View Menu");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter choice >> ");
        int choice = 0;
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("--- Order Management ---");
                System.out.println("1. Place Order");
                System.out.println("2. View Orders for a Table");
                System.out.println("3. Cancel Order");
                System.out.println("4. Back to Main Menu");
                System.out.print("Enter your choice (1-4): ");
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

    public int getTableNumber() {
        int tableNumber = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.print("Enter table number: ");
                tableNumber = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                tableNumber = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Invalid input. Enter A Whole Number As Table Number.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return tableNumber;
    }

    public int getTableChoice(String inputStr) {
        int choice = 0;

        System.out.println("--- Create New Order ---");
        System.out.println("Occupied Tables");
        System.out.println("****************************************************");
        System.out.println("Entry   Table Number");
        System.out.println("====================================================");
        System.out.print(inputStr);
        System.out.println("----------------------------------------------------");
        System.out.println();
        System.out.println("Please select a table to create a new order.");
        System.out.print("Enter the table entry number >> ");
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void displayMenu(String inputStr) {
        System.out.println(inputStr);
    }

    public int getMenuItemChoice() {
        System.out.print("Enter the entry number of the menu item to add to the order: ");

        int entryNumber = -1;
        while (entryNumber < 1 || entryNumber > 3) {
            if (scanner.hasNextInt()) {
                entryNumber = scanner.nextInt();
                if (entryNumber < 1 || entryNumber > 3) {
                    System.out.println("Invalid entry number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }

        return entryNumber;
    }

    public boolean confirmAddAnotherItem(MenuItem chosenMenuItem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(chosenMenuItem.name + " has been added to the order.");
        System.out.print("Do you want to add another item? (yes/no): ");

        String response = scanner.nextLine().trim().toLowerCase();
        System.out.println();
        return response.equals("yes");
    }

    public void displayOrderCreationComplete(int tableNo) {
        System.out.println("Order creation complete for Table " + tableNo + ".");
        System.out.println();
    }

    public void displayOrderAddedSuccessfully(int tableNo) {
        System.out.println("Order successfully added for Table " + tableNo + ".");
        System.out.println();
    }

}
