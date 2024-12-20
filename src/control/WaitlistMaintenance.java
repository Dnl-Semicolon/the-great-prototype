package control;

import adt.ArrayList;
import adt.LinkedQueue;
import adt.ListInterface;
import adt.QueueInterface;
import boundary.WaitlistMaintenanceUI;
import dao.WaitlistDAO;
import entity.Customer;
import entity.MenuItem;

import java.util.Iterator;

public class WaitlistMaintenance {
    private WaitlistMaintenanceUI waitlistMaintenanceUI;
    private WaitlistDAO waitlistDAO;
    private QueueInterface<Customer> waitlist = new LinkedQueue<>();

    public WaitlistMaintenance() {
        waitlistMaintenanceUI = new WaitlistMaintenanceUI();
        waitlistDAO = new WaitlistDAO();
        waitlist = waitlistDAO.retrieveFromFile();
    }

    public QueueInterface<Customer> getWaitlistQueue() {
        return waitlist;
    }

    public void displayWaitlist() {
        if (!waitlist.isEmpty()) {
            waitlistMaintenanceUI.displayWaitlist(getWaitlist());
        } else {
            displayEmptyWaitlistMessage();
        }
    }

    public void displayEmptyWaitlistMessage() {
        waitlistMaintenanceUI.displayEmptyWaitlistMessage();
    }

    public String getWaitlist() {
        StringBuilder inputStr = new StringBuilder();
        Iterator<Customer> iterator = waitlist.getIterator();
        Customer customer;
        while (iterator.hasNext()) {
            customer = iterator.next();
            inputStr.append(String.format("%3d.   ", customer));
            inputStr.append(customer.toString());
            inputStr.append("\n");
        }
        return inputStr.toString();
    }

    public void addCustomerToWaitlist() {
        String customerName = "";
        int customerPartySize = 0;
        String customerContactNumber = "";
        Customer newCustomer = new Customer();

        boolean isValidInput = false;

        while (!isValidInput) {
            customerName = waitlistMaintenanceUI.inputCustomerName();
            if (Customer.isValidName(customerName)) {
                isValidInput = true;
            }
        }

        isValidInput = false;

        while (!isValidInput) {
            customerPartySize = waitlistMaintenanceUI.inputCustomerPartySize();
            if (Customer.isValidPartySize(customerPartySize)) {
                isValidInput = true;
            }
        }

        isValidInput = false;

        while (!isValidInput) {
            customerContactNumber = waitlistMaintenanceUI.inputCustomerContactNumber();
            if (Customer.isValidContactNumber(customerContactNumber)) {
                isValidInput = true;
            }
        }

        newCustomer.setName(customerName);
        newCustomer.setPartySize(customerPartySize);
        newCustomer.setPhoneNumber(customerContactNumber);

        waitlist.enqueue(newCustomer);
        waitlistDAO.saveToFile(waitlist);

        displayWaitlist();
    }

    public void runWaitlistMaintenance() {
        int choice = 0;
        do {
            choice = waitlistMaintenanceUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    addCustomerToWaitlist();
                    break;
                case 2:
                    displayWaitlist();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Exiting system");
                    System.out.println();
                    break;
            }
        } while (choice != 5);
    }

    public static void main(String[] args) {
        WaitlistMaintenance waitlistMaintenance = new WaitlistMaintenance();
        waitlistMaintenance.runWaitlistMaintenance();
    }
}
