package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.MyCookBook.Utiltis.DropDownListAdapter;
import com.example.mycookbook.mycookbook.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class PersonalFragment extends Fragment {

    private PopupWindow pw;

    TableLayout tbLayout;
    LinearLayout llPopup;
    RelativeLayout rlpersonalfrag;
    ListView lvDropDownList;

    View popupView;
  //  View singlGalleryView;
    View  rootView    ;
    View  AddAlbumView    ;
    View  SettingsView    ;
  //  View  popUpView   ;
  //  View  dropDownView;

    Fragment subFrag;
    FragmentTransaction fragTransaction;

    Button btnMyAlbums;
    Button btnAllAlbums;
    Button btnSettings;
    Button btnAddAlbum;

    ArrayList<CheckBox> alFoodCategory;
    ArrayList<CheckBox> alNoFoodCategory;
    DropDownListAdapter mAdapter;

    LayoutInflater MyInflater;



    public PersonalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyInflater       = inflater;

        rootView         = inflater.inflate(R.layout.activity_personal_fregment, container, false);
        SettingsView     = inflater.inflate(R.layout.settings_personal, container, false);
        AddAlbumView     = inflater.inflate(R.layout.add_album, container, false);

       // dropDownView    = inflater.inflate(R.layout.drop_down_list_row, container, false);
        popupView       = inflater.inflate(R.layout.popup, null);
       // singlGalleryView     = inflater.inflate(R.layout.custom_toast, container, false);


  //      llPopup                   = (LinearLayout)       rootView.findViewById(R.id.DropDownList);
  //      lvDropDownList            = (ListView)           popupView.findViewById(R.id.lvDropDownList);

        btnMyAlbums = (Button) rootView.findViewById(R.id.bMyAlbums);
        btnAllAlbums = (Button) rootView.findViewById(R.id.bAllAlbums);
        btnSettings  = (Button) rootView.findViewById(R.id.bSettings);
        btnAddAlbum  = (Button) rootView.findViewById(R.id.bAddAlbum);

        btnMyAlbums.setOnClickListener(btnOnClickListener);
        btnAllAlbums.setOnClickListener(btnOnClickListener);
        btnSettings.setOnClickListener(btnOnClickListener);
        btnAddAlbum.setOnClickListener(btnOnClickListener);

        subFrag = new MyGallery();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.fragPersonal, subFrag);
        fragTransaction.commit();

        return rootView;
    }
    private ArrayList<ImageView> imageViewReader( ) {
        ArrayList<ImageView> a = new ArrayList<>();

        ImageView iv = new ImageView(getActivity().getBaseContext());

        iv.setImageResource(R.drawable.messenger_bubble_large_blue);
        iv.setImageResource(R.mipmap.ic_launcher);
        iv.setImageResource(R.mipmap.coocbook_logo);
        iv.setImageResource(R.mipmap.red_balloon_2_icon);
        iv.setImageResource(R.mipmap.cookbook_poster);
        iv.setImageResource(R.mipmap.red_gear_icon );
        iv.setImageResource(R.mipmap.red_camera_icon);
        iv.setImageResource(R.mipmap.red_search_icon);
        iv.setImageResource(R.mipmap.red_lock_icon);
        iv.setImageResource(R.mipmap.red_unlock_icon);
        iv.setImageResource(R.mipmap.red_user_icon);


        return a;
    }

    private ArrayList<File> imageReader(File root) {
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            if(files[i].isDirectory()) {
                a.addAll(imageReader(files[i]));
            }
            else{
                if(files[i].getName().endsWith(".JPG") || files[i].getName().endsWith(".PNG") ){
                    a.add(files[i]);
                }
            }

        }
        return a;
    }

    public void initCategories(ListView lvListView, ArrayList<CheckBox> items, int ArrayName){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                ArrayName,
                android.R.layout.simple_list_item_1);

        items = new ArrayList<CheckBox>();
        for (int i = 0; i < adapter.getCount(); i++){
            //      String s = (String)adapter.getItem(i);
            CheckBox c = new CheckBox(getActivity().getBaseContext() );
            items.add(c);

        }

        mAdapter = new DropDownListAdapter(items, getActivity().getBaseContext());
        lvListView.setAdapter(mAdapter);

    }

    private void initiatePopUp(){

        pw = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        pw.setHeight(LayoutParams.WRAP_CONTENT);

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });

    }

    public void createCheckBoxOnLayout(@ArrayRes int ArrayName){

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                ArrayName,
                android.R.layout.activity_list_item);
        TableRow tr = createTbRow();
        CheckBox ckCategory = null;

        for (int i = 0; i < adapter.getCount(); i++)
        {

            if (i%3 == 0){
                if (ckCategory != null) {
                    tbLayout.addView(tr);
                    tr = createTbRow();

                }
            }

            ckCategory = new CheckBox(getActivity().getBaseContext());
            ckCategory.setText((String)adapter.getItem(i));
            tr.addView(ckCategory);

        }
        tbLayout.addView(tr);

    }

    public TableRow createTbRow(){

        TableRow tr = new TableRow(getActivity().getBaseContext());
        TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(trLP);
        //   tr.setTextDirection(View.LAYOUT_DIRECTION_RTL);
        return tr;
    }

    public void handleNoCategories(){
        final Button btnDDNoPersonalCategories = (Button)rootView.findViewById(R.id.btnDropDownPersonalCategoriesNOT);
        btnDDNoPersonalCategories.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //    ListView  lvDropDownListNot = (ListView)         rootView.findViewById(R.id.lvDropDownListNot);

                // get all of the categories
                initCategories(lvDropDownList , alNoFoodCategory, R.array.personal_no_pref_array);

                // set the pop up window
                initiatePopUp();

                // dismiss
                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }});

                pw.showAsDropDown(btnDDNoPersonalCategories);

            }});
    }

    public void handleCategories(){
        final Button btnDDPersonalCategories = (Button)rootView.findViewById(R.id.btnDropDownPersonalCategories);
        btnDDPersonalCategories.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {

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

                pw.showAsDropDown(btnDDPersonalCategories);

            }});


    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Bundle b = new Bundle();

            if (v == btnMyAlbums) {
                fragTransaction = getFragmentManager().beginTransaction().detach(subFrag);
                b.putBoolean("MyAlbum", true);
                subFrag = new MyGallery();
                subFrag.setArguments(b);

            }
            else if (v == btnAllAlbums) {
//                fragTransaction = getFragmentManager().beginTransaction().detach(subFrag);
                fragTransaction = getFragmentManager().beginTransaction().remove(subFrag);
                b.putBoolean("MyAlbum", false);
                subFrag = new MyGallery();
                subFrag.setArguments(b);

            }
            else if (v == btnSettings) {
                subFrag = new SettingsPersonal();
            }
            else if (v == btnAddAlbum) {
                           subFrag = new AddAlbum();
            }

            fragTransaction = getFragmentManager().beginTransaction().replace(R.id.fragPersonal, subFrag);
            subFrag.setArguments(b);
            fragTransaction.addToBackStack(null);
            fragTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTransaction.commit();


        }
    };

}




