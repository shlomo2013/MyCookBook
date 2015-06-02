package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    AutoCompleteTextView myAutoComplete;
    TableLayout tbLayout;
    ArrayList<Recipe> myRecepies;
    boolean click = true;
    private PopupWindow pw;

    private RadioGroup rgpFilter;

    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_feed_fregment, container, false);

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

        myRecepies = Queries.getLastRecipes(10);

        for (int i = 0; i < myRecepies.size(); i++)
        {
            TextView tvRecipe = new TextView(getActivity().getBaseContext());
            ImageView ivRecipePhoto = new ImageView(getActivity().getBaseContext());
//            ImageButton btRecipePhoto = new ImageButton(getActivity().getBaseContext());
            ImageButton btLike = new ImageButton(getActivity().getBaseContext());

            TableRow tr = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trLP);
            //tr.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            tr.setGravity(Gravity.RIGHT);
//            tr.setClickable(true);
//            tr.setId(Integer.parseInt(myRecepies.get(i).getObjectId()));
//            tr.setOnClickListener(new rowOnClickListener() {
//                public void onClick(View v) {
//
//                }
//            });

            // Handle recipe text
            tvRecipe.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tvRecipe.setMinimumWidth(350);
            tvRecipe.setText("המתכון של טל\n\n" + "\n" + "רכיבים:\nשוקולד\nסוכר");
            //tvRecipe.setTextDirection(View.LAYOUT_DIRECTION_RTL);
            tvRecipe.setClickable(true);
            tvRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: open recipe window
                }
            });

            // Handle recipe image
            ivRecipePhoto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            ivRecipePhoto.setMaxWidth(350);
            ivRecipePhoto.setMaxHeight(350);
            ivRecipePhoto.setAdjustViewBounds(true);
            //TODO: delete
            //myRecepies.get(i).setRecipePic(String.valueOf(R.drawable.com_facebook_profile_picture_blank_square));
            // TODO: return
            //ivRecipePhoto.setImageResource(Integer.parseInt (myRecepies.get(i).getRecipePic()));
            ivRecipePhoto.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
            ivRecipePhoto.setClickable(true);
            ivRecipePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click) {
                        initiatePopUp(R.drawable.com_facebook_profile_picture_blank_square);
                        pw.showAtLocation(rootView, Gravity.BOTTOM, 10, 10);
                        click = false;
                    }
                    else{
                        pw.dismiss();
                        click = true;
                    }
                }
            });

            //ivRecipePhoto.setId(Integer.parseInt(myRecepies.get(i).getObjectId().toString()));

            // Handle like button
            btLike.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //btLike.setMaxWidth(350);
            //btLike.setMaxHeight(150);
            //btLike.setAdjustViewBounds(true);
            btLike.setImageResource(R.mipmap.red_like_icon);
            btLike.setClickable(true);
            btLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: save like
                }
            });

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

    private void initiatePopUp(int resId){

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.imagepopup, null);

        pw = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        //pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        final ImageView RecipePhoto = (ImageView) popupView.findViewById(R.id.ImageRecipePhoto);
        RecipePhoto.setImageResource(resId);

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });

    }


}