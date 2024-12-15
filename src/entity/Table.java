package entity;

import adt.*;

public class Table {

    public int tableNo;
    public ListInterface<Order> orders = new ArrayList<>();
    public Customer customer;

    public Table(int tableNo) {
        this.tableNo = tableNo;
        customer = null;
    }

    @Override
    public String toString() {
        return "Table{" + "tableNo=" + tableNo + '}';
    }
}
