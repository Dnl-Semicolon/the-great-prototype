package entity;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String name;
    private int currentStock;
    private String code;

    public Ingredient() {
    }

    public Ingredient(String code, String name, int currentStock) {
        this.name = name;
        this.currentStock = currentStock;
        this.code = code;
    }

    public Ingredient(String name, int currentStock) {
        this("", name, currentStock);
    }

    public void reduceStock(int quantityUsed) {
        currentStock -= quantityUsed;
    }

    public boolean isLowStock(int threshold) {
        return currentStock <= threshold;
    }

    @Override
    public String toString() {
        return String.format("Code: %-10s Name: %-20s Stock: %d", code, name, currentStock);
    }

    // Getter and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getCurrentStock() {return currentStock;}
    public void setCurrentStock(int currentStock) {this.currentStock = currentStock;}
    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}
}
