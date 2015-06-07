package com.MyCookBook.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MyCookBook.Entities.Album;
import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

public class CookBookGalleryActivity extends ActionBarActivity {


    public int thumb[] = { R.mipmap.cheese_cake, R.mipmap.chips,
            R.mipmap.konus_pizza , R.mipmap.yami,
            R.mipmap.lazania ,R.mipmap.oreo ,
            R.mipmap.pankaiyk ,R.mipmap.pay_coco ,
            R.mipmap.lazania2 ,R.mipmap.peanut_butter ,
            R.mipmap.pizza , R.mipmap.lazania3, };

    ImageView iv;
    ImageView ivRecipeImage;

    TextView RcipeName;
    TextView DishType;
    TextView Level;
    TextView KitchenType;
    TextView Category;
    CheckBox Vegan;
    CheckBox Vegaterian;
    CheckBox Diet;
    ArrayList<Recipe> RecipesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book_gallery);

        ivRecipeImage   = (ImageView) findViewById(R.id.RecipeImage);
        RcipeName       = (TextView) findViewById(R.id.tvRcipeName);
        DishType        = (TextView) findViewById(R.id.tvDishType);
        Level           = (TextView) findViewById(R.id.tvLevel);
        KitchenType     = (TextView) findViewById(R.id.tvKitchenType);
        Category        = (TextView) findViewById(R.id.tvCategory);
        Vegan           = (CheckBox) findViewById(R.id.cbVegan);
        Vegaterian      = (CheckBox) findViewById(R.id.cbVeg);
        Diet            = (CheckBox) findViewById(R.id.cbDiet);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String albumID = extras.getString("AlbumID");
            if(albumID != null)
            {
                // TODO dana: להשתמש בפונקציה ששולפת את המתכומנים לפי אלבום
                Album a = Queries.getAlbumById(albumID);
                a.getAlbumName();

                RecipesList = a.getAlbumRecipes();
            }
        }
        else
        {
            //..oops!
        }
        Gallery g = (Gallery)findViewById(R.id.gallery);
      //  iv = (ImageView)findViewById(R.id.tempImageView);

        // Create adapter gallery
        g.setAdapter( new ImageAdapter(this));

        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO dana: לקחת את המתכון עלפי התמונה שלחצה ולהקפיץ מסף של מתכון
                Toast.makeText(getApplicationContext(), "מתכון:", Toast.LENGTH_SHORT);
                ivRecipeImage.setImageResource(thumb[position]);
                RcipeName.setText("דניאל נחמיאס המעפנה");
//                DishType       ;
//                Level          ;
//                KitchenType    ;
//                Category       ;
                Vegan.setChecked(true);          ;
//                Vegaterian     ;
//                Diet           ;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cook_book_gallery, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;
        int ImageBackRound;

        public ImageAdapter(Context c){
            this.context = c;
        }

        @Override
        public int getCount() {
            return thumb.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image = new ImageView(context);
            image.setImageResource(thumb[position]);
            return image;
        }
    }

    public void fillRecipe(Recipe r){

       ivRecipeImage.setImageBitmap( r.getRecipePicture());
                       RcipeName.setText("דניאל נחמיאס המעפנה");
       //                DishType       ;
       //                Level          ;
       //                KitchenType    ;
       //                Category       ;
                       Vegan.setChecked(true);          ;
       //                Vegaterian     ;
       //                Diet           ;
    }
}
