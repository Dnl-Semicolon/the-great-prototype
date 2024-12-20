package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Ingredient;

import java.io.*;

public class IngredientDAO {

    private static IngredientDAO singletonInstance;

    private String fileName = "ingredient.dat";

    private IngredientDAO() {}

    public static IngredientDAO getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new IngredientDAO();
        }
        return singletonInstance;
    }

    public void saveToFile(ListInterface<Ingredient> ingredientList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(ingredientList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<Ingredient> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<Ingredient> ingredientList = new ArrayList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            ingredientList = (ArrayList<Ingredient>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.INVENTORY");
            ingredientList = initBackup();
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.INVENTORY");
            ingredientList = initBackup();
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.INVENTORY");
            ingredientList = initBackup();
        } finally {
            return ingredientList;
        }
    }

    public ListInterface<Ingredient> initBackup() {
        ListInterface<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("I1001", "Chicken Patty", 100));
        ingredientList.add(new Ingredient("I1002", "Beef Patty", 100));
        ingredientList.add(new Ingredient("I1003", "Burger Bun", 100));
        ingredientList.add(new Ingredient("I1004", "Spaghetti Noodles", 100));
        ingredientList.add(new Ingredient("I1005", "Minced Chicken", 100));
        ingredientList.add(new Ingredient("I1006", "Tomato", 100));
        ingredientList.add(new Ingredient("I1007", "Fish", 100));
        ingredientList.add(new Ingredient("I1008", "French Fries", 100));
        ingredientList.add(new Ingredient("I1009", "Mushroom", 100));
        ingredientList.add(new Ingredient("I1010", "Green Pepper", 100));
        ingredientList.add(new Ingredient("I1011", "Cheese", 100));
        ingredientList.add(new Ingredient("I1012", "Peperoni", 100));
        ingredientList.add(new Ingredient("I1013", "Coconut Rice", 100));
        ingredientList.add(new Ingredient("I1014", "Anchovies", 100));
        ingredientList.add(new Ingredient("I1015", "Ground Nuts", 100));
        ingredientList.add(new Ingredient("I1016", "Sambal", 100));
        ingredientList.add(new Ingredient("I1017", "Cucumber", 100));
        ingredientList.add(new Ingredient("I1018", "Chicken", 100));
        ingredientList.add(new Ingredient("I1019", "Garlic", 100));
        ingredientList.add(new Ingredient("I1020", "Butter", 100));
        ingredientList.add(new Ingredient("I1021", "Bread", 100));
        ingredientList.add(new Ingredient("I1022", "Chocolate", 100));
        ingredientList.add(new Ingredient("I1023", "Eggs", 100));
        ingredientList.add(new Ingredient("I1024", "Flour", 100));
        ingredientList.add(new Ingredient("I1025", "Sugar", 100));
        ingredientList.add(new Ingredient("I1026", "Cream Cheese", 100));
        ingredientList.add(new Ingredient("I1027", "Graham Crust", 100));
        ingredientList.add(new Ingredient("I1028", "Milk", 100));
        ingredientList.add(new Ingredient("I1029", "Ice Cream", 100));
        ingredientList.add(new Ingredient("I1030", "Chocolate Syrup", 100));
        ingredientList.add(new Ingredient("I1031", "Coffee Beans", 100));
        saveToFile(ingredientList);
        return ingredientList;
    }
}
