package com.MyCookBook.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.MyCookBook.Activity.CookBookGalleryActivity;
import com.MyCookBook.Entities.Album;
import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Utiltis.DropDownListAdapter;
import com.MyCookBook.Utiltis.Group;
import com.MyCookBook.Utiltis.MyExpandableListAdapter;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class AddRecipeFragment extends Fragment {

    private PopupWindow pw;
    private ArrayList<CheckBox> alFoodCategory;

    View rootView;
    View popupView;
    TableLayout tbLayout;
    ListView lvDropDownList;
    LinearLayout ll;

    // album
    private ListView lvAlbums;
    private Album selectedAlbum;

    // arguments from screen
    EditText recipeName              ;
    EditText recipeHowToMake         ;
    Spinner recipeLevel              ;
    Spinner recipeKitchenType        ;
    CheckBox recipeDiet              ;
    CheckBox recipeVegan             ;
    CheckBox recipeVegetarian        ;
    RadioGroup  recipeDishTypeGroup  ;
    RadioButton recipeDishType       ;
    ExpandableListView recipeCategory;
    Button bSelecPic;
    Button bAddIngridient;
    Button bSetCategory;
    Button bSave;
    ImageView viewImage;
    ImageView gallaryPic;

    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();
    ArrayList<Grocery> groceries = new ArrayList<Grocery>();
    ArrayList<Album> cookBooks = new ArrayList<Album>();

    private int nIngridientsCounter = 0;
    private int levelId;
    private int KitchenTypeId;

    public AddRecipeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView            = inflater.inflate(R.layout.activity_addrecipe_fragment, container , false);
        popupView            = inflater.inflate(R.layout.popup, container , false);
        tbLayout            = (TableLayout)          rootView.findViewById(R.id.tbIngredients2);
        viewImage           = (ImageView)            rootView.findViewById(R.id.viewImage);
        bAddIngridient      = (Button)               rootView.findViewById(R.id.btnAddIngridient);
        bSave               = (Button)               rootView.findViewById(R.id.btnSaveRecipe);
        lvDropDownList      = (ListView)             popupView.findViewById(R.id.lvDropDownList);
        ll                  = (LinearLayout)         rootView.findViewById(R.id.LinearLayout1);

        // arguments from screen
        recipeName                   = (EditText)           rootView.findViewById(R.id.etRecName);
        recipeHowToMake              = (EditText)           rootView.findViewById(R.id.etHowToMake);
        recipeLevel                  = (Spinner)            rootView.findViewById(R.id.Level);
        recipeKitchenType            = (Spinner)            rootView.findViewById(R.id.KitchenType);
        recipeDiet                   = (CheckBox)           rootView.findViewById(R.id.cbDiet);
        recipeVegan                  = (CheckBox)           rootView.findViewById(R.id.cbVegan);
        recipeVegetarian             = (CheckBox)           rootView.findViewById(R.id.cbVeg);
        recipeCategory               = (ExpandableListView)           rootView.findViewById(R.id.elvCategoriess);
        bSelecPic                    = (Button)             rootView.findViewById(R.id.btnSelectPhoto);
        recipeDishTypeGroup          = (RadioGroup)         rootView.findViewById(R.id.rgDishType);
        recipeDishType               = (RadioButton)        rootView.findViewById(recipeDishTypeGroup.getCheckedRadioButtonId());
        lvAlbums                     = (ListView)           rootView.findViewById(R.id.lvAlbumes);


        // LEVEL
        final Spinner dropdownLevel = (Spinner) rootView.findViewById(R.id.Level);
        levelId = dropdownLevel.getId();
        createSpinner((dropdownLevel), R.array.Levels);

        // KITCHEN TYPE
        final Spinner dropdownKitchenType = (Spinner) rootView.findViewById(R.id.KitchenType);
        KitchenTypeId = dropdownKitchenType.getId();
        createSpinner((dropdownKitchenType), R.array.categories);

        //Handle Category
        createExpendableList();
      //  ExpandableListView categoryList = (ExpandableListView) rootView.findViewById(R.id.elvCategoriess);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
//                        R.array.categories,
//                        R.layout.listrow_category_details);
//        recipeCategory.setAdapter(adapter);


        final MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity() , groups);
        recipeCategory.setAdapter(adapter);
//

        // Add ingridient
        bAddIngridient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nIngridientsCounter = addIngridientToScreen(nIngridientsCounter);
            }
        });

        // Handle Photo select
        bSelecPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        // ALBUM
        cookBooks = Queries.getAlbumUserCreated(Queries.getMyUser());
        lvAlbums.setAdapter(new AlbumsAdapter(inflater,cookBooks));

        //on item Click
        lvAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getActivity().getApplicationContext(), CookBookGalleryActivity.class);
                gallaryPic = (ImageView) view.findViewById(R.id.thumb);
                selectedAlbum = (Album) gallaryPic.getTag();
                Toast.makeText(
                        getActivity(),
                        selectedAlbum.getAlbumName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        // Save recipe
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputIngredients();
                SaveRecipe(rootView);
            }
        });

        return rootView;
    }

    private void SaveRecipe(View v) {

        String category = "מרקים";
        // אין תת קטגוריה עדין
        String subCategory = "חמים";
        // צריך לקחת את התיאור של הרדיו בטן שנבחר
        String dishType = " sdsds"; //recipeDishType.getText().toString(); //(String)recipeDishType.getSel().toString();

        String difficulty = (String)recipeLevel.getSelectedItem();
        String kitchenType = (String)recipeKitchenType.getSelectedItem();
        String preparation = recipeHowToMake.getText().toString();
        boolean diet = recipeDiet.isChecked();
        boolean vegetarian = recipeVegetarian.isChecked();
        boolean vegan = recipeVegan.isChecked();
        String name = recipeName.getText().toString();

        Recipe r = new Recipe(name, category , subCategory,preparation ,  dishType,
                difficulty, kitchenType, diet, vegetarian, vegan);

        r.updateGroceries(groceries);
        r.savePic(selectedBitmap);
        r.addRecipe(Queries.getMyUser());

        if(selectedAlbum!=null)
            selectedAlbum.addRecipe(r);
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    private void createSpinner(Spinner sp){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.ingridient_type_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        sp.setAdapter(adapter);

    }

//    public void handleCategories() {
//        final Button btnDDPersonalCategories = (Button) rootView.findViewById(R.id.btnDropDownPersonalCategories);
//        btnDDPersonalCategories.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                popupView = layoutInflater.inflate(R.layout.popup, null);
//                lvDropDownList = (ListView) popupView.findViewById(R.id.lvDropDownList);
//
//                // get all of the categories
//                initCategories(lvDropDownList, alFoodCategory, R.array.recipie_category);
//
//                // set the pop up window
//                initiatePopUp();
//
//                // dismiss
//                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
//                btnDismiss.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        pw.dismiss();
//                    }
//                });
//
//                pw.showAsDropDown(btnDDPersonalCategories);
//
//            }
//        });
//    }

    public void initCategories(ListView lvListView, ArrayList<CheckBox> items, int ArrayName){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                ArrayName,
                android.R.layout.simple_list_item_1);

        items = new ArrayList<CheckBox>();
        for (int i = 0; i < adapter.getCount(); i++){
            String s = (String)adapter.getItem(i);
            CheckBox c = new CheckBox(getActivity().getBaseContext());
            c.setText(s);

            items.add(c);
        }
        DropDownListAdapter mAdapter = new DropDownListAdapter(items, getActivity().getBaseContext());
        lvListView.setAdapter(mAdapter);

    }

    private void initiatePopUp(){

        pw = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

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
    public Bitmap selectedBitmap = null;


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

                    selectedBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage.setImageBitmap(selectedBitmap);


                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
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
                                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                                c.moveToFirst();
                                int columnIndex = c.getColumnIndex(filePath[0]);
                                String picturePath = c.getString(columnIndex);
                                c.close();
                                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                                Log.w("path:", picturePath + "");
                                viewImage.setImageBitmap(thumbnail);
            }
        }
    }

    public int addIngridientToScreen(int nExsistingIngridients){

        //Add Ingridient to counter
        nExsistingIngridients++;

        // set the attribute id
        int nId = nExsistingIngridients * 10;

        AutoCompleteTextView actNewIngredient= new AutoCompleteTextView(getActivity().getBaseContext());
        Spinner spNewIngredientType = new Spinner(getActivity().getBaseContext());
        EditText etNewIngredientAmount = new EditText(getActivity().getBaseContext());
        ImageButton btnCancel = new ImageButton(getActivity().getBaseContext());


        TableRow tr = new TableRow(getActivity().getBaseContext());
        TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(trLP);
        tr.setTextDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle AutoCompleteTextView
        actNewIngredient.setLayoutParams(new TableRow.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        actNewIngredient.setMaxWidth(320);
        actNewIngredient.setId(nId + 1);

        // Handle Spinner
        createSpinner(spNewIngredientType);
        spNewIngredientType.setScrollContainer(true);
        spNewIngredientType.setLayoutParams(new TableRow.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        spNewIngredientType.setId(nId + 2);

        // Handle EditText
        etNewIngredientAmount.setInputType(3);
        etNewIngredientAmount.setLayoutParams(new TableRow.LayoutParams(130, TableRow.LayoutParams.WRAP_CONTENT));
        etNewIngredientAmount.setId(nId + 3);


        btnCancel.setImageResource(R.drawable.cancel);
        //TableRow.LayoutParams lp = new TableRow.LayoutParams(100, 100);
        btnCancel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100, 100);
        //Drawable replacer = getResources().getDrawable(R.drawable.);

       // btnCancel.setLayoutParams(lp);
        btnCancel.setOnClickListener(btnCancelOnClickListener);
        btnCancel.setBackgroundColor(Color.TRANSPARENT);
        btnCancel.setTag(nExsistingIngridients);
        btnCancel.setId(nExsistingIngridients);
        //btnCancel.setMaxHeight(100);
        //btnCancel.setMaxWidth(100);


        // Add to layOut
        tr.addView(actNewIngredient);
        tr.addView(spNewIngredientType);
        tr.addView(etNewIngredientAmount);
        tr.addView(btnCancel);

        tbLayout.addView(tr);
        return nExsistingIngridients;
    }

    private View.OnClickListener btnCancelOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //check which green ball was clicked
            ImageButton imgBtn = (ImageButton) v;
            TableRow currRow = ((TableRow)v.getParent());
            currRow.removeAllViews();
        }
    };

    public void btnCategoryDropDawn(){
        // get all of the categories
        initCategories(lvDropDownList , alFoodCategory, R.array.recipie_category);

        // set the pop up window
        initiatePopUp();

        // dismiss
        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }});

        pw.showAsDropDown(bSetCategory);
    }

    public void addListenerOnSpinnerItemSelection() {
        // 	spIngredType = (Spinner) rootView.findViewById(R.id.spinner1);
        // 	spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void createExpendableList() {

//        for (int j = 0; j < 2; j++) {
//          Group group = new Group("Test " + j);
//          for (int i = 0; i < 5; i++) {
//            group.children.add("Sub Item" + i);
//          }
//          groups.append(j, group);
//        }

        Group CategoryGroup = new Group("קטגוריה" );
        // Group CategoryGroup = new Group("קטגוריות " );

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.recipie_category,
                android.R.layout.simple_expandable_list_item_1);

        for (int i = 0; i < adapter.getCount(); i++) {
            String category =(String)adapter.getItem(i);

            //  createListView;
            CategoryGroup.children.add(category);
        }
        groups.append(0, CategoryGroup);

//        CategoryGroup = new Group("תת קטגוריה" );
//   //     CategoryGroup = new Group("תת קטגוריה " );
//        // TODO dana: ליצור פונקציה שתביא את התת קטגוריה ע״פ  הקטגוריה שנבחרה
//        if (true){
//
//            // Create an ArrayAdapter using the string array and a default spinner layout
//            ArrayAdapter<CharSequence> subAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
//                    R.array.subCategoryBread,
//                    android.R.layout.simple_expandable_list_item_1);
//
//            for (int i = 0; i < subAdapter.getCount(); i++) {
//                String subCategory =(String)subAdapter.getItem(i);
//
//                //  createListView;
//                CategoryGroup.children.add(subCategory);
//            }
//        }
//        else{
//            CategoryGroup.children.add( "לא קיים תת קטגוריה" );
//        }

//        groups.append(1, CategoryGroup);

    }

    private void createListView(ListView lv, @ArrayRes int dataList){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                dataList,
                android.R.layout.simple_dropdown_item_1line);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        lv.setAdapter(adapter);

    }

    private void getInputIngredients(){

        int nid = 1;

        for (int i = 1 ; i <= nIngridientsCounter; i++){
            nid = i;
            nid *= 10;

            AutoCompleteTextView  matirial    = (AutoCompleteTextView)   rootView.findViewById(nid+1);
            Spinner               form        = (Spinner)                rootView.findViewById(nid+2);
            EditText              amount      = (EditText)               rootView.findViewById(nid+3);
            if(matirial!=null && form!=null && amount!=null) {
                //  g.initGrocery(matirial.getText().toString() , form.toString(), amount.getText().toString());
                Grocery g = new Grocery(matirial.getText().toString(), form.getSelectedItem().toString(), amount.getText().toString());
                groceries.add(g);
            }
        }
    }

    private void createSpinner(Spinner sp, @ArrayRes int dataList){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                dataList,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (sp.getId() != levelId && sp.getId() != KitchenTypeId)
        {
            sp.setVisibility(View.INVISIBLE);
        }

        // Apply the adapter to the spinner
        sp.setAdapter(adapter);

    }

}


class categoryAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Album> cookbook;
    ImageView iv;


    public categoryAdapter(LayoutInflater inflater ) {
        this.layoutInflater = inflater;
    }

    @Override
    public int getCount() {
        // Set the count value to the total number of items in the Array
        return cookbook.size();
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

        // Inflate the item layout and set the views
        View listItem;
        listItem = layoutInflater.inflate(R.layout.all_albums_list, null);

        // Initialize the views in the layout
         iv = (ImageView) listItem.findViewById(R.id.thumb);
        TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
        TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);

        Album a = cookbook.get(position);
        iv.setTag(a);

        iv.setImageBitmap(a.getAlbumPicture());
        tvTitle.setText(a.getAlbumName());
        tvDesc.setText(a.getDescription());

        return listItem;
    }

}

