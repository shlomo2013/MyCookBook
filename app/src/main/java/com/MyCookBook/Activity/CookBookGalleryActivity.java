package com.MyCookBook.Activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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


    Gallery g;
    Button bAddRecipe;
    private PopupWindow pw;
    boolean click;


    ImageView iv;
    ImageView ivRecipeImage;

    // Recipe Include
    TextView RcipeName;
    TextView DishType;
    TextView Level;
    TextView KitchenType;
    TextView Category;
    TextView AlbumName;
    CheckBox Vegan;
    CheckBox Vegaterian;
    CheckBox Diet;
    ArrayList<Recipe> RecipesList;

    //Album Include
    ImageView viAlbumPic;
    EditText albumName;
    EditText albumType;
    EditText albumDesc;
    Button btnAlbumPic;
    Button btnSave;
    ListView lvAllUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book_gallery);

        // Gallery
        g           = (Gallery)findViewById(R.id.gallery);
        bAddRecipe  = (Button) findViewById(R.id.btnAddRecipeToAlbum);

        // Recipe Include
        ivRecipeImage   = (ImageView) findViewById(R.id.RecipeImage);
        RcipeName       = (TextView) findViewById(R.id.tvRcipeName);
        DishType        = (TextView) findViewById(R.id.tvDishType);
        Level           = (TextView) findViewById(R.id.tvLevel);
        KitchenType     = (TextView) findViewById(R.id.tvKitchenType);
        Category        = (TextView) findViewById(R.id.tvCategory);
        AlbumName        = (TextView) findViewById(R.id.tvAlbumName);
        Vegan           = (CheckBox) findViewById(R.id.cbVegan);
        Vegaterian      = (CheckBox) findViewById(R.id.cbVeg);
        Diet            = (CheckBox) findViewById(R.id.cbDiet);

        //Album Include
        albumType   = (EditText)    findViewById(R.id.etAlbumType);
        albumName   = (EditText)    findViewById(R.id.etAlbumName);
        albumDesc   = (EditText)    findViewById(R.id.etAlbumDetails);
        viAlbumPic  = (ImageView)   findViewById(R.id.AlbumPic);
        btnAlbumPic =  (Button)     findViewById(R.id.btnSelectPhoto);
        btnSave     =  (Button)     findViewById(R.id.bSaveUser);
        lvAllUsers  =  (ListView)   findViewById(R.id.AllUsersListView);


        lvAllUsers.setVisibility(View.INVISIBLE);
        btnSave.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String albumID = extras.getString("AlbumID");
            if(albumID != null)
            {
                Album a = Queries.getAlbumById(albumID);
                AlbumName.setText(a.getAlbumName());

                // get Album recipe
                RecipesList = a.getAlbumRecipes();
                int albumPics[] = new int[RecipesList.size()];

                // set recipe
                Recipe r;
                for (int i= 0 ; i < RecipesList.size(); i++){
                    r = RecipesList.get(i);
                    //TODO - create list of pics
                    //  albumPics[i]= (r.getRecipePicture()).get
                }
            }
        }
        else
        {
            AlbumName.setText("שגיאה!! לא  נמצא אלבום" );

        }

        bAddRecipe.setOnClickListener(btnAddRecipeOnClickListener);

        // Create adapter gallery
        g.setAdapter( new ImageAdapter(this, RecipesList));
        g.setOnItemClickListener(onGalletyItemSelect);

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
        ArrayList<Recipe> RecipesListPerAlbum;

        public ImageAdapter(Context c, ArrayList<Recipe> RecipesListPerAlbum){
            this.context = c;
            this.RecipesListPerAlbum = RecipesListPerAlbum;
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

//            Recipe r = RecipesListPerAlbum.get(position);
//            image.setTag(r);
//
//            image.setImageResource(thumb[position]);
//

            return image;
        }
    }

    public View.OnClickListener btnAddRecipeOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (click) {
                initiatePopUp();
                // Handle show big picture
                pw.showAtLocation(bAddRecipe, Gravity.TOP, 100, 100);
                click = false;
            }
            else{
                pw.dismiss();
                click = true;
            }


        }

    };

    AdapterView.OnItemClickListener onGalletyItemSelect = new AdapterView.OnItemClickListener() {

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
//                Diet
        }
    };

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

    private void initiatePopUp(){

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_addrecipe_fragment, null);

        pw = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        //pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        final ImageView RecipePhoto = (ImageView) popupView.findViewById(R.id.ImageRecipePhoto);
//        RecipePhoto.setImageBitmap(photo);


        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });
    }
}
