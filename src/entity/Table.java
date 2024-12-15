package entity;

import adt.*;

import java.io.Serializable;

public class Table implements Serializable {

    public int tableNo;
    public ListInterface<Order> orders;
    public Customer customer;
    public Reservation reservation;

    public Table() {
        this(0);
    }

    public Table(int tableNo) {
        this.tableNo = tableNo;
        orders = new ArrayList<>();
        customer = null;
        reservation = null;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public ListInterface<Order> getOrders() {
        return orders;
    }

    public void setOrders(ListInterface<Order> orders) {
        this.orders = orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public static boolean isValidTableNumber(int tableNumber) {
        return tableNumber > 0 && tableNumber <= 100;
    }

    @Override
    public String toString() {
        StringBuilder inputStr = new StringBuilder();
        String tableData = String.format("Table %-2d", tableNo);
        String statusData = "";
        String remarksData = "None";
        if (this.customer == null) {
            if (this.reservation == null) {
                statusData = "[Empty]";
                remarksData = "None";
            } else if (this.reservation.isTimeToReserve()) {
                statusData = "[Reserved]";
                remarksData = "Reservation Time: ";
            } else {
                statusData = "[Empty]";
                remarksData = "None";
            }
        } else {
            statusData = "[Occupied]";
            remarksData = "Session Started: ";
        }

        String strFormat = String.format("%s   %-10s   %s", tableData, statusData, remarksData);
        inputStr.append(strFormat);
        return inputStr.toString();
    }
    //+--------+----------+------------+----------------------------+
    //| Entry  | Table    | Status     | Remarks                    |
    //+--------+----------+------------+----------------------------+
    //| 1      | Table 1  | [Empty]    | None                       |
    //| 2      | Table 2  | [Reserved] | Reservation Time: 7:30 PM  |
    //| 3      | Table 3  | [Occupied] | Session Started: 7:00 PM   |
    //| 4      | Table 4  | [Empty]    | None                       |
    //+--------+----------+------------+----------------------------+
}
