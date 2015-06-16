package com.MyCookBook.Entities;

import com.example.mycookbook.mycookbook.Queries;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by si on 15/06/2015.
 */
@ParseClassName("PersonalSettings")
public class PersonalSettings  extends ParseObject {
    public static final String User = "User";
    public static final String Sweets = "Sweets";
    public static final String Breakfast = "Breakfast";
    public static final String soups = "soups";
    public static final String salads = "salads";
    public static final String additions = "additions";
    public static final String Drinks = "Drinks";
    public static final String BreadAndBaking = "BreadAndBaking";
    public static final String RiceAndPastas = "RiceAndPastas";
    public static final String Alcohol = "Alcohol";
    public static final String Meat = "Meat";
    public PersonalSettings(){
    }

    public PersonalSettings(User user){
        this.put(Sweets,0);
        this.put(Breakfast,0);
        this.put(soups,0);
        this.put(salads,0);
        this.put(additions,0);
        this.put(Drinks,0);
        this.put(BreadAndBaking,0);
        this.put(RiceAndPastas,0);
        this.put(Alcohol,0);
        this.put(Meat,0);
        this.put(User, user.getObjectId());
    }

    public void incCategory(String category){
        this.increment(category);
        this.saveInBackground();
    }

    public int getValue(String category){
        return this.getInt(category);
    }

    public void setUser(User user){
        this.put(User,user);
    }


}
