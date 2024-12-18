package control;

import adt.*;
import adt.ArrayList;
import boundary.ReservationsUI;
import entity.Reservation;
import entity.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Objects;

public class Reservations {
    private ReservationsUI reservationMaintenanceUI = new ReservationsUI();
    private int operatingHoursStart;
    private int operatingHoursEnd;
    private TableMaintenance tableMaintenance;
    private int tables;
    private Map<Key, ListInterface<Reservation>> reservations;
    private int lastId;

    public Reservations(int operatingHoursStart, int operatingHoursEnd) {
        this.operatingHoursStart = operatingHoursStart;
        this.operatingHoursEnd = operatingHoursEnd;
        tableMaintenance = new TableMaintenance();
        tables = tableMaintenance.getListOfTables().getNumberOfEntries();
        reservations = new HashMap<>();
        lastId = 1000;
    }

    public LocalDate getTomorrowDate() {
        return LocalDate.now().plusDays(1);
    }

    public ListInterface<String> timeSlots() {
        ListInterface<String> slots = new ArrayList<>();
        for (int h = operatingHoursStart; h < operatingHoursEnd; h++) {
            slots.add(String.format("%02d:00", h));
        }
        return slots;
    }

    public String parseTime(String timeInput) {
        try {
            timeInput = timeInput.trim().toUpperCase();
            String[] parts = timeInput.split(" ");
            if (parts.length != 2) return null;
            String timePart = parts[0];
            String ampm = parts[1];

            String[] hhmm = timePart.split(":");
            if (hhmm.length != 2) return null;

            int hour = Integer.parseInt(hhmm[0]);
            int minute = Integer.parseInt(hhmm[1]);
            if (minute != 0) return null;

            if (!ampm.equals("AM") && !ampm.equals("PM")) return null;

            if (!ampm.equals("PM") && hour != 12) {
                hour += 12;
            } else if (!ampm.equals("AM") && hour == 12) {
                hour = 0;
            }

            if (hour < operatingHoursStart || hour >= operatingHoursEnd) return null;

            return String.format("%02d:%02d", hour, minute);
        } catch (Exception ex) {
            return null;
        }
    }

    public int checkAvailability(LocalDate date, String timeSlot) {
        Key key = new Key(date, timeSlot);
        if (!reservations.containsKey(key)) {
            return tables;
        } else {
            return tables - reservations.get(key).getNumberOfEntries();
        }
    }

    public void viewAvailability() {
        LocalDate date = getTomorrowDate();
        System.out.println("Time Slots for " + date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")) + ":");
        ListInterface<String> timeSlots = timeSlots();
        String slot = "";
        for (int i = 1; i <= timeSlots.getNumberOfEntries(); i++) {
            slot = timeSlots.getEntry(i);
            int available = checkAvailability(date, slot);
            System.out.println(format24to12(slot) + " - Available Tables: " + available);
        }
        System.out.print("\nPress ENTER to return to main menu...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private Integer findFreeTable(LocalDate date, String timeSlot) {
        Key key = new Key(date, timeSlot);
        Set<Integer> used = new HashSet<>();
        if (reservations.containsKey(key)) {
            ListInterface<Reservation> reservationList = reservations.get(key);
            for (int i = 1; i <= reservationList.getNumberOfEntries(); i++) {
                Reservation r = reservationList.getEntry(i);
                used.add(r.getReservedTableNumber());
            }
        }
        for (int i = 1; i <= tables; i++) {
            if (!used.contains(i)) return i;
        }
        return null;
    }

    public String generateReservationId() {
        lastId++;
        return "R" + lastId;
    }

    public void makeReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name");
        String name = scanner.nextLine().trim();
        System.out.println("Enter part size (1-4): ");
        int partySize;
        try {
            partySize = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Invalid party size.");
            System.out.print("Press ENTER to continue...");
            scanner.nextLine();
            return;
        }
        System.out.print("Enter reservation time (HH:MM AM/PM) for " + getTomorrowDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) + ":");
        String timeInput = scanner.nextLine().trim();
        String timeSlot = parseTime(timeInput);
        if (timeSlot == null) {
            System.out.println("Invalid time or time out of operating hours.");
            System.out.println();
            System.out.print("Press ENTER to continue...");
            scanner.nextLine();
            return;
        }

        Reservation added = addReservation(name, partySize, getTomorrowDate(), timeSlot);
        if (added == null) {
            System.out.print("Press ENTER to continue...");
            scanner.nextLine();
            return;
        }
        System.out.println("Reservation created successfully!");
        System.out.println(added);
        System.out.print("Press ENTER to continue...");
        scanner.nextLine();
    }

    public Reservation addReservation(String customerName, int partySize, LocalDate date, String timeSlot) {
        if (partySize < 1 || partySize > 4) {
            System.out.println("Error: Party size must be between 1 and 4.");
            return null;
        }

        if (!date.equals(getTomorrowDate())) {
            System.out.println("Error: Reservations must be made exactly one day in advance.");
            return null;
        }

        int available = checkAvailability(date, timeSlot);
        if (available == 0) {
            System.out.println("Error: No tables available at that time.");
            return null;
        }

        Integer tableNum = findFreeTable(date, timeSlot);
        if (tableNum == null) {
            System.out.println("Error: No tables available at that time.");
            return null;
        }

        String rid = generateReservationId();
        Reservation r = new Reservation(rid, customerName, partySize, date, timeSlot, tableNum);
        Key key = new Key(date, timeSlot);
        if (!reservations.containsKey(key)) {
            reservations.put(key, new ArrayList<>());
        }
        reservations.get(key).add(r);
        return r;
    }

    private Reservation findReservationById(String reservationId) {
        ListInterface<ListInterface<Reservation>> rLists = (ListInterface<ListInterface<Reservation>>) reservations.values();
        int n = rLists.getNumberOfEntries();
        for (int i = 1; i <= n; i++) {
            ListInterface<Reservation> rList = rLists.getEntry(i);
            for (int j = 1; j<= rList.getNumberOfEntries(); j++) {
                Reservation r = rList.getEntry(j);
                if (r.getReservationId().equals(reservationId)) {
                    return r;
                }
            }
        }
        return null;
    }

    public ListInterface<Reservation> listReservations() {
        ListInterface<Reservation> all = new ArrayList<>();
        ListInterface<ListInterface<Reservation>> rLists = (ListInterface<ListInterface<Reservation>>) reservations.values();
        int n = rLists.getNumberOfEntries();
        for (int i = 1; i <= n; i++) {
            ListInterface<Reservation> rList = rLists.getEntry(i);
            for (int j = 1; j<= rList.getNumberOfEntries(); j++) {
                Reservation r = rList.getEntry(j);
                all.add(r);
            }
        }
        return all;
    }

    public void displayReservations() {
        ListInterface<Reservation> allRes = listReservations();
        if (allRes.isEmpty()) {
            System.out.println("No upcoming reservations.");
            System.out.println();
        } else {
            System.out.println("Upcoming Reservations for " + getTomorrowDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) + ":");
            System.out.println("-------------------------------------------------");
            for (int i = 1; i <= allRes.getNumberOfEntries(); i++) {
                Reservation r = allRes.getEntry(i);
                System.out.println(r);
                System.out.println("-------------------------------------------------");
            }
        }
    }

    private String format24to12(String slot) {
        String[] parts = slot.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        String ampm = "AM";
        int hour12 = hour;
        if (hour == 0) {
            hour12 = 12;
        } else if (hour == 12) {
            ampm = "PM";
        } else if (hour > 12) {
            hour12 = hour - 12;
            ampm = "PM";
        }
        return String.format("%d:%02d %s", hour12, minute, ampm);
    }

    static class Key {
        LocalDate date;
        String timeSlot;

        Key(LocalDate date, String timeSlot) {
            this.date = date;
            this.timeSlot = timeSlot;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Key)) return false;
            Key k = (Key) o;
            return k.date.equals(this.date) && k.timeSlot.equals(this.timeSlot);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date, timeSlot);
        }
    }

    public void runReservationMaintenance() {
        int choice = 0;
        do {
            choice = getMainMenuChoice();
            switch (choice) {
                case 1:
                    viewAvailability();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 5:
                    displayReservations();
                    break;
                case 6:
                    System.out.println("Exiting");
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid Choice.");
                    System.out.println();
            }
        } while (choice != 6);
    }

    public static void main(String[] args) {
        Reservations reservationMaintenance = new Reservations(10, 22);
        reservationMaintenance.runReservationMaintenance();
    }

    public int getMainMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        System.out.println("=======================================");
        System.out.println("Welcome to the Restaurant Reservation");
        System.out.println("(Current Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")) + ")");
        System.out.println("Reservations can only be made for " + getTomorrowDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        System.out.println("=======================================");
        System.out.println("1. View Availability");
        System.out.println("2. Add Reservation");
        System.out.println("3. Modify Reservation");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. List All Reservations");
        System.out.println("6. Exit");
        System.out.print("Enter Your Choice (1-6) >> ");
        choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
}
