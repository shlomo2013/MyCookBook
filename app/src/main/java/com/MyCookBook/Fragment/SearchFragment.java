package com.MyCookBook.Fragment;

import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.SparseBooleanArray;
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
import android.widget.Toast;

import com.example.mycookbook.mycookbook.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.mycookbook.mycookbook.R.array.personal_no_pref_array;

public class SearchFragment extends Fragment {

    private RadioGroup rgpTypes;

    // Categories checkboxes
    private CheckBox cbSoop;
    private CheckBox cbBreakfast;
    private CheckBox cbSweets;
    private CheckBox cbDrinks;
    private CheckBox cbBread;

    // Dish types checkboxes
    private CheckBox cbMain;
    private CheckBox cbFirst;
    private CheckBox cbDesert;

    // Special limits checkboxes
    private CheckBox cbVeg;
    private CheckBox cbVegan;
    private CheckBox cbDiet;

    // Listeners
    private  View.OnClickListener checkBoxListenerCategory;
    private  View.OnClickListener checkBoxListenerType;
    private  View.OnClickListener checkBoxListenerSpec;
    private  View.OnClickListener ListViewListener;


    private ArrayList<String> categoryArr;
    private ArrayAdapter<String> aa;
    private int compId;
    private int noCopmId;
    private int levelId;
    private int KitchenTypeId;
    private  ListView myListView;
    private  ListView myListViewNo;

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

        /*final Spinner dropdownComp = (Spinner) rootView.findViewById(R.id.component);
        compId = dropdownComp.getId();
        createSpinner((dropdownComp), personal_no_pref_array);

        final Spinner dropdownNoCopm = (Spinner) rootView.findViewById(R.id.noComponent);
        noCopmId = dropdownNoCopm.getId();
        createSpinner((dropdownNoCopm), personal_no_pref_array); */

        final Spinner dropdownLevel = (Spinner) rootView.findViewById(R.id.Level);
        levelId = dropdownLevel.getId();
        createSpinner((dropdownLevel), R.array.Levels);

        final Spinner dropdownKitchenType = (Spinner) rootView.findViewById(R.id.KitchenType);
        KitchenTypeId = dropdownKitchenType.getId();
        createSpinner((dropdownKitchenType), R.array.categories);

        //String[] dataList = R.array.personal_no_pref_array;

        myListView = (ListView) rootView.findViewById(R.id.listView);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                personal_no_pref_array,
                android.R.layout.simple_list_item_multiple_choice);

        // Assign adapter to ListView
        myListView.setAdapter(adapter);

        myListViewNo = (ListView) rootView.findViewById(R.id.listViewNo);
        final ArrayAdapter<CharSequence> adapterNo = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                personal_no_pref_array,
                android.R.layout.simple_list_item_multiple_choice);

        // Assign adapter to ListView
        myListViewNo.setAdapter(adapterNo);

        rgpTypes = (RadioGroup) rootView.findViewById(R.id.RBgroup);

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

        // Listener for Categories checkboxes
        checkBoxListenerCategory = new View.OnClickListener() {

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

        cbSoop.setOnClickListener(checkBoxListenerCategory);
        cbBreakfast.setOnClickListener(checkBoxListenerCategory);
        cbSweets.setOnClickListener(checkBoxListenerCategory);
        cbBread.setOnClickListener(checkBoxListenerCategory);
        cbDrinks.setOnClickListener(checkBoxListenerCategory);

        cbMain=(CheckBox) rootView.findViewById(R.id.CBmain);
        cbFirst=(CheckBox) rootView.findViewById(R.id.CBfirst);
        cbDesert=(CheckBox) rootView.findViewById(R.id.CBdesert);

        // Listener for Dish type checkboxes
        checkBoxListenerType = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(cbMain.isChecked()) {

                }

                if(cbFirst.isChecked()) {

                }

                if(cbDesert.isChecked()) {

                }

            }
        };

        cbMain.setOnClickListener(checkBoxListenerType);
        cbFirst.setOnClickListener(checkBoxListenerType);
        cbDesert.setOnClickListener(checkBoxListenerType);

        cbVeg=(CheckBox) rootView.findViewById(R.id.CBveg);
        cbVegan=(CheckBox) rootView.findViewById(R.id.CBvegan);
        cbDiet=(CheckBox) rootView.findViewById(R.id.CBdiet);

        // Listener for Special limits checkboxes
        checkBoxListenerSpec = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(cbVeg.isChecked()) {

                }

                if(cbVegan.isChecked()) {

                }

                if(cbDiet.isChecked()) {

                }
            }
        };

        cbVeg.setOnClickListener(checkBoxListenerSpec);
        cbVegan.setOnClickListener(checkBoxListenerSpec);
        cbDiet.setOnClickListener(checkBoxListenerSpec);

        myListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                final SparseBooleanArray checkedItems = myListView.getCheckedItemPositions();
                //ListIte adapter = new ItemListAdapter(this, data);
                //setListAdapter(adapter);

                // For each element in the status array
                final int checkedItemsCount = checkedItems.size();
                for (int i = 0; i < checkedItemsCount; ++i) {
                    // This tells us the item position we are looking at
                    final int position1 = checkedItems.keyAt(i);
                    //Object obj =  myListView.getItemAtPosition(position);
                    //LauncherActivity.ListItem = myListView.getItemAtPosition(position);

                    // And this tells us the item status at the above position
                    //final boolean isChecked = checkedItems.valueAt(i);

                    // And we can get our data from the adapter like that
                    //final CharSequence currentItem = adapter.getItem(position);
                }
            }
        });


        return rootView;
    }

    private void createSpinner(Spinner sp, @ArrayRes int dataList){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                dataList,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (sp.getId() != compId && sp.getId() != noCopmId && sp.getId() != levelId && sp.getId() != KitchenTypeId)
        {
            sp.setVisibility(View.INVISIBLE);
        }

        // Apply the adapter to the spinner
        sp.setAdapter(adapter);

    }

    public void onListItemClick(ListView myListView, View v, int position, long id) {

        String prompt =
                "clicked item: " + myListView.getItemAtPosition(position).toString() + "\n\n";

        prompt += "selected items: \n";
        int count = myListView.getCount();
        SparseBooleanArray sparseBooleanArray = myListView.getCheckedItemPositions();
        for (int i = 0; i < count; i++){
            if (sparseBooleanArray.get(i)) {
                prompt += myListView.getItemAtPosition(i).toString() + "\n";
            }
        }

        Toast.makeText(
                getActivity(),
                prompt,
                Toast.LENGTH_LONG).show();
    }

}
