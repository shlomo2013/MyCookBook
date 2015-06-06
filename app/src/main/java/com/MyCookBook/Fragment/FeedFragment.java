package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.MyCookBook.Entities.Grocery;
import com.MyCookBook.Entities.Recipe;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FeedFragment extends Fragment {

    AutoCompleteTextView myAutoComplete;
    TableLayout tbLayout;
    ArrayList<Recipe> myRecipes;
    private PopupWindow pw;
    boolean click = true;
    private RadioGroup rgpFilter;
    private int  currRecipe;
    private TextView txtLikes;
    private TextWatcher autoCompListenerCategory;
    String category;
    View rootView;
    ScrollView scView;

    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_feed_fregment, container, false);
        myAutoComplete = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        category = String.valueOf(myAutoComplete.getText());

        autoCompListenerCategory = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String[] categories = new String[R.layout.activity_feed_fregment];
                for(int i = 0; i < categories.length; i++)
                {
                    if (categories[i].equals(category)) {
                        ArrayList<String> categoryList = new ArrayList<String>(1);
                        categoryList.add(category);
                        myRecipes = Queries.RecipesSearchPartial(categoryList, null, null, null, null, null, null);
                    }
                }
            }
        };

        myAutoComplete.addTextChangedListener(autoCompListenerCategory);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.categories,
                android.R.layout.simple_list_item_1);
        myAutoComplete.setAdapter(adapter);

        rgpFilter = (RadioGroup) rootView.findViewById(R.id.RBgroup);
        scView = (ScrollView) rootView.findViewById(R.id.ScrollView);

        rgpFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
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
        });

        // Set first display recipes
        myRecipes = Queries.getLastRecipes(15);
        tbLayout = (TableLayout) rootView.findViewById(R.id.tbFeed);
        setFeed();

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

    private void setFeed()
    {

        int count = tbLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tbLayout.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        for (int i = 0; i < myRecipes.size(); i++)
        {
            currRecipe = i;

            TextView tvRecipe = new TextView(getActivity().getBaseContext());
            final ImageView ivRecipePhoto = new ImageView(getActivity().getBaseContext());
//            ImageButton btRecipePhoto = new ImageButton(getActivity().getBaseContext());
            ImageButton btLike = new ImageButton(getActivity().getBaseContext());
            txtLikes = new TextView(getActivity().getBaseContext());

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
                    myRecipes.get(currRecipe).Like();
                    txtLikes.setText(myRecipes.get(currRecipe).getLikesCounter() + "אהבו!");
                }
            });

            // Add to layOut
            tr.addView(tvRecipe);
            tr.addView(ivRecipePhoto);

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

            txtLikes.setText(myRecipes.get(i).getLikesCounter() + "אהבו!");

            // Add to layOut
            tr2.addView(txtLikes);
            tr2.addView(btLike);
            tbLayout.addView(tr2);
        }
    }

}