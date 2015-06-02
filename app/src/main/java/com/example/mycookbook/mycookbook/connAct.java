package com.example.mycookbook.mycookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.MyCookBook.Entities.*;
import com.MyCookBook.Fragment.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
public class connAct extends Activity {
    CallbackManager callbackManager;
    private AccessToken accessToken;
    String urlString = null;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //callback Manager
        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        if(AccessToken.getCurrentAccessToken()!=null){
            accessToken = AccessToken.getCurrentAccessToken();
            callMainActivity();
        }

        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.v("LoginActivity", response.toString());
                                Queries.isUserAlreadyExists(accessToken.getUserId());
                                User s = Queries.getMyUser();

                                try {
                                    Log.v("user parameters set for: ", object.getString("name"));

                                    s.setName(object.getString("name"));
                                    s.setEmail(object.getString("email"));
                                    s.setGender(object.getString("gender"));
                                    s.setBirthday(object.getString("birthday"));
                                }catch(Exception e){
                                    Log.v("Facebook Params ", "one of the params cannot be retrieved");
                                    s.saveInBackground();
                                }
                                try{
                                    urlString = String.valueOf(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    urlString = urlString.replace("https","http");
                                    bitmap = getBitmapFromURL(urlString);

                                    ParseFile file = new ParseFile("profile.jpeg", bitmapToByteArray(bitmap));
                                    file.saveInBackground();
                                    Queries.getMyUser().put("Profile",file);
                                    Queries.getMyUser().saveInBackground();

                                    ImageView IV= (ImageView)findViewById(R.id.imageView);
                                    IV.setImageBitmap(bitmap);
                                    //Log.d("url ", object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    s.saveInBackground();
                                }catch(Exception e){
                                    Log.v("Facebook Profile picture", "cannot retrieved");
                                }
                                callMainActivityAfterLogin();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture.width(300)");
                request.setParameters(parameters);
                request.executeAsync();
                //callMainActivity();
            }

            @Override
            public void onCancel() {
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });

        loginButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(accessToken.isExpired())
                    LoginManager.getInstance().logInWithReadPermissions(connAct.this, Arrays.asList("public_profile", "user_friends"));
            }
        });
    }


    private void callMainActivity(){
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("faceUser",accessToken);
        Queries.isUserAlreadyExists(accessToken.getUserId());


        ImageView IV= (ImageView)findViewById(R.id.imageView);
        Bitmap btmp = Queries.getProfilePicture();
        if(btmp!=null) {
            IV.setImageBitmap(btmp);
        }else{
            Log.d("Profile picture:","not shown");
        }

        intent.putExtra("myUserId",Queries.getMyUser().getUserId());
        startActivity(intent);
    }

    private void callMainActivityAfterLogin(){
        //ImageView IV= (ImageView)findViewById(R.id.imageView);
        //Drawable drw = ImageOperations(this,urlString,"profile");
        //IV.setBackgroundDrawable(drw);
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("faceUser", accessToken);
        intent.putExtra("myUserId",Queries.getMyUser().getUserId());
        startActivity(intent);
    }

    public Object fetch(String address) throws MalformedURLException,IOException {

        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            Log.d("shay url:",src);

            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    private Drawable ImageOperations(Context ctx, String url, String saveFilename) {

        try {
            InputStream is = (InputStream) this.fetch(url);
            Drawable d = Drawable.createFromStream(is, saveFilename);
            return d;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bmp)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
