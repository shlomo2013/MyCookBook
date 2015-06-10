package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.MyCookBook.Activity.CookBookGalleryActivity;
import com.MyCookBook.Entities.Album;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

/**
 * Created by nirgadasi on 5/30/15.
 */
public class MyGallery extends Fragment {

    private Boolean bMyAlbums = true;
    private ListView lvAlbums;
    private ArrayList<Album> cookBooks;

    // Create Array's of titles, descriptions and thumbs resource id's:
    private String myTitle[] = { "Cup Cake", "Donut", "Eclair", "Froyo",
            "Ginger Bread", "Honey Comb", "Icecream Sandwich", "Jelly Bean"};

    private String myDesc[] = { "משפחה", "חברים",
            "מרוקאי", "תמני", "קינוחים",
            "פייסבוק", "צבא", "פומבי"};

    private int MyAlbumesPics[] = { R.mipmap.red_camera_icon, R.mipmap.red_balloon_2_icon,
            R.mipmap.red_balloon_plus_icon , R.mipmap.red_cross_icon ,
            R.mipmap.red_like_icon ,R.mipmap.red_lock_icon ,
            R.mipmap.red_like_icon ,R.mipmap.red_lock_icon };

    private int AllAlbumesPics[] = {
            R.mipmap.red_like_icon ,R.mipmap.red_lock_icon ,
            R.mipmap.red_unlock_icon , R.mipmap.red_home_icon, };

    private String allTitle[] = {  "Lazania", "Pizza", "Icecream", "Yolo" };
    private String allDesc[] = { "איטלקי", "בשר", "צמחוני", "טבעוני" };




    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Boolean isMyAlbums = true;
        View rootView = inflater.inflate(R.layout.gallery_view,  container , false);
        Bundle b = getArguments();
        if(b != null) {
            isMyAlbums  = b.getBoolean("MyAlbum");
        }

        // Initialize the variables:
        lvAlbums = (ListView) rootView.findViewById(R.id.lvAlbumes);

        if (isMyAlbums) {
                    cookBooks = Queries.getAlbumUserCreated(Queries.getMyUser());
                    // Set an Adapter to the ListView
                    lvAlbums.setAdapter(new AlbumsAdapter(inflater, MyAlbumesPics, myDesc, myTitle));

                }
        else{

            cookBooks = Queries.getUserAlbum(Queries.getMyUser());

            // Set an Adapter to the ListView
            lvAlbums.setAdapter(new AlbumsAdapter(inflater, AllAlbumesPics, allDesc, allTitle));

        }

        //on item Click
        lvAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getActivity().getApplicationContext(), CookBookGalleryActivity.class);
                // TODO dana :  שליחה של המזהה עאלבום על מנת לשלוף את כל המתכונים במתכון
                i.putExtra("AlbumID", "uXA9Uv0d89");
                startActivity(i);
            }
        });


        return rootView;
    }
}

// Create an Adapter Class extending the BaseAdapter
class AlbumsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private int[] picItems;
    private String[] itemsDesc;
    private String[] itemsTitle;

    public AlbumsAdapter(LayoutInflater inflater, int[] itemsPIc ,String[] itemsDesc ,String[] itemsTitle) {
        this.layoutInflater = inflater;
        this.picItems = itemsPIc;
        this.itemsDesc = itemsDesc;
        this.itemsTitle = itemsTitle;
    }

    @Override
    public int getCount() {
// Set the count value to the total number of items in the Array
        return picItems.length;
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
        int pos = position;
//        if (listItem == null) {
        listItem = layoutInflater.inflate(R.layout.all_albums_list, null);
//        }

// Initialize the views in the layout
        ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
        TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
        TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);

// Set the views in the layout
        iv.setBackgroundResource(picItems[pos]);
        tvTitle.setText(itemsTitle[pos]);
        tvDesc.setText(itemsDesc[pos]);
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
