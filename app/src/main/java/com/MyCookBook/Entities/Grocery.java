package com.MyCookBook.Entities;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by shirabd on 12/05/2015.
 */
@ParseClassName("Grocery")
public class Grocery  extends ParseObject {
    public final String MaterialName = "MaterialName";
    public final String Form = "Form";
    public final String Amount = "Amount";
    public Grocery(){

    }
    public Grocery(String materialName,String form,String amount){
        this.put(MaterialName, materialName);
        this.put(Form, form);
        this.put(Amount, amount);
        try {
            this.save();
        }catch(Exception e){
            Log.d("Cannot save Grocery: ",e.getMessage());
        }
    }

    public String getMaterialName(){
        return this.getString(MaterialName);
    }

    public String getForm(){
        return this.getString(Form);
    }

    public String getAmount(){
        return this.getString(Amount);
    }
}
