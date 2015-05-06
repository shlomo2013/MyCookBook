package com.MyCookBook.Fragment;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
import android.widget.TextView;

import com.example.mycookbook.mycookbook.R;

import java.util.ArrayList;

/**
 * Created by nirgadasi on 4/29/15.
 */
public class PersonalFragment extends Fragment {

    private PopupWindow pw;
    TableLayout tbLayout;
    Button btnDDPersonalCategories;
    Button btnOpenPopup;
    LinearLayout llPopup;
    RelativeLayout tlpersonalfrag;
    ViewGroup vgl;
    View popUpView;
    private boolean expanded; 		//to  store information whether the selected values are displayed completely or in shortened representatn
   	public static boolean[] checkSelected;	// store select/unselect information about the values in the list

    public PersonalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View  rootView     = inflater.inflate(R.layout.activity_personal_fregment, container, false);
                    popUpView    = inflater.inflate(R.layout.pop_up_window, container, false);
        final View  dropDownView = inflater.inflate(R.layout.drop_down_list_row, container, false);

        tbLayout                 = (TableLayout) rootView.findViewById(R.id.tbPersonalLayout);
        llPopup                  = (LinearLayout) rootView.findViewById(R.id.DropDownList);
        tlpersonalfrag           = (RelativeLayout) rootView.findViewById(R.id.personalfragRelativelayout);
        btnDDPersonalCategories  = (Button) rootView.findViewById(R.id.btnDropDownPersonalCategories);

//********************************************************************************************
        final ArrayList<String> items = new ArrayList<String>();
           	items.add("Item 1");
           	items.add("Item 2");
           	items.add("Item 3");
           	items.add("Item 4");
           	items.add("Item 5");

        checkSelected = new boolean[items.size()];
        //initialize all values of list to 'unselected' initially
        for (int i = 0; i < checkSelected.length; i++) {
            checkSelected[i] = false;
        }

        /*SelectBox is the TextView where the selected values will be displayed in the form of "Item 1 & 'n' more".
         * When this selectBox is clicked it will display all the selected values
         * and when clicked again it will display in shortened representation as before.
         * */
        final TextView tv = (TextView) dropDownView.findViewById(R.id.tvSelectOption);
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!expanded){
                    //display all selected values
                String selected = "";
                int flag = 0;
                for (int i = 0; i < items.size(); i++) {
                    if (checkSelected[i] == true) {
                         selected += items.get(i);
                         selected += ", ";
                        flag = 1;
                    }
                }
                if(flag==1)
                tv.setText(selected);
                expanded =true;
                }
                else{
                    //display shortened representation of selected values
                    tv.setText(DropDownListAdapter.getSelected());
                    expanded = false;
                }
            }
        });

          //onClickListener to initiate the dropDown list
        btnDDPersonalCategories.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				initiatePopUp(items,tv);
			}
		});



//********************************************************************************************

//        btnDDPersonalCategories.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                final PopupWindow popupWindow = new PopupWindow(popUpView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
//
//             //background cannot be null if we want the touch event to be active outside the pop-up window
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setTouchable(true);
//
//                //inform pop-up the touch event outside its window
//                popupWindow.setOutsideTouchable(true);
//                //the pop-up will be dismissed if touch event occurs anywhere outside its window
//                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                public boolean onTouch(View v, MotionEvent event) {
//
//                    // TODO Auto-generated method stub
//                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                        popupWindow.dismiss();
//                    return true;
//                    }
//
//                    return false;
//                    }
//                    });
//
//                //Set the content of pop-up window as layout and anchor the pop-up to the bottom-left corner of the desired view.
//                popupWindow.setContentView(rootView);
//
//                RelativeLayout rlPersonalFrag = (RelativeLayout)rootView.findViewById(R.id.personalfragRelativelayout);
//                popupWindow.showAsDropDown(rlPersonalFrag);
//
//                //and finally set the drop-down listview with the data source items:
//                final ListView lvDropDownList = (ListView) rootView.findViewById(R.id.lvDropDownList);
//
//                // Create an ArrayAdapter using the string array and a default spinner layout
//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
//                                                                             R.array.personal_pref_array,
//                                                                             android.R.layout.activity_list_item);
//
//                //’items’ is the values’ list and ‘tv’ is the textview where the selected values are displayed
//                lvDropDownList.setAdapter(adapter);
//
////                *******  SAVE ********
////                Button btnSaveCategory = (Button) llPopup.findViewById(R.id.btnSaveCategory);
////                btnSaveCategory.setOnClickListener(new Button.OnClickListener() {
////
////                    @Override
////                    public void onClick(View v) {
////                        // TODO Auto-generated method stub
////                            //savePrefCategories();
////                    }
////                });
//
//                popupWindow.showAsDropDown(btnOpenPopup, 50, -30);
//
//            }
//        });
//
////        for (int i = 0; i < checkSelected.length; i++) {
////            checkSelected[i] = false;
////
////            TableRow trtitle = createTbRow();
////            TextView tvCategoryTitle = new TextView(getActivity().getBaseContext());
////            tvCategoryTitle.setText("הקטגוריות המועדפות שלי");
////            tvCategoryTitle.setTextDirection(View.TEXT_DIRECTION_RTL);
////            trtitle.addView(tvCategoryTitle);
////            tbLayout.addView(trtitle);
////            creatCheckBoxOnLayout(R.array.personal_pref_array);
////
////            trtitle = createTbRow();
////            TextView tvNoCategoryTitle = new TextView(getActivity().getBaseContext());
////            tvNoCategoryTitle.setText("ממרכיבים שאין להציג ");
////            tvNoCategoryTitle.setTextDirection(View.TEXT_DIRECTION_RTL);
////            trtitle.addView(tvNoCategoryTitle);
////            tbLayout.addView(trtitle);
////            creatCheckBoxOnLayout(R.array.personal_no_pref_array);
////        }
            return rootView;

    }


    public void creatCheckBoxOnLayout(@ArrayRes int ArrayName){

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
        tr.setTextDirection(View.LAYOUT_DIRECTION_RTL);
        return tr;
    }


    /*
     * Function to set up the pop-up window which acts as drop-down list
     * */
    private void initiatePopUp(ArrayList<String> items, TextView tv){

    	//get the view to which drop-down layout is to be anchored
    	pw = new PopupWindow(popUpView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);

    	//Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
    	pw.setBackgroundDrawable(new BitmapDrawable());
    	pw.setTouchable(true);

    	//let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
    	pw.setOutsideTouchable(true);
    	pw.setHeight(LayoutParams.WRAP_CONTENT);

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

    	//provide the source layout for drop-down
    	pw.setContentView(popUpView);

    	//anchor the drop-down to bottom-left corner of 'layout1'
    	pw.showAsDropDown(tlpersonalfrag);

    	//populate the drop-down list
    	final ListView list = (ListView) popUpView.findViewById(R.id.lvDropDownList);
        DropDownListAdapter adapter = new DropDownListAdapter(getActivity().getBaseContext(), items, tv);
    	list.setAdapter(adapter);
    }



}



