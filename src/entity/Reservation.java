package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation implements Serializable {
    private LocalDateTime reservationDateTime;
    private LocalDate date;
    private String timeSlot;
    private String lastName;
    private int partySize;
    private String contactNumber;
    private int reservedTableNumber;
    private String reservationId;

    public Reservation() {
    }

    public Reservation(LocalDateTime reservationDateTime, String lastName, int partySize, String contactNumber, int reservedTableNumber) {
        this.reservationDateTime = reservationDateTime;
        this.lastName = lastName;
        this.partySize = partySize;
        this.contactNumber = contactNumber;
        this.reservedTableNumber = reservedTableNumber;
    }

    public Reservation(String reservationId, String lastName, int partySize, LocalDate date, String timeSlot, int reservedTableNumber) {
        this.reservationDateTime = null;
        this.lastName = lastName;
        this.partySize = partySize;
        this.date = date;
        this.timeSlot = timeSlot;
        this.reservedTableNumber = reservedTableNumber;
        this.reservationId = reservationId;
    }

    public boolean isReserveTime() {
        LocalTime reservationTime = parseTime();
        if (reservationTime == null) {
            return false;
        }
        if (!date.equals(LocalDate.now())) {
            return false;
        }

        LocalTime now = LocalTime.now();

        // Calculate 15 minutes before and after the reservation time
        LocalTime fifteenBefore = reservationTime.minusMinutes(15);
        LocalTime fifteenAfter = reservationTime.plusMinutes(15);

        // Return true if current time is within [reservationTime - 15 minutes, reservationTime + 15 minutes]
        return !now.isBefore(fifteenBefore) && !now.isAfter(fifteenAfter);
    }

    public LocalTime parseTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(timeSlot, formatter);
        } catch (Exception ex) {
            return null;
        }
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getReservedTableNumber() {
        return reservedTableNumber;
    }

    public void setReservedTableNumber(int reservedTableNumber) {
        this.reservedTableNumber = reservedTableNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public boolean isTimeToReserve() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(reservationDateTime.minusHours(1)) && now.isBefore(reservationDateTime);
    }

    @Override
    public String toString() {
        StringBuilder outputStr = new StringBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        outputStr.append(String.format("Customer: %s\n", this.lastName));
        outputStr.append("Date & Time: " + date.format(format) + " " + timeSlot + "\n");
        outputStr.append(String.format("Table: #%d\n", this.reservedTableNumber));
        outputStr.append(String.format("Party Size: %d", this.partySize));
        return outputStr.toString();
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
}
