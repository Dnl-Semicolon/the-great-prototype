package control;

import boundary.ReservationsUI;

public class Reservations {
    private ReservationsUI reservationMaintenanceUI = new ReservationsUI();
    public void runReservationMaintenance() {
        int choice = 0;
        do {
            choice = reservationMaintenanceUI.getMainMenuChoice();
            switch (choice) {

            }
        } while (choice != 6);
    }
}
