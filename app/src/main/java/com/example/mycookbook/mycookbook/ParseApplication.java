package com.example.mycookbook.mycookbook;

import android.app.Application;
import android.util.Log;

import com.MyCookBook.Entities.Recipe;
import com.MyCookBook.Entities.User;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by shirabd on 10/05/2015.
 */

    public class ParseApplication extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            ParseObject.registerSubclass(User.class);
            ParseObject.registerSubclass(Recipe.class);

            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "lqJzh3MlHyHEqhnljVS7miJgRt4ORJkCujfnwrj6", "9zUYNNU9yi4rm4SaeNInke1gCYY8CDVI36Cu3Nd3");
            Log.e("Parse Message:", "initialize");
        }
    }
