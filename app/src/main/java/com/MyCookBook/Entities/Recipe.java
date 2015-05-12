package com.MyCookBook.Entities;

import com.parse.ParseClassName;
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
    ArrayList<Grocery> groceries = new ArrayList<Grocery>();


    public Recipe() {
    }

    public void initRecipe(String name,String category,String subCategory,String preparation){
        this.put(Name, name);
        this.put(Category, category);
        this.put(SubCategory, subCategory);
        this.put(Preparation, preparation);
        this.put(Groceries, groceries);
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


}
