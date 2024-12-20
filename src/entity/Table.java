package entity;

import adt.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Table implements Serializable {

    private ListInterface<OrderItem> orderList;

    public int tableNo;
    public ListInterface<Order> orders;
    public Customer customer;
    public ListInterface<Reservation> reservations;
    private LocalDateTime occupiedUntil = null;
    private String status;

    public Table() {
        this(0);
    }

    public Table(int tableNo) {
        this.orderList = new ArrayList<>();
        this.tableNo = tableNo;
        orders = new ArrayList<>();
        customer = null;
        reservations = new ArrayList<>();
        status = "[Empty]";
    }

    public void occupy(Customer customer) {
        setCustomer(customer);
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public void updateStatus() {
        if (customer != null) {
            status = "[Occupied]";
        }
        if (!reservations.isEmpty()) {
            for (int i = 1; i <= reservations.getNumberOfEntries(); i++) {
                Reservation reservation = reservations.getEntry(i);
                boolean isReserveTime = reservation.isReserveTime();
                if (isReserveTime) {
                    status = "[Reserved]";
                    break;
                }
            }
            if (!status.equals("[Reserved]")) {
                status = "[Empty]";
            }
        } else {
            status = "[Empty]";
        }
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder inputStr = new StringBuilder();
        String tableData = String.format("Table %-2d", tableNo);
        String statusData = "";
        String remarksData = "None";
        if (this.customer == null) {
            statusData = "[Empty]";
//            if (this.reservation == null) {
//                statusData = "[Empty]";
//                remarksData = "None";
//            } else if (this.reservation.isTimeToReserve()) {
//                statusData = "[Reserved]";
//                remarksData = "Reservation Time: ";
//            } else {
//                statusData = "[Empty]";
//                remarksData = "None";
//            }
        } else {
            statusData = "[Occupied]";
            remarksData = "Session Started: ";
        }

        String strFormat = String.format("%s   %-10s   %s", tableData, statusData, remarksData);
        inputStr.append(strFormat);
        return inputStr.toString();
    }


    // Getters and Setters
    public int getTableNo() {return tableNo;}
    public void setTableNo(int tableNo) {this.tableNo = tableNo;}
    public ListInterface<Order> getOrders() {return orders;}
    public void setOrders(ListInterface<Order> orders) {this.orders = orders;}
    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}
    public ListInterface<Reservation> getReservations() {return reservations;}
    public static boolean isValidTableNumber(int tableNumber) {return tableNumber > 0 && tableNumber <= 100;}

    public ListInterface<OrderItem> getOrderList() {return orderList;}
    public boolean addToOrderList(OrderItem newOrderItem) {
        for (int i = 1; i <= orderList.getNumberOfEntries(); i++) {
            OrderItem orderListItem = orderList.getEntry(i);
            if (newOrderItem.equals(orderListItem)) {
                orderListItem.addToQuantity(newOrderItem.getQuantity());
                return true;
            }
        }

        return orderList.add(newOrderItem);
    }

    public boolean isOrderListEmpty() {
        return orderList.isEmpty();
    }
}
