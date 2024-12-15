package entity;

import java.time.LocalDateTime;

public class Reservation {
    private LocalDateTime reservationDateTime;
    private String lastName;
    private int partySize;
    private String contactNumber;
    private int reservedTableNumber;

    public Reservation() {
    }

    public Reservation(LocalDateTime reservationDateTime, String lastName, int partySize, String contactNumber, int reservedTableNumber) {
        this.reservationDateTime = reservationDateTime;
        this.lastName = lastName;
        this.partySize = partySize;
        this.contactNumber = contactNumber;
        this.reservedTableNumber = reservedTableNumber;
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
}
