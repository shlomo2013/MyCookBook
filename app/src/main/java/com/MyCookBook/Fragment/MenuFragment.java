package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mycookbook.mycookbook.R;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class MenuFragment extends Fragment{

    Fragment frag;
    FragmentTransaction fragTransaction;
    ImageButton btnFeed;
    ImageButton btnPersonal;
    ImageButton btnSearch;
    ImageButton btnAddRecipe;
    ImageButton btnLogOff;
//    ImageView ivProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.multi_menu,  container , false);

        btnFeed      = (ImageButton) rootView.findViewById(R.id.btnFeed);
        btnPersonal  = (ImageButton) rootView.findViewById(R.id.btnPersonal);
        btnSearch    = (ImageButton) rootView.findViewById(R.id.btnSearch);
        btnAddRecipe = (ImageButton) rootView.findViewById(R.id.btnAddRecipe);
        btnLogOff    = (ImageButton) rootView.findViewById(R.id.btnAddRecipe);
//        ivProfile    = (ImageView) rootView.findViewById(R.id.ivProfilepic);

//        ivProfile.setImageBitmap(Queries.getProfilePicture());
        btnFeed.setOnClickListener(btnOnClickListener);
        btnPersonal.setOnClickListener(btnOnClickListener);
        btnSearch.setOnClickListener(btnOnClickListener);
        btnAddRecipe.setOnClickListener(btnOnClickListener);
        btnLogOff.setOnClickListener(btnOnClickListener);
        return rootView;
    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            if (v == btnFeed) {
                frag = new FeedFragment();
            } else if (v == btnSearch) {
                frag = new SearchFragment();
            } else if (v == btnAddRecipe) {
                frag = new AddRecipeFragment();
            } else if (v == btnPersonal) {
                frag = new PersonalFragment();
            } else {
                frag = new FeedFragment();
            }

            fragTransaction = getFragmentManager().beginTransaction().replace(R.id.fragContainer, frag);
            fragTransaction.addToBackStack(null);
            fragTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTransaction.commit();


        }
    };
}

