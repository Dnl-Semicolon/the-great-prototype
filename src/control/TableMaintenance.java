package control;

import adt.ListInterface;
import boundary.TableMaintenanceUI;
import dao.TableDAO;
import entity.Table;

public class TableMaintenance {
    private TableMaintenanceUI tableMaintenanceUI;
    private TableDAO tableDAO;
    private ListInterface<Table> tables;

    public TableMaintenance() {
        tableMaintenanceUI = new TableMaintenanceUI();
        tableDAO = new TableDAO();
        tables = tableDAO.retrieveFromFile();
    }

    private void addTable() {
    }

    public void runTableMaintenance() {
        int choice = 0;
        do {
            choice = tableMaintenanceUI.getMainMenuChoice();

            switch (choice) {
                case 1:
                    addTable();
                    break;
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        TableMaintenance tableMaintenance = new TableMaintenance();
        tableMaintenance.runTableMaintenance();
    }
}
