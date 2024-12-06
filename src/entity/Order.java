package entity;

import adt.*;

public class Order {

    public int tableNo;
    public ListInterface<MenuItem> orderItems = new ArrayList<>();

    public Order(int tableNo) {
        this.tableNo = tableNo;
    }
}
