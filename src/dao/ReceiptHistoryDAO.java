package dao;

import adt.HashMap;

/**
 *
 * @author ZY
 */
public class ReceiptHistoryDAO {

    private HashMap<Integer, Receipt> receiptHistory;

    public ReceiptHistoryDAO() {
        receiptHistory = new HashMap<>();
    }

    public void addReceipt(Receipt receipt) {
        receiptHistory.put(receipt.getReceiptNo(), receipt);
    }

    public Receipt getReceipt(int receiptNo) {
        return receiptHistory.get(receiptNo);
    }

    public void printReceiptHistory() {
        if (receiptHistory.isEmpty()) {
            System.out.println("No receipts found!");
        } else {
            System.out.println("==================== Receipt History ====================");
            System.out.println("=========================================================");
        }
    }
}
