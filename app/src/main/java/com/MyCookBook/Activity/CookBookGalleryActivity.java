package com.MyCookBook.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mycookbook.mycookbook.R;

public class CookBookGalleryActivity extends ActionBarActivity {


    public int thumb[] = { R.mipmap.cheese_cake, R.mipmap.chips,
            R.mipmap.konus_pizza , R.mipmap.yami,
            R.mipmap.lazania ,R.mipmap.oreo ,
            R.mipmap.pankaiyk ,R.mipmap.pay_coco ,
            R.mipmap.lazania2 ,R.mipmap.peanut_butter ,
            R.mipmap.pizza , R.mipmap.lazania3, };

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book_gallery);

        Gallery g = (Gallery)findViewById(R.id.gallery);
        // Create adapter gallery
        g.setAdapter( new ImageAdapter(this));
        iv = (ImageView)findViewById(R.id.tempImageView);
        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "מתכון:" , Toast.LENGTH_SHORT);
                iv.setImageResource(thumb[position]);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cook_book_gallery, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;
        int ImageBackRound;

        public ImageAdapter(Context c){
            this.context = c;
        }

        @Override
        public int getCount() {
            return thumb.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image = new ImageView(context);
            image.setImageResource(thumb[position]);
            return image;
        }
    }
}
