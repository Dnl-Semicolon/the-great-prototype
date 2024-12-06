package entity;

public class Customer {
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

    @Override
    public String toString() {
        StringBuilder outputStr = new StringBuilder();
        outputStr.append(String.format("%-17s  %-4d %18s", name, partySize, phoneNumber));
        return outputStr.toString();
    }
}
