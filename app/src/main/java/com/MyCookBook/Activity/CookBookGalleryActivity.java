package com.MyCookBook.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.MyCookBook.Entities.Album;
import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class CookBookGalleryActivity extends ActionBarActivity {

    Gallery g;
    Button bAddRecipe;
    private PopupWindow pw;
    boolean click = true;
    Bitmap[] imageBitmaps;
    TableLayout tbLayout;

    ImageView iv;
    ImageView ivRecipeImage;

    // Recipe Include
    TextView RcipeName;
    TextView DishType;
    TextView Level;
    TextView KitchenType;
    TextView Category;
    TextView IngredientsTitle;
    TextView IngredientName;
    TextView IngredientType;
    TextView IngredientAmount;
    TextView HowToMakeTitle;
    TextView HowToMake;
    CheckBox Vegan;
    CheckBox Vegaterian;
    CheckBox Diet;
    ArrayList<Recipe> RecipesList;

    //Album Include
    Album selectedAlbum;
    ImageView viAlbumPic;
    EditText albumName;
    EditText albumType;
    EditText albumDesc;
    TextView albumNameTitle;
    TextView albumTypeTitle;
    TextView albumDescTitle;
    Button btnAlbumPic;
    Button btnSaveAlbum;
    ListView lvAllUsers;
    Boolean isAlbumEditable = false;
    Bitmap selectedAlbumPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book_gallery);

        // Gallery
        g           = (Gallery)findViewById(R.id.gallery);
        bAddRecipe  = (Button) findViewById(R.id.btnAddRecipeToAlbum);
        tbLayout    = (TableLayout) findViewById(R.id.tbIngredients);

        // Recipe Include
        ivRecipeImage   = (ImageView) findViewById(R.id.RecipeImage);
        RcipeName       = (TextView) findViewById(R.id.tvRcipeName);
        DishType        = (TextView) findViewById(R.id.tvDishType);
        Level           = (TextView) findViewById(R.id.tvLevel);
        KitchenType     = (TextView) findViewById(R.id.tvKitchenType);
        Category        = (TextView) findViewById(R.id.tvCategory);
        Vegan           = (CheckBox) findViewById(R.id.cbVegan);
        Vegaterian      = (CheckBox) findViewById(R.id.cbVeg);
        Diet            = (CheckBox) findViewById(R.id.cbDiet);
        IngredientsTitle= (TextView)  findViewById(R.id.tvIngredientsTitle);
        IngredientName  = (TextView)  findViewById(R.id.tvIngredientName);
        IngredientType  = (TextView)  findViewById(R.id.tvIngredientType);
        IngredientAmount= (TextView)  findViewById(R.id.tvIngredientAmount);
        HowToMakeTitle  = (TextView)  findViewById(R.id.tvHowToMakeTitle);
        HowToMake       = (TextView)  findViewById(R.id.tvHowToMake);

        //Album Include
        albumType       = (EditText)    findViewById(R.id.etAlbumType);
        albumName       = (EditText)    findViewById(R.id.etAlbumName);
        albumDesc       = (EditText)    findViewById(R.id.etAlbumDetails);
        albumNameTitle  = (TextView)    findViewById(R.id.tvrRcipeName);
        albumTypeTitle  = (TextView)    findViewById(R.id.tvAlbumType);
        albumDescTitle  = (TextView)    findViewById(R.id.tvAlbumDetails);
        viAlbumPic      = (ImageView)   findViewById(R.id.AlbumPic);
        btnAlbumPic     =  (Button)     findViewById(R.id.btnSelectPhoto);
        btnSaveAlbum    =  (Button)     findViewById(R.id.bSaveUser);
        lvAllUsers      =  (ListView)   findViewById(R.id.AllUsersListView);
        btnAlbumPic     =  (Button)     findViewById(R.id.btnSelectPhoto);

        makeRecipeInvisibale();
        lvAllUsers.setVisibility(View.INVISIBLE);
        btnSaveAlbum.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String albumID = extras.getString("AlbumID");
            if(albumID != null)
            {
                setEditableAlbum(isAlbumEditable);
                selectedAlbum = Queries.getAlbumById(albumID);

                albumName.setText(selectedAlbum.getAlbumName());
                albumType.setText(selectedAlbum.getAlbumType());
                albumDesc.setText((selectedAlbum.getDescription()));
                if (selectedAlbumPic != null) {
                    selectedAlbum.savePic(selectedAlbumPic);
                }
                viAlbumPic.setImageBitmap(selectedAlbum.getAlbumPicture());

                albumName.setOnLongClickListener(onAlbumLongClick);
                albumType.setOnLongClickListener(onAlbumLongClick);
                viAlbumPic.setOnLongClickListener(onAlbumLongClick);
                albumNameTitle.setOnLongClickListener(onAlbumLongClick);
                albumTypeTitle.setOnLongClickListener(onAlbumLongClick);
                albumDescTitle.setOnLongClickListener(onAlbumLongClick);

                // get Album recipe
                RecipesList = selectedAlbum.getAlbumRecipes();
                imageBitmaps = new Bitmap[RecipesList.size()];

                if (RecipesList != null) {
                    for (int i = 0; i < RecipesList.size(); i++) {
                        Recipe r = RecipesList.get(i);
                        Bitmap b = r.getRecipePicture();
                        imageBitmaps[i] =  b;

                    }
                }
            }
        }

        else
        {
            Toast t = new Toast(this);
            t.setText("לא נמצאו מתכונים עבור ספר הבישול");
            t.show();
        }

        bAddRecipe.setOnClickListener(btnAddRecipeOnClickListener);


        // Create adapter gallery
        g.setAdapter( new ImageAdapter(this, RecipesList, imageBitmaps));
        g.setOnItemClickListener(onGalletyItemSelect);

        //ALBUM edit
        btnAlbumPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnSaveAlbum.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      ArrayList<User> uu = new ArrayList<User>();
                      uu.add(Queries.getMyUser());
                      selectedAlbum.setAlbumName(albumName.getText().toString());
                      selectedAlbum.setAlbumType(albumType.getText().toString());
                      selectedAlbum.setDescription(albumDesc.getText().toString());

                      isAlbumEditable= !isAlbumEditable;
                      setEditableAlbum(isAlbumEditable);

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




    public View.OnClickListener btnAddRecipeOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if (click) {
                initiatePopUp();
                // Handle show big picture
                pw.showAsDropDown(bAddRecipe);
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

            ivRecipeImage.setImageBitmap(imageBitmaps[position]);
            Recipe r = RecipesList.get(position);
            RcipeName.setText(r.getName());

            KitchenType.setText(r.getKitchenType());
            DishType.setText(r.getDishType());
            Level.setText(r.getDifficulty());
            Category.setText(r.getCategory());

            Vegan.setChecked(r.getVegan());
            Vegaterian.setChecked(r.getVegetarian());
            Diet.setChecked(r.getDiet());

            HowToMake.setText(r.getPreparation());
            ArrayList<Grocery> gg = r.getRecipeGroceries();
            for(int i = 0; i < gg.size(); i++) {
                Grocery g = gg.get(i);
//                IngredientName.setText(g.getAmount());
//                IngredientType.setText(g.getForm());
//                IngredientAmount.setText(g.getMaterialName());
//
                addIngridientToScreen(g.getMaterialName(),g.getForm() , g.getAmount());

            }

            ivRecipeImage.setVisibility(View.VISIBLE);
            RcipeName.setVisibility(View.VISIBLE);
            DishType.setVisibility(View.VISIBLE);
            Level.setVisibility(View.VISIBLE);
            KitchenType.setVisibility(View.VISIBLE);
            Category.setVisibility(View.VISIBLE);
            Vegan.setVisibility(View.VISIBLE);
            Vegaterian.setVisibility(View.VISIBLE);
            Diet.setVisibility(View.VISIBLE);
            IngredientsTitle.setVisibility(View.VISIBLE);
            HowToMakeTitle.setVisibility(View.VISIBLE);
            HowToMake.setVisibility(View.VISIBLE);

        }
    };

    private void initiatePopUp(){

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_addrecipe_fragment, null);

        pw = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setTouchable(true);
        popupView.setBackgroundColor(getResources().getColor(R.color.primary_material_dark));

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);

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

    public void setEditableAlbum(boolean isEditable){
        albumName.setEnabled(isEditable);
        albumType.setEnabled(isEditable);
        btnAlbumPic.setEnabled(isEditable);
        albumDesc.setEnabled(isEditable);
        if (isEditable) {
            lvAllUsers.setVisibility(View.VISIBLE);
            btnSaveAlbum.setVisibility(View.VISIBLE);
        }
        else{
            lvAllUsers.setVisibility(View.INVISIBLE);
            btnSaveAlbum.setVisibility(View.INVISIBLE);
        }
    }

    View.OnLongClickListener onAlbumLongClick = new View.OnLongClickListener(){


        @Override
        public boolean onLongClick(View v) {
            isAlbumEditable = !isAlbumEditable;
            setEditableAlbum(isAlbumEditable);

            return false;
        }
    };


    public void makeRecipeInvisibale(){
        ivRecipeImage.setVisibility(View.INVISIBLE);
        RcipeName.setVisibility(View.INVISIBLE);
        DishType.setVisibility(View.INVISIBLE);
        Level.setVisibility(View.INVISIBLE);
        KitchenType.setVisibility(View.INVISIBLE);
        Category.setVisibility(View.INVISIBLE);
        Vegan.setVisibility(View.INVISIBLE);
        Vegaterian.setVisibility(View.INVISIBLE);
        Diet.setVisibility(View.INVISIBLE);
        IngredientsTitle.setVisibility(View.INVISIBLE);
        HowToMakeTitle.setVisibility(View.INVISIBLE);
        HowToMake.setVisibility(View.INVISIBLE);
    }


    public void addIngridientToScreen(String name, String type , String amount){

        // set the attribute id

        TextView actNewIngredient= new TextView(this);
        TextView spNewIngredientType = new TextView(this);
        TextView etNewIngredientAmount = new TextView(this);

        actNewIngredient.setText(name);
        spNewIngredientType.setText(type);
        etNewIngredientAmount.setText(amount);

        TableRow tr = new TableRow(this);
        TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(trLP);
        tr.setTextDirection(View.LAYOUT_DIRECTION_RTL);
/**
        // Handle AutoCompleteTextView
        actNewIngredient.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        actNewIngredient.setMaxWidth(320);

        // Handle Spinner
        spNewIngredientType.setScrollContainer(true);
        spNewIngredientType.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        // Handle EditText
        etNewIngredientAmount.setInputType(3);
        etNewIngredientAmount.setLayoutParams(new TableRow.LayoutParams(130, TableRow.LayoutParams.WRAP_CONTENT));
**/
        // Add to layOut
        tr.addView(actNewIngredient);
        tr.addView(spNewIngredientType);
        tr.addView(etNewIngredientAmount);

        tbLayout.addView(tr);
    }

 //8************************ pic ****************************************
    //8************************ pic ****************************************

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    selectedAlbumPic = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viAlbumPic.setImageBitmap(selectedAlbumPic);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        selectedAlbumPic.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                //                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                //                c.moveToFirst();
                //                int columnIndex = c.getColumnIndex(filePath[0]);
                //                String picturePath = c.getString(columnIndex);
                //                c.close();
                //                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                //                Log.w("path:", picturePath+"");
                //                viewImage.setImageBitmap(thumbnail);
            }
        }
    }
    //8************************ pic ****************************************
    //8************************ pic ****************************************





    public class ImageAdapter extends BaseAdapter {
        private Context context;
        int defaultImageBackRound;
        Bitmap[] imageBitmaps;
        Bitmap placeHolder;
        ArrayList<Recipe> RecipesListPerAlbum;

        public ImageAdapter(Context c, ArrayList<Recipe> RecipesListPerAlbum, Bitmap[] imageBitmaps){
            this.context = c;
            this.RecipesListPerAlbum = RecipesListPerAlbum;
            this.imageBitmaps = imageBitmaps;

            TypedArray styleAttrs = context.obtainStyledAttributes(R.styleable.PicGallery);
            defaultImageBackRound = styleAttrs.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 0 );
            styleAttrs.recycle();
        }

        @Override
        public int getCount() {
            return imageBitmaps.length;
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

            if (RecipesListPerAlbum != null && imageBitmaps != null) {

                image.setImageBitmap(imageBitmaps[position]);
                //image
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image.setBackgroundResource(defaultImageBackRound);

                Recipe r = RecipesListPerAlbum.get(position);
                image.setTag(r);

            }

            return image;
        }
    }

}


