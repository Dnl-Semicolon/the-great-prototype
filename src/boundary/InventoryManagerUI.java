package boundary;

import java.util.Scanner;

public class InventoryManagerUI {

    private Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int choice = 0;
        System.out.println("--- Inventory Managerment ---");
        System.out.println("1. Add New Ingredient"); // C create
        System.out.println("2. Display Inventory"); // R read
        System.out.println("3. Update Inventory"); // U update
        System.out.println("4. Remove Inventory Item"); // D delete
        System.out.print("ENter Your CHoice (1-5): ");
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

}
