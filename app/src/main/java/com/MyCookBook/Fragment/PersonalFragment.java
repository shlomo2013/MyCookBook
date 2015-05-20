package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.MyCookBook.Activity.CookBookGalleryActivity;
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
    View singlGalleryView;
    View  rootView    ;
    View  popUpView   ;
    View  dropDownView;

    ArrayList<CheckBox> alFoodCategory;
    ArrayList<CheckBox> alNoFoodCategory;
    DropDownListAdapter mAdapter;

    Integer[] pics = {R.mipmap.cookbook_poster,
            R.mipmap.ic_launcher,
            R.mipmap.red_address_book_icon,
            R.mipmap.red_balloon_2_icon,
            R.mipmap.red_gear_icon,
            R.mipmap.red_camera_icon };

    ImageView ivPic;
    GridView gvAlboms;
    Gallery gAlbome;
    ArrayList<ImageView> alAlbumList;
    LayoutInflater MyInflater;


    //////////******************************************************************//////
    //////////******************************************************************//////

   public ListView lv;

    // Create Array's of titles, descriptions and thumbs resource id's:
    public String title[] = { "Cup Cake", "Donut", "Eclair", "Froyo",
            "Ginger Bread", "Honey Comb", "Icecream Sandwich", "Jelly Bean",
            "Lazania", "Pizza", "Icecream", "Yolo" };

    public String desc[] = { "משפחה", "חברים",
            "מרוקאי", "תמני", "קינוחים",
            "פייסבוק", "צבא", "פומבי", "איטלקי",
            "בשר", "צמחוני", "טבעוני" };

    public int thumb[] = { R.mipmap.red_camera_icon, R.mipmap.red_balloon_2_icon,
            R.mipmap.red_balloon_plus_icon , R.mipmap.red_cross_icon ,
            R.mipmap.red_like_icon ,R.mipmap.red_lock_icon ,
            R.mipmap.red_unlock_icon , R.mipmap.red_home_icon, };


    //////////******************************************************************//////
    //////////******************************************************************//////
    public PersonalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyInflater = inflater;
        rootView     = inflater.inflate(R.layout.activity_personal_fregment, container, false);
        dropDownView = inflater.inflate(R.layout.drop_down_list_row, container, false);
        popupView    = inflater.inflate(R.layout.popup, null);
        singlGalleryView     = inflater.inflate(R.layout.custom_toast, container, false);


        llPopup                   = (LinearLayout)       rootView.findViewById(R.id.DropDownList);
     //   gvAlboms                  =(GridView)            rootView.findViewById(R.id.albumGrid);
        lvDropDownList            = (ListView)           popupView.findViewById(R.id.lvDropDownList);



        //////////******************************************************************//////
        //////////******************************************************************//////
        // Initialize the variables:
        lv = (ListView) rootView.findViewById(R.id.listView);

        // Set an Adapter to the ListView
        lv.setAdapter(new VersionAdapter(inflater));

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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity().getApplicationContext(), CookBookGalleryActivity.class);
                startActivity(i);
            }
        });

        //////////******************************************************************//////
        //////////******************************************************************//////




        handleCategories();
        handleNoCategories();
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
                // TODO Auto-generated method stub
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
                        // TODO Auto-generated method stub
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

                pw.showAsDropDown(btnDDPersonalCategories);

            }});


    }

// Create an Adapter Class extending the BaseAdapter
class VersionAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    public VersionAdapter(LayoutInflater inflater) {
// TODO Auto-generated constructor stub
        layoutInflater = inflater;
    }

    @Override
    public int getCount() {
// Set the count value to the total number of items in the Array
        return title.length;
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

// Inflate the item layout and set the views
        View listItem = convertView;
        int pos = position;
        if (listItem == null) {
            listItem = layoutInflater.inflate(R.layout.list_items, null);
        }

// Initialize the views in the layout
        ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
        TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
        TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);

// Set the views in the layout
        iv.setBackgroundResource(thumb[pos]);
        tvTitle.setText(title[pos]);
        tvDesc.setText(desc[pos]);

        return listItem;
    }

}

}

