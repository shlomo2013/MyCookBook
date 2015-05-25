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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.MyCookBook.CategoriesUtil.DropDownListAdapter;
import com.MyCookBook.CategoriesUtil.Group;
import com.MyCookBook.CategoriesUtil.MyExpandableListAdapter;
import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
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
    Button bSelecPic;
    Button bAddIngridient;
    Button bSetCategory;
    Button bSave;
    View rootView;
    TableLayout tbLayout;
    ListView lvDropDownList;
    View popupView;

    // AutoCompleteTextView actIngredient;
    //EditText etIngredientAmount;

    ArrayList<Grocery> groceries = new ArrayList<Grocery>();
 // more efficient than HashMap for mapping integers to objects
  SparseArray<Group> groups = new SparseArray<Group>();

    public AddRecipeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView            = inflater.inflate(R.layout.activity_addrecipe_fragment, container , false);
        popupView            = inflater.inflate(R.layout.popup, container , false);
        tbLayout            = (TableLayout)          rootView.findViewById(R.id.tbIngredients);
        bSelecPic           = (Button)               rootView.findViewById(R.id.btnSelectPhoto);
        viewImage           = (ImageView)            rootView.findViewById(R.id.viewImage);
        bAddIngridient      = (Button)               rootView.findViewById(R.id.btnAddIngridient);
        // bSetCategory        = (Button)               rootView.findViewById(R.id.btnAddCategoryToRecipe);
        bSave               = (Button)               rootView.findViewById(R.id.btnSaveRecipe);
        lvDropDownList      = (ListView)             popupView.findViewById(R.id.lvDropDownList);

        //      actIngredient       = (AutoCompleteTextView) rootView.findViewById(R.id.actIngedient);
        //      etIngredientAmount  = (EditText)             rootView.findViewById(R.id.etTextAmount);

        createData();
        ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.elvCategoriess);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity() , groups);
        listView.setAdapter(adapter);

        bAddIngridient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                addIngridientToScreen();
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
                SaveRecipe(rootView);
            }
        });

        return rootView;
    }

    private void SaveRecipe(View v) {
        Recipe r = new Recipe();

        EditText name = (EditText) v.findViewById(R.id.etRecName);
//            v.findViewById()
//            r.initRecipe(name.toString(), ArrayList<String> categories,String subCategory,String preparation,String dishType,
//                                       String difficulty,String kitchenType,String diet,String vegetarian,String vegan);
//            r.addRecipe(Queries.getMyUser());
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
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
                initCategories(lvDropDownList, alFoodCategory, R.array.personal_pref_array);

                // set the pop up window
                initiatePopUp();

                // dismiss
                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
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
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });

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
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
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

    public void addIngridientToScreen(){

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
        actNewIngredient.setMaxWidth(350);
        // Handle Spinner
        createSpinner(spNewIngredientType);
        spNewIngredientType.setScrollContainer(true);
        spNewIngredientType.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        // Handle EditText
        etNewIngredientAmount.setInputType(3);
        etNewIngredientAmount.setLayoutParams(new TableRow.LayoutParams(120,TableRow.LayoutParams.WRAP_CONTENT));

        // Add to layOut
        tr.addView(actNewIngredient);
        tr.addView(spNewIngredientType);
        tr.addView(etNewIngredientAmount);

        tbLayout.addView(tr);
    }

    public void btnCategoryDropDawn(){
               // get all of the categories
                initCategories(lvDropDownList , alFoodCategory, R.array.personal_pref_array);

                // set the pop up window
                initiatePopUp();

                // dismiss
                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        pw.dismiss();
                    }});

                pw.showAsDropDown(bSetCategory);
    }
    public void addListenerOnSpinnerItemSelection() {
        // 	spIngredType = (Spinner) rootView.findViewById(R.id.spinner1);
        // 	spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void createData() {
       for (int j = 0; j < 5; j++) {
         Group group = new Group("Test " + j);
         for (int i = 0; i < 5; i++) {
           group.children.add("Sub Item" + i);
         }
         groups.append(j, group);
       }
     }

}

