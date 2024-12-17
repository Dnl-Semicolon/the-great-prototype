package control;

import adt.ListInterface;
import boundary.CreateOrderUI;
import entity.MenuItem;
import entity.Order;
import entity.OrderItem;
import entity.Table;
import java.util.Scanner;

public class CreateOrder {
    private CreateOrderUI createOrderUI;
    private MenuMaintenance menuMaintenance;
    private TableMaintenance tableMaintenance;
    private Table table;
    private Order order;

    public CreateOrder() {
        createOrderUI = new CreateOrderUI();
        menuMaintenance = new MenuMaintenance();
        tableMaintenance = new TableMaintenance();
        table = new Table();
        order = new Order();
    }

    public void viewMenu() {
        menuMaintenance.viewMenu();
    }

    public void addItemToOrder(MenuItem menuItem, int quantity) {
        OrderItem orderItem = new OrderItem(menuItem, quantity);
        order.getOrderItems().add(orderItem);
        table.getOrders().add(order);
        order = new Order();
    }

    public void handleAddItemToOrder() {
        viewMenu();
        MenuItem selectedMenuItem = selectMenuItem();
        if (selectedMenuItem != null) {
            int quantity = enterQuantity(selectedMenuItem.getName());
            if (quantity > 0) {
                addItemToOrder(selectedMenuItem, quantity);
                System.out.println("[Success] Added " + quantity + "x " + selectedMenuItem.getName() + " to the order.");
                System.out.println();
            }
        }
    }

    private MenuItem selectMenuItem() {
        Scanner scanner = new Scanner(System.in);
        ListInterface<MenuItem> menu = menuMaintenance.getMenuList();
        MenuItem selectedItem = new MenuItem();

        while (true) {
            System.out.print("Enter the Item ID to add to the order: >> ");
            String choice = scanner.nextLine().trim();
            System.out.println();

            try {
                int index = Integer.parseInt(choice);
                if (index >= 1 && index <= menu.getNumberOfEntries()) {
                    selectedItem = menu.getEntry(index);
                    break;
                } else {
                    System.out.println("Invalid selection. Please enter a number between 1 and " + menu.getNumberOfEntries() + ".");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return selectedItem;
    }

    private int enterQuantity(String itemName) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the quantity for " + itemName + ": >> ");
            String input = scanner.nextLine().trim();
            System.out.println();
            try {
                int quantity = Integer.parseInt(input);
                if (quantity > 0) {
                    return quantity;
                } else {
                    System.out.println("Quantity must be greater than zero. Try again.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: Not a valid quantity. Enter a whole number.");
            }
        }
    }

    public void displayTitle() {
        StringBuilder outputStr = new StringBuilder();
        outputStr.append(String.format("--- Create Order: Table %-2d---", table.getTableNo()));
        createOrderUI.displayTitle(outputStr);
    }

    public void runCreateOrder() {
        table = tableMaintenance.getListOfTables().getEntry(5);
        if (table == null) {
            return;
        }
        order.setTableNo(table.getTableNo());
        int choice = 0;
        do {
            displayTitle();
            choice = createOrderUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    handleAddItemToOrder();
                    break;
                default:
                    System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-5.");
                    System.out.println();
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        CreateOrder createOrder = new CreateOrder();
        createOrder.runCreateOrder();
    }
}
