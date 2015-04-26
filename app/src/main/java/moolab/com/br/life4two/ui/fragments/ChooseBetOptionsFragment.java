package moolab.com.br.life4two.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.adapter.BetOptionsAdapter;
import moolab.com.br.life4two.core.BetOptions;
import moolab.com.br.life4two.core.model.BetOption;
import moolab.com.br.life4two.ui.view.DividerItemDecoration;

/**
 * Created by lucas on 29/03/15.
 */
public class ChooseBetOptionsFragment extends Fragment {

    @InjectView(R.id.recycler_bet_options)
    RecyclerView mRecyclerView;

    private Callback mCallback;
    private LinearLayoutManager mLayoutManager;
    private BetOptionsAdapter betOptionsAdapter;

    public ChooseBetOptionsFragment() {
    }

    public interface Callback {
        public void onOptionsChoosed(List<BetOption> optionsSelected);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChooseBetOptionsFragment.Callback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bet_options, container, false);
        ButterKnife.inject(this, rootView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        betOptionsAdapter = new BetOptionsAdapter(getActivity());
        mRecyclerView.setAdapter(betOptionsAdapter);

        BetOptions betOptions = new BetOptions(getActivity());
        betOptions.fetchBetOptions(new BetOptions.BetCallback() {

            @Override
            public void onFetchBetOptions(List<BetOption> data) {
                betOptionsAdapter.setData(data);
                betOptionsAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    @OnClick(R.id.action_next_step)
    public void onNextStep() {
        mCallback.onOptionsChoosed(betOptionsAdapter.getSelecteds());
    }
}
