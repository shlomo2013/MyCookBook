package com.MyCookBook.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mycookbook.mycookbook.Queries;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public static final String DishType = "DishType";
    public static final String Difficulty = "Difficulty";
    public static final String KitchenType = "KitchenType";
    public static final String Diet = "Diet";
    public static final String Vegetarian = "Vegetarian";
    public static final String Vegan = "Vegan";
    public static final String RecipePic = "RecipePic";
    public static final String Likes = "Likes";
    public static final String LikesCounter = "LikesCounter";
    //ArrayList<Grocery> groceries = new ArrayList<Grocery>();


    public Recipe() {
    }


    private void putOrDefault(String att,String value){
        if(value==null){
            this.put(att,"");
        }else{
            this.put(att,value);
        }
    }

    private void putBoolean(String att,boolean value){
        this.put(att,String.valueOf(value));
    }

    public Recipe(String name,String category,String subCategory,String preparation,String dishType,
                           String difficulty,String kitchenType,boolean diet,boolean vegetarian,boolean vegan){
        setName(name);
        setCategory(category);
        setSubCategory(subCategory);
        setPreparation(preparation);
        setDishType(dishType);
        setDifficulty(difficulty);
        setKitchenType(kitchenType);
        setDiet(diet);
        setVegetarian(vegetarian);
        setVegan(vegan);
        this.put(LikesCounter,0);
        try {
            this.save();
        }catch(Exception e){
            Log.d("Cannot Save Recipe ",e.getMessage());
        }
    }

    public void addRecipe(User user) {
        this.put("createdBy", user);
        this.saveInBackground();
    }

    public void updateGroceries(ArrayList<Grocery> groceries){
        if(groceries!=null && groceries.size()!=0) {
            for(Grocery grc:groceries){
                this.addGrocery(grc);
            }
        }
        this.saveInBackground();
    }
    public void addGrocery(Grocery grocery){
        ParseRelation<ParseObject> relation = this.getRelation(Groceries);
        relation.add(grocery);
        this.saveInBackground();
        /*
        if(grocery!=null) {
            this.put(Groceries, grocery);
        }
        this.saveInBackground();*/
    }

    public void savePic(Bitmap bitmap){
        if(bitmap!=null) {
            ParseFile file = new ParseFile("RecipePic.jpeg", bitmapToByteArray(bitmap));
            file.saveInBackground();
            this.put(RecipePic, file);
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

    public ArrayList<Grocery> getRecipeGroceries(){
        ArrayList<Grocery> returnRec = new ArrayList<Grocery>();
        List<ParseObject> recList = null;

        ParseRelation relation = this.getRelation(Groceries);
        ParseQuery query = relation.getQuery();

        try {
            recList = query.find();
        }catch (Exception e){
            Log.d("bug",e.getMessage());
        }
        if(recList==null)
        {
            Log.d("recList: ","is null");
        }else {
            for (ParseObject rec : recList) {
                returnRec.add((Grocery) rec);
            }
        }

        return returnRec;


    }


    public void Like() {
        ParseRelation<ParseObject> relation = this.getRelation(Likes);
        relation.add(Queries.getMyUser());
        this.increment(LikesCounter);
        try {
            this.save();
        } catch (Exception e) {
            Log.d("Like: ", e.getMessage());

        }

        List<ParseObject> recList = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PersonalSettings");
        query.whereEqualTo(PersonalSettings.User, Queries.getMyUser().getObjectId());
        try {
            recList = query.find();
        } catch (Exception e) {
            Log.d("HashSettings Error:", e.getMessage());
        }

        if (recList != null && recList.size() != 0) {
            PersonalSettings userSettings = (PersonalSettings) recList.get(0);
            userSettings.incCategory(Queries.convertCategoryName(this.getCategory()));
        }

    }

    public void LikebyUser(User user){
        ParseRelation<ParseObject> relation = this.getRelation(Likes);
        relation.add(user);
        this.increment(LikesCounter);
        this.saveInBackground();
    }

    public void UnLike(){
        ParseRelation<ParseObject> relation = this.getRelation(Likes);
        relation.remove(Queries.getMyUser());
        this.increment(LikesCounter,-1);
        this.saveInBackground();
    }

    public void UnLikebyUser(User user){
        ParseRelation<ParseObject> relation = this.getRelation(Likes);
        relation.remove(user);
        this.increment(LikesCounter,-1);
        this.saveInBackground();
    }

    public String getName(){
        return this.getString(Name);
    }
    public User getCreatedBy(){
        return Queries.getUserById(((ParseObject)this.get("createdBy")).getObjectId());
    }

    public String getCategory(){
        return this.getString(Category);
    }

    public String getSubCategory(){
        return this.getString(SubCategory);
    }

    public String getPreparation(){
        return this.getString(Preparation);
    }

    public String getDishType(){
        return this.getString(DishType);
    }

    public String getDifficulty(){
        return this.getString(Difficulty);
    }

    public String getKitchenType(){
        return this.getString(KitchenType);
    }

    public boolean getDiet(){
        return this.getBoolean(Diet);
    }

    public boolean getVegetarian(){
        return this.getBoolean(Vegetarian);
    }
    public boolean getVegan(){
        return this.getBoolean(Vegan);
    }

    public int getLikesCounter(){
        return this.getInt(LikesCounter);
    }

    public ArrayList<User>  getUsersWhoLikeThisRecipe(){
        ArrayList<User> returnRec = new ArrayList<User>();
        List<ParseObject> recList = null;

        ParseRelation relation = this.getRelation(Likes);
        ParseQuery query = relation.getQuery();

        try {
            recList = query.find();
        }catch (Exception e){
            Log.d("bug",e.getMessage());
        }
        if(recList==null)
        {
            Log.d("recList: ","is null");
        }else {
            for (ParseObject rec : recList) {
                returnRec.add((User) rec);
            }
        }

        return returnRec;
    }

    public Bitmap getRecipePicture() {
        byte[] data= null;
        Bitmap bmp = null;

        Object pic = this.get(Recipe.RecipePic);
        ParseFile applicantResume = null;

        if(pic!= null){
            applicantResume = (ParseFile)pic;
        }else{
            return null;
        }

        try {
            data = applicantResume.getData();
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        }catch(Exception e){
            Log.e("User Profile Picture:","cannot retrieve picture");
        }

        return bmp;
    }


    public void setName(String param){
        putOrDefault(Name,param);
    }
    public void setCategory(String param){
        putOrDefault(Category,param);
    }
    public void setSubCategory(String param){
        putOrDefault(SubCategory,param);
    }
    public void setPreparation(String param){
        putOrDefault(Preparation,param);
    }
    public void setDishType(String param){
        putOrDefault(DishType,param);
    }
    public void setDifficulty(String param){
        putOrDefault(Difficulty,param);
    }
    public void setKitchenType(String param){
        putOrDefault(KitchenType,param);
    }
    public void setDiet(boolean param){
        this.put(Diet, param);
    }
    public void setVegetarian(boolean param){
        this.put(Vegetarian, param);
    }
    public void setVegan(boolean param){
        this.put(Vegan, param);
    }
    public void setRecipePic(String param){
        putOrDefault(RecipePic,param);
    }
}
