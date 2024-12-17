package control;

import adt.ArrayList;
import adt.ListInterface;
import boundary.ReservationMaintenanceUI;
import dao.ReservationDAO;
import entity.Reservation;
import entity.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ReservationMaintenance {
    private ReservationMaintenanceUI reservationMaintenanceUI;
    private ReservationDAO reservationDAO;
    private ListInterface<Reservation> reservationList = new ArrayList<>();
    private static final String OPENING_TIME = "10:00 AM";
    private static final String CLOSING_TIME = "10:00 PM";
    private static final int TIME_INTERVAL = 30; // In minutes
    private TableMaintenance tableMaintenance;

    public ReservationMaintenance() {
        reservationMaintenanceUI = new ReservationMaintenanceUI();
        reservationDAO = new ReservationDAO();
//        reservationList = reservationDAO.retrieveFromFile();
        tableMaintenance = new TableMaintenance();
    }

    public void timelineView() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime openingTime = LocalTime.parse(OPENING_TIME, timeFormatter);
        LocalTime closingTime = LocalTime.parse(CLOSING_TIME, timeFormatter);

        ListInterface<String> timeSlots = new ArrayList<>();
        LocalTime currentTime = openingTime;

        while (!currentTime.isAfter(closingTime)) {
            timeSlots.add(currentTime.format(timeFormatter));
            currentTime = currentTime.plusMinutes(TIME_INTERVAL);
        }

        StringBuilder table = new StringBuilder();
        table.append("Table   | ");
        String slot;
        int n = timeSlots.getNumberOfEntries();
        for (int i = 1; i <= n; i++) {
            slot = timeSlots.getEntry(i);
            table.append(String.format("%-9s| ", slot));
        }
        table.append("\n");
        table.append("-".repeat(9 + (n * 11))).append("\n");

        reservationMaintenanceUI.timelineView(table.toString());
    }

    public void displayReservations() {
        if (!reservationList.isEmpty()) {
            reservationMaintenanceUI.displayReservations(getReservations());
        } else {
            reservationMaintenanceUI.displayEmptyReservationListMessage();
        }
    }

    public String getReservations() {
        return "";
    }

    public void makeReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Number of Guests: ");
        int partySize = scanner.nextInt();
        scanner.nextLine();

        ListInterface<Table> tables = tableMaintenance.getListOfTables();

        if (tables.isEmpty()) {
            System.out.println("Sorry, no tables available for your party size.");
            return;
        }
        Table availableTable;
        System.out.println("\nAvailable Tables:");
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            availableTable = tables.getEntry(i);
            System.out.printf("Table %d (Capacity: %d)\n",
                    availableTable.getTableNo(), availableTable.getCapacity());
        }

        System.out.print("\nEnter Reservation Date and Time (YYYY-MM-DD HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        LocalDateTime reservationTime;
        try {
            reservationTime = LocalDateTime.parse(dateTimeStr,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            System.out.println("Invalid date format. Reservation cancelled.");
            return;
        }

        // Select a table
        System.out.print("Enter Table Number: ");
        int tableNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Table selectedTable = null;
        boolean found = false;
        int n = tables.getNumberOfEntries();
        for (int i = 1; i <= n && !found; i++) {
            selectedTable = tables.getEntry(i);
            if (selectedTable.getTableNo() == tableNumber) {
                found = true;
            }
        }

        if (!found) {
            selectedTable = null;
        }

        if (selectedTable == null) {
            System.out.println("Invalid table selection. Reservation cancelled.");
            return;
        }


    }

    public void addReservation() {
        String dateInput = "";
        String timeInput = "";

        boolean isValidInput = false;
        while (!isValidInput) {
            dateInput = reservationMaintenanceUI.inputReservationDate();
            if (isValidReservationDate(dateInput)) {
                isValidInput = true;
            }
        }

        isValidInput = false;
        while (!isValidInput) {
            timeInput = reservationMaintenanceUI.inputReservationTime();
            if (isValidReservationTime(timeInput)) {
                isValidInput = true;
            }
        }

        System.out.println("Available Tables for 20/12/2024 at 18:30:");

    }

    private void runReservationMaintenance() {
        int choice = 0;
        do {
            choice = reservationMaintenanceUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    displayReservations();
                    break;
                case 3:
                    timelineView();
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Exiting system");
                    System.out.println();
                    break;
                default:
                    System.out.println("Error: Not A Valid Choice. Enter A Choice Within 1-5.");
                    System.out.println();
            }
        } while (choice != 5);
    }

    public static boolean isValidReservationDate(String dateInputStr) {
        if (dateInputStr == null || !dateInputStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        try {
            LocalDate inputDate = LocalDate.parse(dateInputStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            LocalDate today = LocalDate.now(); // Get today's date
            LocalDate tomorrow = today.plusDays(1); // Get tomorrow's date

            return inputDate.isEqual(tomorrow);

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isValidReservationTime(String timeInput) {
        if (timeInput == null || !timeInput.matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            return false;
        }

        try {
            LocalTime inputTime = LocalTime.parse(timeInput, DateTimeFormatter.ofPattern("HH:mm"));
            // Define business hours (8:00 AM to 10:00 PM)
            LocalTime openingTime = LocalTime.of(8, 0);
            LocalTime closingTime = LocalTime.of(22, 0);

            // Check if the input time is within business hours
            return !inputTime.isBefore(openingTime) && !inputTime.isAfter(closingTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        ReservationMaintenance reservationMaintenance = new ReservationMaintenance();
        reservationMaintenance.runReservationMaintenance();
    }
}
