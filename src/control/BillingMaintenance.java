package control;

import adt.ListInterface;
import entity.Bill;
import entity.OrderItem;
import entity.Table;

import java.util.Scanner;

public class BillingMaintenance {
    private Scanner scanner = new Scanner(System.in);
    private TableMaintenance tableMaintenance = TableMaintenance.getInstance();
    private ListInterface<Table> tables = tableMaintenance.getListOfTables();
    private Table selectedTable = null;
    private BillingManager billingManager = new BillingManager();

    public BillingMaintenance() {}

    public void runBillingMaintenance() {
        int choice = 0;
        do {
            choice = getMainMenuChoice();
            switch (choice) {
                case 1:
                    processPayment();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
            }
        } while (choice != 0);
    }

    private void processPayment() {
        if (tables.isEmpty()) {
            handleNoTables();
            return;
        }

        int choice = 0;

        do {
            choice = getTableNumberChoice();

            if (choice == 0) {
                System.out.println("Back to Main Menu");
                System.out.println();
            } else if (choice > 0 && choice <= tables.getNumberOfEntries()) {
                this.selectedTable = tables.getEntry(choice);
                System.out.println("Table " + this.selectedTable.getTableNo() + " Selected");
                System.out.println();
                performBillManager();
                System.out.println("END");
            } else {
                System.out.println("Invalid choice. Please try again.");
                System.out.println();
            }

        } while (choice != 0);
    }

    private void performBillManager() {
        for (int i = 1; i <= this.selectedTable.getOrderList().getNumberOfEntries(); i++) {
            OrderItem orderItem = this.selectedTable.getOrderList().getEntry(i);
            String field1 = orderItem.getMenuItem().getName();
            int field2 = orderItem.getQuantity();
            double field3 = orderItem.getMenuItem().getPrice();
            billingManager.itemsList.add(new Bill(field1, field2, field3));
        }
        billingManager.calculateBill();
    }

    private void performSummary() {
        System.out.println("=====================================================");
        System.out.println("               Order Summary for Table " + this.selectedTable.getTableNo());
        System.out.println("=====================================================");
        double total = 0.0;
        ListInterface<OrderItem> orderList = this.selectedTable.getOrderList();
        for (int i = 1; i <= orderList.getNumberOfEntries(); i++) {
            OrderItem item = orderList.getEntry(i);
            double itemTotal = item.getQuantity() * item.getMenuItem().getPrice();
            total += itemTotal;
            System.out.printf("%-3d x %-20s @ $%.2f each = $%.2f\n",
                    item.getQuantity(),
                    item.getMenuItem().getName(),
                    item.getMenuItem().getPrice(),
                    itemTotal);
        }
        System.out.println("-----------------------------------------------------");
        System.out.printf("Total: $%.2f\n", total);
        System.out.println("=====================================================");
        System.out.println();

        int paymentChoice = 0;
        while (true) {
            paymentChoice = getPaymentMethodChoice();

            switch (paymentChoice) {
                case 1:
                    System.out.println("Processing Cash Payment...");
                    // Add logic for handling cash payment if needed
                    break;
                case 2:
                    System.out.println("Processing Card Payment...");
                    // Add logic for handling card payment if needed
                    break;
                default:
                    System.out.println("Invalid choice. Returning to Billing Management Menu...");
            }
        }
    }

    public int getPaymentMethodChoice() {
        int choice;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("Select Payment Method:");
                System.out.println("1. Cash");
                System.out.println("2. Card");
                System.out.print("Enter choice (1 or 2): ");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                isExceptionFound = true;
                scanner.nextLine();
                choice = 0;
                System.out.println();
                System.out.println("Invalid Choice. Enter A Whole Number");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    private int getTableNumberChoice() {
        int choice;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("=========== Payment Process ===========");
                System.out.println("Step 1: Select the table you want to bill.");
                displayAllTables();
                System.out.println("0. Return to Billing Management Menu");
                System.out.println("=======================================");
                System.out.print("Enter the table number (e.g., 1): ");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                isExceptionFound = true;
                scanner.nextLine();
                choice = 0;
                System.out.println();
                System.out.println("Invalid Choice. Enter A Whole Number");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void displayAllTables() {
        tableMaintenance.displayAllTables();
    }

    private void handleNoTables() {
        System.out.println("No tables to bill.");
        System.out.println();
    }

    private int getMainMenuChoice() {
        int choice;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("========== Billing Management =========");
                System.out.println("1. Process Payments");
                System.out.println("2. View Sales Reports");
                System.out.println("0. Return to main menu");
                System.out.println("=======================================");
                System.out.print("Enter choice (0-2): ");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                isExceptionFound = true;
                scanner.nextLine();
                choice = 0;
                System.out.println();
                System.out.println("Invalid Choice. Enter A Whole Number");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return choice;
    }
}
