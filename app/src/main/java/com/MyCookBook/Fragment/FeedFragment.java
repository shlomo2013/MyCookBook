package com.MyCookBook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.mycookbook.mycookbook.R;

public class FeedFragment extends Fragment {

    AutoCompleteTextView myAutoComplete;

    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_feed_fregment , container , false);

      //  Spinner dropdown = (Spinner)rootView.findViewById(R.id.spinner1);
     //   String[] items = new String[]{"1", "2", "three"};
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
     //   dropdown.setAdapter(adapter);

        myAutoComplete = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);

        //myAutoComplete.addTextChangedListener(this);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.ingridient_type_array,
                android.R.layout.simple_list_item_1);
        myAutoComplete.setAdapter(adapter);

        return rootView;
    }


}
