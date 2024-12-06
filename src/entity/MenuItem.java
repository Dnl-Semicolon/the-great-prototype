package entity;

import adt.*;

public class MenuItem {

    public String name;
    public double price;
    public ListInterface<Ingredient> recipe = new ArrayList<>();

    public MenuItem() {
        name = "";
        price = 0;
    }

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        // default given toString
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", recipe=" + recipe +
                '}';
    }
}
