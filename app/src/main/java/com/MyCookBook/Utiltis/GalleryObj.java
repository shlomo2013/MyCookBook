package com.MyCookBook.Utiltis;

import android.graphics.Bitmap;

/**
 * Created by nirgadasi on 6/8/15.
 */
public class GalleryObj {
    public Bitmap b;
    public String itemsDesc;
    public String itemsTitle;


    public GalleryObj(Bitmap b, String itemsDesc, String itemsTitle) {
        this.b = b;
        this.itemsDesc = itemsDesc;
        this.itemsTitle = itemsTitle;
    }
}
