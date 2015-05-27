package com.MyCookBook.Entities;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by shirabd on 09/05/2015.
 */
@ParseClassName("Recipe")
public class Recipe  extends ParseObject {
    public static final String Name = "Name";
    public static final String Category = "Category";
    public static final String SubCategory = "SubCategory";
    public static final String Preparation = "Preparation";
    public static final String Groceries = "Groceries";
    public static final String DishType = "DishType";
    public static final String Difficulty = "Difficulty";
    public static final String KitchenType = "KitchenType";
    // TODO Should be boolean
    public static final String Diet = "Diet";
    // TODO Should be boolean
    public static final String Vegetarian = "Vegetarian";
    // TODO Should be boolean
    public static final String Vegan = "Vegan";
    public static final String RecipePic = "RecipePic.jpg";
    ArrayList<Grocery> groceries = new ArrayList<Grocery>();

    // TODO מתכון ע״פ קוד מתכון

    public Recipe() {
    }

    public static String getName() {
        return Name;
    }

    public static String getCategory() {
        return Category;
    }

    public static String getSubCategory() {
        return SubCategory;
    }

    public static String getPreparation() {
        return Preparation;
    }

    public static String getGroceries() {
        return Groceries;
    }

    public void setGroceries(ArrayList<Grocery> groceries) {
        this.groceries = groceries;
    }

    public static String getDishType() {
        return DishType;
    }

    public static String getDifficulty() {
        return Difficulty;
    }

    public static String getKitchenType() {
        return KitchenType;
    }

    public static String getDiet() {
        return Diet;
    }

    public static String getVegetarian() {
        return Vegetarian;
    }

    public static String getVegan() {
        return Vegan;
    }

    public static String getRecipePic() {
        return RecipePic;
    }

    public void initRecipe(String name, String category ,String subCategory,String preparation,String dishType,
                           String difficulty,String kitchenType,String diet,String vegetarian,String vegan){
        this.put(Name, name);
        this.put(Category, category);
        this.put(SubCategory, subCategory);
        this.put(Preparation, preparation);
        this.put(Groceries, groceries);
        this.put(DishType, dishType);
        this.put(Difficulty, difficulty);
        this.put(KitchenType, kitchenType);
        this.put(Diet, diet);
        this.put(Vegetarian, vegetarian);
        this.put(Vegan, vegan);
        this.saveInBackground();
    }

    public void addRecipe(User user) {
        this.put("createdBy", user);
        this.saveInBackground();
    }

        // TODO delete after user is fixed
    public void addRecipe(String user) {
        this.put("createdBy", user);
        this.saveInBackground();
    }

    public void updateGroceries(ArrayList<Grocery> groceries){
        this.put(Groceries, groceries);
        this.saveInBackground();
    }
    public void addGrocery(Grocery grocery){
        groceries.add(grocery);
        this.put(Groceries, groceries);
        this.saveInBackground();
    }

    public void savePic(byte[] data){
        //byte[] data = "Working at Parse is great!".getBytes();
        ParseFile file = new ParseFile(RecipePic, data);
        file.saveInBackground();
        this.put(RecipePic,file);
        this.saveInBackground();
    }
}
