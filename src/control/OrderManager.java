package control;

import adt.*;
import boundary.*;
import dao.*;
import entity.*;

import java.util.Scanner;

public class OrderManager {

    private MenuMaintenance menuMaintenance;
    private ListInterface<MenuItem> menu;

    private TableMaintenance tableMaintenance;
    private ListInterface<Table> tables;

    private MenuItemDAO menuItemDAO = new MenuItemDAO();
    private TableDAO tableDAO = TableDAO.getInstance();
    private OrderManagerUI orderManagerUI = new OrderManagerUI();
    private InventoryManager inventoryManager;

    public OrderManager() {
        menuMaintenance = MenuMaintenance.getInstance();
        menu = menuMaintenance.getMenuList();
        tableMaintenance = TableMaintenance.getInstance();
        tables = tableMaintenance.getListOfTables();
        inventoryManager = InventoryManager.getInstance();
    }

    public void runOrderManager() {
        int choice = 0;
        do {
            choice = orderManagerUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    cancelOrder();
                    break;
                case 4:
                    System.out.println("Returning to Main Menu");
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid Choice. Enter A Choice From 1-4.");
            }
        } while (choice != 4);
    }

    public void cancelOrder() {
        int choice = inputTableChoice();
        Table currentTable = null;
        boolean found = false;
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            currentTable = tables.getEntry(i);
            if (choice == currentTable.getTableNo()) {
                found = true;
                cancelOrderForATable(choice);
                break;
            }
        }

        if (!found) {
            handleFailedToViewOrders();
        }
    }

    private void handleFailedToGetOrderItem() {
        System.out.println("Failed to Get Order Item to Cancel from Order");
    }

    private void cancelOrderForATable(int tableNumber) {
        Scanner scanner = new Scanner(System.in);
        Table currentTable = tables.getEntry(tableNumber);
        if (currentTable == null) {
            System.out.println("Failed to get Table " + tableNumber);
            return;
        }
        if (!currentTable.isOrderListEmpty()) {
            displayOrdersForATable(currentTable);
            System.out.println("End of Orders");
            System.out.println();

            ListInterface<OrderItem> orderList = currentTable.getOrderList();
            OrderItem removedOrderItem = null;
            int choice = 0;
            do {
                choice = inputOrderItemChoice();

                if (choice > 0 && choice <= orderList.getNumberOfEntries()) {
                    removedOrderItem = orderList.remove(choice);
                    System.out.println(removedOrderItem.getMenuItem().getName() + " has been removed.");
                    tableMaintenance.save();
                    System.out.println();
                } else if (choice == 0) {
                    System.out.println("Back to Main Menu");
                    System.out.println();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
                }

            } while (choice != 0);


        } else {
            handleTableEmptyOrderList();
        }
    }


    private int inputOrderItemChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.print("Enter the number of the item to remove (or 0 to exit): ");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                choice = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Invalid input. Enter A Whole Number As Choice.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void viewOrders() {
        int choice = inputTableChoice();
        Table currentTable = null;
        boolean found = false;
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            currentTable = tables.getEntry(i);
            if (choice == currentTable.getTableNo()) {
                found = true;
                viewOrdersForATable(choice);
                break;
            }
        }

        if (!found) {
            handleFailedToViewOrders();
        }
    }

    public void viewOrdersForATable(int tableNumber) {
        Scanner scanner = new Scanner(System.in);
        Table currentTable = tables.getEntry(tableNumber);
        if (currentTable == null) {
            System.out.println("Failed to get Table " + tableNumber);
            return;
        }
        if (!currentTable.isOrderListEmpty()) {
            displayOrdersForATable(currentTable);
            System.out.println("End of Orders");
            System.out.println();
            System.out.print("Press ENTER to continue");
            scanner.nextLine();
            System.out.println();
        } else {
            handleTableEmptyOrderList();
        }
    }

    private void displayOrdersForATable(Table currentTable) {
        ListInterface<OrderItem> currentOrderList = currentTable.getOrderList();
        OrderItem orderItem = null;
        System.out.println(" ID    Name                  Qty");
        for (int i = 1; i <= currentOrderList.getNumberOfEntries(); i++) {
            orderItem = currentOrderList.getEntry(i);
            System.out.printf("%3d.   ", i);
            System.out.println(orderItem);
        }
        System.out.println();
    }

    private void handleTableEmptyOrderList() {
        System.out.println("This table has not placed any orders.");
        System.out.println();
    }

    private void handleFailedToViewOrders() {
        System.out.println("Table does not exist.");
        System.out.println();
    }

    private int inputTableChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.print("Enter the Table Number: ");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                choice = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Invalid input. Enter A Whole Number As Choice.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    private int inputMenuItemChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.print("Enter the ID of the menu item to add to order (or 0 to exit): ");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                choice = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Invalid input. Enter A Whole Number As Choice.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    private int inputMenuItemQty() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.print("PROMPT");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                choice = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Invalid input. Enter A Whole Number As Choice.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    private void handleNoMenuToPlaceOrder() {
        System.out.println("No Menu Items To Place Order");
        System.out.println();
    }

    private void handleFailedToCreateOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Failed to Create Order. Going back to Main Menu");
        System.out.println();
        System.out.print("Press ENTER to continue");
        scanner.nextLine();
        System.out.println();
    }

    public boolean isMenuEmpty() {
        if (menu == null || menu.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void placeOrder() {
        if (!isMenuEmpty()) {
            int tableNumber = 0;
            tableMaintenance.displayAllTables();
            tableNumber = orderManagerUI.getTableNumber();
            boolean added = createOrder(tableNumber);
            if (!added) {
                handleFailedToCreateOrder();
            }
        } else {
            handleNoMenuToPlaceOrder();
        }
    }

    public void displayMenu() {
        menuMaintenance.viewMenu();
    }

    private boolean createOrder(int tableNumber) {
        Scanner scanner = new Scanner(System.in);
        int num = 0;

        Table currentTable = tableMaintenance.getCurrentTable(tableNumber);
        if (currentTable == null) {
            return false;
        }

        displayMenu();

        //Enter the ID of the menu item to add to order (or 0 to exit):
        int choice = 0;
        do {
            choice = inputMenuItemChoice(); // could handle exceptions from letters
            if (choice > 0 && choice <= menu.getNumberOfEntries()) {
                processMenuItemSelection(currentTable, choice);
            } else if (choice == 0) {
                finalizeOrder(currentTable);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        tableMaintenance.save();
        return true;
    }

    private void processMenuItemSelection(Table currentTable, int menuItemId) {
        MenuItem selectedMenuItem = menu.getEntry(menuItemId);
        if (selectedMenuItem == null) {
            System.out.println("Menu item not found. Please select another item.");
            return;
        }

        int quantity = inputMenuItemQuantity();

        if (!selectedMenuItem.canFulfillOrder(quantity)) {
            System.out.println("Insufficient stock to fulfill this order. Please choose another item or adjust quantity.");
            return;
        }

        addMenuItemToOrder(currentTable, selectedMenuItem, quantity);
    }

    private void deductInventory(OrderItem orderItem) {
        for (int i = 1; i <= orderItem.getMenuItem().getRecipe().getNumberOfEntries(); i++) {
            Ingredient ingredient = orderItem.getMenuItem().getRecipe().getEntry(i);
            int totalDeduction = orderItem.getQuantity();
            ingredient.reduceStock(totalDeduction);
        }
    }

    /**
     * Adds the specified MenuItem and quantity to the current table's order.
     */
    private void addMenuItemToOrder(Table currentTable, MenuItem selectedMenuItem, int quantity) {
        OrderItem newOrderItem = new OrderItem(selectedMenuItem, quantity);
        boolean added = handleAddMenuItemToTable(currentTable, newOrderItem);
        if (added) {
            System.out.println(quantity + " x " + selectedMenuItem.getName() + " successfully added to order.");
            System.out.println();
        } else {
            handleFailedToCreateOrder();
        }
    }

    /**
     * Finalizes the order, saves the table state, and displays the final order to the user.
     */
    private void finalizeOrder(Table currentTable) {
        for (int i = 1; i <= currentTable.getOrderList().getNumberOfEntries(); i++) {
            OrderItem orderItem = currentTable.getOrderList().getEntry(i);
            deductInventory(orderItem);
        }
        System.out.println("Order Placed");
        System.out.println();
        displayCurrentOrder(currentTable);
        tableMaintenance.save();
        //Order finalized and inventory updated
    }

    /**
     * Displays the current order items along with their quantities and totals.
     */
    private void displayCurrentOrder(Table currentTable) {
        // Assuming you have a method getOrderItems() that returns a list of OrderItem objects.
        ListInterface<OrderItem> orderItems = currentTable.getOrderList();
        if (orderItems == null || orderItems.isEmpty()) {
            System.out.println("No items in the order.");
            return;
        }

        System.out.println("Current Order for Table " + currentTable.getTableNo() + ":");
        System.out.println("-----------------------------------------------------");
        double total = 0.0;
        for (int i = 1; i <= orderItems.getNumberOfEntries(); i++) {
            OrderItem item = orderItems.getEntry(i);
            double itemTotal = item.getQuantity() * item.getMenuItem().getPrice();
            total += itemTotal;
            System.out.println(item.getQuantity() + " x "
                    + item.getMenuItem().getName()
                    + " @ " + item.getMenuItem().getPrice()
                    + " each = " + itemTotal);
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("Total: " + total);
        System.out.println();
    }

    private boolean createOrderBACKUP(int tableNumber) {
        Scanner scanner = new Scanner(System.in);
        int num = 0;

        Table currentTable = tableMaintenance.getCurrentTable(tableNumber);
        if (currentTable == null) {
            return false;
        }

        displayMenu();

        //Enter the ID of the menu item to add to order (or 0 to exit):
        int choice = 0;
        do {
            choice = inputMenuItemChoice(); // could handle exceptions from letters
            if (choice > 0 && choice <= menu.getNumberOfEntries()) {
                MenuItem selectedMenuItem = menu.getEntry(choice);
                if (selectedMenuItem == null) {
                    return false;
                }
                OrderItem newOrderItem = new OrderItem(selectedMenuItem, 1);
                boolean added = handleAddMenuItemToTable(currentTable, newOrderItem);
                if (added) {
                    System.out.println(selectedMenuItem.getName() + " successfully added to order.");
                    System.out.println();
                } else {
                    handleFailedToCreateOrder();
                }
            } else if (choice == 0) {
                System.out.println("Order Placed");
                System.out.println();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        tableMaintenance.save();
        return true;
    }

    private boolean handleAddMenuItemToTable(Table currentTable, OrderItem newOrderItem) {
        return currentTable.addToOrderList(newOrderItem);
    }

    public void createOrder1() {
        int tableNumber = 0;
        tableNumber = orderManagerUI.getTableChoice(getOccupiedTables());
        Order newOrder = new Order(tableNumber);
        boolean addingItems = true;
        while (addingItems) {
            orderManagerUI.displayMenu(getMenu());
            int menuItemChoice = orderManagerUI.getMenuItemChoice();
            MenuItem chosenMenuItem = menu.getEntry(menuItemChoice);
//            newOrder.orderItems.add(chosenMenuItem);
            addingItems = orderManagerUI.confirmAddAnotherItem(chosenMenuItem);
        }
        orderManagerUI.displayOrderCreationComplete(tableNumber);
        Table table = tables.getEntry(tableNumber);
        table.orders.add(newOrder);
        orderManagerUI.displayOrderAddedSuccessfully(tableNumber);
    }

    /**
     * Prompts the user to enter the quantity for the chosen item.
     */
    private int inputMenuItemQuantity() {
        Scanner scanner = new Scanner(System.in);

        int quantity = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.print("Enter quantity: ");
                quantity = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                quantity = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Invalid input. Please enter a positive number.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return inputMenuItemQuantity();
        }
        return quantity;
    }

    public String getMenu() {
        StringBuilder outputStr = new StringBuilder();
        outputStr.append("Menu:\n");
        outputStr.append("====================================================\n");
        outputStr.append(String.format("%-5s %-20s %10s\n", "No.", "Name", "Price"));
        outputStr.append("----------------------------------------------------\n");

        for (int i = 1; i <= menu.getNumberOfEntries(); i++) {
            MenuItem item = menu.getEntry(i);
            outputStr.append(String.format("%-5d %-20s %10.2f\n", i, item.name, item.price));
        }

        outputStr.append("====================================================\n");
        return outputStr.toString();
    }


    private String getOccupiedTables() {
        StringBuilder outputStr = new StringBuilder();
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            Table table = tables.getEntry(i);
            if (table.customer != null) {
                outputStr.append(i);
                outputStr.append(". ");
                outputStr.append(table);
                outputStr.append("\n");
            }
        }
        return outputStr.toString();
    }

    public String getUnoccupiedTables() {
        StringBuilder outputStr = new StringBuilder();
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            Table table = tables.getEntry(i);
            outputStr.append(i);
            outputStr.append(". ");
            outputStr.append(table);
            outputStr.append("\n");
        }
        return outputStr.toString();
    }

    public static void main(String[] args) {
        OrderManager orderManager = new OrderManager();
        orderManager.runOrderManager();
    }
}
