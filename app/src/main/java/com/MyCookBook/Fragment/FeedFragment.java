package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.R;

public class FeedFragment extends Fragment {

    AutoCompleteTextView myAutoComplete;
    TableLayout tbLayout;
    private RadioGroup rgpFilter;

    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_feed_fregment, container, false);

        //  Spinner dropdown = (Spinner)rootView.findViewById(R.id.spinner1);
        //   String[] items = new String[]{"1", "2", "three"};
        //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        //   dropdown.setAdapter(adapter);

        myAutoComplete = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);

        //myAutoComplete.addTextChangedListener(this);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.categories,
                android.R.layout.simple_list_item_1);
        myAutoComplete.setAdapter(adapter);

        rgpFilter = (RadioGroup) rootView.findViewById(R.id.RBgroup);

        rgpFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    switch (rb.getId()) {
                        case R.id.RBnew:
                            break;
                        case R.id.RBloved:
                            break;
                        case R.id.RBtop5:
                            break;
                    }
                }
            }
        });

        tbLayout = (TableLayout) rootView.findViewById(R.id.tbFeed);
        //tbLayout.setTextDirection(View.LAYOUT_DIRECTION_RTL);
        //tbLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        for (int i = 0; i < 4; i++) {

            TextView tvRecipe = new TextView(getActivity().getBaseContext());
            ImageView ivRecipePhoto = new ImageView(getActivity().getBaseContext());
            ImageButton btLike = new ImageButton(getActivity().getBaseContext());

            TableRow tr = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trLP);
            //tr.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            tr.setGravity(Gravity.RIGHT);
            tr.setClickable(true);
            Recipe r = new Recipe();
        //    tr.setId(Integer.parseInt(r.getObjectId()));
//            tr.setOnClickListener(new rowOnClickListener() {
//                public void onClick(View v) {
//
//                }
//            });

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
            ivRecipePhoto.setClickable(true);
            ivRecipePhoto.setOnClickListener(photoOnClickListener);

            // Handle like button
            btLike.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //btLike.setMaxWidth(350);
            //btLike.setMaxHeight(150);
            //btLike.setAdjustViewBounds(true);
            btLike.setImageResource(R.mipmap.red_like_icon);
            btLike.setClickable(true);

            // Add to layOut
            tr.addView(tvRecipe);
            tr.addView(ivRecipePhoto);
            // tr.addView(btLike);

            tbLayout.addView(tr);

            TableRow tr2 = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP2 = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr2.setLayoutParams(trLP2);
            tr2.setGravity(Gravity.RIGHT);

            // Handle like button
            /*btLike.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
           // btLike.setMinimumWidth(350);
            btLike.setText("אהבתי");
            btLike.setBackgroundResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha); */

            // Add to layOut
            tr2.addView(btLike);
            tbLayout.addView(tr2);
        }

        int rowNumCount = tbLayout.getChildCount();
        for(int count = 1; count < rowNumCount; count++) {
            View v = tbLayout.getChildAt(count);
            if(v instanceof TableRow) {
                final TableRow clickRow = (TableRow)v;
                int rowCount = clickRow.getChildCount();
                v.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

//                        Context context = getTabHost().getContext();
//                        TableRow row = (TableRow)v;
//                        TextView tv = (TextView)row.getChildAt(0);
//                        CharSequence text = "Lot VALUE Selected: " + tv.getText();
//                        int duration = Toast.LENGTH_SHORT;
//                        Toast.makeText(context, text, duration).show();
                    }
                });
            }
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


    View.OnClickListener photoOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            tbLayout = (TableLayout) v.findViewById(R.id.tbFeed);
            //tbLayout.setOnClickListener();
        }

    };


}