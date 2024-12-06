package dao;

import adt.*;
import entity.*;

public class MenuItemDAO {

    public ListInterface<MenuItem> initMenuItems() {
        ListInterface<MenuItem> menuList = new ArrayList<>();

        menuList.add(new MenuItem("Chicken Perperoni", 15.00));
        menuList.add(new MenuItem("Hawaiian Chicken", 15.00));
        menuList.add(new MenuItem("BBQ Chicken", 15.00));

        return menuList;
    }

}
