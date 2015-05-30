package com.MyCookBook.CategoriesUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.mycookbook.mycookbook.R;

import java.util.List;

public class DropDownListAdapter extends  ArrayAdapter<CheckBox>   {

    List<CheckBox> lFoodCategory = null;
    Context context;
    ModelHolder holder;

    public DropDownListAdapter( List<CheckBox>  lFoodCategory, Context context) {
        super(context, R.layout.drop_down_list_row ,lFoodCategory);
        this.context = context;
        this.lFoodCategory = lFoodCategory;
    }

    private  static class ModelHolder{
        public CheckBox cbhCategory;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        holder = new ModelHolder();

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.drop_down_list_row, null);

            holder = new ModelHolder();
            holder.cbhCategory = (CheckBox) v.findViewById(R.id.cbCheckbox);
            holder.cbhCategory.setTag(holder);

            v.setTag(holder);

        } else {
            holder = (ModelHolder) v.getTag();
        }

        holder.cbhCategory.setText(lFoodCategory.get(position).getText());

        //whenever the checkbox is clicked the selected values textview is updated with new selected values
        holder.cbhCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                holder.cbhCategory.setText(lFoodCategory.get(position).getText());
            }
        });

        if (lFoodCategory.get(position).isChecked())
            holder.cbhCategory.setChecked(true);
        else
            holder.cbhCategory.setChecked(false);
        return v;
    }

}


