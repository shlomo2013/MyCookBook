package com.MyCookBook.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;

/**
 * Created by shirabd on 09/05/2015.
 */
@ParseClassName("User")
public class User extends ParseObject {

    public static final String UserId ="UserId";
    public static final String Name ="Name";
    public static final String Email ="Email";
    public static final String Gender ="Gender";
    public static final String Birthday ="Birthday";
    public static final String AllergiesSet = "AllergiesSet";

    public User() {
    }

    public ParseQuery<ParseObject> findMyRecipies(User user){
        ParseQuery<ParseObject> recQuery = ParseQuery.getQuery("Recipe");
        recQuery.whereEqualTo("createdBy", user);

        return recQuery;
    }

    private void putOrDefault(String att,String value){
        if(value==null){
            this.put(att,"");
        }else{
            this.put(att,value);
        }
    }

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

    public String getUserId() {
        return getString(UserId);
    }
    public void setUserId(String value) {
        putOrDefault(UserId,value);
    }

    public String getName() {
        return getString(Name);
    }
    public void setName(String value) {
        putOrDefault(Name,value);
    }

    public String getEmail() {
        return getString(Email);
    }
    public void setEmail(String value) {
        putOrDefault(Email,value);
    }

    public String getGender() {
        return getString(Gender);
    }
    public void setGender(String value) {
        putOrDefault(Gender,value);
    }

    public String getBirthday() {
        return getString(Birthday);
    }
    public void setBirthday(String value) {
        putOrDefault(Birthday,value);
    }

    public Bitmap getProfilePic() {
        byte[] data= null;
        Bitmap bmp = null;
        ParseFile applicantResume = (ParseFile) this.get("Profile");

        try {
            data = applicantResume.getData();
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        }catch(Exception e){
            Log.e("User Profile Picture:", "cannot retrieve picture");
        }

        return bmp;
    }

    public void setAllergiesSet(ArrayList<String> allergiesSet) {
        if(allergiesSet!=null && AllergiesSet.length()!=0)
            this.put(AllergiesSet,allergiesSet);
    }


    public ArrayList<String> getAllergiesSet() {
        ArrayList<String> retCategories = new ArrayList<String>();

        if(retCategories != null && retCategories.size() != 0) {
            for (Object s : this.getList(AllergiesSet)) {
                retCategories.add(s.toString());
            }
        }
        return retCategories;
        //ArrayList<String>)this.getList(AllergiesSet);
    }
}
