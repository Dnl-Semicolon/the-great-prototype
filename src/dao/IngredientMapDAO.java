package dao;

import adt.ArrayList;
import adt.HashMap;
import adt.ListInterface;
import adt.MapInterface;
import entity.Ingredient;

import java.io.*;

public class IngredientMapDAO {

    private static IngredientMapDAO singletonInstance;

    private String fileName = "ingredient_map.dat";

    private IngredientMapDAO() {}

    public static IngredientMapDAO getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new IngredientMapDAO();
        }
        return singletonInstance;
    }

    public void saveToFile(MapInterface<String, Ingredient> ingredientMap) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(ingredientMap);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public MapInterface<String, Ingredient> retrieveFromFile() {
        File file = new File(fileName);
        MapInterface<String, Ingredient> ingredientMap = new HashMap<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            ingredientMap = (HashMap<String, Ingredient>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.INVENTORYMAP");
            ingredientMap = initBackup();
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.INVENTORYMAP");
            ingredientMap = initBackup();
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.INVENTORYMAP");
            ingredientMap = initBackup();
        } finally {
            return ingredientMap;
        }
    }

    public MapInterface<String, Ingredient> initBackup() {
        MapInterface<String, Ingredient> ingredientMap = new HashMap<>();
        ingredientMap.put("I1001", new Ingredient("Chicken Patty", 100));
        ingredientMap.put("I1002", new Ingredient("Beef Patty", 100));
        ingredientMap.put("I1003", new Ingredient("Burger Bun", 100));
        ingredientMap.put("I1004", new Ingredient("Spaghetti Noodles", 100));
        ingredientMap.put("I1005", new Ingredient("Minced Chicken", 100));
        ingredientMap.put("I1006", new Ingredient("Tomato", 100));
        ingredientMap.put("I1007", new Ingredient("Fish", 100));
        ingredientMap.put("I1008", new Ingredient("French Fries", 100));
        ingredientMap.put("I1009", new Ingredient("Mushroom", 100));
        ingredientMap.put("I1010", new Ingredient("Green Pepper", 100));
        ingredientMap.put("I1011", new Ingredient("Cheese", 100));
        ingredientMap.put("I1012", new Ingredient("Peperoni", 100));
        ingredientMap.put("I1013", new Ingredient("Coconut Rice", 100));
        ingredientMap.put("I1014", new Ingredient("Anchovies", 100));
        ingredientMap.put("I1015", new Ingredient("Ground Nuts", 100));
        ingredientMap.put("I1016", new Ingredient("Sambal", 100));
        ingredientMap.put("I1017", new Ingredient("Cucumber", 100));
        ingredientMap.put("I1018", new Ingredient("Chicken", 100));
        ingredientMap.put("I1019", new Ingredient("Garlic", 100));
        ingredientMap.put("I1020", new Ingredient("Butter", 100));
        ingredientMap.put("I1021", new Ingredient("Bread", 100));
        ingredientMap.put("I1022", new Ingredient("Chocolate", 100));
        ingredientMap.put("I1023", new Ingredient("Eggs", 100));
        ingredientMap.put("I1024", new Ingredient("Flour", 100));
        ingredientMap.put("I1025", new Ingredient("Sugar", 100));
        ingredientMap.put("I1026", new Ingredient("Cream Cheese", 100));
        ingredientMap.put("I1027", new Ingredient("Graham Crust", 100));
        ingredientMap.put("I1028", new Ingredient("Milk", 100));
        ingredientMap.put("I1029", new Ingredient("Ice Cream", 100));
        ingredientMap.put("I1030", new Ingredient("Chocolate Syrup", 100));
        ingredientMap.put("I1031", new Ingredient("Coffee Beans", 100));
        saveToFile(ingredientMap);
        return ingredientMap;
    }
}
