package entity;

import java.io.Serializable;

public class Customer implements Serializable {
    public String name;
    public int partySize;
    public String phoneNumber;

    public Customer() {

    }

    public Customer(String name, int partySize, String phoneNumber) {
        this.name = name;
        this.partySize = partySize;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static boolean isValidName(String name) {
        boolean isValid = true;
        if (!name.isEmpty()) {
            for (int i = 0; i < name.length() && isValid; i++) {
                char currentChar = name.charAt(i);
                if (!Character.isLetter(currentChar) && currentChar != ' ') {
                    isValid = false;
                }
            }
            return isValid;
        } else {
            isValid = false;
            return isValid;
        }
    }

    public static boolean isValidPartySize(int customerPartySize) {
        return customerPartySize > 0 && customerPartySize <= 100;
    }

    public static boolean isValidContactNumber(String customerContactNumber) {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder outputStr = new StringBuilder();
        outputStr.append(String.format("%-17s %-4d %18s", name, partySize, phoneNumber));
        return outputStr.toString();
    }
}
