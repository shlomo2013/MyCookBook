package com.MyCookBook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycookbook.mycookbook.R;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_search_fragment, container , false);
        return rootView;
    }
}