package boundary;

import java.util.Scanner;

public class TableMaintenanceUI {

    private Scanner scanner;

    public TableMaintenanceUI() {
        scanner = new Scanner(System.in);
    }

    public void displayNoTablesMessage() {
        System.out.println("No Tables To Display");
        System.out.println();
    }

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("--- Table Maintenance ---");
                System.out.println("1. Add Table");
                System.out.println("2. Display All Tables");
                System.out.println("3. Search Table");
                System.out.println("4. Delete Table");
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

    public int inputNewTableNumber() {
        int tableNumber = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("New Table Number");
                System.out.print(">> ");
                tableNumber = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                tableNumber = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Not A Valid Table Number. Enter A Whole Number As A Table Number.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return tableNumber;
    }

    public void displayAllTables(String allTables) {
        System.out.println("--- Available Tables ---");
        System.out.println("Entry  Table      Status       Remarks");
        System.out.println(allTables);
    }
}
