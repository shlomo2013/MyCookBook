package com.example.mycookbook.mycookbook;

import android.util.Log;

import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by shirabd on 12/05/2015.
 */
public class Queries {
    static User myUser;
    static boolean success;

    public static void updateMyUser(String userId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("UserId", userId);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    myUser = (User)userList.get(0);
                    Log.d("User", "Retrieved " + myUser.getObjectId() + " scores");
                    //success = true;
                } else {
                    myUser = null;
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
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
}
