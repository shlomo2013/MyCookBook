package com.MyCookBook.Entities;

import android.util.Log;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

/**
 * Created by si on 30/05/2015.
 */
@ParseClassName("Album")
public class Album  extends ParseObject {
    public static final String Name="Name";
    public static final String Pic="Pic";
    public static final String Recipes="Recipes";
    public static final String Users="Users";


    public void addRecipe(Recipe recipe){
        ParseRelation<ParseObject> relation = this.getRelation(Recipes);
        relation.add(recipe);
        this.saveInBackground();
    }

    public void addUser(User user){
        ParseRelation<ParseObject> relation = this.getRelation(Users);
        relation.add(user);
        this.saveInBackground();
    }

    public ArrayList<Recipe> getAlbumRecipes(){
        ArrayList<Recipe> returnRec = new ArrayList<Recipe>();
        List<ParseObject> recList = null;

        ParseRelation relation = this.getRelation(Recipes);
        ParseQuery query = relation.getQuery();


        try {
            recList = query.find();
        }catch (Exception e){
            Log.d("bug",e.getMessage());
        }
        if(recList==null)
        {
            Log.d("recList: ","is null");
        }
        Log.d("Success: ","recList= "+recList.size());
        for (ParseObject rec:recList){
            returnRec.add((Recipe)rec);
        }

        return returnRec;
    }


    public ArrayList<User> getAlbumUsers(){
        ArrayList<User> returnUsers = new ArrayList<User>();
        List<ParseObject> foundList = null;

        ParseRelation relation = this.getRelation(Users);
        ParseQuery query = relation.getQuery();

        try {
            foundList = query.find();
        }catch (Exception e){
        }

        for (ParseObject rec:foundList){
            returnUsers.add((User)rec);
        }

        return returnUsers;
    }

    public Album(){
    }
    public String getAlbumName() {
        return getString(Name);
    }
    public void setAlbumName(String value) {
        putOrDefault(Name,value);
        this.saveInBackground();
    }

    private void putOrDefault(String att,String value){
        if(value==null){
            this.put(att,"");
        }else{
            this.put(att,value);
        }
    }
}
