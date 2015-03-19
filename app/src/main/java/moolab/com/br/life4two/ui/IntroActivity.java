package moolab.com.br.life4two.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.fabric.sdk.android.Fabric;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.parsecloud.ParseConfigMaster;


public class IntroActivity extends ActionBarActivity {

    private static final String TWITTER_KEY = "HFLtQqct9AIkG2WMptq2Yw8fP";
    private static final String TWITTER_SECRET = "rzJyxwDQrfKlWlbaGxLklFgzSJtOWl89vgIoFx5VvdtwjjbfVp";

    @InjectView(R.id.auth_button)
    DigitsAuthButton digitsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_intro);

        ButterKnife.inject(this);

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, final String phoneNumber) {
                ParseAnonymousUtils.logIn(new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {

                        if (parseUser == null) {
                            return;
                        }

                        parseUser.put(ParseConfigMaster.USER_KEY_PHONENUMBER, phoneNumber);
                        parseUser.saveEventually();
                    }
                });
            }

            @Override
            public void failure(DigitsException exception) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
