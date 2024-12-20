package control;

import adt.ArrayList;
import adt.ListInterface;
import adt.MapInterface;
import boundary.MenuMaintenanceUI;
import dao.MenuDAO;
import entity.Ingredient;
import entity.MenuItem;
import java.util.Scanner;

public class MenuMaintenance {

	private static MenuMaintenance singletonInstance;

	private MenuMaintenanceUI menuMaintenanceUI;
	private MenuDAO menuDAO;
	private ListInterface<MenuItem> menu;
	private MenuItem selectedMenuItem;

	private ListInterface<Ingredient> productList;
	private MapInterface<String, Ingredient> ingredientMap;


	private MenuMaintenance() {
		menuMaintenanceUI = new MenuMaintenanceUI();
		menuDAO = MenuDAO.getInstance();
		menu = menuDAO.retrieveFromFile();
		selectedMenuItem = null;
		productList = InventoryManager.getInstance().getProductList();
		ingredientMap = InventoryManager.getInstance().getIngredientMap();
	}

	public static MenuMaintenance getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new MenuMaintenance();
		}
		return singletonInstance;
	}

	public ListInterface<MenuItem> getMenuList() {
		return menu;
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
				displayMenu();
				System.out.println("Search Menu Items");
				System.out.println("Enter a keyword to filter menu items (or type 'exit' to return to the main menu):");
				System.out.print("> ");
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

	public void viewMenu() {
		if (!menu.isEmpty()) {
			menuMaintenanceUI.viewMenuItems(getMenuItems());
		} else {
			menuMaintenanceUI.displayEmptyMenuMessage();
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

	public void searchMenuItemOrRecipeUpdate() {
		this.selectedMenuItem = searchMenuItem();
		if (this.selectedMenuItem == null) {
			System.out.println("Returning to Main Menu");
			System.out.println();
			return;
		}
		viewRecipe();
		if (selectedMenuItem.getRecipe().isEmpty()) {
			System.out.println("This item currently has no associated recipe and cannot be ordered until a recipe is added.");
			System.out.println();
			addRecipeChoice();
		} else {
			recipeUpdateChoice();
		}
	}

	private void viewRecipe() {
		String recipeStr = this.selectedMenuItem.printRecipe();
		System.out.println("Current Recipe: " + recipeStr);
		System.out.println();
	}

	public void showRecipe(MenuItem selectMenuItem) {
		String recipeStr = selectMenuItem.printRecipe();
		System.out.println("Current Recipe: " + recipeStr);
		System.out.println();
	}

	private void addRecipeChoice() {
		int choice = 0;
		boolean moveToUpdateMenu = false;
		do {
			if (!selectedMenuItem.getRecipe().isEmpty()) {
				moveToUpdateMenu = true;
				break;
			}
			choice = getAddRecipeChoice();
			switch (choice) {
				case 1:
					addIngredientToRecipe();
					break;
				case 2:
					addMultipleIngredientsToRecipe();
					break;
				case 3:
					System.out.println("Returning to Main Menu.");
					System.out.println();
					break;
				default:
					System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-2.");
					System.out.println();
			}
		} while (choice != 3);
		if (moveToUpdateMenu) {
			recipeUpdateChoice();
		}
	}

	private void recipeUpdateChoice() {
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		do {
			choice = getRecipeUpdateChoice();
			switch (choice) {
				case 1:
					viewRecipe();
					System.out.print("Press ENTER to continue");
					scanner.nextLine();
					System.out.println();
					break;
				case 2:
					addIngredientToRecipe();
					break;
				case 3:
					addMultipleIngredientsToRecipe();
					break;
				case 4:
					removeIngredientFromRecipe();
					break;
				case 5:
					System.out.println("Returning to Main Menu.");
					System.out.println();
					break;
				default:
					System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-4.");
					System.out.println();
			}
		} while (choice != 5);
	}

	private void addIngredientToRecipe() {
		Scanner scanner = new Scanner(System.in);
		ListInterface<Ingredient> recipe = selectedMenuItem.getRecipe();
		// Assuming you have:
		// Map<String, Ingredient> ingredientMap
		// List<Ingredient> recipe
		// Scanner scanner
		// String menuItemName - the name of the current menu item you are editing

		String display = "";
		for (int i = 1; i <= productList.getNumberOfEntries(); i++) {
			display += productList.getEntry(i) + "\n";
		}
		System.out.println("List of Products:\n" + display);

		System.out.print("Enter Ingredient Code to Add: ");
		String codeToAdd = scanner.nextLine().trim();
		System.out.println();
		// Check if the ingredient code exists in the map
		if (!ingredientMap.containsKey(codeToAdd)) {
			System.out.println("No ingredient found with code " + codeToAdd + ".");
			System.out.println();
		} else {
			// Ingredient found, get its details
			Ingredient ing = ingredientMap.get(codeToAdd);
			System.out.print("Ingredient '" + ing.getName() + "' found. Add to recipe? (y/n): ");
			String confirmation = scanner.nextLine().trim().toLowerCase();
			System.out.println();

			if (confirmation.equals("y")) {
				// Check if ingredient is already in the recipe to avoid duplicates if needed
				if (recipe.contains(ing)) {
					System.out.println("This ingredient is already in the recipe.");
				} else {
					recipe.add(ing);
					System.out.println(ing.getName() + " added to " + selectedMenuItem.getName() + " recipe.");
					menuDAO.saveToFile(menu);
					System.out.println();
				}
			} else {
				System.out.println("Addition canceled.");
			}
		}

	}

	private void addMultipleIngredientsToRecipe() {
		Scanner scanner = new Scanner(System.in);
		ListInterface<Ingredient> recipe = selectedMenuItem.getRecipe();
		// Assuming:
		// Map<String, Ingredient> ingredientMap
		// ListInterface<Ingredient> productList (or some list of products)
		// Scanner scanner
		// String menuItemName - the name of the current menu item

		// Display all available products to the user
		StringBuilder display = new StringBuilder();
		for (int i = 1; i <= productList.getNumberOfEntries(); i++) {
			display.append(productList.getEntry(i)).append("\n");
		}
		System.out.println("List of Products:\n" + display);

		System.out.println("Enter multiple Ingredient Codes separated by a comma (e.g. ING1,ING2,ING3):");
		System.out.print("Ingredient Codes: ");
		String line = scanner.nextLine().trim();

		// Split by comma
		String[] codesToAdd = line.split(",");

		// Keep track of successes and failures for reporting at the end
		int addedCount = 0;
		ListInterface<String> alreadyInRecipe = new ArrayList<>();
		ListInterface<String> notFound = new ArrayList<>();

		for (String code : codesToAdd) {
			String trimmedCode = code.trim();

			if (!ingredientMap.containsKey(trimmedCode)) {
				// Ingredient not found
				notFound.add(trimmedCode);
			} else {
				// Ingredient found
				Ingredient ing = ingredientMap.get(trimmedCode);
				if (recipe.contains(ing)) {
					// Ingredient already in the recipe
					alreadyInRecipe.add(ing.getName());
				} else {
					// Confirm addition
					System.out.println();
					System.out.print("Ingredient '" + ing.getName() + "' found. Add to recipe? (y/n): ");
					String confirmation = scanner.nextLine().trim().toLowerCase();
					System.out.println();

					if (confirmation.equals("y")) {
						recipe.add(ing);
						addedCount++;
						System.out.println(ing.getName() + " added to " + selectedMenuItem.getName() + " recipe.");
					} else {
						System.out.println("Addition canceled for " + ing.getName() + ".");
					}
				}
			}
		}

		// After processing all codes:
		menuDAO.saveToFile(menu);

		// Provide a summary
		System.out.println();
		if (addedCount > 0) {
			System.out.println(addedCount + " ingredient(s) successfully added.");
		}
		if (!alreadyInRecipe.isEmpty()) {
			System.out.println("These ingredients were already in the recipe: " + String.join((CharSequence) ", ", (CharSequence) alreadyInRecipe));
		}
		if (!notFound.isEmpty()) {
			System.out.println("No ingredient found with code(s): " + String.join((CharSequence) ", ", (CharSequence) notFound));
		}

		System.out.println("Process completed.\n");
	}


	private void removeIngredientFromRecipe() {
	}

	public int getRecipeUpdateChoice() {
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		boolean isExceptionFound = false;
		do {
			try {
				System.out.println("Currently Selected Menu Item: " + selectedMenuItem.getName());
				System.out.println("--- Recipe Update Options ---");
				System.out.println("1. View Recipe");
				System.out.println("2. Add Ingredient to Recipe");
				System.out.println("3. Add Multiple Ingredients to Recipe at once");
				System.out.println("4. Remove Ingredient from Recipe");
				System.out.println("5. Return to Main Menu");
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

	public int getAddRecipeChoice() {
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		boolean isExceptionFound = false;
		do {
			try {
				System.out.println("Currently Selected Menu Item: " + selectedMenuItem.getName());
				System.out.println("--- Recipe Update Options ---");
				System.out.println("1. Add Ingredient to Recipe");
				System.out.println("2. Add Multiple Ingredients to Recipe at once");
				System.out.println("3. Return to Main Menu");
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
					searchMenuItemOrRecipeUpdate();
					break;
				case 4:
					deleteMenuItem();
					break;
				case 5:
					System.out.println("Exiting System...");
					System.out.println();
					break;
				default:
					System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-5.");
					System.out.println();
			}
		} while (choice != 5);
	}



	public static void main(String[] args) {
		MenuMaintenance menuMaintenance = new MenuMaintenance();
		menuMaintenance.runMenuMaintenance();
	}
}
