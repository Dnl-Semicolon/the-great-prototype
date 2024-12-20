package entity;

import java.io.Serializable;

/**
 *
 * @author Choo Zheng Yi
 */
public class Bill implements Serializable{
    
    private String item;
    private int quantity;
    private double price;
    
    public Bill(){
        
    }
    
    public Bill(String item, int quantity, double price){
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getItem(){
        return item;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setItem(String item){
        this.item = item;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
   
  
}
