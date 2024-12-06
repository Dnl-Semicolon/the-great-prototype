package boundary;

import entity.*;
import java.util.Scanner;

public class ReservationManagerUI {
    private Scanner scanner = new Scanner(System.in);
    public int getMainMenuChoice() {
        System.out.println("--- Reservation & Waitlist Management Subsystem ---");
        System.out.println("1. Seat Guest");
        System.out.println("2. Reservation Management");
        System.out.println("3. Waitlist Management");
        System.out.println("4. Back to Subsystem Menu");
        System.out.print("Enter Your Choice (1-4) >> ");
        int choice = 0;
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public Customer inputCustomerDetails1() {
        Customer newCustomer = new Customer();
        System.out.println("Add New Customer");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.println();
        System.out.print("Enter contact number: ");
        String phoneNo = scanner.nextLine();
        System.out.println();
        System.out.print("Enter party size: ");
        int partySize = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        newCustomer = new Customer(name, partySize, phoneNo);
        System.out.println("New Customer Added To Waitlist.\n");
        return newCustomer;
    }

    public void displayWaitlist(String inputStr) {
        System.out.println("Waitlist");
        System.out.println("****************************************************");
        System.out.println("Entry   Name             Guests       Phone Number");
        System.out.println("====================================================");
        System.out.print(inputStr);
        System.out.println("----------------------------------------------------");
        System.out.println();
    }

    public int inputCustomerToRemove() {
        int position = 0;
        System.out.print("Enter the entry number to remove (or 0 to cancel): ");
        position = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        return position;
    }

    public int getSeatGuestChoice() {
        System.out.println("--- Seat Guest ---");
        System.out.println("1. Seat Guest from Waitlist");
        System.out.println("2. Seat Guest from Reservation List");
        System.out.println("3. Back to Subsystem Menu");
        System.out.print("Enter Your Choice (1-3) >> ");
        int choice = 0;
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public int getReservationManagementChoice() {
        System.out.println("--- Reservation Management ---");
        System.out.println("1. ");
        System.out.println("2. ");
        System.out.println("3. ");
        System.out.println("4. Back to Subsystem Menu");
        System.out.print("Enter Your Choice (1-4) >> ");
        int choice = 0;
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public int getWaitlistManagementChoice() {
        System.out.println("--- Waitlist Management ---");
        System.out.println("1. Add Customer to Waitlist");
        System.out.println("2. View Waitlist");
        System.out.println("3. Update Waitlist");
        System.out.println("4. Remove Customer from Waitlist");
        System.out.println("5. Back to Subsystem Menu");
        System.out.print("Enter Your Choice (1-5) >> ");
        int choice = 0;
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public Customer inputCustomerDetails() {
        Customer newCustomer;

        boolean retry = true;
        String name = "";
        int partySize = 0;
        String phoneNumber = "";
        boolean confirm = false;

        do {
            newCustomer = new Customer();
            System.out.println("+-------------------------+");
            System.out.println("|  Type customer details  |");
            System.out.println("| according to the prompt |");
            System.out.println("|     and press <Enter>   |");
            System.out.println("+-------------------------+");
            System.out.println();
            System.out.println("--- Add Customer to Waitlist Form ---");
            System.out.println("Name");
            System.out.print(">> ");
            name = scanner.nextLine();
            System.out.println("Guests (Pax)");
            System.out.print(">> ");
            partySize = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Phone Number");
            System.out.print(">> ");
            phoneNumber = scanner.nextLine();
            System.out.println();
            System.out.println("Action: Add New Customer to Waitlist");
            System.out.println("------------------------------------");
            System.out.println("Name         : " + name);
            System.out.println("Guests       : " + partySize);
            System.out.println("Phone Number : " + phoneNumber);
            System.out.println();
            System.out.print("Confirm action? (y/n) >> ");
            char confirmChar = scanner.nextLine().trim().toLowerCase().charAt(0);
            System.out.println();
            retry = confirmChar == 'n';
        } while (retry);

        newCustomer = new Customer(name, partySize, phoneNumber);
        return newCustomer;
    }

    public void displayConfirmationMessageAddedCustomer(int place) {
        System.out.println("Added to Waitlist. Currently placed at (" + place + ").");
        System.out.println();
    }

    public boolean getConfirmationToSeatGuestFromWaitlist(Customer customer) {
        boolean confirm = true;
        System.out.println("The next guest to be seated is:");
        System.out.printf("Entry: 1 | Name: %s | Guests: %d | Phone Number: %s%n",
                customer.name, customer.partySize, customer.phoneNumber);
        System.out.print("Confirm seating this guest? (y/n) >> ");
        char confirmChar = scanner.nextLine().trim().toLowerCase().charAt(0);
        System.out.println();
        return confirm;
    }

    public int getTableNumberChoice(String inputStr) {
        int choice = 0;
        System.out.println("Tables");
        System.out.println("****************************************************");
        System.out.println("Entry   Table Number");
        System.out.println("====================================================");
        System.out.print(inputStr);
        System.out.println("----------------------------------------------------");
        System.out.println();
        System.out.print("Enter Your Choice >> ");
        choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void displayConfirmedSeatingMessage(Table chosenTable) {
        System.out.println("Seated\n" + chosenTable.customer + "\nat Table " + chosenTable.tableNo);
        System.out.println();
    }
}
