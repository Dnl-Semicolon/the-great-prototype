package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author 
 */

public class Receipt {
    private double subtotal;
    private int tax;
    private int discount;
    private int serviceCharge;
    private double grandTotal;
    private LocalDateTime timeNow;
    private int receiptNo;
    
    public Receipt(){
        this.timeNow = LocalDateTime.now();
    }
    
    public Receipt(double subtotal, int tax, int discount, int serviceCharge, double grandTotal, LocalDateTime timeNow, int receiptNo){
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.serviceCharge = serviceCharge;
        this.grandTotal = grandTotal;
        this.timeNow = timeNow;
        this.receiptNo = receiptNo;
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
        return String.format(
            "%-38s  RM%6.2f\n" + 
            "%-44s  %1d%%\n" +    
            "%-43s  %2d%%\n" +    
            "%-43s  %2d%%\n" +    
            "------------------------------------------------\n" +
            "%-39s RM%6.2f",    
            "SUBTOTAL:", subtotal,
            "TAX:", tax,
            "DISCOUNT:", discount,
            "SERVICE CHARGES:", serviceCharge,
            "GRAND TOTAL:", grandTotal
        );     
    }
}
