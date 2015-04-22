package moolab.com.br.life4two.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucas on 29/03/15.
 */
public class InvitedFragment extends Fragment {

    @InjectView(R.id.boo)
    TextView boo;

    private Callback mCallback;


    public interface Callback {
        public void onAcceptInvitation();
        public void onRejectInvitation();
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

    public InvitedFragment() {
    }

    @OnClick(R.id.action_reject)
    public void onReject() {
        mCallback.onRejectInvitation();
    }

    @OnClick(R.id.action_accept)
    public void onAccept() {
        mCallback.onAcceptInvitation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invited, container, false);
        ButterKnife.inject(this, rootView);

        if (getArguments() != null) {
            boo.setText(getArguments().getString(ParseKeysMaster.SENT_BY));
        }

        return rootView;
    }
}
