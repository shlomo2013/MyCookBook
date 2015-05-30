package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;

import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.example.mycookbook.mycookbook.ParseApplication;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import static android.widget.ImageButton.*;

public class MainActivity extends ActionBarActivity {

    Fragment frag;
    FragmentTransaction fragTransaction;
    ImageButton btnFeed;
    ImageButton btnPersonal;
    ImageButton btnSearch;
    ImageButton btnAddRecipe;
    ImageButton btnLogOff;
    String myUserId;
    // TODO: user object - temp
    public static User myUser = new User();

    private void setMyUserId(Bundle savedInstanceState){
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("myUserId");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("myUserId");
        }
        myUserId = newString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMyUserId(savedInstanceState);
        Queries.updateMyUser(myUserId);

        Queries.updateTypeRecipes(Recipe.Category,"jjjjjjj",Queries.getMyUser());

        frag = new FeedFragment();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.fragContainer, frag);
        fragTransaction.commit();

        frag = new MenuFragment();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.menuFrag, frag);
        fragTransaction.commit();

    }


}
       // return view;//super.onCreateView(inflater, container, savedInstanceState);


/*
        // if (savedInstanceState == null) {
        //    getSupportFragmentManager().beginTransaction().add (R.layout.activity_main , new MenuFragment()).commit();
       // }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
/*
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_feed_fregment, container, false);
            return rootView;
        }
    }
*/


