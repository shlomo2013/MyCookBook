package com.MyCookBook.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {


    private TableLayout tbLayout;
    private ArrayList<Recipe> myRecipes;
    boolean click = true;
    private PopupWindow pw;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.serch_result, container , false);
        myRecipes = SearchFragment.getResults();

        tbLayout = (TableLayout) rootView.findViewById(R.id.tbResults);
       // myRecipes = SearchFragment

        for (int i = 0; i < myRecipes.size(); i++)
        {
            //currRecipe = i;

            TextView tvRecipe = new TextView(getActivity().getBaseContext());
            final ImageView ivRecipePhoto = new ImageView(getActivity().getBaseContext());

            TableRow tr = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trLP);
            tr.setGravity(Gravity.RIGHT);

            // Handle recipe text
            tvRecipe.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tvRecipe.setMinimumWidth(350);

            String myResipe;
            myResipe = "אופן הכנה:"  + "\n" + myRecipes.get(i).getPreparation() + "\n" + "רכיבים:";
            ArrayList<Grocery> grocery = myRecipes.get(i).getRecipeGroceries();

            for (int j = 0; j < grocery.size(); j++)
            {
                myResipe.concat("\n" + grocery.get(j));
            }


            tvRecipe.setText(myResipe);
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
                        // TODO: id
                        //Queries.getRecipeById(String.valueOf(ivRecipePhoto.getId()));
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

           // Add to layOut
            tr.addView(tvRecipe);
            tr.addView(ivRecipePhoto);
            tbLayout.addView(tr);
        }


        return rootView;
    }

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
}}
