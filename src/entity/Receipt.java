package entity;

import adt.ListInterface;
import boundary.ReceiptUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Choo Zheng Yi
 */

public class Receipt {
    private int tableNo;
    private String cLastName;
    private String cContactNo;
    private double subtotal;
    private int tax;
    private int discount;
    private int serviceCharge;
    private double grandTotal;
    private LocalDateTime timeNow;
    private int receiptNo;
    
    private ListInterface<Bill> items;
    
    public Receipt(){
        this.timeNow = LocalDateTime.now();
    }
    
    public Receipt(int tableNo, String cLastName, String cContactNo, 
            double subtotal, int tax, int discount, int serviceCharge, double grandTotal, LocalDateTime timeNow, int receiptNo){
        this.tableNo = tableNo;
        this.cLastName = cLastName;
        this.cContactNo = cContactNo;
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.serviceCharge = serviceCharge;
        this.grandTotal = grandTotal;
        this.timeNow = timeNow;
        this.receiptNo = receiptNo;
    }
    
    public void setItems(ListInterface<Bill> items) {
        this.items = items;
    }
    
    public ListInterface<Bill> getItems() {
        return items;
    }
    
    public int getTableNo(){
        return tableNo;
    }
    
    public void setTableNo(int tableNo){
        this.tableNo = tableNo;
    }
    
    public String getCLastName(){
        return cLastName;
    }
    
    public void setCLastName(String cLastName){
        this.cLastName = cLastName;
    }
    
    public String getCContactNo(){
        return cContactNo;
    }
    
    public void setCContactNo(String cContactNo){
        this.cContactNo = cContactNo;
    }
    
    public double getSubtotal(){
        return subtotal;
    }
    
    public int getTax(){
        return tax;
    }
    
    public int getDiscount(){
        return discount;
    }
    
    public int getServiceCharge(){
        return serviceCharge;
    }
    
    public double getGrandTotal(){
        return grandTotal;
    }
    
    public LocalDateTime getTimeNow(){
        return timeNow;
    }
    
    public int getReceiptNo(){
        return receiptNo;
    }
    
    public void setSubtotal(double subtotal){
        this.subtotal = subtotal;
    }
    
    public void setTax(int tax){
        this.tax = tax;
    }
    
    public void setDiscount(int discount){
        this.discount = discount;
    }
    
    public void setServiceCharge(int serviceCharge){
        this.serviceCharge = serviceCharge;
    }
    
    public void setGrandTotal(double grandTotal){
        this.grandTotal = grandTotal;
    }
    
    public void setTimeNow(LocalDateTime timeNow){
        this.timeNow = timeNow;
    }
    
    public void setReceiptNo(int receiptNo){
        this.receiptNo = receiptNo;
    }
    
    public String getFormatterDate (){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timeNow.format(formatterDate);
    }
    
    @Override
    public String toString() {
   
        ReceiptUI receiptUI = new ReceiptUI();
        return receiptUI.printReceipt();
    }

}
