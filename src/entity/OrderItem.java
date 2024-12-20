package entity;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public OrderItem() {
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addToQuantity(int addQuantity) {
        quantity += addQuantity;
    }

    @Override
    public String toString() {
        return String.format("%-21s %3d", menuItem.getName(), quantity);
    }
}
