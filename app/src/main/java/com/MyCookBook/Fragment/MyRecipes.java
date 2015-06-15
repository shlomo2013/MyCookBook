package com.MyCookBook.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MyCookBook.Entities.Album;
import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;


/**
 * Created by nirgadasi on 6/15/15.
 */
public class MyRecipes extends Fragment {

    private ListView lvRecipe;

    private ArrayList<Album> cookBooks;
    private ArrayList<Recipe> userRecipies;
    private final String strAddToAlbum = "הוסף לספר מתכונים";
    private final String srtEditRecipe = "עריכה";
    private final String strCancle = "ביטול";
    View RecipeRowView;
    View rootView;

    private PopupWindow pwAlbum;
    ImageView RecipePic;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.gallery_view,  container , false);
        RecipeRowView = inflater.inflate(R.layout.all_albums_list,  container , false);


        // Initialize the variables:
        lvRecipe = (ListView) rootView.findViewById(R.id.lvAlbumes);

        userRecipies = Queries.getUserRecipes(Queries.getMyUser());

        // Set an Adapter to the ListView
        lvRecipe.setAdapter(new RecipeAdapter(inflater,userRecipies));

        //on item Click
        lvRecipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openOptionWindow();
//                Intent i = new Intent(getActivity().getApplicationContext(), CookBookGalleryActivity.class);
//                RecipePic           = (ImageView)            view.findViewById(R.id.thumb);

            }
        });


        return rootView;
    }


    private void openOptionWindow() {

        final CharSequence[] options = { strAddToAlbum , srtEditRecipe,strCancle };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("אפשרויות");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(strAddToAlbum)) {
                    initiatePopUp();
                    pwAlbum.showAtLocation(rootView, Gravity.BOTTOM, 10, 10);


                } else if (options[item].equals(srtEditRecipe)) {


                } else if (options[item].equals(strCancle)) {
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


            } else if (requestCode == 2) {

            }
        }
    }

    private void initiatePopUp() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.gallery_view, null);
        ListView lv = (ListView)popupView.findViewById(R.id.lvAlbumes);

        pwAlbum = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cookBooks = Queries.getAlbumUserCreated(Queries.getMyUser());

        // Set an Adapter to the ListView
        lv.setAdapter(new AlbumsAdapter(layoutInflater,cookBooks));

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pwAlbum.setBackgroundDrawable(new BitmapDrawable());
        pwAlbum.setFocusable(true);
        popupView.setBackgroundColor(getResources().getColor(R.color.primary_dark_material_light));
        pwAlbum.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pwAlbum.setOutsideTouchable(true);

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pwAlbum.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pwAlbum.dismiss();
                    return true;
                }
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListView lv = (ListView)view.findViewById(R.id.lvAlbumes);
                lv
                Toast.makeText(
                        getActivity(),
                        lv,
                        Toast.LENGTH_LONG).show();


                pwAlbum.dismiss();

            }


        });

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

}

// Create an Adapter Class extending the BaseAdapter
class RecipeAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Recipe> cookbook;
    ImageView iv;


    public RecipeAdapter(LayoutInflater inflater ,ArrayList<Recipe> cookbook) {
        this.layoutInflater = inflater;
        this.cookbook = cookbook;
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

        Recipe a = cookbook.get(position);
        iv.setTag(a);

        iv.setImageBitmap(a.getRecipePicture());
        tvTitle.setText(a.getName());
        return listItem;
    }



}


//////////******************************************************************//////
//////////******************************************************************//////

// Set on item click listener to the ListView


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  long arg3) {
//                // actions to be performed when a list item clicked
//                int pos = arg2;
//
//                View layout = singlGalleryView.findViewById(R.id.toast_layout_root);
//
//                ImageView iv = (ImageView) layout.findViewById(R.id.toast_iv);
//                TextView tv = (TextView) layout.findViewById(R.id.toast_tv);
//
//                iv.setBackgroundResource(thumb[pos]);
//                tv.setText(title[pos]);
//
//                Toast toast = new Toast(getActivity().getApplicationContext());
//                toast.setView(layout);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//            }
//        });
