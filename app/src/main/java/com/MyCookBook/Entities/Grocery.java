package com.MyCookBook.Entities;

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
    public void initGrocery(String materialName,String form,String amount){
        this.put(MaterialName, materialName);
        this.put(Form, form);
        this.put(Amount, amount);
    }
}
