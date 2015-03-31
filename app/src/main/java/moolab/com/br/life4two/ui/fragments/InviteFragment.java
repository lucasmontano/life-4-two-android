package moolab.com.br.life4two.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucas on 29/03/15.
 */
public class InviteFragment extends Fragment {

    @InjectView(R.id.boo_phone)
    EditText mBooPhone;

    public InviteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invite, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.action_invite)
    public void onInvite() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put(ParseKeysMaster.BOO_PHONE, mBooPhone.getText().toString());
        currentUser.saveInBackground();
    }
}
