package boundary;

import entity.Ingredient;

import java.util.Scanner;

public class InventoryManagerUI {

    private Scanner scanner = new Scanner(System.in);

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("--- Inventory Management ---");
                System.out.println("1. Add New Ingredient");
                System.out.println("2. Display Inventory");
                System.out.println("3. Checking Low Stock Inventory");
                System.out.println("4. Restock Inventory Item");
                System.out.println("5. Back");
                System.out.print("Enter Your Choice (1-5): ");
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

    // Display menu and get user choice
    public int getMenuChoice() {
        System.out.println("--- Inventory Management Subsystem ---");
        System.out.println("1. Add New Ingredient");
        System.out.println("2. Display Inventory");
        System.out.println("3. Checking Low Stock Inventory");
        System.out.println("4. Restock Inventory Item");
        System.out.println("5. Back");
        System.out.print("Enter Your Choice (1-5): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return choice;
    }
    
    public void display(String display){
        System.out.println("List of Products:\n" + display);
    }

    // Get ingredient code from user
    public String getIngredientCode() {
        System.out.print("Enter Ingredient Code: ");
        return scanner.nextLine().trim();
    }

    // Get ingredient name from user
    public String getIngredientName() {
        System.out.print("Enter Ingredient Name: ");
        return scanner.nextLine().trim();
    }

    // Get ingredient quantity from user
    public int getIngredientQuantity() {
        int qty = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.print("Enter Quantity: ");
                qty = scanner.nextInt();
                if (qty <= 0) {
                    throw new IllegalArgumentException("Quantity must be a positive integer.");
                }
                isExceptionFound = false;
            } catch (IllegalArgumentException e) {
                // Handle specific illegal argument scenario
                System.out.println();
                System.out.println("Error: " + e.getMessage());
                System.out.println();
                scanner.nextLine(); // Clear buffer
                isExceptionFound = true;
                qty = 0;
            } catch (Exception ex) {
                scanner.nextLine();
                qty = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Invalid input. Please enter a positive integer.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return qty;
    }

    // Add a new ingredient
    public Ingredient addIngredient() {
        System.out.println("--- Add New Ingredient ---");
        String name = getIngredientName();
        int quantity = getIngredientQuantity();

        return new Ingredient(name, quantity); // Ensure your `Ingredient` entity has this constructor
    }

    // Display inventory details
    public void displayInventory(String inventoryDetails) {
        System.out.println("--- Current Inventory ---");
        System.out.println(inventoryDetails);
    }

    // Update inventory (get user input for restocking)
    public int getRestockQuantity() {
        System.out.print("Enter Quantity to Restock: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a positive integer.");
            scanner.next();
        }
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return quantity;
    }

    // Notify user of low stock
    public void notifyLowStock(String ingredientName) {
        System.out.println("Low stock alert! Please restock: " + ingredientName);
    }

   
    
        public Scanner getScanner() {
        return scanner;
    }

}
