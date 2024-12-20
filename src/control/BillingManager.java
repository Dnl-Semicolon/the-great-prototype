package control;

import adt.*;
import boundary.*;
import dao.*;
import entity.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author Choo Zheng Yi
 */
public class BillingManager {

    private BillingManagerUI billUI = new BillingManagerUI();
    private ReceiptUI receiptUI = new ReceiptUI();
    private SalesReportUI salesReportUI = new SalesReportUI();
    private Receipt receipt = new Receipt();
    private SalesReport salesReport = new SalesReport();
    private ReceiptDAO receiptDAO = new ReceiptDAO();
    private SalesReportDAO salesReportDAO = new SalesReportDAO();
    public ListInterface<Bill> itemsList = new ArrayList();
    private HashMap<LocalDate, Double> salesReports = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public BillingManager() {
        salesReports = salesReportDAO.readReport();
    }

    public void runBillingManager() {
        int choice = 0;
        do {
            choice = billUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    payment();
                    break;
                case 2:
                    getsalesReport();
                    break;
                case 0:
                    System.out.println("Exiting......");
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");
                    System.out.println();
            }
        } while (choice != 0);
    }

    public void payment() {
        manageTable();
        billUI.getPaymentMenu();
        int choice = 0;
        do {
            choice = billUI.getPaymentMenuChoice();
            switch (choice) {
                case 1:
                    calculateBill();
                    runBillingManager();
                    break;
                case 2:
                    getItemList();
                    break;
                case 0:
                    System.out.println("Exiting......");
                    runBillingManager();
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");
            }
        } while (choice != 0);
    }

    public int checkValidation() {
        int choice;

        while (true) {
            System.out.print("Enter choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a integer!");
                scanner.next();
            }
        }
        return choice;
    }

    public void manageTable() {
        billUI.getTable();

        int tableChoice = checkValidation();
        System.out.println("");

        if (tableChoice == 0) {
            runBillingManager();
        }

    }

    public void calculateBill() {
        double subtotal = calculateSubTotal();
        int tax = calculateTax();
        int discount = calculateDiscount();
        int serviceCharge = calculateServiceCharge();
        double grandTotal = calculateGrandTotal();

        receipt.setSubtotal(subtotal);
        receipt.setTax(tax);
        receipt.setDiscount(discount);
        receipt.setServiceCharge(serviceCharge);
        receipt.setGrandTotal(grandTotal);
        receipt.setItems(itemsList);

        receiptUI.setBill(receipt);
        System.out.println(receiptUI.printReceipt());

        LocalDate today = LocalDate.now();
        double totalSales = salesReports.getOrDefault(today, 0.0) + grandTotal;
        salesReports.put(today, totalSales);
        salesReportDAO.writeReport(salesReports);

    }

    public double calculateSubTotal() {
        double subtotal = 0.0;
        for (int i = 1; i <= itemsList.getNumberOfEntries(); i++) {
            Bill item = itemsList.getEntry(i);
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    public int calculateTax() {
        final int TAX = 6;
        return TAX;
    }

    public int calculateDiscount() {
        int discount = 0;

        LocalTime timeNow = LocalTime.now();

        LocalTime breakfastDiscountStart = LocalTime.of(8, 0);
        LocalTime breakfastDiscountEnd = LocalTime.of(10, 0);

        LocalTime lunchDiscountStart = LocalTime.of(12, 0);
        LocalTime lunchDiscountEnd = LocalTime.of(14, 0);

        LocalTime dinnerDiscountStart = LocalTime.of(18, 0);
        LocalTime dinnerDiscountEnd = LocalTime.of(20, 0);

        if (timeNow.isAfter(breakfastDiscountStart) && timeNow.isBefore(breakfastDiscountEnd)) {
            discount = 5;
        }
        if (timeNow.isAfter(lunchDiscountStart) && timeNow.isBefore(lunchDiscountEnd)) {
            discount = 10;
        }
        if (timeNow.isAfter(dinnerDiscountStart) && timeNow.isBefore(dinnerDiscountEnd)) {
            discount = 8;
        }
        return discount;
    }

    public int calculateServiceCharge() {
        final int SERVICE_CHARGE = 10;
        return SERVICE_CHARGE;
    }

    public double calculateGrandTotal() {
        double subtotal = calculateSubTotal();
        double tax = (subtotal * calculateTax()) / 100;
        double discount = (subtotal * calculateDiscount()) / 100;
        double serviceCharge = (subtotal * calculateServiceCharge()) / 100;

        return subtotal + tax - discount + serviceCharge;
    }

    public double calculateSales(double totalSales) {

        return totalSales;
    }

    public void showTodayReport() {

        LocalDate today = LocalDate.now();
        salesReports = salesReportDAO.readReport();
        Double todaySales = salesReports.get(today);

        if (todaySales != null) {
            System.out.println("\tToday date  : " + today);
            System.out.printf("\tToday sales : RM%.2f\n", todaySales);
        } else {
            System.out.println("No sales data available for today.");
        }
    }

    public void searchSalesReport(LocalDate searchDate) {

        salesReports = salesReportDAO.readReport();
        Double totalSales = salesReports.get(searchDate);

        if (totalSales != null) {
            System.out.println("\tDate        : " + searchDate);
            System.out.printf("\tTotal sales : RM%.2f\n", totalSales);
        } else {
            System.out.println("No sales data available for " + searchDate + ".");
        }
    }

    public void getsalesReport() {
        int choice;
        do {
            billUI.getReportMenu();
            choice = checkValidation();
            switch (choice) {
                case 1:
                    salesReportUI.printReportToday();
                    break;

                case 2:
                    LocalDate searchDate;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    boolean validateDate = false;
                    scanner.nextLine();

                    while (!validateDate) {
                        System.out.print("Enter a date (yyyy-MM-dd): ");
                        String date = scanner.nextLine();

                        try {
                            if (date.isEmpty()) {
                                System.out.println("Date cannot be empty!");
                            } else {
                                searchDate = LocalDate.parse(date, formatter);
                                salesReportUI.printSearchReport(searchDate);
                                validateDate = true;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format! Please enter yyyy-MM-dd!");
                        }
                    }
                    break;

                case 0:
                    System.out.println("Exting......");
                    runBillingManager();
                    break;

                default:
                    System.out.println("Invalid choice, please try again!");
            }
        } while (choice != 0);
    }

    public void getItemList() {
        int choice;
        if (itemsList.getNumberOfEntries() == 0) {
            System.out.println("No items been ordered!");
        } else {

            do {
                billUI.displayItem(itemsList);
                choice = checkValidation();
                switch (choice) {
                    case 1:
                        System.out.println("Enter the item number to remove or 0 to Exit ");
                        int itemNumber = checkValidation();
                        if (itemNumber > 0 && itemNumber <= itemsList.getNumberOfEntries()) {
                            Bill removedItem = itemsList.remove(itemNumber);
                            System.out.println("The item removed: " + removedItem.getItem());
                            getItemList();
                            break;
                        } else if (itemNumber == 0) {
                            System.out.println("Exting......");
                            runBillingManager();
                            break;
                        } else {
                            System.out.println("Invalid item number.");
                        }
                        break;

                    case 0:
                        System.out.println("Exting......");
                        runBillingManager();
                        break;

                    default:
                        System.out.println("Invalid choice, please try again!");
                }
                break;
            } while (choice != 0);
        }
    }
}
