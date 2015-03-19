package moolab.com.br.life4two;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

import moolab.com.br.life4two.parsecloud.ParseConfig;

/**
 * Created by lucas on 18/03/15.
 */
public class App extends Application {

    public void onCreate() {
        super.onCreate();

        ParseCrashReporting.enable(this);
        Parse.initialize(this, ParseConfig.APP_ID, ParseConfig.CLIENT_KEY);
    }
}
