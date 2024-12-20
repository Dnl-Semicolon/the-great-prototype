package control;

import adt.ArrayList;
import adt.ListInterface;
import boundary.TableMaintenanceUI;
import dao.TableDAO;
import entity.Table;

public class TableMaintenance {
    private static TableMaintenance singletonInstance;
    private TableMaintenanceUI tableMaintenanceUI;
    private TableDAO tableDAO;
    private ListInterface<Table> tables;
    private ListInterface<Table> occupiedTables;
    private ListInterface<Table> emptyTables;
    private ListInterface<Table> reservedTables;

    private TableMaintenance() {
        tableMaintenanceUI = new TableMaintenanceUI();
        tableDAO = TableDAO.getInstance();
        tables = tableDAO.retrieveFromFile();
        occupiedTables = new ArrayList<>();
        emptyTables = new ArrayList<>();
        reservedTables = new ArrayList<>();
    }

    public static TableMaintenance getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new TableMaintenance();
        }
        return singletonInstance;
    }

    public Table getCurrentTable(int tableNumber) {
        Table currentTable = null;
        currentTable = tables.getEntry(tableNumber);
        return currentTable;
    }

    public void updateStatus() {
        for (int i = 1; i <= tables.getNumberOfEntries(); i++) {
            Table table = tables.getEntry(i);
            table.updateStatus();
            if (table.getStatus() == "[Occupied]") {
                occupiedTables.add(table);
            } else if (table.getStatus() == "[Reserved]") {
                reservedTables.add(table);
            } else {
                emptyTables.add(table);
            }
        }
    }

    public ListInterface<Table> getListOfTables() {
        return tables;
    }

    public void displayAllTables() {
        if (!tables.isEmpty()) {
            tableMaintenanceUI.displayAllTables(getAllTables());
        } else {
            tableMaintenanceUI.displayNoTablesMessage();
        }
    }

    public String getAllTables() {
        StringBuilder inputStr = new StringBuilder();
        Table tableEntry;
        int n = tables.getNumberOfEntries();
        for (int i = 1; i <= n; i++) {
            tableEntry = tables.getEntry(i);
            inputStr.append(String.format("%3d.   ", i));
            inputStr.append(tableEntry.toString());
            inputStr.append("\n");
        }
        return inputStr.toString();
    }

    public void addTable() {
        int tableNoInput = 0;
        boolean isValidInput = false;
        while (!isValidInput) {
            tableNoInput = tableMaintenanceUI.inputNewTableNumber();
            if (Table.isValidTableNumber(tableNoInput)) {
                isValidInput = true;
            }
        }
        Table newTable = new Table(tableNoInput);
        tables.add(newTable);
        tableDAO.saveToFile(tables);
        displayAllTables();
    }

    public void save() {
        tableDAO.saveToFile(tables);
    }

    public void runTableMaintenance() {
        int choice = 0;
        do {
            choice = tableMaintenanceUI.getMainMenuChoice();

            switch (choice) {
                case 1:
                    addTable();
                    break;
                case 2:
                    displayAllTables();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Exiting system");
                    System.out.println();
                    break;
                default:

            }
        } while (choice != 5);
    }

    public static void main(String[] args) {
        TableMaintenance tableMaintenance = new TableMaintenance();
        tableMaintenance.runTableMaintenance();
    }
}
