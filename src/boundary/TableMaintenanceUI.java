package boundary;

import java.util.Scanner;

public class TableMaintenanceUI {

    private Scanner scanner;

    public TableMaintenanceUI() {
        scanner = new Scanner(System.in);
    }

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("--- Table Maintenance ---");
                System.out.println("1. Add Table");
                System.out.println("2. Display Tables");
                System.out.println("3. Search Table");
                System.out.println("4. Delete Table");
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
}
