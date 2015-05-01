package com.MyCookBook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mycookbook.mycookbook.R;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class FeedFragment extends Fragment {

    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_feed_fregment , container , false);

      //  Spinner dropdown = (Spinner)rootView.findViewById(R.id.spinner1);
     //   String[] items = new String[]{"1", "2", "three"};
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
     //   dropdown.setAdapter(adapter);

        return rootView;
    }


}
