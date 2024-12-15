package control;

import adt.ArrayList;
import adt.HashMap;
import adt.ListInterface;
import boundary.BillingManagerUI;
import boundary.ReceiptUI;
import dao.BillDAO;
import entity.*;
import java.time.LocalTime;
import java.util.Scanner;

/**
 *
 * @author 
 */ 

public class BillingManager {
    
    private BillingManagerUI billUI = new BillingManagerUI();
    private ReceiptUI receiptUI = new ReceiptUI();
    private Receipt bill = new Receipt();
    private BillDAO billDAO = new BillDAO();
    private ListInterface<Bill> itemsList = new ArrayList();
    private HashMap<Integer, Receipt> receiptHistory = new HashMap<>();
   

    public static void main(String[] args) {
        
        BillingManager billmanager = new BillingManager();
        
        billmanager.itemsList.add(new Bill("Burger", 1, 10.50));
        billmanager.itemsList.add(new Bill("Pizza", 2, 22.00));
        billmanager.itemsList.add(new Bill("Pasta", 1, 15.75));
        billmanager.itemsList.add(new Bill("Salad", 3, 8.50));
        billmanager.itemsList.add(new Bill("Steak", 1, 35.00));
        billmanager.itemsList.add(new Bill("Fries", 2, 5.25));
        billmanager.itemsList.add(new Bill("Soda", 4, 3.00));
        billmanager.itemsList.add(new Bill("Coffee", 1, 4.50));
        billmanager.itemsList.add(new Bill("Ice Cream", 1, 6.75));
        billmanager.itemsList.add(new Bill("Soup", 1, 7.25));
        
        billmanager.runBillingManager();
        
        
    }
    
    public void runBillingManager(){
        
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        do{
        System.out.println();
        choice = billUI.getMenu();
        
        switch(choice){
            case 1:
                manageTable();
                receiptUI.printReceipt();
                break;
                
            case 0:
                System.out.println("Exting......");
                break;
                
            default :
                System.out.println("Invalid choice, please try again!");
        }
        
        }while(choice != 0);
        
    }
    
    public void manageTable(){
        
        int tableChoice = billUI.getTable();
        
        billDAO.writeBill(itemsList);               // uncompleted.
        
        calculateBill();
                
    }
    
    
    public void calculateBill(){
        double subtotal = calculateSubTotal();
        int tax = calculateTax();
        int discount = calculateDiscount();
        int serviceCharge = calculateServiceCharge();
        double grandTotal = calculateGrandTotal();
        bill.setSubtotal(subtotal);
        bill.setTax(tax);
        bill.setDiscount(discount);
        bill.setServiceCharge(serviceCharge);
        bill.setGrandTotal(grandTotal);
        
        receiptUI.setBill(bill);
    }
     
    public double calculateSubTotal(){
        double subtotal = 0.0;
        for(int i = 1; i <= itemsList.getNumberOfEntries();i ++){
            Bill item = itemsList.getEntry(i);
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }
    
    public int calculateTax(){
        final int TAX = 6;
        return TAX;
    }
    public int calculateDiscount(){
        int discount = 0;
        
        LocalTime timeNow = LocalTime.now();
        
        LocalTime breakfastDiscountStart = LocalTime.of(8, 0);
        LocalTime breakfastDiscountEnd = LocalTime.of(10, 0);
        
        LocalTime lunchDiscountStart = LocalTime.of(12, 0);
        LocalTime lunchDiscountEnd = LocalTime.of(14, 0);
        
        LocalTime dinnerDiscountStart = LocalTime.of(18, 0);
        LocalTime dinnerDiscountEnd = LocalTime.of(20, 0);
        
         if(timeNow.isAfter(breakfastDiscountStart) && timeNow.isBefore(breakfastDiscountEnd)){
            discount = 5;
        }
        if(timeNow.isAfter(lunchDiscountStart) && timeNow.isBefore(lunchDiscountEnd)){
            discount = 10;
        }
        if(timeNow.isAfter(dinnerDiscountStart) && timeNow.isBefore(dinnerDiscountEnd)){
            discount = 8;
        }
        return discount;
    }
    
    public int calculateServiceCharge(){
        final int SERVICE_CHARGE = 10;
        return SERVICE_CHARGE;
    }
    
    public double calculateGrandTotal(){
        double subtotal = calculateSubTotal();
        double tax = (subtotal * calculateTax()) / 100;
        double discount = (subtotal * calculateDiscount()) / 100;
        double serviceCharge = (subtotal * calculateServiceCharge()) /100 ;
        
        return subtotal + tax - discount + serviceCharge ;
    }
}
