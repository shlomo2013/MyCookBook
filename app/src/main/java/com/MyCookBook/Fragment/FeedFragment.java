package com.MyCookBook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mycookbook.mycookbook.R;

public class FeedFragment extends Fragment {

    AutoCompleteTextView myAutoComplete;
    TableLayout tbLayout;

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

        tbLayout = (TableLayout) rootView.findViewById(R.id.tbFeed);
        //tbLayout.setTextDirection(View.LAYOUT_DIRECTION_RTL);
        //tbLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        for (int i = 0; i < 4; i++) {

            TextView tvRecipe = new TextView(getActivity().getBaseContext());
            ImageView ivRecipePhoto = new ImageView(getActivity().getBaseContext());

            TableRow tr = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trLP);

            // Handle recipe text
            tvRecipe.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tvRecipe.setMinimumWidth(350);
            tvRecipe.setText("המתכון של טל\n\n" + i + "\n" + "רכיבים:\nשוקולד\nסוכר");
            //tvRecipe.setTextDirection(View.LAYOUT_DIRECTION_RTL);

            // Handle recipe image
            ivRecipePhoto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            ivRecipePhoto.setMaxWidth(350);
            ivRecipePhoto.setMaxHeight(350);
            ivRecipePhoto.setAdjustViewBounds(true);
            ivRecipePhoto.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);

            // Add to layOut
            tr.addView(tvRecipe);
            tr.addView(ivRecipePhoto);

            tbLayout.addView(tr);
        }

        /*TextView tvIngredients = new TextView(getActivity().getBaseContext());
        TableRow tr2 = new TableRow(getActivity().getBaseContext());
        TableRow.LayoutParams trLP2 = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        tr.setLayoutParams(trLP2);

        // Handle recipe text
        tvIngredients.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tvIngredients.setMinimumWidth(350);
        tvIngredients.setText("רכיבים:\nשוקולד\nסוכר");
        //tvIngredients.setTextDirection(View.LAYOUT_DIRECTION_RTL);

        // Add to layOut
        tr2.addView(tvIngredients);
        tbLayout.addView(tr2); */

        return rootView;
    }


}
