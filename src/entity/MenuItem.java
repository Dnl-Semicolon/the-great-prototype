package entity;

import adt.*;

import java.io.Serializable;

public class MenuItem implements Serializable {

    public String name;
    public double price;
    public ListInterface<Ingredient> recipe = new ArrayList<>();

    public MenuItem() {
        name = "";
        price = 0.00;
    }

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}

    public static boolean isValidName(String name) {
        boolean isValid = true;
        if (!name.isEmpty()) {
            for (int i = 0; i < name.length() && isValid; i++) {
                char currentChar = name.charAt(i);
                if (!Character.isLetter(currentChar) && currentChar != ' ') {
                    isValid = false;
                }
            }
            return isValid;
        } else {
            isValid = false;
            return isValid;
        }
    }

    public static boolean isValidPrice(double price) {
        return price >= 0 && price <= 10000;
    }

    public ListInterface<Ingredient> getRecipe() {return recipe;}
    public void setRecipe(ListInterface<Ingredient> recipe) {this.recipe = recipe;}

    public String printRecipe() {
        if (recipe.isEmpty()) {
            return "[No recipe]";
        }
        StringBuilder recipeStr = new StringBuilder();
        recipeStr.append("[");

        for (int i = 1; i <= recipe.getNumberOfEntries(); i++) {
            Ingredient ing = recipe.getEntry(i);
            recipeStr.append(recipe.getEntry(i).getName());
            if (i < recipe.getNumberOfEntries()) {
                recipeStr.append(", ");
            }
        }

        recipeStr.append("]");
        return recipeStr.toString();
    }

    public boolean canFulfillOrder(int requiredQuantity) {
        for (int i = 1; i <= recipe.getNumberOfEntries(); i++) {
            Ingredient ingredient = recipe.getEntry(i);
            if (ingredient.getCurrentStock() < requiredQuantity) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%-21s %6.2f", name, price);
    }
}
