package com.MyCookBook.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.MyCookBook.CategoriesUtil.DropDownListAdapter;
import com.MyCookBook.CategoriesUtil.Group;
import com.MyCookBook.CategoriesUtil.MyExpandableListAdapter;
import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
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

    ImageView viewImage;
    View rootView;
    View popupView;

    Button bSelecPic;
    Button bAddIngridient;
    Button bSetCategory;
    Button bSave;

    TableLayout tbLayout;
    ListView lvDropDownList;
    LinearLayout ll;

    ArrayList<Grocery> groceries = new ArrayList<Grocery>();

    // arguments from screen
    EditText recipeName              ;
    EditText recipeHowToMake         ;
    Spinner recipeLevel              ;
    Spinner recipeKitchenType        ;
    CheckBox recipeFirst             ;
    CheckBox recipeMain              ;
    CheckBox recipeDesert            ;
    CheckBox recipeDiet              ;
    CheckBox recipeVegan             ;
    CheckBox recipeVegetarian        ;
    RadioGroup  recipeDishTypeGroup  ;
    RadioButton recipeDishType       ;
    ExpandableListView recipeCategory;

    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();

    private int nIngridientsCounter = 0;
    private int levelId;
    private int KitchenTypeId;

    public AddRecipeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView            = inflater.inflate(R.layout.activity_addrecipe_fragment, container , false);
        popupView            = inflater.inflate(R.layout.popup, container , false);
        tbLayout            = (TableLayout)          rootView.findViewById(R.id.tbIngredients);
        viewImage           = (ImageView)            rootView.findViewById(R.id.viewImage);
        bAddIngridient      = (Button)               rootView.findViewById(R.id.btnAddIngridient);
        bSave               = (Button)               rootView.findViewById(R.id.btnSaveRecipe);
        lvDropDownList      = (ListView)             popupView.findViewById(R.id.lvDropDownList);
        ll                  = (LinearLayout)             rootView.findViewById(R.id.LinearLayout1);

        // arguments from screen
        recipeName                   = (EditText)           rootView.findViewById(R.id.etRecName);
        recipeHowToMake              = (EditText)           rootView.findViewById(R.id.etHowToMake);
        recipeLevel                  = (Spinner)            rootView.findViewById(R.id.Level);
        recipeKitchenType            = (Spinner)            rootView.findViewById(R.id.KitchenType);
        recipeDiet                   = (CheckBox)           rootView.findViewById(R.id.cbDiet);
        recipeVegan                  = (CheckBox)           rootView.findViewById(R.id.cbVegan);
        recipeVegetarian             = (CheckBox)           rootView.findViewById(R.id.cbVeg);
//        recipeFirst                  = (CheckBox)           rootView.findViewById(R.id.CBfirst);
//        recipeMain                   = (CheckBox)           rootView.findViewById(R.id.CBmain);
//        recipeDesert                 = (CheckBox)           rootView.findViewById(R.id.CBdesert);
        recipeCategory               = (ExpandableListView) rootView.findViewById(R.id.elvCategoriess);
        bSelecPic                    = (Button)             rootView.findViewById(R.id.btnSelectPhoto);
        recipeDishTypeGroup          = (RadioGroup)         rootView.findViewById(R.id.rgDishType);
        recipeDishType               = (RadioButton)        rootView.findViewById(recipeDishTypeGroup.getCheckedRadioButtonId());

        final Spinner dropdownLevel = (Spinner) rootView.findViewById(R.id.Level);
        levelId = dropdownLevel.getId();
        createSpinner((dropdownLevel), R.array.Levels);
;
        final Spinner dropdownKitchenType = (Spinner) rootView.findViewById(R.id.KitchenType);
        KitchenTypeId = dropdownKitchenType.getId();
        createSpinner((dropdownKitchenType), R.array.recipie_category);

        createExpendableList();
        ExpandableListView categoryList = (ExpandableListView) rootView.findViewById(R.id.elvCategoriess);
        final MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity() , groups);
        categoryList.setAdapter(adapter);

//        final ListView dropdownLevel = (ListView) rootView.findViewById(R.id.lvLevel);
//        createListView(dropdownLevel, R.array.Levels);

        // Handle Categories list
//        categoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                int numItems = adapter.getChildrenCount(groupPosition);
//                int layoutHight = numItems * 20;
//             //   ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, layoutHight));
//                return false;
//            }
//        });

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

        // Handle Categories btn
//        bSetCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                btnCategoryDropDawn();
//            }
//        });

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
        String subCategory = "חמים";
        String dishType = "ss"; //recipeDishType.getText().toString();

        String difficulty = (String)recipeLevel.getSelectedItem();
        String kitchenType = (String)recipeKitchenType.getSelectedItem();
        String preparation = recipeHowToMake.getText().toString();
        boolean diet = recipeDiet.isChecked();
        boolean vegetarian = recipeVegetarian.isChecked();
        boolean vegan = recipeVegan.isChecked();
        String name = recipeName.getText().toString();

        Recipe r = new Recipe(name, category , subCategory,preparation ,  dishType,
                                    difficulty, kitchenType, diet, vegetarian, vegan);

        //EditText name = (EditText) v.findViewById(R.id.etRecName);
//        r.initRecipe(name, category , subCategory,preparation ,  dishType,
//                     difficulty, kitchenType, diet, vegetarian, vegan);
        r.updateGroceries(groceries);
        r.addRecipe(Queries.getMyUser());
        r.savePic(selectedBitmap);
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

    public void handleCategories() {
        final Button btnDDPersonalCategories = (Button) rootView.findViewById(R.id.btnDropDownPersonalCategories);
        btnDDPersonalCategories.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                popupView = layoutInflater.inflate(R.layout.popup, null);
                lvDropDownList = (ListView) popupView.findViewById(R.id.lvDropDownList);

                // get all of the categories
                initCategories(lvDropDownList, alFoodCategory, R.array.recipie_category);

                // set the pop up window
                initiatePopUp();

                // dismiss
                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });

                pw.showAsDropDown(btnDDPersonalCategories);

            }
        });
    }

    public void initCategories(ListView lvListView, ArrayList<CheckBox> items, int ArrayName){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                ArrayName,
                android.R.layout.simple_list_item_1);

        items = new ArrayList<CheckBox>();
        for (int i = 0; i < adapter.getCount(); i++){
            String s = (String)adapter.getItem(i);
            CheckBox c = new CheckBox(getActivity().getBaseContext());
            c.setText(s);
            //
            //            if(i%2 == 0) {
            //                c.setChecked(true);
            //            }else
            //            {
            //                c.setChecked(false);
            //            }
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

    public int addIngridientToScreen(int nExsistingIngridients){

        //Add Ingridient to counter
        nExsistingIngridients++;

        // set the attribute id
        int nId = nExsistingIngridients * 10;

        AutoCompleteTextView actNewIngredient= new AutoCompleteTextView(getActivity().getBaseContext());
        Spinner spNewIngredientType = new Spinner(getActivity().getBaseContext());
        EditText etNewIngredientAmount = new EditText(getActivity().getBaseContext());

        TableRow tr = new TableRow(getActivity().getBaseContext());
        TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(trLP);
        tr.setTextDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle AutoCompleteTextView
        actNewIngredient.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        actNewIngredient.setMaxWidth(320);
        actNewIngredient.setId(nId + 1);

        // Handle Spinner
        createSpinner(spNewIngredientType);
        spNewIngredientType.setScrollContainer(true);
        spNewIngredientType.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        spNewIngredientType.setId(nId + 2);

        // Handle EditText
        etNewIngredientAmount.setInputType(3);
        etNewIngredientAmount.setLayoutParams(new TableRow.LayoutParams(130, TableRow.LayoutParams.WRAP_CONTENT));
        etNewIngredientAmount.setId(nId + 3);

        // Add to layOut
        tr.addView(actNewIngredient);
        tr.addView(spNewIngredientType);
        tr.addView(etNewIngredientAmount);

        tbLayout.addView(tr);
        return nExsistingIngridients;
    }

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

          //  g.initGrocery(matirial.getText().toString() , form.toString(), amount.getText().toString());
            Grocery g = new Grocery(matirial.getText().toString() , form.getSelectedItem().toString(), amount.getText().toString());
            groceries.add(g);
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

