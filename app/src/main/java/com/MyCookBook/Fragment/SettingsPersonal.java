package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nirgadasi on 5/30/15.
 */
public class SettingsPersonal extends Fragment {

    private ListAdapter ddlCategiriesAdapter;
    private ListAdapter ddlNoCategiriesAdapter;
    private ListAdapter lCategoriesAdapter;
    private ArrayList<CheckBox> alFoodCategory;
    private SparseBooleanArray lCheck;
    private ArrayList<CheckBox> alNoFoodCategory;
    private ListView lvNoCategoriesList;
    private  ListView myListViewNo;

    Button btnSaveCategory;

    public static ArrayList<String> GroceryIn;
    public static ArrayList<String> GroceryOut;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_personal,  container , false);
//        lvNoCategoriesList          = (ListView)           rootView.findViewById(R.id.lvNoCategoriess);
        btnSaveCategory             = (Button)           rootView.findViewById(R.id.bSaveCategory);
        myListViewNo                = (ListView) rootView.findViewById(R.id.listViewNo);

        Queries.refreshAllGroceries();
        HashMap<String, String> hm = Queries.groceriesList;
        ArrayList<String> alPref = new ArrayList<>(hm.values());
        String[] prefList = alPref.toArray(new String[alPref.size()]);

        myListViewNo = (ListView) rootView.findViewById(R.id.listViewNo);
        ArrayAdapter<String> adapterNo = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_multiple_choice  ,
                prefList );

        // Assign adapter to ListView
        myListViewNo.setAdapter(adapterNo);

//        alFoodCategory =  initCategories( R.array.categories);
//        ddlCategiriesAdapter = new DropDownListAdapter(alFoodCategory, getActivity().getBaseContext());
//        lvCategoriesList.setAdapter(ddlCategiriesAdapter);


//        alNoFoodCategory =  initCategories( R.array.personal_no_pref_array);
//        ddlNoCategiriesAdapter = new DropDownListAdapter(alNoFoodCategory, getActivity().getBaseContext());
//        lvNoCategoriesList.setAdapter(ddlNoCategiriesAdapter);


        //on item Click
        btnSaveCategory.setOnClickListener(btnOnClickListener);


        return rootView;

    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

//            SparseBooleanArray checked =  lvCategoriesList.getCheckedItemPositions();
//            for (int i = 0; i < lvCategoriesList.getAdapter().getCount(); i++) {
//                if (checked.get(i)) {
//                    GroceryIn.add((lvCategoriesList.getItemAtPosition(i)).toString());
//                }
//            }
            SparseBooleanArray checked =  lvNoCategoriesList.getCheckedItemPositions();
            for (int i = 0; i < lvNoCategoriesList.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    GroceryOut.add((lvNoCategoriesList.getItemAtPosition(i)).toString());
                }
            }

//            lCheck =  lvCategoriesList.getCheckedItemPositions();
//            lCheck.get(2);
//
//            lCategoriesAdapter = lvCategoriesList.getAdapter();
//            CheckBox b = (CheckBox) lCategoriesAdapter.getItem(0);
//            b.isSelected();
//            b.getText();
//             b = (CheckBox) lCategoriesAdapter.getItem(2);
//                      b.isSelected();
//                      b.getText();

        }
    };




    public ArrayList<CheckBox> initCategories(int ArrayName) {
        ArrayList<CheckBox> items;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                ArrayName,
                android.R.layout.simple_list_item_multiple_choice);

        CheckBox c;

        items = new ArrayList<CheckBox>();
        for (int i = 0; i < adapter.getCount(); i++) {
            String s = (String) adapter.getItem(i);
            c = new CheckBox(getActivity().getBaseContext());
            c.setText(s);
            items.add(c);
        }
        return items;
    }
}
