package moolab.com.br.life4two.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import moolab.com.br.life4two.R;

/**
 * Created by lucas on 29/03/15.
 */
public class InvitedFragment extends Fragment {

    @InjectView(R.id.boo)
    TextView boo;

    public InvitedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invited, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}
