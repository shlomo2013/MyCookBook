package com.MyCookBook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.mycookbook.mycookbook.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class PersonalFragment extends Fragment {


    TableLayout  tbLayout;

        public PersonalFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_personal_fregment, container , false);
            tbLayout            = (TableLayout)          rootView.findViewById(R.id.tbPersonalLayout);
            creatCheckBoxOnLayout();
            return rootView;
        }

    public void creatCheckBoxOnLayout(){

            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                                                             R.array.personal_pref_array,
                                                                             android.R.layout.activity_list_item);
        TableRow tr = createTbRow();
        CheckBox ckCategory = null;

        for (int i = 0; i < adapter.getCount(); i++)
        {

            if (i%3 == 0){
                if (ckCategory != null) {
                    tbLayout.addView(tr);
                    tr = createTbRow();

                }
            }

            ckCategory = new CheckBox(getActivity().getBaseContext());
            ckCategory.setText((String)adapter.getItem(i));
            tr.addView(ckCategory);

        }
        tbLayout.addView(tr);

    }

    public TableRow createTbRow(){

        TableRow tr = new TableRow(getActivity().getBaseContext());
        TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
              TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(trLP);
        tr.setTextDirection(View.LAYOUT_DIRECTION_RTL);
        return tr;
    }
}
