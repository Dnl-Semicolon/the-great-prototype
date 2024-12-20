package boundary;

import adt.*;
import boundary.*;
import dao.*;
import entity.*;

import java.time.LocalDate;
/**
 *
 * @author Choo Zheng Yi
 */
public class ReceiptUI {
    
    Receipt receipt = new Receipt();
    ReceiptDAO receiptDAO = new ReceiptDAO();
    SalesReportDAO salesReportDAO = new SalesReportDAO();
    SalesReport salesReport = new SalesReport();
    
    private HashMap<LocalDate, Double> salesReports = new HashMap<>();
    
    public void setBill(Receipt receipt){
        this.receipt = receipt;
    }

    public String printReceipt(){
        int receiptNo = receiptDAO.readReceiptID();
        receipt.setReceiptNo(receiptNo);
        receiptDAO.writeReceiptNo(receiptNo + 1);  
        salesReports.put(salesReport.getDate(),receipt.getGrandTotal());
        salesReportDAO.writeReport(salesReports);
        double totalSales = receipt.getGrandTotal();

        StringBuilder receiptDetails = new StringBuilder();

        receiptDetails.append("==================== RECEIPT ===================");
        receiptDetails.append(String.format("\nReceipt No: %d\n", receiptNo));
        receiptDetails.append(String.format("%-45s %2s","TABLE NO:", receipt.getTableNo()));                                     // add later
        receiptDetails.append(String.format("\n%-42s %5s","CUSTOMER LAST NAME:",receipt.getCLastName()));                        // add later
        receiptDetails.append(String.format("\n%-36s %11s","CUSTOMER CONTACT NUMBER:", receipt.getCContactNo()));                // add later
        receiptDetails.append(String.format("\n%-28s %19s\n","DATE TIME:" , receipt.getFormatterDate()));
        receiptDetails.append("\n");
        receiptDetails.append("NO  ITEMS\t\tQUANTITY      PRICE/UNIT\n");

        displayItems(receipt.getItems(), receiptDetails);

        receiptDetails.append("\n");
        receiptDetails.append(String.format("%-38s  RM%6.2f\n", "SUBTOTAL:", receipt.getSubtotal()));
        receiptDetails.append(String.format("%-44s  %1d%%\n", "TAX:", receipt.getTax()));
        receiptDetails.append(String.format("%-43s  %2d%%\n", "DISCOUNT:", receipt.getDiscount()));
        receiptDetails.append(String.format("%-43s  %2d%%\n", "SERVICE CHARGES:", receipt.getServiceCharge()));
        receiptDetails.append("------------------------------------------------\n");
        receiptDetails.append(String.format("%-39s RM%6.2f\n", "GRAND TOTAL:", receipt.getGrandTotal()));

        if(receipt.getDiscount() == 5){
            receiptDetails.append("------------------------------------------------\n");
            receiptDetails.append("     5% discount added for breakfast discount\n");
            receiptDetails.append("     From 8.00 a.m. - 10.00 a.m\n");
        }
        if(receipt.getDiscount() == 10){
            receiptDetails.append("------------------------------------------------\n");
            receiptDetails.append("     10% discount added for lunch discount\n");
            receiptDetails.append("     From 12.00 a.m. - 2.00 p.m\n");
        }
        if(receipt.getDiscount() == 8){
            receiptDetails.append("------------------------------------------------\n");
            receiptDetails.append("     8% discount added for dinner discount\n");
            receiptDetails.append("     From 6.00 p.m. - 8.00 p.m\n");
        }
        receiptDetails.append("================================================");
        
        return receiptDetails.toString();  
    }
           
    public void displayItems(ListInterface<Bill> itemsList, StringBuilder receiptDetails){
        int num = 0;
        for (int i = 1; i <= itemsList.getNumberOfEntries(); i ++){
            Bill item = itemsList.getEntry(i);
            num++;
            receiptDetails.append(String.format("%-3s %-22s %-11d RM%6.2f%n", num + ".", item.getItem(), item.getQuantity(), item.getPrice()));
        }
    }

}
