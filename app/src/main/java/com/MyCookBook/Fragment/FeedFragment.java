package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    AutoCompleteTextView myAutoComplete;
    TableLayout tbLayout;
    ArrayList<Recipe> myRecipes;
    private PopupWindow pw;
    private  PopupWindow pwRecipe;
    boolean click = true;
    boolean clickRecipe = true;
    private RadioGroup rgpFilter;
    private int  currRecipe;
    private TextView txtLikes;
    private TextWatcher autoCompListenerCategory;
    String category;
    View rootView;
    ScrollView scView;
    RadioButton rb;
    RadioButton rbNew;
    RadioButton rbLoved;
    RadioButton rbTop;
    boolean textChanged = false;

    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_feed_fregment, container, false);
        myAutoComplete = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        category = String.valueOf(myAutoComplete.getText());
        rgpFilter = (RadioGroup) rootView.findViewById(R.id.RBgroup);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.categories,
                android.R.layout.simple_list_item_1);
        myAutoComplete.setAdapter(adapter);

        myAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAutoComplete.setText("");
                textChanged = true;
            }
        });

        autoCompListenerCategory = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                category = String.valueOf(myAutoComplete.getText());

                if (category.equals("")) {
                    if  (textChanged) {
                        rbNew = (RadioButton) rootView.findViewById(R.id.RBnew);
                        rbLoved = (RadioButton) rootView.findViewById(R.id.RBloved);
                        rbTop = (RadioButton) rootView.findViewById(R.id.RBtop5);

                        if (rbNew.isChecked()) {
                            setFilterByRB(rbNew);
                        }
                        if (rbLoved.isChecked()) {
                            setFilterByRB(rbLoved);
                        }
                        if (rbTop.isChecked()) {
                            setFilterByRB(rbTop);
                        }
                    }
                }
                else
                {
                    ArrayList<String> categoryList = new ArrayList<String>(1);
                    categoryList.add(category);
                    ArrayList<Recipe> myRecipesTest = Queries.RecipesSearchPartial(categoryList, null, null, null, null, null, null);
                    myRecipes = myRecipesTest;
                    setFeed();
                }
            }
        };

        myAutoComplete.addTextChangedListener(autoCompListenerCategory);
        scView = (ScrollView) rootView.findViewById(R.id.ScrollView);

        rgpFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    setFilterByRB(rb);
                }
            }
        });

        // Set first display recipes
        myRecipes = Queries.getLastRecipes(15);
        tbLayout = (TableLayout) rootView.findViewById(R.id.tbFeed);
        setFeed();

        return rootView;
    }

    View.OnClickListener photoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tbLayout = (TableLayout) v.findViewById(R.id.tbFeed);
        }
    };

    private void initiatePopUp(Bitmap photo){

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

    private void setFeed()
    {
        int count = tbLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tbLayout.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        for (int i = 0; i < myRecipes.size(); i++)
        {
            final TextView tvRecipe = new TextView(getActivity().getBaseContext());
            final ImageView ivRecipePhoto = new ImageView(getActivity().getBaseContext());
            final ImageButton btLike = new ImageButton(getActivity().getBaseContext());
            txtLikes = new TextView(getActivity().getBaseContext());

            final TextView tvUserName = new TextView(getActivity().getBaseContext());
            final ImageView ivUserPhoto = new ImageView(getActivity().getBaseContext());

            TableRow trUser = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLPuser = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            trUser.setLayoutParams(trLPuser);
            trUser.setGravity(Gravity.RIGHT);

            tvUserName.setText(myRecipes.get(i).getCreatedBy().getName());
            tvUserName.setTextSize(15);
            tvUserName.setWidth(300);
            tvUserName.setGravity(Gravity.RIGHT);
            ivUserPhoto.setImageBitmap(myRecipes.get(i).getCreatedBy().getProfilePic());
            ivUserPhoto.setMaxWidth(110);
            ivUserPhoto.setMinimumWidth(110);
            ivUserPhoto.setMaxHeight(110);
            ivUserPhoto.setMinimumHeight(110);
            ivUserPhoto.setAdjustViewBounds(true);

            TableRow trName = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLPName = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            trName.setLayoutParams(trLPName);
            trName.setGravity(Gravity.RIGHT);

            final TextView tvRecipeName = new TextView(getActivity().getBaseContext());

            tvRecipeName.setText(myRecipes.get(i).getName());
            tvRecipeName.setTextSize(18);

            TableRow tr = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(trLP);
            tr.setGravity(Gravity.RIGHT);

            // Handle recipe text
            tvRecipe.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tvRecipe.setMinimumWidth(350);
            tvRecipe.setTag(myRecipes.get(i));

            String myRecipe;
            myRecipe = "אופן הכנה:" + "\n" + myRecipes.get(i).getPreparation();// + "\n" + "רכיבים:";
//            ArrayList<Grocery> grocery = myRecipes.get(i).getRecipeGroceries();
//
//            for (int j = 0; j < grocery.size(); j++)
//            {
//                myRecipe = myRecipe + "\n" + grocery.get(j).getAmount() + " " +  grocery.get(j).getForm() + " " +  grocery.get(j).getMaterialName();
//            }

            tvRecipe.setText(myRecipe.substring(0, 70) + "...");
            tvRecipe.setWidth(600);

            tvRecipe.setClickable(true);
            tvRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickRecipe) {
                        // Handle show big picture
                        initiatePopUp((Recipe)ivRecipePhoto.getTag());
                        pwRecipe.showAtLocation(rootView, Gravity.BOTTOM, 10, 10);
                        clickRecipe = false;
                    }
                    else{
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
                        initiatePopUp(((Recipe)ivRecipePhoto.getTag()).getRecipePicture());
                        pw.showAtLocation(rootView, Gravity.BOTTOM, 10, 10);
                        click = false;
                    }
                    else{
                        pw.dismiss();
                        click = true;
                    }
                }
            });

            // Handle like button
            btLike.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            btLike.setImageResource(R.mipmap.red_like_icon);
            btLike.setClickable(true);
            btLike.setTag(myRecipes.get(i));
            btLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Recipe)btLike.getTag()).Like();
                    txtLikes.setText(((Recipe)btLike.getTag()).getLikesCounter() + "אהבו!");
                    setFeed();
                }
            });

            trUser.addView(tvUserName);
            trUser.addView(ivUserPhoto);
            trUser.setBackgroundColor(Color.argb(255, 115, 115, 115));
            tbLayout.addView(trUser);

            trName.addView(tvRecipeName);
            trName.setBackgroundColor(Color.argb(255, 135, 135, 135));
            tbLayout.addView(trName);

            // Add to layOut
            tr.addView(tvRecipe);
            tr.addView(ivRecipePhoto);
            tr.setBackgroundColor(Color.argb(255, 135, 135, 135));

            tbLayout.addView(tr);

            TableRow tr2 = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP2 = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tr2.setLayoutParams(trLP2);
            tr2.setGravity(Gravity.RIGHT);

            txtLikes.setText(myRecipes.get(i).getLikesCounter() + "אהבו!");

            // Add to layOut
            tr2.addView(txtLikes);
            tr2.addView(btLike);
            tr2.setBackgroundColor(Color.argb(255, 135, 135, 135));
            tbLayout.addView(tr2);

            // Add empty line
            TableRow trEmptyLine = new TableRow(getActivity().getBaseContext());
            TableRow.LayoutParams trLP2empty = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT);
            trEmptyLine.setLayoutParams(trLP2empty);
            trEmptyLine.setGravity(Gravity.RIGHT);

            final TextView tvEmptyLine = new TextView(getActivity().getBaseContext());
            tvEmptyLine.setTextSize(14);

            trEmptyLine.addView(tvEmptyLine);
            tbLayout.addView(trEmptyLine);
        }
    }

    private void setFilterByRB(RadioButton rb) {
        switch (rb.getId()) {
            case R.id.RBnew:
                myRecipes = Queries.getLastRecipes(15);
                setFeed();
                break;
            case R.id.RBloved:
                myRecipes = Queries.getTopRatedRecipes(15);
                setFeed();
                break;
            case R.id.RBtop5:
                myRecipes = Queries.getTopRatedRecipes(5);
                setFeed();
                break;
        }
    }
}