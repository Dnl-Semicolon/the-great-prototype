package boundary;

import adt.ListInterface;
import dao.*;
import entity.*;

/**
 *
 * @author 
 */
public class ReceiptUI {
    
    Receipt receipt = new Receipt();
    BillDAO billDAO = new BillDAO();
    
    public void setBill(Receipt receipt){
        this.receipt = receipt;
    }
    
    public void printReceipt(){
        int receiptNo = billDAO.readReceiptID();
          
        System.out.println("==================== RECEIPT ===================");
        System.out.println("Receipt No: " + receiptNo);
        System.out.printf("%-45s %2s","TABLE NO:", "");                                     // add later
        System.out.printf("\n%-42s %5s","CUSTOMER LAST NAME:","");                          // add later
        System.out.printf("\n%-36s %11s","CUSTOMER CONTACT NUMBER:", "");                   // add later
        System.out.printf("\n%-28s %19s\n","DATE TIME:" , receipt.getFormatterDate());
        System.out.println("");
        System.out.println("NO  ITEMS\t\tQUANTITY\tPRICE");
        
        ListInterface<Bill> items = billDAO.readBill();
        int num = 0;
        for (int i = 1; i <= items.getNumberOfEntries(); i ++){
            Bill item = items.getEntry(i);
            num++;
            System.out.printf("%-3s %-22s %-11d RM%6.2f%n", num + ".", item.getItem(), item.getQuantity(), item.getPrice());

        }
        System.out.println("\n------------------------------------------------");
        System.out.println(receipt.toString());
        System.out.println("================================================");
        
        receiptNo ++;
        billDAO.writeReceiptNo(receiptNo);
    }
}
