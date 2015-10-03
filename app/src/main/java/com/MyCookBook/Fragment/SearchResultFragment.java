package com.MyCookBook.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    private PopupWindow pwRecipe;
    boolean clickRecipe = true;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.serch_result, container, false);
        myRecipes = SearchFragment.getResults();

        tbLayout = (TableLayout) rootView.findViewById(R.id.tbResults);
        // myRecipes = SearchFragment

        for (int i = 0; i < myRecipes.size(); i++) {
            final TextView tvUserName = new TextView(getActivity().getBaseContext());
            final ImageView ivUserPhoto = new ImageView(getActivity().getBaseContext());

            // TODO: להוסיף יוזר ותמונה
//            tvUserName.setText(myRecipes.get(i));
//            ivUserPhoto.setMaxWidth(10);
//            ivUserPhoto.setMinimumWidth(10);
//            ivUserPhoto.setMaxHeight(10);
//            ivRecipePhoto.setMinimumHeight(10);

            TableRow trName = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLPName = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            trName.setLayoutParams(trLPName);
            trName.setGravity(Gravity.RIGHT);

            final TextView tvRecipeName = new TextView(getActivity().getBaseContext());

            tvRecipeName.setText(myRecipes.get(i).getName());
            tvRecipeName.setTextSize(18);

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

            String myRecipe;
            myRecipe = "אופן הכנה:" + "\n" + myRecipes.get(i).getPreparation();// + "\n" + "רכיבים:";
//            ArrayList<Grocery> grocery = myRecipes.get(i).getRecipeGroceries();
//
//            for (int j = 0; j < grocery.size(); j++)
//            {
//                myRecipe = myRecipe + "\n" + grocery.get(j).getAmount() + " " +  grocery.get(j).getForm() + " " +  grocery.get(j).getMaterialName();
//            }

            if(myRecipe.length()>70) {
                tvRecipe.setText(myRecipe.substring(0, 70) + "...");
            }else{
                tvRecipe.setText(myRecipe);
            }

            tvRecipe.setWidth(600);

            tvRecipe.setClickable(true);
            tvRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: open recipe window
                    if (clickRecipe) {
                        // Handle show big picture
                        initiatePopUp((Recipe) ivRecipePhoto.getTag());
                        pwRecipe.showAtLocation(rootView, Gravity.BOTTOM, 10, 10);
                        clickRecipe = false;
                    } else {
                        pwRecipe.dismiss();
                        clickRecipe = true;
                    }
                }
            });

            // Handle recipe image
            ivRecipePhoto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            ivRecipePhoto.setMaxWidth(350);
            ivRecipePhoto.setMaxHeight(350);
            ivRecipePhoto.setAdjustViewBounds(true);
            ivRecipePhoto.setImageBitmap(myRecipes.get(i).getRecipePicture());
            ivRecipePhoto.setTag(myRecipes.get(i));
            ivRecipePhoto.setClickable(true);
            ivRecipePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click) {
                        // Handle show big picture
                        initiatePopUp(((Recipe) ivRecipePhoto.getTag()).getRecipePicture());
                        pw.showAtLocation(rootView, Gravity.BOTTOM, 10, 10);
                        click = false;
                    } else {
                        pw.dismiss();
                        click = true;
                    }
                }
            });

//              trName.addView(ivUserPhoto);
//            trName.addView(tvUserName);
            trName.addView(tvRecipeName);
            trName.setBackgroundColor(Color.argb(255, 135, 135, 135));
            tbLayout.addView(trName);

            // Add to layOut
            tr.addView(tvRecipe);
            tr.addView(ivRecipePhoto);
            tr.setBackgroundColor(Color.argb(255, 135, 135, 135));
            tbLayout.addView(tr);

            // Add empty line
            TableRow trEmptyLine = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP2empty = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            trEmptyLine.setLayoutParams(trLP2empty);
            trEmptyLine.setGravity(Gravity.RIGHT);

            final TextView tvEmptyLine = new TextView(getActivity().getBaseContext());
            tvEmptyLine.setTextSize(14);

            trEmptyLine.addView(tvEmptyLine);
                trEmptyLine.setBackgroundColor(Color.argb(255, 100, 100, 100));
            tbLayout.addView(trEmptyLine);
        }


        return rootView;
    }

    private void initiatePopUp(Bitmap photo) {

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
        RecipePhoto.setImageBitmap(photo);

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

    private void initiatePopUp(Recipe currRecipe){

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.recipe_overview, null);

        pwRecipe = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pwRecipe.setBackgroundDrawable(new BitmapDrawable());
        pwRecipe.setFocusable(true);
        popupView.setBackgroundColor(getResources().getColor(R.color.primary_material_dark));
        pwRecipe.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pwRecipe.setOutsideTouchable(true);
        //pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        final TextView RecipeName = (TextView) popupView.findViewById(R.id.tvRcipeName);
        RecipeName.setText(currRecipe.getName());

        final ImageView RecipePhoto = (ImageView) popupView.findViewById(R.id.RecipeImage);
        RecipePhoto.setImageBitmap(currRecipe.getRecipePicture());

        final TextView RecipeIngAmount = (TextView) popupView.findViewById(R.id.tvIngredientAmount);
        final TextView RecipeIngType = (TextView) popupView.findViewById(R.id.tvIngredientType);
        final TextView RecipeIngName = (TextView) popupView.findViewById(R.id.tvIngredientName);
        String myRecipeAmount = "";
        String myRecipeType = "";
        String myRecipeName = "";
        ArrayList<Grocery> grocery = currRecipe.getRecipeGroceries();

        for (int j = 0; j < grocery.size(); j++)
        {
            myRecipeAmount = myRecipeAmount + "\n" + grocery.get(j).getAmount();
            myRecipeType = myRecipeType +"\n" + grocery.get(j).getForm();
            myRecipeName = myRecipeName + "\n" + grocery.get(j).getMaterialName();
        }

        RecipeIngAmount.setText(myRecipeAmount);
        RecipeIngType.setText(myRecipeType);
        RecipeIngName.setText(myRecipeName);

        final TextView HowToMake = (TextView) popupView.findViewById(R.id.tvHowToMake);
        HowToMake.setText(currRecipe.getPreparation());

        final TextView Category = (TextView) popupView.findViewById(R.id.tvCategory);
        Category.setText(currRecipe.getCategory());

        final TextView KitchenType = (TextView) popupView.findViewById(R.id.tvKitchenType);
        KitchenType.setText(currRecipe.getKitchenType());

        final TextView Level = (TextView) popupView.findViewById(R.id.tvLevel);
        Level.setText(currRecipe.getDifficulty());

        final TextView DishType = (TextView) popupView.findViewById(R.id.tvDishType);
        DishType.setText(currRecipe.getDishType());

        if (currRecipe.getDiet())
        {
            final CheckBox cbDiet = (CheckBox) popupView.findViewById(R.id.cbDiet);
            cbDiet.setChecked(true);
        }

        if (currRecipe.getVegan())
        {
            final CheckBox cbVegan = (CheckBox) popupView.findViewById(R.id.cbVegan);
            cbVegan.setChecked(true);
        }

        if (currRecipe.getDiet())
        {
            final CheckBox cbVeg = (CheckBox) popupView.findViewById(R.id.cbVeg);
            cbVeg.setChecked(true);
        }

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pwRecipe.setTouchInterceptor(new View.OnTouchListener() {

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
