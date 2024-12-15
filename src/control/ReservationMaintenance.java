package control;

import adt.ArrayList;
import adt.ListInterface;
import boundary.ReservationMaintenanceUI;
import dao.ReservationDAO;
import entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationMaintenance {
    private ReservationMaintenanceUI reservationMaintenanceUI;
    private ReservationDAO reservationDAO;
    private ListInterface<Reservation> reservationList = new ArrayList<>();

    public ReservationMaintenance() {
        reservationMaintenanceUI = new ReservationMaintenanceUI();
        reservationDAO = new ReservationDAO();
        reservationList = reservationDAO.retrieveFromFile();
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

    public void addReservation() {
        String dateInput = "";

        boolean isValidInput = false;
        while (!isValidInput) {
            dateInput = reservationMaintenanceUI.inputReservationDate();
            if (isValidReservationDate(dateInput)) {
                isValidInput = true;
            }
        }

    }

    private void runReservationMaintenance() {
        int choice = 0;
        do {
            choice = reservationMaintenanceUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    addReservation();
                    break;
                case 2:
                    displayReservations();
                    break;
                case 3:
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
