package boundary;
import java.util.Scanner;
/**
 *
 * @author 
 */
public class BillingManagerUI {
    
    Scanner scanner = new Scanner(System.in);
    
    public int getMenu(){
        System.out.println("========== Billing Management =========");
        System.out.println("1. Continue payment");
        System.out.println("2. Payment History");
        System.out.println("0. Return to main menu");
        System.out.println("=======================================");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        System.out.println("");
        return choice;
    }
    
    public int getTable(){
        System.out.println("=============== Select a table ===============");
        
        System.out.println("==============================================");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        System.out.println("");
        return choice;
    }
    
}
