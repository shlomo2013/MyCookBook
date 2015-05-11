package com.MyCookBook.Entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by shirabd on 09/05/2015.
 */
@ParseClassName("Recipe")
public class Recipe  extends ParseObject {
    public final String Category = "Category";
    public final String Sub_Category = "SubCategory";

    public Recipe() {
    }

    public void initRecipe(){

    }
}
