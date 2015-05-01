package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mycookbook.mycookbook.R;

import static android.widget.ImageButton.*;

public class MainActivity extends ActionBarActivity {

    Fragment frag;
    FragmentTransaction fragTransaction;
    ImageButton btnFeed;
    ImageButton btnPersonal;
    ImageButton btnSearch;
    ImageButton btnAddRecipe;
    ImageButton btnLogOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


