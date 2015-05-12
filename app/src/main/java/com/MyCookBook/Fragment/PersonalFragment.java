package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class PersonalFragment extends Fragment {

    private PopupWindow pw;
    TableLayout tbLayout;
    LinearLayout llPopup;
    RelativeLayout rlpersonalfrag;
    ListView lvDropDownList;
    View popupView;

    View  rootView    ;
    View  popUpView   ;
    View  dropDownView;

    ArrayList<CheckBox> alFoodCategory;
    ArrayList<CheckBox> alNoFoodCategory;
    DropDownListAdapter mAdapter;

    public PersonalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView     = inflater.inflate(R.layout.activity_personal_fregment, container, false);
        dropDownView = inflater.inflate(R.layout.drop_down_list_row, container, false);
        popupView    = inflater.inflate(R.layout.popup, null);

        llPopup                  = (LinearLayout)       rootView.findViewById(R.id.DropDownList);
        rlpersonalfrag           = (RelativeLayout)     rootView.findViewById(R.id.personalfragRelativelayout);
        lvDropDownList           = (ListView)           popupView.findViewById(R.id.lvDropDownList);

        handleCategories();
        handleNoCategories();
        return rootView;
    }


    public void initCategories(ListView lvListView, ArrayList<CheckBox> items, int ArrayName){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                ArrayName,
                android.R.layout.simple_list_item_1);

        items = new ArrayList<CheckBox>();
        for (int i = 0; i < adapter.getCount(); i++){
            String s = (String)adapter.getItem(i);
            CheckBox c = new CheckBox(getActivity().getBaseContext());
            c.setText(s);
//
//            if(i%2 == 0) {
//                c.setChecked(true);
//            }else
//            {
//                c.setChecked(false);
//            }
            items.add(c);

        }

        mAdapter = new DropDownListAdapter(items, getActivity().getBaseContext());
        lvListView.setAdapter(mAdapter);

    }

    private void initiatePopUp(){

        pw = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        pw.setHeight(LayoutParams.WRAP_CONTENT);

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new OnTouchListener() {

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

    public void createCheckBoxOnLayout(@ArrayRes int ArrayName){

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                ArrayName,
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
        //   tr.setTextDirection(View.LAYOUT_DIRECTION_RTL);
        return tr;
    }

    public void handleNoCategories(){
        final Button btnDDNoPersonalCategories = (Button)rootView.findViewById(R.id.btnDropDownPersonalCategoriesNOT);
        btnDDNoPersonalCategories.setOnClickListener(new Button.OnClickListener(){

                 @Override
                 public void onClick(View arg0) {
                 //    ListView  lvDropDownListNot = (ListView)         rootView.findViewById(R.id.lvDropDownListNot);

                     // get all of the categories
                     initCategories(lvDropDownList , alNoFoodCategory, R.array.personal_no_pref_array);

                     // set the pop up window
                     initiatePopUp();

                     // dismiss
                     Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                     btnDismiss.setOnClickListener(new Button.OnClickListener(){
                         @Override
                         public void onClick(View v) {
                             // TODO Auto-generated method stub
                             pw.dismiss();
                         }});

                     pw.showAsDropDown(btnDDNoPersonalCategories);

                 }});
    }

    public void handleCategories(){
        final Button btnDDPersonalCategories = (Button)rootView.findViewById(R.id.btnDropDownPersonalCategories);
        btnDDPersonalCategories.setOnClickListener(new Button.OnClickListener(){

                 @Override
                 public void onClick(View arg0) {

                     // get all of the categories
                     initCategories(lvDropDownList , alFoodCategory, R.array.personal_pref_array);

                     // set the pop up window
                     initiatePopUp();

                     // dismiss
                     Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                     btnDismiss.setOnClickListener(new Button.OnClickListener(){
                         @Override
                         public void onClick(View v) {
                             // TODO Auto-generated method stub
                             pw.dismiss();
                         }});

                     pw.showAsDropDown(btnDDPersonalCategories);

                 }});


    }
}



