package entity;

import adt.*;

public class Order {

    public int tableNo;
    public ListInterface<OrderItem> orderItems = new ArrayList<>();

    public Order(int tableNo) {
        this.tableNo = tableNo;
    }

    public Order() {
        this(0);
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public ListInterface<OrderItem> getOrderItems() {
        return orderItems;
    }

}
