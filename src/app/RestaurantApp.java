package app;

import adt.*;
import control.InventoryManager;
import control.OrderManager;
import control.ReservationManager;
import java.util.Scanner;

public class RestaurantApp {
    private Scanner scanner = new Scanner(System.in);
    private InventoryManager inventoryManager = new InventoryManager();
    private OrderManager orderManager = new OrderManager(inventoryManager);
    private ReservationManager reservationManager = new ReservationManager(orderManager);
    public static void main(String[] args) {
        new RestaurantApp().runRestaurantApp();
    }

    public void runRestaurantApp() {
        int choice = 0;
        do {
            System.out.println("--- Restaurant Management System ---");
            System.out.println("1. Order Processing Management");
            System.out.println("2. Inventory Management");
            System.out.println("3. Billing Management");
            System.out.println("4. Reservation & Waitlist Management");
            System.out.println("5. Exit System");
            System.out.print("Enter Your Choice (1-5) >> ");
            choice = scanner.nextInt();
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
                    break;
                case 4:
                    reservationManager.runReservationManager();
                    break;
                case 5:
                    break;
            }

        } while (choice != 5);
    }
}
