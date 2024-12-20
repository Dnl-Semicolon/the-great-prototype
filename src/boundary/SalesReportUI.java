package boundary;

import control.BillingManager;

import java.time.LocalDate;
/**
 *
 * @author Choo Zheng Yi
 */
public class SalesReportUI {
    
    
   
    public void printReportToday(){   
        BillingManager billingmanager = new BillingManager();
        System.out.println("=============== Today Sales Report ===============");
        billingmanager.showTodayReport();
        System.out.println("==================================================\n");
    }
    
    public void printSearchReport(LocalDate searchDate){
        BillingManager billingmanager = new BillingManager();
        System.out.println("\n================= Sales Report =================");
        billingmanager.searchSalesReport(searchDate);
        System.out.println("================================================\n");   
    }
}
