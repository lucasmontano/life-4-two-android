package moolab.com.br.life4two.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import moolab.com.br.life4two.R;
import moolab.com.br.life4two.core.Cupid;
import moolab.com.br.life4two.ui.fragments.InviteFragment;
import moolab.com.br.life4two.ui.fragments.InvitedFragment;
import moolab.com.br.life4two.ui.fragments.ReadyFragment;
import moolab.com.br.life4two.ui.fragments.WaitingFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            Cupid cupid = new Cupid(this);
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
    }

    private void navToWaiting() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new WaitingFragment()).commit();
    }

    private void navToInvited() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new InvitedFragment()).commit();
    }

    private void navToReady() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new ReadyFragment()).commit();
    }

    private void navToInvite() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new InviteFragment()).commit();
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
}
