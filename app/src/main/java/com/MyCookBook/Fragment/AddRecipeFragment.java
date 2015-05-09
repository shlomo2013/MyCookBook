package com.MyCookBook.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import com.example.mycookbook.mycookbook.R;

import java.io.File;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class AddRecipeFragment extends Fragment {

    ImageView viewImage;
    Button bSelecPic;
    Button bAddIngridient;
    // Spinner spIngredType;
    View rootView;
    TableLayout tbLayout;
   // AutoCompleteTextView actIngredient;
    //EditText etIngredientAmount;

    public AddRecipeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView            = inflater.inflate(R.layout.activity_addrecipe_fragment, container , false);
        tbLayout            = (TableLayout)          rootView.findViewById(R.id.tbIngredients);
        bSelecPic           = (Button)               rootView.findViewById(R.id.btnSelectPhoto);
        viewImage           = (ImageView)            rootView.findViewById(R.id.viewImage);
        bAddIngridient      = (Button)               rootView.findViewById(R.id.btnAddIngridient);
  //      actIngredient       = (AutoCompleteTextView) rootView.findViewById(R.id.actIngedient);
  //      etIngredientAmount  = (EditText)             rootView.findViewById(R.id.etTextAmount);

        bAddIngridient.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View arg0) {

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
       });
        // Handle Photo select
        bSelecPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        return rootView;
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
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

                    String path = android.os.Environment
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

    public void addIngridientButton(){


    }
    public void addListenerOnSpinnerItemSelection() {
  // 	spIngredType = (Spinner) rootView.findViewById(R.id.spinner1);
  // 	spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
     }
}
