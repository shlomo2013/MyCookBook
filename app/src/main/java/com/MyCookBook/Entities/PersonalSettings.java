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
    public static final String Classify = "Classify";

    public static final String ANIN_TASTE = "1";
    public static final String STANDARD_TASTE = "2";
    public static final String AMAMI_TASTE = "3";

    public static String convertTaste(String value){
        switch(value){
            case ANIN_TASTE:
                return "אנין טעם";
            case STANDARD_TASTE:
                return "טעם סטנדרטי";
            case AMAMI_TASTE:
                return "טעם עממי";
        }
        return "";
    }



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
        this.put(Classify,0);
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
