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

import com.MyCookBook.Utiltis.DropDownListAdapter;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

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
    private ListView lvCategoriesList;
    private ListView lvNoCategoriesList;
    Button btnSaveCategory;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_personal,  container , false);
        lvCategoriesList            = (ListView)           rootView.findViewById(R.id.lvCategoriess);
        lvNoCategoriesList            = (ListView)           rootView.findViewById(R.id.lvNoCategoriess);
        btnSaveCategory            = (Button)           rootView.findViewById(R.id.bSaveCategory);


        alFoodCategory =  initCategories( R.array.categories);
        ddlCategiriesAdapter = new DropDownListAdapter(alFoodCategory, getActivity().getBaseContext());
        lvCategoriesList.setAdapter(ddlCategiriesAdapter);


        //on item Click
        btnSaveCategory.setOnClickListener(btnOnClickListener);


        alNoFoodCategory =  initCategories( R.array.personal_no_pref_array);
        ddlNoCategiriesAdapter = new DropDownListAdapter(alNoFoodCategory, getActivity().getBaseContext());
        lvNoCategoriesList.setAdapter(ddlNoCategiriesAdapter);

        return rootView;

    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            SparseBooleanArray checked =  lvCategoriesList.getCheckedItemPositions();
            for (int i = 0; i < lvCategoriesList.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
//                    groceryIn.add((lvCategoriesList.getItemAtPosition(i)).toString());
                }
            }
             checked =  lvNoCategoriesList.getCheckedItemPositions();
             for (int i = 0; i < lvNoCategoriesList.getAdapter().getCount(); i++) {
                 if (checked.get(i)) {
//                     groceryIn.add((lvNoCategoriesList.getItemAtPosition(i)).toString());
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
