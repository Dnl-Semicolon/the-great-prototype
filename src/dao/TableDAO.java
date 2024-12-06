package dao;

import adt.*;
import entity.*;

public class TableDAO {

    public ListInterface<Table> initTables() {
        ListInterface<Table> tableList = new ArrayList<>();
        tableList.add(new Table(1));
        tableList.add(new Table(2));
        tableList.add(new Table(3));
        return tableList;
    }

}
