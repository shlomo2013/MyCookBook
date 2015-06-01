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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn);

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
                                try {
                                    Log.v("user parameters set for: ", object.getString("name"));

                                    User s = Queries.getMyUser();
                                    s.setName(object.getString("name"));
                                    s.setEmail(object.getString("email"));
                                    s.setGender(object.getString("gender"));
                                    s.setBirthday(object.getString("birthday"));
                                    s.saveInBackground();
                                    callMainActivityAfterLogin();
                                }catch(Exception e){
                                    Log.v("Shayyoz check= ", "Failed");
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
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
        intent.putExtra("myUserId",Queries.getMyUser().getUserId());
        startActivity(intent);
    }

    private void callMainActivityAfterLogin(){
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("faceUser", accessToken);
        intent.putExtra("myUserId",Queries.getMyUser().getUserId());
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
}
