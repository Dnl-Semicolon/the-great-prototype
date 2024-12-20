package control;

import adt.ArrayList;
import adt.HashMap;
import adt.ListInterface;
import adt.MapInterface;
import app.RestaurantApp;
import boundary.InventoryManagerUI;
import dao.IngredientDAO;
import dao.IngredientMapDAO;
import entity.Ingredient;
import entity.PersistentCounter;

public class InventoryManager {

    private static InventoryManager singletonInstance;

    private InventoryManagerUI inventoryManagerUI = new InventoryManagerUI();
    private IngredientDAO ingredientDAO = IngredientDAO.getInstance();
    private IngredientMapDAO ingredientMapDAO = IngredientMapDAO.getInstance();

    private ListInterface<Ingredient> productList = new ArrayList<>();
    private MapInterface<String, Ingredient> ingredientMap = new HashMap<>();

    private PersistentCounter persistentCounter;

    private static final int LOW_STOCK_THRESHOLD = 10;

    private InventoryManager() {
        // Retrieve existing data from the file
        productList = ingredientDAO.retrieveFromFile();
        ingredientMap = ingredientMapDAO.retrieveFromFile();
        persistentCounter = new PersistentCounter();
    }

    public static InventoryManager getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new InventoryManager();
        }
        return singletonInstance;
    }

    public MapInterface<String, Ingredient> getIngredientMap() {
        return ingredientMap;
    }

    public ListInterface<Ingredient> getProductList() {
        return productList;
    }

    // Add a new ingredient to inventory
    public void addNewIngredient() {
        Ingredient newIngredient = inventoryManagerUI.addIngredient();

        if (newIngredient == null) {
            handleFailToCreateIngredient();
            return;
        }

        String base = "";
        int numberPart = 0;
        String newCode = generateCode();
        if (ingredientMap.containsKey(newCode)) {
            base = newCode.substring(0,1);
            numberPart = Integer.parseInt(newCode.substring(1));

            while (ingredientMap.containsKey(newCode)) {
                numberPart++;
                newCode = base + numberPart;
            }
        }

        persistentCounter.update(numberPart);
        newIngredient.setCode(newCode);

        // Add to product list
        productList.add(newIngredient);
        ingredientMap.put(newCode, newIngredient);
        // Save updated product list to file
        ingredientDAO.saveToFile(productList);
        ingredientMapDAO.saveToFile(ingredientMap);
    }

    private void handleFailToCreateIngredient() {
        System.out.println("Failed to create ingredient");
        System.out.println();
    }

    public String generateCode() {
        StringBuilder code = new StringBuilder();
        code.append("I");
        persistentCounter.increment();
        code.append(String.format("%d", persistentCounter.getCurrentNumber()));
        return code.toString();
    }

    public String getAllIngredient() {
        String display = "";
        for (int i = 1; i <= productList.getNumberOfEntries(); i++) {
            display += productList.getEntry(i) + "\n";
        }
        return display;
    }

    // Display the current inventory
    public void displayInventory() {
        inventoryManagerUI.display(getAllIngredient());
    }
    
    public void checkStock() { 
        StringBuilder lowStockIngredients = new StringBuilder();
        // Iterate through the productList and check stock levels
        for (int i = 1; i <= productList.getNumberOfEntries(); i++) {
            Ingredient ingredient = productList.getEntry(i);
            // Check if the stock is below the LOW_STOCK_THRESHOLD
            if (ingredient.getCurrentStock() < LOW_STOCK_THRESHOLD) {
                lowStockIngredients.append(ingredient.getName())
                                    .append(" - ")
                                    .append(ingredient.getCurrentStock())
                                    .append(" in stock\n");
            }
        }
        // If there are ingredients with low stock, alert the user
        if (lowStockIngredients.length() > 0) {
            inventoryManagerUI.display("Low stock alert:\n" + lowStockIngredients.toString());
        } else {
            inventoryManagerUI.display("All ingredients are sufficiently stocked.");
        }
    }

    
    public void reStock() {
        // Get the name of the ingredient to restock
        String ingredientName = inventoryManagerUI.getIngredientName();
        // Get the quantity to add
        int quantityToAdd = inventoryManagerUI.getRestockQuantity();

        boolean ingredientFound = false;

        // Iterate through the productList to find the ingredient
        for (int i = 1; i <= productList.getNumberOfEntries(); i++) {
            Ingredient ingredient = productList.getEntry(i);
            if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                ingredientFound = true;
                // Update the stock quantity
                ingredient.setCurrentStock(ingredient.getCurrentStock() + quantityToAdd);
                // Save the updated product list to the file
                ingredientDAO.saveToFile(productList);
                inventoryManagerUI.display("Successfully restocked " + quantityToAdd + " units of " + ingredientName + ".");
                break;
            }
        }

        if (!ingredientFound) {
            inventoryManagerUI.display("Ingredient not found in the inventory.");
        }
    }

    public void runInventoryManager() {
        int choice = 0;
        do {
            choice = inventoryManagerUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    addNewIngredient();
                    break;
                case 2:
                    displayInventory();
                    break;
                case 3:
                    checkStock();
                    break;
                case 4:
                    reStock();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu");
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    System.out.println();
            }
        } while (choice != 5);
    }

    public void runInventoryManager1() {
        int choice = -1;  // Initialize choice variable with invalid default
        do {
            try {
                // Display the menu
                System.out.println("--- Inventory Management ---");
                System.out.println("1. Add New Ingredient");
                System.out.println("2. Display Inventory");
                System.out.println("3. Checking Low Stock Inventory");
                System.out.println("4. Restock Inventory Item");
                System.out.println("5. Back");
                System.out.print("Enter Your Choice (1-5): ");

                // Check if the input is an integer
                if (!inventoryManagerUI.getScanner().hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    inventoryManagerUI.getScanner().nextLine();  // Clear invalid input
                    continue;  // Restart the loop
                }

                // Retrieve the choice
                choice = inventoryManagerUI.getScanner().nextInt();
                inventoryManagerUI.getScanner().nextLine();  // Clear the newline character left in the buffer

                // Validate range
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    continue;  // Restart the loop
                }

                // Process the valid menu choice
                switch (choice) {
                    case 1:
                        addNewIngredient();
                        break;
                    case 2:
                        displayInventory();
                        break;
                    case 3:
                        checkStock();
                        break;
                    case 4:
                        reStock();
                        break;
                    case 5:
//                        back();  // Call the back method
                        return;  // Exit the method to return to the main menu
                    default:
                        System.out.println("Unexpected error.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                inventoryManagerUI.getScanner().nextLine();  // Clear invalid input
            }
        } while (choice != 0);
    }
    
    // Retrieve an ingredient by its name
    public Ingredient getIngredientByName(String name) {
        for (int i = 1; i <= productList.getNumberOfEntries(); i++) {
            Ingredient ingredient = productList.getEntry(i);
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient; // Found the ingredient
            }
        }
        return null; // Ingredient not found
    }

    // Save the current inventory state to a file
    public void saveInventory() {
        ingredientDAO.saveToFile(productList); // Save the productList back to the file
    }

    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.runInventoryManager();
    }

    private void populateMapFromList() { // ONLY USED ONCE
        for (int i = 1; i <= productList.getNumberOfEntries(); i++) {
            Ingredient ing = productList.getEntry(i);
            // Assuming ing.getCode() returns the code string
            // If there's a chance of duplicates already in the list,
            // you might handle that here. Otherwise, just put them directly.
            ingredientMap.put(ing.getCode(), ing);
        }
        ingredientMapDAO.saveToFile(ingredientMap);
        System.out.println("DONE");
    }
}
