package boundary;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalDate;

public class ReservationsUI {
    private Scanner scanner = new Scanner(System.in);
    public int getMainMenuChoice() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M d, yyyy");

        int choice = 0;
        System.out.println("=======================================");
        System.out.println("Welcome to the Restaurant Reservation");
        System.out.println("(Current Date: )");
        System.out.println("=======================================");
        return choice;
    }
}/*

=======================================
Welcome to the Restaurant Reservation CLI
(Current Date: April 1, 2024)
Reservations can only be made for April 2, 2024
=======================================
1. View Availability
2. Add Reservation
3. Modify Reservation
4. Cancel Reservation
5. List All Reservations
6. Exit
Please select an option:
















*/