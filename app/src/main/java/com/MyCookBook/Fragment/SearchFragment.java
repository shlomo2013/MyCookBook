package com.MyCookBook.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ExpandableListView;

import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SearchFragment extends Fragment {

    private RadioGroup rgpTypes;
    private CheckBox cbSoop;
    private CheckBox cbBreakfast;
    private CheckBox cbSweets;
    private CheckBox cbDrinks;
    private CheckBox cbBread;
    private  View.OnClickListener checkBoxListener;
    private ArrayList<String> categoryArr;
    private ArrayAdapter<String> aa;
    private int compId;
    private int noCopmId;
    private ListView myListView;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_search_fragment, container , false);

        categoryArr = new ArrayList<String>();

        final Spinner dropdownSweets = (Spinner) rootView.findViewById(R.id.lowSweets);
        createSpinner((dropdownSweets), R.array.subCategorySweets);

        final Spinner dropdownBf = (Spinner) rootView.findViewById(R.id.lowBreakfest);
        createSpinner((dropdownBf), R.array.subCategoryBreakfest);

        final Spinner dropdownSoop = (Spinner) rootView.findViewById(R.id.lowSoop);
        createSpinner((dropdownSoop), R.array.subCategorySoop);

        final Spinner dropdownDrinks = (Spinner) rootView.findViewById(R.id.lowDrinks);
        createSpinner((dropdownDrinks), R.array.subCategoryDrinks);

        final Spinner dropdownBread = (Spinner) rootView.findViewById(R.id.lowBread);
        createSpinner((dropdownBread), R.array.subCategoryBread);

        final Spinner dropdownComp = (Spinner) rootView.findViewById(R.id.component);
        compId = dropdownComp.getId();
        createSpinner((dropdownComp), R.array.personal_no_pref_array);

        final Spinner dropdownNoCopm = (Spinner) rootView.findViewById(R.id.noComponent);
        noCopmId = dropdownNoCopm.getId();
        createSpinner((dropdownNoCopm), R.array.personal_no_pref_array);

        myListView = (ListView) rootView.findViewById(R.id.listView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>()

        // Assign adapter to ListView
       // myListView.setAdapter(adapter);

        rgpTypes = (RadioGroup) rootView.findViewById(R.id.RBgroup);

                /* Attach CheckedChangeListener to radio group */
        rgpTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    switch (rb.getId())
                    {
                        case R.id.RBall:
                            break;
                        case R.id.RBwebsite:
                            break;
                        case R.id.RBusers:
                            break;
                    }
                }

            }
        });

        cbSoop=(CheckBox) rootView.findViewById(R.id.CBsoop);
        cbBreakfast=(CheckBox) rootView.findViewById(R.id.CBbreakfast);
        cbBread=(CheckBox) rootView.findViewById(R.id.CBbredAnd);
        cbDrinks=(CheckBox) rootView.findViewById(R.id.CBdrinks);
        cbSweets=(CheckBox) rootView.findViewById(R.id.CBsweets);

        checkBoxListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //tv=(TextView)findViewById(R.id.tvDetails);
                //tv.setText("I Like ");

                if(cbSoop.isChecked()) {
                    categoryArr.add("SOOP");
                    dropdownSoop.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownSoop.setVisibility(View.INVISIBLE);
                }

                if(cbBreakfast.isChecked()) {
                    categoryArr.add("BREAKFEST");
                    dropdownBf.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownBf.setVisibility(View.INVISIBLE);
                }

                if(cbSweets.isChecked()) {
                    categoryArr.add("SWEETS");
                    dropdownSweets.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownSweets.setVisibility(View.INVISIBLE);
                }

                if(cbBread.isChecked()) {
                    categoryArr.add("BREAD");
                    dropdownBread.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownBread.setVisibility(View.INVISIBLE);
                }

                if(cbDrinks.isChecked()) {
                    categoryArr.add("DRINKS");
                    dropdownDrinks.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownDrinks.setVisibility(View.INVISIBLE);
                }

            }
        };

        cbSoop.setOnClickListener(checkBoxListener);
        cbBreakfast.setOnClickListener(checkBoxListener);
        cbSweets.setOnClickListener(checkBoxListener);
        cbBread.setOnClickListener(checkBoxListener);
        cbDrinks.setOnClickListener(checkBoxListener);

        return rootView;
    }

    private void createSpinner(Spinner sp, @ArrayRes int dataList){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                dataList,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (sp.getId() != compId && sp.getId() != noCopmId)
        {
            sp.setVisibility(View.INVISIBLE);
        }

        // Apply the adapter to the spinner
        sp.setAdapter(adapter);

    }

}
