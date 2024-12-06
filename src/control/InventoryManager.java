package control;

import boundary.*;

public class InventoryManager {

    private InventoryManagerUI inventoryManagerUI = new InventoryManagerUI();

    public void runInventoryManager() {
        int choice = 0;
        do {
            choice = inventoryManagerUI.getMenuChoice();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }

        } while (choice !=5);
    }
}
