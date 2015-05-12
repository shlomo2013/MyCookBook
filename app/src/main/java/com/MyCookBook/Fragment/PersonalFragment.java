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
    Button btnDDPersonalCategories;
    LinearLayout llPopup;
    RelativeLayout rlpersonalfrag;
    ListView lvDropDownList;

    View  rootView    ;
    View  popUpView   ;
    View  dropDownView;

    ArrayList<CheckBox> alFoodCategory;
    DropDownListAdapter mAdapter;

    public PersonalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView     = inflater.inflate(R.layout.activity_personal_fregment, container, false);
        popUpView    = inflater.inflate(R.layout.pop_up_window, container, false);
        dropDownView = inflater.inflate(R.layout.drop_down_list_row, container, false);

        llPopup                  = (LinearLayout)       rootView.findViewById(R.id.DropDownList);
        rlpersonalfrag           = (RelativeLayout)     rootView.findViewById(R.id.personalfragRelativelayout);
        btnDDPersonalCategories  = (Button)             rootView.findViewById(R.id.btnDropDownPersonalCategories);
        lvDropDownList           = (ListView)           popUpView.findViewById(R.id.lvDropDownList);

        //onClickListener to initiate the dropDown list
        btnDDPersonalCategories.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                initCategories();
                initiatePopUp(alFoodCategory);
            }
        });
        return rootView;
    }


    public void initCategories(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.personal_pref_array,
                android.R.layout.simple_list_item_1);

        alFoodCategory = new ArrayList<CheckBox>();
        for (int i = 0; i < adapter.getCount(); i++){
            String s = (String)adapter.getItem(i);
            CheckBox c = new CheckBox(getActivity().getBaseContext());
            c.setText(s);

            if(i%2 == 0) {
                c.setChecked(true);
            }else
            {
                c.setChecked(false);
            }
            alFoodCategory.add(c);

            mAdapter = new DropDownListAdapter(alFoodCategory, getActivity().getBaseContext());
            lvDropDownList.setAdapter(mAdapter);

        }
    }

    private void initiatePopUp(ArrayList<CheckBox> items){

        pw = new PopupWindow(popUpView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);

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

        //provide the source layout for drop-down
        pw.setContentView(popUpView);

        //anchor the drop-down to bottom-left corner
        pw.showAsDropDown(rlpersonalfrag);

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
}



