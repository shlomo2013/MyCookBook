package com.MyCookBook.Entities;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by shirabd on 09/05/2015.
 */
@ParseClassName("User")
public class User extends ParseObject {

    public static final String USER_ID="UserId";
    public static final String RECIPES="Recipes";

    //ParseRelation<ParseObject> recipesRel = this.getRelation(RECIPES);

    public User() {
    }

    public String getUserId() {
        return getString(USER_ID);
    }
    public void setUserId(String value) {
        put(USER_ID, value);
    }

    public ParseQuery<ParseObject> findMyRecipies(User user){
        ParseQuery<ParseObject> recipiesQuery = ParseQuery.getQuery("Recipe");
        recipiesQuery.whereEqualTo("createdBy", user);

        return recipiesQuery;
    }

    //public ParseRelation getRecip

    public boolean updateStrValue(String entityType,String entityId, String valId ,String value){
        boolean returnVal;
        // Create a pointer to an object of class Point with id dlkj83d
        ParseObject entity = ParseObject.createWithoutData(entityType, entityId);

        // Set a new value on quantity
        entity.put(valId, value);

        // Save
        entity.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    //returnVal = true;
                } else {
                    //returnVal = false;
                }
            }
        });

        return true;
    }
}
