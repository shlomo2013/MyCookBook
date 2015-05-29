package com.example.mycookbook.mycookbook;

import android.util.Log;

import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.parse.FindCallback;
import com.parse.ParseException;
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

    public static void updateMyUser(String userId){
        List<ParseObject> userList = null;
        Log.d("userId= " ,userId);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("UserId", userId);
        try {
            userList = query.find();
        }catch (Exception e){
            myUser = null;
            Log.d("score", "Error: " + e.getMessage());
        }
        Log.d("search=","success");
        myUser = (User)userList.get(userList.size()-1);
        Log.d("User", "Retrieved " + myUser.getObjectId() + " name=" + myUser.getUserId());

        /*
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                Log.d("search Exception-",e.getMessage());
                if (e == null) {
                    Log.d("search=","success");
                    myUser = (User)userList.get(userList.size()-1);
                    Log.d("User", "Retrieved " + myUser.getObjectId() + " name=" + myUser.getUserId());
                    //success = true;
                } else {
                    Log.d("search=","falied");
                    myUser = null;
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
*/
        Log.e("search=","end");
    }

    public static User getMyUser(){
        return myUser;
    }

    public static void eraseCurrentUser(){
        myUser = null;
    }

    public static void updateTypeRecipes(String field, String value, User user){
        ParseQuery<ParseObject> recQuery = ParseQuery.getQuery("Recipe");
        recQuery.whereEqualTo("createdBy", user);
        List<ParseObject> recList = null;
        try {
            recList = recQuery.find();
        }catch(Exception e) {
        }
                    for (ParseObject a : recList) {
                        //Recipe a = (Recipe) recList.get(0);
                        a.put(field, value);
                        a.saveInBackground();
                    }
    }

    /* Returns the recipies in ParseObject List*/
    public static ArrayList<Recipe> getUserRecipies(User user){
        ArrayList<Recipe> returnRec = new ArrayList<Recipe>();
        ParseQuery<ParseObject> recQuery = ParseQuery.getQuery("Recipe");
        recQuery.whereEqualTo("createdBy", user);
        List<ParseObject> recList = null;
        try {
            recList = recQuery.find();
        }catch(Exception e) {
            Log.d("Queries Exception","cannot find recipies for user");
        }
        Log.d("Number of rec:",String.valueOf(recList.size()));

        for (ParseObject rec:recList){
            returnRec.add((Recipe)rec);
        }
        return returnRec;
    }

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
}
