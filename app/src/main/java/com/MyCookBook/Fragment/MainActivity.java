package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.MyCookBook.Entities.Album;
import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Queue;

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

    private void setMyUserId(Bundle savedInstanceState) {
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("myUserId");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("myUserId");
        }
        myUserId = newString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMyUserId(savedInstanceState);
        Queries.updateMyUser(myUserId);
        Log.d("shay", "n");
        Log.d("User Object is:",Queries.getMyUser().getObjectId());
        Log.d("shay","y");

        Recipe r = new Recipe("Jahnun","Yamen","italian new","cook etc","small","hard ptstsot"," ",true,false,false);


        r.addGrocery(new Grocery("חלב","כוס","2"));
        r.addGrocery(new Grocery("יין","כף","3"));
        r.saveInBackground();
        r.addRecipe(Queries.getMyUser());

        Recipe d = new Recipe("Lahchuch","Yamen","italian new","cook etc","small","hard ptstsot"," ",true,false,false);
        d.saveInBackground();
        d.addRecipe(Queries.getMyUser());

        Album newAlbum = new Album();
        newAlbum.setAlbumName("Temoni");
        newAlbum.addUser(Queries.getMyUser());

        Album newAlbum2 = new Album();
        newAlbum2.setAlbumName("Temoni2");
        newAlbum2.addUser(Queries.getMyUser());

        newAlbum.addRecipe(r);
        newAlbum.addRecipe(d);

        try {
            newAlbum.save();
            newAlbum2.save();
        }catch(com.parse.ParseException e){
            Log.d("save bug",e.getMessage());
        }

        ArrayList<Recipe> recipes = newAlbum.getAlbumRecipes();

        ArrayList<Album> myalbums = Queries.getUserAlbum(Queries.getMyUser());
        for(Album alb:myalbums){
            Log.d("Album related: ",alb.getAlbumName());
        }


        ArrayList<Album> myCreatedalbums = Queries.getAlbumUserCreated(Queries.getMyUser());
        for(Album alb:myCreatedalbums){
            Log.d("Album Created: ",alb.getAlbumName());
        }
        //ArrayList<Recipe> recipies = Queries.getUserRecipes(Queries.getMyUser());

        for(Recipe rec:recipes){
            Log.d("recipe related: ",rec.getName());
            Log.d("recipe is Diet?: ",String.valueOf(rec.getDiet()));
        }

        for(Grocery gro:r.getRecipeGroceries()){
            Log.d("Grocery material: ",gro.getMaterialName());
            Log.d("Grocery Amount: ",gro.getAmount());
        }

    //Queries.updateTypeRecipes(Recipe.Category,"jjjjjjj",Queries.getMyUser());


        frag = new FeedFragment();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.fragContainer, frag);
        fragTransaction.commit();

        frag = new MenuFragment();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.menuFrag, frag);
        fragTransaction.commit();
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


}
