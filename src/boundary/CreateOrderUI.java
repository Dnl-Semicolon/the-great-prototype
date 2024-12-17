package boundary;

import java.util.Scanner;

public class CreateOrderUI {
    private Scanner scanner;

    public CreateOrderUI() {
        scanner = new Scanner(System.in);
    }

    public void displayTitle(StringBuilder outputStr) {
        System.out.println(outputStr);
    }

    public int getMainMenuChoice() {
        int choice = 0;
        boolean isExceptionFound = false;
        do {
            try {
                System.out.println("1. View Menu");
                System.out.println("2. Add Item to Order");
                System.out.println("3. Remove Item from Order");
                System.out.println("4. View Current Order");
                System.out.println("5. Submit Order");
                System.out.println("6. Cancel Order");
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
}
