package moolab.com.br.life4two.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import moolab.com.br.life4two.R;
import moolab.com.br.life4two.parsecloud.ParseKeysMaster;

/**
 * Created by lucasmontano on 21/04/15.
 */
public class BetOptionsAdapter extends RecyclerView.Adapter<BetOptionsAdapter.ViewHolder> {

    private List<ParseObject> data = new ArrayList<ParseObject>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.bet_name)
        public TextView mName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    public BetOptionsAdapter() {

    }

    public void setData(List<ParseObject> data) {
        this.data = data;
    }

    @Override
    public BetOptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bet_option, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText(data.get(position).getString(ParseKeysMaster.NAME));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
