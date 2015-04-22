package moolab.com.br.life4two.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nispok.snackbar.Snackbar;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import moolab.com.br.life4two.R;
import moolab.com.br.life4two.core.Cupid;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;
import moolab.com.br.life4two.ui.fragments.InviteFragment;
import moolab.com.br.life4two.ui.fragments.InvitedFragment;
import moolab.com.br.life4two.ui.fragments.ReadyFragment;
import moolab.com.br.life4two.ui.fragments.WaitingFragment;

public class MainActivity extends ActionBarActivity implements InviteFragment.Callback, InvitedFragment.Callback {

    private Cupid cupid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            loadStatus();
        }
    }

    private void loadStatus() {
        cupid = new Cupid(this);
        cupid.getStatus(ParseUser.getCurrentUser(), new Cupid.CupidStatus() {
            @Override
            public void isInvited() {
                navToInvited();
            }

            @Override
            public void isEmpty() {
                navToInvite();
            }

            @Override
            public void isWaiting() {
                navToWaiting();
            }

            @Override
            public void isReady() {
                navToReady();
            }
        });
    }

    private void navToWaiting() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new WaitingFragment()).commit();
    }

    private void navToInvited() {

        Bundle args = new Bundle();
        args.putString(ParseKeysMaster.SENT_BY, cupid.getInvitedBy().getUsername());

        InvitedFragment fragment = new InvitedFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void navToReady() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReadyFragment()).commit();
    }

    private void navToInvite() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new InviteFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInviteSent() {
        loadStatus();
    }

    @Override
    public void onAcceptInvitation() {
        cupid.saveStatus(Cupid.ACCEPTED, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) return;
                Snackbar.with(getApplicationContext()).text(getString(R.string.msg_accept_done)).show(MainActivity.this);
                loadStatus();
            }
        });
    }

    @Override
    public void onRejectInvitation() {
        cupid.saveStatus(Cupid.REJECTED, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) return;
                Snackbar.with(getApplicationContext()).text(getString(R.string.msg_reject_done)).show(MainActivity.this);
                loadStatus();
            }
        });
    }
}
