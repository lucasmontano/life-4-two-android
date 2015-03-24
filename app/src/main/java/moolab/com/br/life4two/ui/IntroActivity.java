package moolab.com.br.life4two.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.parsecloud.ParseConfigMaster;


public class IntroActivity extends ActionBarActivity {

    private static final String TWITTER_KEY = "HFLtQqct9AIkG2WMptq2Yw8fP";
    private static final String TWITTER_SECRET = "rzJyxwDQrfKlWlbaGxLklFgzSJtOWl89vgIoFx5VvdtwjjbfVp";

    @InjectView(R.id.logo)
    ImageView logo;

    @InjectView(R.id.bg)
    ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_intro);

        ButterKnife.inject(this);

        Picasso.with(this).load(R.drawable.fundo).into(bg);
        Picasso.with(this).load(R.drawable.logo).into(logo);
    }

    @OnClick(R.id.btn_start)
    public void onAuthNumber() {
        AuthCallback callback = new AuthCallback() {
            @Override
            public void success(final DigitsSession session, final String phoneNumber) {

                ParseUser.logInInBackground(String.valueOf(session.getId()), phoneNumber, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (e != null) {
                            signupParseUser(phoneNumber, session.getId());
                            return;
                        }
                    }
                });
            }

            @Override
            public void failure(DigitsException exception) {

            }
        };
        Digits.authenticate(callback, R.style.CustomDigitsTheme);
    }

    private void signupParseUser(String phoneNumber, long id) {
        ParseUser user = new ParseUser();
        user.setUsername(String.valueOf(id));
        user.setPassword(phoneNumber);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    return;
                }

                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
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
