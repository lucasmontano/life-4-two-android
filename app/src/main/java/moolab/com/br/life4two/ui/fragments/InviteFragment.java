package moolab.com.br.life4two.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.core.Cupid;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucas on 29/03/15.
 */
public class InviteFragment extends Fragment {

    @InjectView(R.id.boo_phone)
    EditText mBooPhone;

    private Callback mCallback;

    public InviteFragment() {
    }

    public interface Callback {
        public void onInviteSent();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement InviteFragment.Callback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invite, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.action_invite)
    public void onInvite() {
        ParseObject invite = new ParseObject(ParseKeysMaster.OBJECT_INVITE);
        invite.put(ParseKeysMaster.SENT_TO_PHONE, mBooPhone.getText().toString());
        invite.put(ParseKeysMaster.SENT_BY, ParseUser.getCurrentUser());
        invite.put(ParseKeysMaster.STATUS, Cupid.WAITING);
        invite.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    return;
                }
                mCallback.onInviteSent();
                Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.msg_invite_done)).show(getActivity());
            }
        });
    }
}
