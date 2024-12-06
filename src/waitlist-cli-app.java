import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Customer class to represent waitlist entry
class Customer {
    private String name;
    private String phoneNumber;
    private int partySize;
    private boolean isSpecialNeeds;

    // Constructor
    public Customer(String name, String phoneNumber, int partySize, boolean isSpecialNeeds) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.partySize = partySize;
        this.isSpecialNeeds = isSpecialNeeds;
    }

    // Getters
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getPartySize() { return partySize; }
    public boolean isSpecialNeeds() { return isSpecialNeeds; }

    // toString method for nice display
    @Override
    public String toString() {
        return String.format(
            "Name: %-20s | Phone: %-15s | Party Size: %-3d | Special Needs: %s", 
            name, 
            phoneNumber, 
            partySize, 
            isSpecialNeeds ? "Yes" : "No"
        );
    }
}

// Waitlist management class
class WaitlistManager {
    private List<Customer> waitlist;
    private Scanner scanner;

    public WaitlistManager() {
        waitlist = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Add a new customer to the waitlist
    public void addCustomer() {
        try {
            System.out.println("\n--- Add New Customer ---");
            
            // Get customer name
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine().trim();
            
            // Get phone number with validation
            String phoneNumber;
            while (true) {
                System.out.print("Enter phone number (10 digits): ");
                phoneNumber = scanner.nextLine().trim();
                if (phoneNumber.matches("\\d{10}")) {
                    break;
                }
                System.out.println("Invalid phone number. Please enter 10 digits.");
            }
            
            // Get party size
            int partySize;
            while (true) {
                System.out.print("Enter party size (1-20): ");
                try {
                    partySize = Integer.parseInt(scanner.nextLine().trim());
                    if (partySize > 0 && partySize <= 20) {
                        break;
                    }
                    System.out.println("Party size must be between 1 and 20.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            
            // Get special needs
            System.out.print("Do they have special needs? (y/n): ");
            boolean isSpecialNeeds = scanner.nextLine().trim().toLowerCase().startsWith("y");
            
            // Create and add customer
            Customer newCustomer = new Customer(name, phoneNumber, partySize, isSpecialNeeds);
            waitlist.add(newCustomer);
            
            System.out.println("\nCustomer added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    // View all customers in the waitlist
    public void viewWaitlist() {
        if (waitlist.isEmpty()) {
            System.out.println("\nWaitlist is empty.");
            return;
        }
        
        System.out.println("\n--- Current Waitlist ---");
        System.out.println("Total Customers: " + waitlist.size());
        System.out.println("---------------------------------------------");
        
        for (int i = 0; i < waitlist.size(); i++) {
            System.out.println("Entry #" + (i + 1) + ": " + waitlist.get(i));
        }
        
        System.out.println("---------------------------------------------");
    }

    // Remove a customer from the waitlist
    public void removeCustomer() {
        if (waitlist.isEmpty()) {
            System.out.println("\nWaitlist is empty.");
            return;
        }
        
        viewWaitlist();
        
        try {
            System.out.print("\nEnter the entry number to remove (or 0 to cancel): ");
            int entryToRemove = Integer.parseInt(scanner.nextLine().trim());
            
            if (entryToRemove > 0 && entryToRemove <= waitlist.size()) {
                Customer removedCustomer = waitlist.remove(entryToRemove - 1);
                System.out.println("\nRemoved customer: " + removedCustomer.getName());
            } else if (entryToRemove != 0) {
                System.out.println("Invalid entry number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // Main menu and application logic
    public void run() {
        while (true) {
            try {
                // Display menu
                System.out.println("\n--- Walk-in Waitlist Management ---");
                System.out.println("1. Add Customer");
                System.out.println("2. View Waitlist");
                System.out.println("3. Remove Customer");
                System.out.println("4. Exit");
                System.out.print("Choose an option (1-4): ");
                
                // Get user choice
                String choice = scanner.nextLine().trim();
                
                // Process choice
                switch (choice) {
                    case "1":
                        addCustomer();
                        break;
                    case "2":
                        viewWaitlist();
                        break;
                    case "3":
                        removeCustomer();
                        break;
                    case "4":
                        System.out.println("Exiting Waitlist Management. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        WaitlistManager manager = new WaitlistManager();
        manager.run();
    }
}
