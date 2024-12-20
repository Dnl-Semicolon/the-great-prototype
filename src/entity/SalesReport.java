package entity;


import java.time.LocalDate;
/**
 *
 * @author Choo Zheng Yi
 */
public class SalesReport {
    
    private LocalDate date;
    private double totalSales;
    
    public SalesReport(){
        this.date = LocalDate.now();
        this.totalSales = 0.0;
    }
    
    public SalesReport(LocalDate date, double totalSales){
        this.date = date;
        this.totalSales = totalSales;
        
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public double getTotalSales(){
        return totalSales;
    }
     
    public void setDate(LocalDate date){
        this.date = date;
    }
    
        
}
