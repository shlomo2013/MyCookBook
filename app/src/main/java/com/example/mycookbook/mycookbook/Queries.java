package com.example.mycookbook.mycookbook;

import android.util.Log;

import com.MyCookBook.Entities.Album;
import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shirabd on 12/05/2015.
 */
public class Queries {
    static User myUser;
    static boolean success;

    /*
    This Function get the facebook userId as parameter
    and finds the user object from parse data
    and set it in the "myUser" static variable.
     */
    public static void updateMyUser(String userId){
        List<ParseObject> userList = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        //query.whereEqualTo("UserId", userId);

        //TODO: delete
        String s = "10153329758089662";
        query.whereEqualTo("UserId", s);

        try {
            userList = query.find();
        }catch (Exception e){
            myUser = null;
        }
        // Sets the last created user from parse
        myUser = (User)userList.get(userList.size()-1);
        Log.d("User", "Retrieved " + myUser.getObjectId() + " name=" + myUser.getUserId());
    }

    /*
    The function updates in all the recipies related to the user,
    in the field and value the function gets as parameters
     */
    public static void updateTypeRecipes(String field, String value, User user){
        ParseQuery<ParseObject> recQuery = ParseQuery.getQuery("Recipe");
        recQuery.whereEqualTo("createdBy", user);
        List<ParseObject> recList = null;
        try {
            recList = recQuery.find();
        }catch(Exception e) {
        }
                    for (ParseObject a : recList) {
                        a.put(field, value);
                        a.saveInBackground();
                    }
    }

    /* Returns the all the user's recipes in Recipe type List*/
    public static ArrayList<Recipe> getUserRecipes(User user){
        ArrayList<Recipe> returnRec = new ArrayList<Recipe>();
        ParseQuery<ParseObject> recQuery = ParseQuery.getQuery("Recipe");
        recQuery.whereEqualTo("createdBy", user);
        List<ParseObject> recList = null;
        try {
            recList = recQuery.find();
        }catch(Exception e) {
            Log.d("Queries Exception","cannot find recipes for user");
        }
        Log.d("Number of rec:",String.valueOf(recList.size()));

        if (recList != null) {
            for (ParseObject rec : recList) {
                returnRec.add((Recipe) rec);
            }
        }
        return returnRec;
    }

    /*
    When connecting to the facebook this function runs and gets
    the exisiting user from parse, or creating new user when
    it's the first time
     */
    public static void isUserAlreadyExists(String userId){
        List<ParseObject> userList = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("UserId", userId);
        try {
            userList = query.find();
        }catch (Exception e){
            myUser = null;
            Log.d("score", "Error: " + e.getMessage());
        }
        if(userList.size()!=0) {
            myUser = (User) userList.get(userList.size() - 1);
            Log.d("User", "already exists " + myUser.getObjectId() + " name=" + myUser.getUserId());
        }else{
            myUser = new User();
            myUser.setUserId(userId);
            myUser.saveInBackground();
            Log.d("User", " new User was created " + myUser.getObjectId() + " name=" + myUser.getUserId());
        }
    }

    /*
    The function finds specific Recipe by "Parse" ObjectId
     */
    public static Recipe getRecipeById(String objectId){
        ParseQuery<ParseObject> recQuery = ParseQuery.getQuery("Recipe");
        recQuery.whereEqualTo("objectId", objectId);
        List<ParseObject> recList = null;
        Recipe retRecipe = null;
        try {
            recList = recQuery.find();
        }catch(Exception e) {
            Log.d("getRecipeById Err","cannot find Recipe by objId");
        }
        if(recList.size()!=0) {
            retRecipe = (Recipe)recList.get(0);
        }

        return retRecipe;
    }

    /*Gets the last amount new recipes to present on the feed */
    public static ArrayList<Recipe> getLastRecipes(int amount){
        ArrayList<Recipe> returnRec = new ArrayList<Recipe>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");

        // Retrieve the most recent ones
        query.orderByDescending("createdAt");

        // Only retrieve the last amount selected
        query.setLimit(amount);

        List<ParseObject> recList = null;
        try {
            recList = query.find();
        }catch(Exception e) {
            Log.d("Queries Exception","cannot find recipies for user");
        }
        if (recList != null) {
            for (ParseObject rec : recList) {
                returnRec.add((Recipe) rec);
            }
        }
        return returnRec;
    }

    /*This function return list of albums that the user is related to*/
    public static ArrayList<Album> getUserAlbum(User user){
        ArrayList<Album> returnAlbum = new ArrayList<Album>();
        ParseQuery<ParseObject> recQuery = ParseQuery.getQuery("Album");

        recQuery.whereEqualTo(Album.Users, user);
        List<ParseObject> recList = null;
        try {
            recList = recQuery.find();
        }catch(Exception e) {
            Log.d("Queries Exception","cannot find albums for user");
        }
        Log.d("Number of rec:",String.valueOf(recList.size()));
        if (recList != null){
            for (ParseObject rec:recList){
                returnAlbum.add((Album)rec);
            }
        }

        return returnAlbum;
    }
    public static User getMyUser(){
        return myUser;
    }

    public static void eraseCurrentUser(){
        myUser = null;
    }
}
