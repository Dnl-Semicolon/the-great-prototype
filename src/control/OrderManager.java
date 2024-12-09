package control;

import adt.*;
import boundary.*;
import dao.*;
import entity.*;

public class OrderManager {

    private ListInterface<MenuItem> menu;
    public ListInterface<Table> tables;
    private MenuItemDAO menuItemDAO = new MenuItemDAO();
    private TableDAO tableDAO = new TableDAO();
    private OrderManagerUI orderManagerUI = new OrderManagerUI();
    private InventoryManager inventoryManager;

    public OrderManager(InventoryManager inventoryManager) {
        menu = menuItemDAO.initMenuItems();
        tables = tableDAO.initTables();
    }

    public void runOrderManager() {
        int choice = 0;
        do {
            choice = orderManagerUI.getMenuChoice();
            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        } while (choice != 0);
    }

    public void createOrder() {
        int tableNumber = 0;
        tableNumber = orderManagerUI.getTableChoice(getOccupiedTables());
        Order newOrder = new Order(tableNumber);
        boolean addingItems = true;
        while (addingItems) {
            orderManagerUI.displayMenu(getMenu());
            int menuItemChoice = orderManagerUI.getMenuItemChoice();
            MenuItem chosenMenuItem = menu.getEntry(menuItemChoice);
            newOrder.orderItems.add(chosenMenuItem);
            addingItems = orderManagerUI.confirmAddAnotherItem(chosenMenuItem);
        }
        orderManagerUI.displayOrderCreationComplete(tableNumber);
        Table table = tables.getEntry(tableNumber);
        table.orders.add(newOrder);
        orderManagerUI.displayOrderAddedSuccessfully(tableNumber);
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


}
