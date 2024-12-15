package boundary;

import java.util.Scanner;

public class WaitlistMaintenanceUI {
    private Scanner scanner;

    public WaitlistMaintenanceUI() {
        scanner = new Scanner(System.in);
    }

    public void displayEmptyWaitlistMessage() {
        System.out.println("No Customers on Waitlist To Display");
        System.out.println();
    }

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("--- Waitlist Management ---");
                System.out.println("1. Add Customer to Waitlist");
                System.out.println("2. View Waitlist");
                System.out.println("3. Update Waitlist");
                System.out.println("4. Remove Customer from Waitlist");
                System.out.println("5. Exit");
                System.out.print("Enter Your Choice (1-5) >> ");
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

    public void displayWaitlist(String waitlist) {
        System.out.println("Waitlist");
        System.out.println("Entry  Name            Guests       Phone Number");
        System.out.println(waitlist);
    }

    public void displayFormHeader() {
        System.out.println("+-------------------------+");
        System.out.println("|  Type customer details  |");
        System.out.println("| according to the prompt |");
        System.out.println("|     and press <Enter>   |");
        System.out.println("+-------------------------+");
        System.out.println();
        System.out.println("--- Add Customer to Waitlist Form ---");
    }

    public String inputCustomerName() {
        String name = "";
        System.out.println("Name:");
        System.out.print(">> ");
        name = scanner.nextLine();
        System.out.println();
        return name;
    }

    public int inputCustomerPartySize() {
        int partySize = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("Guests (Pax)");
                System.out.print(">> ");
                partySize = scanner.nextInt();
                isExceptionFound = false;
            } catch (Exception ex) {
                scanner.nextLine();
                partySize = 0;
                isExceptionFound = true;
                System.out.println();
                System.out.println("Error: Not A Valid Party Size. Enter A Whole Number As A Party Size.");
                System.out.println();
            }
        } while (isExceptionFound);
        scanner.nextLine();
        System.out.println();
        return partySize;
    }

    public String inputCustomerContactNumber() {
        String contactNumber = "";
        System.out.println("Contact Number:");
        System.out.print(">> ");
        contactNumber = scanner.nextLine();
        System.out.println();
        return contactNumber;
    }
}
