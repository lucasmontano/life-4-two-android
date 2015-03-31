package moolab.com.br.life4two.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import moolab.com.br.life4two.R;

/**
 * Created by lucas on 29/03/15.
 */
public class ReadyFragment extends Fragment {

    public ReadyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ready, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}
