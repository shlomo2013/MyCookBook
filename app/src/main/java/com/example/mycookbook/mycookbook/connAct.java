package com.example.mycookbook.mycookbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class connAct extends Activity {

    CallbackManager callbackManager;
    private AccessToken accessToken;
    User myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitParse();
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn);



        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("UserId", "10206754341046177");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    myUser = (User)userList.get(0);
                    Log.d("User", "Retrieved " + d.getObjectId() + " scores");
                    Log.d("User", "Retrieved " + userList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });*/




/*
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("User");
        query.getInBackground("9d88sIXvy8", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String d = ((User)object).getUserId();
                    Recipe r = new Recipe();
                    r.put("name",d);
                    r.put("Type","bishul");
                    r.saveInBackground();
                    ((User)object).addRecipe(r);
                    // object will be your game score
                } else {
                }
            }
        });
*/
        /*User s = new User();
        s.setUserId("Shay");
        s.saveInBackground();
*/






        //ParseQuery<ParseObject> query = s.recipesRel.getQuery();





        //callback Manager
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                accessToken = loginResult.getAccessToken();

                myUser = new User();
                myUser.setUserId(accessToken.getUserId());
                myUser.saveInBackground();
                Log.d("create User ", "after");

                Recipe r = new Recipe();
                r.put("name","tbeha");
                r.put("Type","bishul");
                r.saveInBackground();
                r.addRecipe(myUser);

                myUser.saveInBackground();

/*
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
                // Retrieve the most recent ones
                query.orderByDescending("createdAt");
                // Only retrieve the last ten
                //query.setLimit(10);
                // Include the post data with each comment
                query.include("User");*/
                myUser.findMyRecipies(myUser).findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> recipiesList, ParseException e) {
                        // commentList now contains the last ten comments, and the "post"
                        // field has been populated. For example:
                        for (ParseObject recipe : recipiesList) {
                            // This does not require a network access.
                            ParseObject recipe2 = recipe.getParseObject("Recipe");
                            Log.d("post", "retrieved a related post");
                        }
                    }
                });



                Log.d("FB", "access token got.");

                callMainActivity();
                Log.d("FB", "access token got.");

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        loginButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(connAct.this, Arrays.asList("public_profile", "user_friends"));
            }
        });
    }


    private void callMainActivity(){
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("faceUser",accessToken);
        intent.putExtra("myUserId",myUser.getUserId());
        startActivity(intent);
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

    private void InitParse(){


    }
}
