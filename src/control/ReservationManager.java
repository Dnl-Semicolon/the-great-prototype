package control;

import adt.*;
import boundary.*;
import entity.*;

public class ReservationManager {

    private ReservationManagerUI reservationManagerUI = new ReservationManagerUI();
    private ListInterface<Customer> waitlist = new ArrayList<>();
    private OrderManager orderManager;

    public ReservationManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void addCustomerToWaitlist() {
        Customer newCustomer = reservationManagerUI.inputCustomerDetails();
        waitlist.add(newCustomer);
        reservationManagerUI.displayConfirmationMessageAddedCustomer(waitlist.getNumberOfEntries());
        reservationManagerUI.displayWaitlist(getWaitlist());
    }

    public void seatGuest() {
        int choice = 0;
        do {
            choice = reservationManagerUI.getSeatGuestChoice();
            switch (choice) {
                case 1:
//                    seatGuestFromWaitlist();
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        } while (choice != 3);
    }

    //legacy
//    private void seatGuestFromWaitlist() {
//        reservationManagerUI.displayWaitlist(getWaitlist());
//        boolean confirmation = reservationManagerUI.getConfirmationToSeatGuestFromWaitlist(waitlist.getEntry(1));
//        int tableNumber;
//        if (confirmation) {
//            Customer customer = waitlist.remove(1);
//            tableNumber = reservationManagerUI.getTableNumberChoice(orderManager.getUnoccupiedTables());
//            Table chosenTable = orderManager.tables.getEntry(tableNumber);
//            chosenTable.customer = customer;
//            reservationManagerUI.displayConfirmedSeatingMessage(chosenTable);
//        }
//    }

    public void reservationManagement() {
        int choice = 0;
        do {
            choice = reservationManagerUI.getReservationManagementChoice();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        } while (choice != 0);
    }

    public void waitlistManagement() {
        int choice = 0;
        do {
            choice = reservationManagerUI.getWaitlistManagementChoice();
            switch (choice) {
                case 1:
                    addCustomerToWaitlist();
                    break;
                case 2:
                    reservationManagerUI.displayWaitlist(getWaitlist());
                    break;
                case 3:
                    break;
                case 5:
                    break;
            }
        } while (choice != 5);
    }

    public void removeCustomer() {
        System.out.println("--- Current Waitlist ---");
        System.out.println("Total Customers: " + waitlist.getNumberOfEntries());
        reservationManagerUI.displayWaitlist(getWaitlist());
        System.out.println();
        int position = 0;
        position = reservationManagerUI.inputCustomerToRemove();
        if (position == 0)
            return;

        waitlist.remove(position);
    }

    public String getWaitlist() {
        StringBuilder outputStr = new StringBuilder();
        if (waitlist.isEmpty()) {
            return "\n";
        }
        for (int i = 1; i <= waitlist.getNumberOfEntries(); i++) {
            Customer customer = waitlist.getEntry(i);
            outputStr.append(String.format("  %-3d   ", i));
            outputStr.append(customer);
            outputStr.append("\n");
        }
        return outputStr.toString();
    }

    public void runReservationManager() {
        int choice = 0;
        do {
            choice = reservationManagerUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    seatGuest();
                    break;
                case 2:
                    reservationManagement();
                    break;
                case 3:
                    waitlistManagement();
                    break;
                case 4:
                    break;
            }
        } while (choice != 4);
    }
}
