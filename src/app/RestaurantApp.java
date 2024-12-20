package app;

import adt.*;
import control.*;

import java.util.Scanner;

public class RestaurantApp {
    private Scanner scanner = new Scanner(System.in);
    private InventoryManager inventoryManager = InventoryManager.getInstance();
    private OrderManager orderManager = new OrderManager();
    private BillingManager billingManager = new BillingManager();
    private BillingMaintenance billingMaintenance = new BillingMaintenance();
    private ReservationManager reservationManager = new ReservationManager(orderManager);

    public static void main(String[] args) {
        RestaurantApp restaurantApp = new RestaurantApp();
        restaurantApp.runRestaurantApp();
    }

    public void runRestaurantApp() {
        int choice = 0;
        boolean isExceptionFound;
        do {
            try {
                System.out.println("--- Restaurant Management System ---");
                System.out.println("1. Order Processing Management");
                System.out.println("2. Inventory Management");
                System.out.println("3. Billing Management");
                System.out.println("4. Reservation & Waitlist Management");
                System.out.println("5. Restaurant Maintenance");
                System.out.println("6. Exit System");
                System.out.print("Enter Your Choice (1-5) >> ");
                choice = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                isExceptionFound = true;
                choice = 0;
                scanner.nextLine();
                System.out.println();
                System.out.println("Invalid Input. Enter A Whole Number As Your Choice.");
                System.out.println();
            }
            scanner.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    orderManager.runOrderManager();
                    break;
                case 2:
                    inventoryManager.runInventoryManager();
                    break;
                case 3:
                    billingMaintenance.runBillingMaintenance();
                    break;
                case 4:
                    reservationManager.runReservationManager();
                    break;
                case 5:
                    runRestaurantMaintenance();
                    break;
                case 6:
                    System.out.println("Exiting");
                    System.out.println();
                    break;
                default:
                    System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-6.");
                    System.out.println();
            }

        } while (choice != 6);
    }

    private void runRestaurantMaintenance() {
        int choice = 0;
        do {
            choice = getMaintenanceChoice();
            switch (choice) {
                case 1:
                    MenuMaintenance.getInstance().runMenuMaintenance();
                    break;
                case 2:
                    TableMaintenance.getInstance().runTableMaintenance();
                    break;
                case 3:
                    System.out.println("Unsupported");
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Unsupported");
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Exiting");
                    System.out.println();
                    break;
                default:
                    System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-6.");
                    System.out.println();
            }
        } while (choice != 5);
    }

    private int getMaintenanceChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("--- Restaurant Maintenance ---");
                System.out.println("1. Menu Maintenance");
                System.out.println("2. Table Maintenance");
                System.out.println("3. xxxxxxxxxxxxxxxxx");
                System.out.println("4. xxxxxxxxxxxxxxxxx");
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
}
