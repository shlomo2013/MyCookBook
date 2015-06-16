package com.MyCookBook.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;

    private RadioGroup rgpTypes;

    // Categories checkboxes
    private CheckBox cbSoop;
    private CheckBox cbBreakfast;
    private CheckBox cbSweets;
    private CheckBox cbDrinks;
    private CheckBox cbBread;
    private CheckBox cbMeat;
    private CheckBox cbSalad;
    private CheckBox cbRiceAndPasta;
    private CheckBox cbSides;



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
    private  View.OnClickListener searchButtenListener;
    private  View.OnClickListener checkBoxListenerType;
    private  View.OnClickListener checkBoxListenerSpec;
    private  View.OnClickListener ListViewListener;

    // Lists
    private ArrayList<String> categoryArr;
    private ArrayList<String> subCategory;
    private ArrayList<String> dishType;
    private ArrayList<String> groceryIn;
    private ArrayList<String> groceryOut;
    private static ArrayList<Recipe> searchResults;

    private int compId;
    private int noCopmId;
    private int levelId;
    private int KitchenTypeId;
    private  ListView myListView;
    private  ListView myListViewNo;

    private boolean veg = false;
    private boolean vegan = false;
    private boolean diet = false;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_search_fragment, container , false);

        categoryArr = new ArrayList<String>();
        subCategory = new ArrayList<String>();
        dishType = new ArrayList<String>();
        groceryIn = new ArrayList<String>();
        groceryOut = new ArrayList<String>();

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

        final Spinner dropdownLevel = (Spinner) rootView.findViewById(R.id.Level);
        levelId = dropdownLevel.getId();
        createSpinner((dropdownLevel), R.array.Levels);

        final Spinner dropdownKitchenType = (Spinner) rootView.findViewById(R.id.KitchenType);
        KitchenTypeId = dropdownKitchenType.getId();
        createSpinner((dropdownKitchenType), R.array.kitchenType);

//        String[] dataList = R.array.personal_no_pref_array;

        Queries.refreshAllGroceries();
        HashMap<String, String> hm = Queries.groceriesList;
        ArrayList<String> alPref = new ArrayList<>(hm.values());
        String[] prefList = alPref.toArray(new String[alPref.size()]);

        // build the table from mPlatesList
//        Set<String> unionSet = new HashSet<String>();
//        for (HashMap<String, String> hashMap : hm) {
//            unionSet.addAll(hashMap.values());
//        }
//
//        String[] prefList = unionSet.toArray(new String[unionSet.size()]);

        for (int i = 0; i < prefList.length; i++)
        {
            prefList[i] = alPref.get(i);
        }

        myListView = (ListView) rootView.findViewById(R.id.listView);
//        PrefAdapter adapter1 = new PrefAdapter(inflater, alPref, myListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_multiple_choice  , prefList );

//        Assign adapter to ListView
        myListView.setAdapter(adapter);


//        myListView.setAdapter(adapter1);

//        final ArrayAdapter<String> adapter = ArrayAdapter<>(getActivity().getBaseContext(),
//                //.createFromResource(getActivity().getBaseContext(),
//                //personal_no_pref_array,
//                android.R.layout.simple_list_item_multiple_choice,
//                prefList);



        myListViewNo = (ListView) rootView.findViewById(R.id.listViewNo);
        ArrayAdapter<String> adapterNo = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_multiple_choice  , prefList );
////        final ArrayAdapter<CharSequence> adapterNo = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
////                personal_no_pref_array,
////                android.R.layout.simple_list_item_multiple_choice);
//
        // Assign adapter to ListView
        myListViewNo.setAdapter(adapterNo);

        cbSoop=(CheckBox) rootView.findViewById(R.id.CBsoop);
        cbBreakfast=(CheckBox) rootView.findViewById(R.id.CBbreakfast);
        cbBread=(CheckBox) rootView.findViewById(R.id.CBbredAnd);
        cbDrinks=(CheckBox) rootView.findViewById(R.id.CBdrinks);
        cbSweets=(CheckBox) rootView.findViewById(R.id.CBsweets);
        cbMeat=(CheckBox) rootView.findViewById(R.id.CBmeat);
        cbSalad = (CheckBox) rootView.findViewById(R.id.CBsalad);
        cbSides = (CheckBox) rootView.findViewById(R.id.CBsides);
        cbRiceAndPasta = (CheckBox) rootView.findViewById(R.id.CBricePasta);

        // Listener for Categories checkboxes
        checkBoxListenerCategory = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //tv=(TextView)findViewById(R.id.tvDetails);
                //tv.setText("I Like ");

                if(cbSoop.isChecked()) {
                    categoryArr.add("מרקים");
                    dropdownSoop.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownSoop.setVisibility(View.INVISIBLE);
                }

                if(cbBreakfast.isChecked()) {
                    categoryArr.add("ארוחת בוקר");
                    dropdownBf.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownBf.setVisibility(View.INVISIBLE);
                }

                if(cbSweets.isChecked()) {
                    categoryArr.add("מתוקים");
                    dropdownSweets.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownSweets.setVisibility(View.INVISIBLE);
                }

                if(cbBread.isChecked()) {
                    categoryArr.add("לחם ומאפים");
                    dropdownBread.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownBread.setVisibility(View.INVISIBLE);
                }

                if(cbDrinks.isChecked()) {
                    categoryArr.add("משקאות");
                    dropdownDrinks.setVisibility(View.VISIBLE);
                }
                else
                {
                    dropdownDrinks.setVisibility(View.INVISIBLE);
                }

                if(cbMeat.isChecked()){
                    categoryArr.add("בשרים");
                }

                if(cbSalad.isChecked()){
                    categoryArr.add("סלטים");
                }

                if(cbRiceAndPasta.isChecked()){
                    categoryArr.add("אורז ופסטה");
                }

                if(cbSides.isChecked()){
                    categoryArr.add("תוספות");
                }

            }
        };

        cbSoop.setOnClickListener(checkBoxListenerCategory);
        cbBreakfast.setOnClickListener(checkBoxListenerCategory);
        cbSweets.setOnClickListener(checkBoxListenerCategory);
        cbBread.setOnClickListener(checkBoxListenerCategory);
        cbDrinks.setOnClickListener(checkBoxListenerCategory);
        cbSides.setOnClickListener(checkBoxListenerCategory);
        cbSalad.setOnClickListener(checkBoxListenerCategory);
        cbRiceAndPasta.setOnClickListener(checkBoxListenerCategory);

        cbMain=(CheckBox) rootView.findViewById(R.id.CBmain);
        cbFirst=(CheckBox) rootView.findViewById(R.id.CBfirst);
        cbDesert=(CheckBox) rootView.findViewById(R.id.CBdesert);

        // Listener for Dish type checkboxes
        checkBoxListenerType = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(cbMain.isChecked()) {
                    dishType.add("עיקרית");
                }

                if(cbFirst.isChecked()) {
                    dishType.add("ראשונה");
                }

                if(cbDesert.isChecked()) {
                    dishType.add("קינוח");
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
                    veg = true;
                }

                if(cbVegan.isChecked()) {
                    vegan = true;
                }

                if(cbDiet.isChecked()) {
                    diet = true;
                }
            }
        };

        cbVeg.setOnClickListener(checkBoxListenerSpec);
        cbVegan.setOnClickListener(checkBoxListenerSpec);
        cbDiet.setOnClickListener(checkBoxListenerSpec);

        final Button btnSearch = (Button) rootView.findViewById(R.id.btSearch);

        searchButtenListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((String.valueOf(dropdownBf.getSelectedItem())).equals("בחר") == false)
                {
                    subCategory.add(String.valueOf(dropdownBf.getSelectedItem()));
                }

                if (String.valueOf(dropdownBread.getSelectedItem()).equals("בחר")  == false) {
                    subCategory.add(String.valueOf(dropdownBread.getSelectedItem()));
                }

                if (String.valueOf(dropdownDrinks.getSelectedItem()).equals("בחר")  == false) {
                    subCategory.add(String.valueOf(dropdownDrinks.getSelectedItem()));
                }

                if (String.valueOf(dropdownSoop.getSelectedItem()).equals("בחר")  == false) {
                    subCategory.add(String.valueOf(dropdownSoop.getSelectedItem()));
                }

                if (String.valueOf(dropdownSweets.getSelectedItem()).equals("בחר")  == false) {
                    subCategory.add(String.valueOf(dropdownSweets.getSelectedItem()));
                }

                SparseBooleanArray checked =  myListView.getCheckedItemPositions();
                for (int i = 0; i < myListView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        groceryIn.add((myListView.getItemAtPosition(i)).toString());
                    }
                }

                checked =  myListViewNo.getCheckedItemPositions();
                for (int i = 0; i < myListViewNo.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        groceryOut.add((myListViewNo.getItemAtPosition(i)).toString());
                    }
                }

                String level = String.valueOf(dropdownLevel.getSelectedItem());
                String kitchenType = String.valueOf(dropdownKitchenType.getSelectedItem());

                if(categoryArr.size() == 0)
                {
                    categoryArr = null;
                }
                if(subCategory.size() == 0)
                {
                    subCategory = null;
                }
                if(dishType.size() == 0)
                {
                    dishType = null;
                }
                if(groceryIn.size() == 0)
                {
                    groceryIn = null;
                }
                if(groceryOut.size() == 0)
                {
                    groceryOut = null;
                }

                if (level.equals("בחר"))
                {
                    level = null;
                }
                if (kitchenType.equals("בחר"))
                {
                    kitchenType = null;
                }

                searchResults = Queries.RecipesSearch(categoryArr, subCategory, dishType, level, kitchenType, diet, veg, vegan, groceryIn, groceryOut);

                if(searchResults.size() != 0){

                    //Intent in = new Intent(, SearchResultFragment.class);
                    //in.putExtra("myRecipes", searchResults);
                    fragTransaction = getFragmentManager().beginTransaction().detach(frag);
                    frag = new SearchResultFragment();
                    //frag.startActivity(in);

                    fragTransaction = getFragmentManager().beginTransaction().replace(R.id.fragContainer, frag);
                    fragTransaction.addToBackStack(null);
//                    fragTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragTransaction.commit();
                }
                else
                {
                    new AlertDialog.Builder(rootView.getContext())
                            .setTitle("תוצאות חיפוש")
                            .setMessage("לא נמצאו תוצאות התואמות את החיפוש")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        };

        btnSearch.setOnClickListener(searchButtenListener);

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

    public static ArrayList<Recipe> getResults()
    {
        return searchResults;
    }

}
//
//public class PrefAdapter extends ArrayAdapter<ArrayList<HashMap<String, String>>> {
//    private ArrayList<String> PrefList;
//    private LayoutInflater layoutInflater;
//    private View rootView;
//
//    public PrefAdapter(LayoutInflater inflater, ArrayList<String> prefList, View rootView)
//    {
//        this.PrefList = prefList;
//        this.layoutInflater = inflater;
//        this.rootView = rootView;
//    }
//
//    @Override
//    public int getCount() {
//        return PrefList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return this.rootView;
//    }
//}

// /class PrefAdapter extends BaseAdapter {
//
//
//    @Override
//    public int getCount() {
//// Set the count value to the total number of items in the Array
//        return users.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return
//    }
//}
