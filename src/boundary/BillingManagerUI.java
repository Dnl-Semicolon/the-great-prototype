package boundary;

import adt.ListInterface;
import entity.Bill;
import entity.Receipt;

import java.util.Scanner;

/**
 *
 * @author Choo Zheng Yi
 */
public class BillingManagerUI {
    private Scanner scanner = new Scanner(System.in);
    Receipt receipt = new Receipt();

    public void setBill(Receipt receipt){
        this.receipt = receipt;
    }

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                getMenu();
                System.out.print("Enter choice: ");
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

    public void getMenu() {
        System.out.println("========== Billing Management =========");
        System.out.println("1. Payment");
        System.out.println("2. Sales report");
        System.out.println("0. Return to main menu");
        System.out.println("=======================================");
    }

    public int getPaymentMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                getPaymentMenu();
                System.out.print("Enter choice: ");
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

    public void getPaymentMenu() {
        System.out.println("================ Payment menu ================");
        System.out.println("1. Continue payment");
        System.out.println("2. Remove an item");
        System.out.println("0. Return to billing mangement menu");
        System.out.println("==============================================");
    }

    public void getTable() {
        System.out.println("=============== Select a table ===============");
        System.out.println("0. Return to billing management menu");
        System.out.println("==============================================");
    }

    public void getReportMenu() {
        System.out.println("================ Sales Report ================");
        System.out.println("1. Today sales report");
        System.out.println("2. Search sales report");
        System.out.println("0. Return to billing mangement menu");
        System.out.println("==============================================");
    }

    public void displayItem(ListInterface<Bill> itemsList){
        int num = 0;
        
        System.out.println("\nTable No: " + receipt.getTableNo());
        System.out.println("=================== Item list ===================");
        for (int i = 1; i <= itemsList.getNumberOfEntries(); i ++){
            Bill item = itemsList.getEntry(i);
            num++;
            System.out.printf("%-3s %-22s %-11d RM%6.2f%n", num + ".", item.getItem(), item.getQuantity(), item.getPrice());
        }
        
        System.out.println("-------------------------------------------------");
        System.out.println("1. Remove an item");
        System.out.println("0. Return to billing mangement menu");
        System.out.println("=================================================");
    }

    
}
