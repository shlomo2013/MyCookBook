package com.MyCookBook.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.MyCookBook.Entities.Album;
import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.example.mycookbook.mycookbook.Queries;
import com.example.mycookbook.mycookbook.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by nirgadasi on 6/6/15.
 */
public class AddAlbum extends Fragment {

    Button btnAlbumPic;
    Button btnSave;
    ImageView viAlbumPic;
    ListView  lvAllUsers;
    View popupView;
    private PopupWindow pw;
    private ArrayList<User> allUsers;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         popupView = inflater.inflate(R.layout.imagepopup, null);

        View rootView = inflater.inflate(R.layout.add_album,  container , false);
        btnAlbumPic =  (Button)         rootView.findViewById(R.id.btnSelectPhoto);
        btnSave     =  (Button)         rootView.findViewById(R.id.bSaveUser);
        viAlbumPic  =  (ImageView)      rootView.findViewById(R.id.AlbumPic);
        lvAllUsers  =  (ListView)       rootView.findViewById(R.id.AllUsersListView);

        allUsers = Queries.getAllUsers();
        lvAllUsers.setAdapter(new UserAdapter(inflater, allUsers, rootView));

        btnAlbumPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album a = new Album();
               // a.save();
            }
        });
        return rootView;

    }

     //8************************ pic ****************************************
    //8************************ pic ****************************************

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viAlbumPic.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                //                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                //                c.moveToFirst();
                //                int columnIndex = c.getColumnIndex(filePath[0]);
                //                String picturePath = c.getString(columnIndex);
                //                c.close();
                //                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                //                Log.w("path:", picturePath+"");
                //                viewImage.setImageBitmap(thumbnail);
            }
        }
    }
    //8************************ pic ****************************************
    //8************************ pic ****************************************


}


// Create an Adapter Class extending the BaseAdapter
class UserAdapter extends BaseAdapter {
    private PopupWindow pw;
    boolean click = true;

    ImageView iv;
    TextView tvTitle;
    TextView tvDesc;

    private LayoutInflater layoutInflater;
    private View rootView;
    private ArrayList<User> users;

    public UserAdapter(LayoutInflater inflater, ArrayList<User> users,  View rootView)
     {
        this.layoutInflater = inflater;
        this.users = users;
         this.rootView = rootView;
    }

    @Override
    public int getCount() {
// Set the count value to the total number of items in the Array
        return users.size();
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

        // Inflate the item layout and set the views
        View listItem;
        listItem = layoutInflater.inflate(R.layout.all_users_list, null);
        User  u = this.users.get(position);

        // Initialize the views in the layout
         iv = (ImageView) listItem.findViewById(R.id.ProfilePic);
         tvTitle = (TextView) listItem.findViewById(R.id.userName);
         tvDesc = (TextView) listItem.findViewById(R.id.UserId);

        iv.setImageBitmap(u.getProfilePic());

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                                         // Handle show big picture
                                         initiatePopUp(((Recipe)iv.getTag()).getRecipePicture());
                                         pw.showAtLocation(rootView, Gravity.BOTTOM, 10, 10);
                                         click = false;
                                     }
                                     else{
                                         pw.dismiss();
                                         click = true;
                                     }
            }
        });
        tvTitle.setText(u.getName());
        tvDesc.setText(u.getUserId());

        return listItem;
    }
    private void initiatePopUp(Bitmap photo){
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

}