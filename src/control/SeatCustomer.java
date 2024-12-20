package control;

import adt.ListInterface;
import adt.QueueInterface;
import boundary.SeatCustomerUI;
import entity.Customer;
import entity.Reservation;
import entity.Table;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SeatCustomer {
    private SeatCustomerUI seatCustomerUI;
    private TableMaintenance tableMaintenance;
    private ReservationMaintenance reservationMaintenance;
    private WaitlistMaintenance waitlistMaintenance;
    private QueueInterface<Customer> waitlist;
    private ListInterface<Table> tables;
    private ListInterface<Reservation> reservations;

    public SeatCustomer() {
        seatCustomerUI = new SeatCustomerUI();
        waitlistMaintenance = new WaitlistMaintenance();
        tableMaintenance = TableMaintenance.getInstance();
        reservationMaintenance = new ReservationMaintenance(10, 22);
        tables = tableMaintenance.getListOfTables();
        reservations = reservationMaintenance.listReservations();
        waitlist = waitlistMaintenance.getWaitlistQueue();
    }

    public void viewTableLayout() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm");
        System.out.println("=======================================");
        System.out.println("Table Layout (Current Time: " + LocalTime.now().format(format) + ")");
        System.out.println("=======================================");
        System.out.println("Legend:");
        System.out.println("(X) = Occupied(Un-selectable)");
        System.out.println("(R) = Reserved(Un-selectable)");
        System.out.println("(*) = Available");
        System.out.println();
        System.out.println("Tables:");
        tableMaintenance.updateStatus();
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            Table table = tables.getEntry(i);
            String status = " ";
            if (table.getStatus() == "[Occupied]") {
                status = "X";
            } else if (table.getStatus() == "[Reserved]") {
                status = "R";
            } else {
                status = "*";
            }
            System.out.printf("Table #%-2d (%s)\n", table.getTableNo(), status);
        }

    }

    public Integer findFreeTable() {
        tableMaintenance.updateStatus();
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            Table table = tables.getEntry(i);
            if (table.getStatus() == "[Empty]") {
                return table.getTableNo();
            }
        }
        return null;
    }

    public void seatWaitlist() {
        if (!tables.isEmpty()) {
            Integer freeTableIndex = findFreeTable();
            if (freeTableIndex == null) {
                System.out.println("All tables are currently occupied. Please wait to be seated.");
                System.out.println();
            } else {
                Customer customer = waitlist.dequeue();
                Table table = tables.getEntry(freeTableIndex);
                table.occupy(customer);
                System.out.println("Seated " + customer.getName() + " at table " + freeTableIndex);
            }
        } else {
            waitlistMaintenance.displayEmptyWaitlistMessage();
        }
    }

    public void seatReservation() {

    }

    public void runSeatCustomer() {
        int choice = 0;
        do {
            choice = getMainMenuChoice();
            switch (choice) {
                case 1:
                    seatWaitlist();
                    break;
                case 2:
                    seatReservation();
                    break;
                case 3:
                    viewTableLayout();
                    break;
                case 4:
                    System.out.println("Exiting");
                    System.out.println();
                    break;
            }
        } while (choice != 4);
    }

    public int getMainMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        System.out.println("--- Seat Customer ---");
        System.out.println("1. Seat Customer from Waitlist");
        System.out.println("2. Seat Customer from Reservation List");
        System.out.println("3. View Table Layout");
        System.out.println("4. Exit");
        System.out.print("Enter Your Choice >> ");
        choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void main(String[] args) {
        SeatCustomer seatCustomer = new SeatCustomer();
        seatCustomer.runSeatCustomer();
    }
}
