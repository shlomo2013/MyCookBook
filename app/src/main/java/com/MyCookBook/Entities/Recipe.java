package com.MyCookBook.Entities;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;

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
    public static final String Diet = "Diet";
    public static final String Vegetarian = "Vegetarian";
    public static final String Vegan = "Vegan";
    public static final String RecipePic = "RecipePic.jpg";
    ArrayList<Grocery> groceries = new ArrayList<Grocery>();


    public Recipe() {
    }

    private void putOrDefault(String att,String value){
        if(value==null){
            this.put(att,"");
        }else{
            this.put(att,value);
        }
    }
    public void initRecipe(String name,String category,String subCategory,String preparation,String dishType,
                           String difficulty,String kitchenType,String diet,String vegetarian,String vegan){
        this.putOrDefault(Name, name);
        this.putOrDefault(Category, category);
        this.putOrDefault(SubCategory, subCategory);
        this.putOrDefault(Preparation, preparation);
        //this.putOrDefault(Groceries, groceries);
        this.putOrDefault(DishType, dishType);
        this.putOrDefault(Difficulty, difficulty);
        this.putOrDefault(KitchenType, kitchenType);
        this.putOrDefault(Diet, diet);
        this.putOrDefault(Vegetarian, vegetarian);
        this.putOrDefault(Vegan, vegan);
        this.saveInBackground();
    }

    public void addRecipe(User user) {
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
    public String getName(){
        return this.getString(Name);
    }
}
