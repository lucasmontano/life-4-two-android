package moolab.com.br.life4two.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import moolab.com.br.life4two.R;

/**
 * Created by lucas on 29/03/15.
 */
public class ChooseRewardFragment extends Fragment {

    private Callback mCallback;

    @InjectView(R.id.reward)
    public EditText mReward;

    public ChooseRewardFragment() {
    }

    public interface Callback {
        public void onChooseReward(String reward);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChooseRewardFragment.Callback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @OnClick(R.id.action_start)
    public void onActionStart() {
        mCallback.onChooseReward(mReward.getText().toString());
    }
}
