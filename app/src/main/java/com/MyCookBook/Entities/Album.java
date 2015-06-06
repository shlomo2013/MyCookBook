package com.MyCookBook.Entities;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseFile;
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
    public static final String CreatedBy="CreatedBy";


    /*Add recipe to album*/
    public void addRecipe(Recipe recipe){
        ParseRelation<ParseObject> relation = this.getRelation(Recipes);
        relation.add(recipe);
        this.saveInBackground();
    }

    /*Add user to album*/
    public void addUser(User user){
        ParseRelation<ParseObject> relation = this.getRelation(Users);
        relation.add(user);
        this.saveInBackground();
    }

    // Get all the recipes of this album
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

    /* Get all the users that related to this album*/
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

    /*Ctor- Use This to do a new album - get album name and the user that create it*/
    public Album(String AlbumName, User userCreated)
    {
        this.setAlbumName(AlbumName);
        this.setUserCreatedBy(userCreated);
    }
    public String getAlbumName()
    {
        return getString(Name);
    }
    public void setAlbumName(String value) {
        putOrDefault(Name,value);
        this.saveInBackground();
    }

    public String getUserCreatedBy()
    {
        return getString(CreatedBy);
    }

    public void setUserCreatedBy(User userCraeted) {
        this.put("CreatedBy",userCraeted);
        this.saveInBackground();
    }

    private void putOrDefault(String att,String value){
        if(value==null){
            this.put(att,"");
        }else{
            this.put(att,value);
        }
    }

    public void savePic(Bitmap bitmap){
        if(bitmap!=null) {
            ParseFile file = new ParseFile("AlbumPic.jpeg", bitmapToByteArray(bitmap));
            file.saveInBackground();
            this.put(Pic, file);
            this.saveInBackground();
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bmp)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}