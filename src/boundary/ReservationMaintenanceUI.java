package boundary;

import java.util.Scanner;

public class ReservationMaintenanceUI {
    private Scanner scanner;

    public ReservationMaintenanceUI() {
        scanner = new Scanner(System.in);
    }

    public void displayEmptyReservationListMessage() {
        System.out.println("No Reservations on Reservation List To Display");
        System.out.println();
    }

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("--- Reservation Management ---");
                System.out.println("1. Make a Reservation");
                System.out.println("2. View Reservations");
                System.out.println("3. Timeline View (Table Availability)");
                System.out.println("4. Update Reservation");
                System.out.println("5. Cancel Reservation");
                System.out.println("6. Exit");
                System.out.print("Enter Your Choice (1-6) >> ");
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

    public void displayReservations(String reservations) {
        System.out.println(reservations);
    }

    public void timelineView(String inputStr) {
        System.out.println("=== Table Availability ===");
        System.out.println();
        System.out.println(inputStr);
    }

    public String inputReservationDate() {
        String dateInput = "";
        System.out.println("Date of Reservation (DD/MM/YYYY):");
        System.out.print(">> ");
        dateInput = scanner.nextLine();
        System.out.println();
        return dateInput;
    }

    public String inputReservationTime() {
        String timeInput = "";
        System.out.println("Time of Reservation (HH:MM, 24-hour format):");
        System.out.print(">> ");
        timeInput = scanner.nextLine();
        System.out.println();
        return timeInput;
    }
}
